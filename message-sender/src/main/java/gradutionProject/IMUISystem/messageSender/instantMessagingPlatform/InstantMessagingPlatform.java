package gradutionProject.IMUISystem.messageSender.instantMessagingPlatform;

import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.line.LineService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InstantMessagingPlatform {
    LINE(new LineService());
    public final IMService service;
}
