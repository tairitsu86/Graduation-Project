package gradutionProject.IMUISystem.eventExecutor.respond;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyVariable;
import gradutionProject.IMUISystem.eventExecutor.entity.RespondConfig;
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
                //TODO
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
        for(NotifyVariable notifyVariable: notifyConfig.getNotifyVariables()){
            value = "";
            Map<String,String> replaceValue = notifyVariable.getReplaceValue();
            List<String> temp = JsonPath.read(json,notifyVariable.getJsonPath());

            for (int i=0;i<temp.size();i++) {
                String s = temp.get(i);

                if(replaceValue!=null&&replaceValue.containsKey(s))
                    s = replaceValue.get(s);

                if(i==0){
                    formatString = notifyVariable.getStartFormat();
                }else if(i==temp.size()-1){
                    formatString = notifyVariable.getEndFormat();
                }else {
                    formatString = notifyVariable.getMiddleFormat();
                }

                value = String.format(formatString==null?"%s":formatString,value,s);
            }
            variables.put(notifyVariable.getVariableName(),value);
        }

        String message = notifyConfig.getRespondTemplate();
        for (String s:variables.keySet())
            message = message.replace(String.format("${%s}",s),variables.get(s));
        return message;
    }
}
