package graduationProject.IMUISystem.eventExecutor.respond;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import graduationProject.IMUISystem.eventExecutor.dto.MenuConfigDto;
import graduationProject.IMUISystem.eventExecutor.dto.NotifyConfigDto;
import graduationProject.IMUISystem.eventExecutor.entity.*;
import graduationProject.IMUISystem.eventExecutor.rabbitMQ.MQEventPublisher;
import graduationProject.IMUISystem.eventExecutor.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RespondServiceImpl implements RespondService{
    private final MQEventPublisher mqEventPublisher;
    private final RestRequestService restRequestService;
    private final ObjectMapper objectMapper;
    @Override
    public void respond(String username, RespondConfig respondConfig, Map<String,Object> parameters, String jsonData) {
        switch (respondConfig.getRespondType()){
            case MENU -> {
                MenuConfig menuConfig;
                try {
                    menuConfig = objectMapper.readValue(respondConfig.getRespondData(), MenuConfig.class);
                } catch (JsonProcessingException e) {
                    log.info("Map menuConfig error: {}",e.getMessage(),e);
                    return;
                }
                MenuConfigDto menuConfigDto = getMenuConfigDto(menuConfig,jsonData);
                Map<String,Object> respondParameters = new HashMap<>();
                if(parameters!=null&&!parameters.isEmpty())
                    respondParameters.putAll(parameters);
                if(menuConfigDto.getParameters()!=null&&!menuConfigDto.getParameters().isEmpty())
                    respondParameters.putAll(menuConfigDto.getParameters());
                mqEventPublisher.newMenu(username,menuConfigDto.getDescription(),menuConfigDto.getOptions(), respondParameters);
            }
            case NOTIFY -> {
                NotifyConfig notifyConfig;
                try {
                    notifyConfig = objectMapper.readValue(respondConfig.getRespondData(), NotifyConfig.class);
                } catch (JsonProcessingException e) {
                    log.info("Map notifyConfig error: {}",e.getMessage(),e);
                    return;
                }
                NotifyConfigDto notifyConfigDto = getNotifyConfigDto(username, notifyConfig, jsonData);
                notifyConfigDto.getUsernameList().addAll(restRequestService.getGroupUsers(notifyConfigDto.getGroupList()));
                mqEventPublisher.notifyUser(
                        new ArrayList<>(new LinkedHashSet<>(notifyConfigDto.getUsernameList()))
                        ,notifyConfigDto.getMessage()
                );
            }
        }
    }


    public NotifyConfigDto getNotifyConfigDto(String username, NotifyConfig notifyConfig, String json){
        Map<String,Object> parameters = new HashMap<>();
        String value;
        String formatString;
        NotifyConfigDto notifyConfigDto = NotifyConfigDto.builder().usernameList(new ArrayList<>(){{add(username);}}).build();
        for(NotifyVariable notifyVariable: notifyConfig.getNotifyVariables()){
            value = "";
            Map<String,String> replaceValue = notifyVariable.getReplaceValue();
            if(notifyVariable.getVariableName().equals("USER_LIST")){
                notifyConfigDto.getUsernameList().addAll((List<String>)getJsonVariable("USER_LIST", json , notifyVariable.getJsonPath()));
                continue;
            }else if(notifyVariable.getVariableName().equals("GROUP_LIST")){
                notifyConfigDto.setGroupList((List<String>)getJsonVariable("GROUP_LIST", json , notifyVariable.getJsonPath()));
                continue;
            }

            List<?> dataList = getJsonVariable(notifyVariable.getVariableName() ,json ,notifyVariable.getJsonPath());

            for (int i=0;i<dataList.size();i++) {
                String s = dataList.get(i).toString();

                if(replaceValue!=null&&replaceValue.containsKey(s))
                    s = replaceValue.get(s);

                if(i==0){
                    formatString = notifyVariable.getStartFormat();
                }else if(i==dataList.size()-1){
                    formatString = notifyVariable.getEndFormat();
                }else {
                    formatString = notifyVariable.getMiddleFormat();
                }

                value = String.format(formatString==null?"%s":formatString,value,s);
            }
            parameters.put(notifyVariable.getVariableName(),value);
        }

        notifyConfigDto.setMessage(setTemplate(notifyConfig.getRespondTemplate(),parameters));

        return notifyConfigDto;
    }

    public MenuConfigDto getMenuConfigDto(MenuConfig menuConfig, String json){
        MenuConfigDto menuConfigDto = MenuConfigDto.builder().parameters(menuConfig.getParameters()).options(new ArrayList<>()).build();
        List<MenuOption> options = menuConfigDto.getOptions();
        for(MenuVariable menuVariable :menuConfig.getVariables()){
            Map<String,String> replaceValue = menuVariable.getReplaceValue();
            if(menuVariable.isGlobal()){
                String s = JsonPath.read(json,menuVariable.getJsonPath());
                if(replaceValue!=null&&replaceValue.containsKey(s))
                    s = replaceValue.get(s);
                menuConfigDto.getParameters().put(menuVariable.getVariableName(),s);
                continue;
            }
            setMenuOption(options,
                    menuVariable.getVariableName(),
                    getJsonVariable(menuVariable.getVariableName(),json,menuVariable.getJsonPath()),
                    menuConfig.getNextEvent()
            );
        }

        menuConfigDto.setDescription(setTemplate(menuConfig.getDescriptionTemplate(),menuConfigDto.getParameters()));

        for (MenuOption option:options)
            option.setDisplayName(setTemplate(menuConfig.getDisplayNameTemplate(),option.getOptionParameters()));

        return menuConfigDto;
    }
    public List<?> getJsonVariable(String variableName, String json, String jsonPath){
        Object data = JsonPath.read(json,jsonPath);

        List<?> dataList;
        if(variableName.startsWith("INT_")){
            if(data instanceof List<?> list && !list.isEmpty()) {
                dataList = (List<Integer>) data;
            }else if(data instanceof Integer i){
                dataList = new ArrayList<>(){{add(i);}};
            }else{
                throw new RuntimeException("getMenuConfigDto error!");
            }
        } else if (variableName.startsWith("BOOL_")) {
            if(data instanceof List<?> list && !list.isEmpty()) {
                dataList = (List<Boolean>) data;
            }else if(data instanceof Boolean b){
                dataList = new ArrayList<>(){{add(b);}};
            }else{
                throw new RuntimeException("getMenuConfigDto error!");
            }
        } else {
            if(data instanceof List<?> list && !list.isEmpty()) {
                dataList = (List<String>) data;
            }else if(data instanceof String s){
                dataList = new ArrayList<>(){{add(s);}};
            }else{
                throw new RuntimeException("getMenuConfigDto error!");
            }
        }
        return dataList;
    }

    public String setTemplate(String template, Map<String,Object> parameters){
        for (String s:parameters.keySet())
            template = template.replace(String.format("${%s}",s),parameters.get(s).toString());
        return template;
    }


    public void setMenuOption(List<MenuOption> options, String variableName, List<?> data, String nextEvent){
        for(int i=0;i<data.size();i++){
            if(i>=options.size())
                options.add(
                        MenuOption.builder()
                                .nextEvent(nextEvent)
                                .optionParameters(new HashMap<>())
                                .build()
                );
            options.get(i).getOptionParameters().put(variableName,data.get(i));
        }
    }


}
