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
    public void respond(String username, RespondConfig respondConfig, Map<String,String> parameters, String jsonData) {
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
                Map<String,String> respondParameters = new HashMap<>();
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


    public NotifyConfigDto getNotifyConfigDto(String username,NotifyConfig notifyConfig, String json){
        Map<String,String> variables = new HashMap<>();
        String value;
        String formatString;
        for(NotifyVariable n: notifyConfig.getNotifyVariables()){
            value = "";
            Map<String,String> replaceValue = n.getReplaceValue();
            Object data = JsonPath.read(json,n.getJsonPath());
            List<String> temp;
            if(data instanceof List<?> list){
                temp = (List<String>) list;
            }else if(data instanceof String){
                temp = new ArrayList<>(){{add((String) data);}};
            }else{
                throw new RuntimeException("getNotifyConfigDto json path read error: \n"+json);
            }


            for (int i=0;i<temp.size();i++) {
                String s = temp.get(i);

                if(replaceValue!=null&&replaceValue.containsKey(s))
                    s = replaceValue.get(s);

                if(i==0){
                    formatString = n.getStartFormat();
                }else if(i==temp.size()-1){
                    formatString = n.getEndFormat();
                }else {
                    formatString = n.getMiddleFormat();
                }

                value = String.format(formatString==null?"%s":formatString,value,s);
            }
            variables.put(n.getVariableName(),value);
        }

        String message = notifyConfig.getRespondTemplate();
        for (String s:variables.keySet())
            message = message.replace(String.format("${%s}",s),variables.get(s));
        NotifyConfigDto notifyConfigDto = NotifyConfigDto.builder().message(message).build();

        notifyConfigDto.setUsernameList(new ArrayList<>(){{add(username);}});

        if(variables.containsKey("USERNAME_LIST"))
            notifyConfigDto.getUsernameList().addAll(Arrays.stream(variables.get("USERNAME_LIST").split(" ")).toList());

        if(variables.containsKey("GROUP_LIST"))
            notifyConfigDto.setGroupList(Arrays.stream(variables.get("GROUP_LIST").split(" ")).toList());

        return notifyConfigDto;
    }

    public MenuConfigDto getMenuConfigDto(MenuConfig menuConfig, String json){
        MenuConfigDto menuConfigDto = MenuConfigDto.builder().parameters(menuConfig.getParameters()).options(new ArrayList<>()).build();
        List<MenuOption> options = menuConfigDto.getOptions();
        for(MenuVariable m :menuConfig.getVariables()){
            Map<String,String> replaceValue = m.getReplaceValue();
            if(m.isGlobal()){
                String s = JsonPath.read(json,m.getJsonPath());
                if(replaceValue!=null&&replaceValue.containsKey(s))
                    s = replaceValue.get(s);
                menuConfigDto.getParameters().put(m.getVariableName(),s);
                continue;
            }
            Object data = JsonPath.read(json,m.getJsonPath());
            List<String> temp;
            if(data instanceof List){
                temp = (List<String>) data;
            }else if(data instanceof String){
                temp = new ArrayList<>(){{add((String) data);}};
            }else{
                throw new RuntimeException("getMenuConfigDto error");
            }

            for(int i=0;i<temp.size();i++){
                if(i>=options.size())
                    options.add(
                            MenuOption.builder()
                                    .nextEvent(menuConfig.getNextEvent())
                                    .optionParameters(new HashMap<>())
                                    .build()
                    );
                options.get(i).getOptionParameters().put(m.getVariableName(),temp.get(i));
            }
        }
        String description = menuConfig.getDescriptionTemplate();
        for (String s :menuConfigDto.getParameters().keySet()){
            description = description.replace(String.format("${%s}",s),menuConfigDto.getParameters().get(s));
        }
        menuConfigDto.setDescription(description);
        for (MenuOption option:options) {
            String displayName = menuConfig.getDisplayNameTemplate();
            for (String s:option.getOptionParameters().keySet())
                displayName = displayName.replace(String.format("${%s}",s),option.getOptionParameters().get(s));
            option.setDisplayName(displayName);
        }

        return menuConfigDto;
    }
}
