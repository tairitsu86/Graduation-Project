package graduationProject.IMUISystem.eventExecutor.respond;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
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
                if(parameters==null)
                    parameters = new HashMap<>();
                if(menuConfig.getParameters()!=null&&!menuConfig.getParameters().isEmpty())
                    parameters.putAll(menuConfig.getParameters());

                menuConfig.setParameters(parameters);
                MenuConfigDto menuConfigDto = getMenuConfigDto(menuConfig,jsonData);
                mqEventPublisher.newMenu(username,menuConfigDto.getDescription(),menuConfigDto.getOptions(), menuConfigDto.getParameters());
            }
            case NOTIFY -> {
                NotifyConfig notifyConfig;
                try {
                    notifyConfig = objectMapper.readValue(respondConfig.getRespondData(), NotifyConfig.class);
                } catch (JsonProcessingException e) {
                    log.info("Map notifyConfig error: {}",e.getMessage(),e);
                    return;
                }
                NotifyConfigDto notifyConfigDto = getNotifyConfigDto(username, notifyConfig, jsonData, parameters);
                notifyConfigDto.getUsernameList().addAll(restRequestService.getGroupUsers(notifyConfigDto.getGroupList()));
                mqEventPublisher.notifyUser(
                        new ArrayList<>(new LinkedHashSet<>(notifyConfigDto.getUsernameList()))
                        ,notifyConfigDto.getMessage()
                );
            }
            case UNFINISHED -> {
                UnfinishedConfig unfinishedConfig;
                try {
                    unfinishedConfig = objectMapper.readValue(respondConfig.getRespondData(), UnfinishedConfig.class);
                } catch (JsonProcessingException e) {
                    log.info("Map unfinishedConfig error: {}",e.getMessage(),e);
                    return;
                }
                mqEventPublisher.publishExecuteEvent(getExecuteEventDto(username, unfinishedConfig, jsonData, parameters));
            }
        }
    }


    public NotifyConfigDto getNotifyConfigDto(String username, NotifyConfig notifyConfig, String json,Map<String,Object> parameters){
        if(parameters==null)
            parameters = new HashMap<>();
        String value;
        String formatString;
        NotifyConfigDto notifyConfigDto = NotifyConfigDto.builder().usernameList(new ArrayList<>(){{add(username);}}).groupList(new ArrayList<>()).build();
        for(NotifyVariable notifyVariable: notifyConfig.getNotifyVariables()){
            value = "";
            Map<String,String> replaceValue = notifyVariable.getReplaceValue();
            if(notifyVariable.getVariableName().equals("USER_LIST")){
                Object data;
                String jsonPath = notifyVariable.getJsonPath();

                if(jsonPath.equals("PAST_PARAMETER"))
                    data = parameters.get(notifyVariable.getVariableName());
                else
                    data = JsonPath.read(json,jsonPath);

                notifyConfigDto.getUsernameList().addAll((List<String>)getJsonVariable("USER_LIST", data));
                continue;
            }else if(notifyVariable.getVariableName().equals("GROUP_LIST")){
                Object data;
                String jsonPath = notifyVariable.getJsonPath();

                if(jsonPath.equals("PAST_PARAMETER"))
                    data = parameters.get(notifyVariable.getVariableName());
                else
                    data = JsonPath.read(json,jsonPath);

                notifyConfigDto.getGroupList().addAll((List<String>)getJsonVariable("GROUP_LIST", data));
                continue;
            }

            Object data;
            String jsonPath = notifyVariable.getJsonPath();

            if(jsonPath.equals("PAST_PARAMETER"))
                data = parameters.get(notifyVariable.getVariableName());
            else
                data = JsonPath.read(json,jsonPath);

            List<?> dataList = getJsonVariable(notifyVariable.getVariableName(), data);

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

                value = String.format(formatString==null?"%s%s":formatString, value, s);
            }
            parameters.put(notifyVariable.getVariableName(), value);
        }

        notifyConfigDto.setMessage(setTemplate(notifyConfig.getRespondTemplate(), parameters));

        return notifyConfigDto;
    }
    public MenuConfigDto getMenuConfigDto(MenuConfig menuConfig, String json){
        MenuConfigDto menuConfigDto = MenuConfigDto.builder().parameters(menuConfig.getParameters()).options(new ArrayList<>()).build();
        List<MenuOption> options = menuConfigDto.getOptions();
        for(MenuVariable menuVariable :menuConfig.getMenuVariables()){
            Map<String,String> replaceValue = menuVariable.getReplaceValue();
            if(menuVariable.isGlobal()){
                Object value = JsonPath.read(json,menuVariable.getJsonPath());
                if(replaceValue!=null&&replaceValue.containsKey(value.toString()))
                    value = replaceValue.get(value.toString());
                if(menuVariable.getVariableName().equals("NEXT_EVENT")){
                    menuConfig.setNextEvent(value.toString());
                    for (MenuOption option:options)
                        option.setNextEvent(value.toString());
                }else{
                    menuConfigDto.getParameters().put(menuVariable.getVariableName(),value);
                }
                menuConfigDto.getParameters().put(menuVariable.getVariableName(),value);
                continue;
            }

            Object data;
            String jsonPath = menuVariable.getJsonPath();
            if(jsonPath.equals("PAST_PARAMETER"))
                data = menuConfigDto.getParameters().get(menuVariable.getVariableName());
            else
                data = JsonPath.read(json,jsonPath);

            setMenuOption(
                    options,
                    menuVariable.getVariableName(),
                    replaceValue,
                    getJsonVariable(menuVariable.getVariableName(),data),
                    menuConfig.getNextEvent()
            );
        }

        menuConfigDto.setDescription(setTemplate(menuConfig.getDescriptionTemplate(),menuConfigDto.getParameters()));

        for (MenuOption option:options)
            option.setDisplayName(setTemplate(menuConfig.getDisplayNameTemplate(),option.getOptionParameters()));

        return menuConfigDto;
    }
    public ExecuteEventDto getExecuteEventDto(String username, UnfinishedConfig unfinishedConfig, String json, Map<String,Object> parameters){
        if(parameters==null) parameters = new HashMap<>();
        for (UnfinishedVariable unfinishedVariable : unfinishedConfig.getUnfinishedVariables()){
            Object o = JsonPath.read(json, unfinishedVariable.getJsonPath());
            parameters.put(unfinishedVariable.getVariableName(), o);
        }

        return ExecuteEventDto.builder()
                .eventName(unfinishedConfig.getNextEvent())
                .executor(username)
                .parameters(parameters)
                .build();
    }
    public List<?> getJsonVariable(String variableName, Object data){
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
    public void setMenuOption(List<MenuOption> options, String variableName,Map<String,String> replaceValue, List<?> data, String nextEvent){
        for(int i=0;i<data.size();i++){
            if(i>=options.size())
                options.add(
                        MenuOption.builder()
                                .nextEvent(nextEvent)
                                .optionParameters(new HashMap<>())
                                .build()
                );

            Object value = data.get(i);
            if(replaceValue!=null&&replaceValue.containsKey(value.toString()))
                value = replaceValue.get(value.toString());
            if(variableName.equals("NEXT_EVENT")){
                options.get(i).setNextEvent(value.toString());
            }else{
                options.get(i).getOptionParameters().put(variableName, value);
            }
        }
    }


}
