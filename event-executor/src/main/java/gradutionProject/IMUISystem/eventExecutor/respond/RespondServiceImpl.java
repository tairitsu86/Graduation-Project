package gradutionProject.IMUISystem.eventExecutor.respond;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import gradutionProject.IMUISystem.eventExecutor.dto.MenuConfigDto;
import gradutionProject.IMUISystem.eventExecutor.entity.*;
import gradutionProject.IMUISystem.eventExecutor.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RespondServiceImpl implements RespondService{
    private final MQEventPublisher mqEventPublisher;
    private final ObjectMapper objectMapper;
    @Override
    public void respond(String username, RespondConfig respondConfig, String jsonData) {
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
                mqEventPublisher.newMenu(username,menuConfig.getDescription(),menuConfigDto.getOptions(),menuConfigDto.getParameters());
            }
            case NOTIFY -> {
                NotifyConfig notifyConfig;
                try {
                    notifyConfig = objectMapper.readValue(respondConfig.getRespondData(), NotifyConfig.class);
                } catch (JsonProcessingException e) {
                    log.info("Map notifyConfig error: {}",e.getMessage(),e);
                    return;
                }
                mqEventPublisher.notifyUser(new ArrayList<>(){{add(username);}}, getMessage(notifyConfig,jsonData));
            }
        }
    }
    public String getMessage(NotifyConfig notifyConfig, String json){
        Map<String,String> variables = new HashMap<>();
        String value;
        String formatString;
        for(NotifyVariable n: notifyConfig.getNotifyVariables()){
            value = "";
            Map<String,String> replaceValue = n.getReplaceValue();
            Object data = JsonPath.read(json,n.getJsonPath());
            List<String> temp;
            if(data instanceof List){
                temp = (List<String>) data;
            }else if(data instanceof String){
                temp = new ArrayList<>(){{add((String) data);}};
            }else{
                return "json path error";
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
        return message;
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
        for (MenuOption option:options) {
            String displayName = menuConfig.getDisplayNameTemplate();
            for (String s:option.getOptionParameters().keySet())
                displayName = displayName.replace(String.format("${%s}",s),option.getOptionParameters().get(s));
            option.setDisplayName(displayName);
        }

        return menuConfigDto;
    }
}
