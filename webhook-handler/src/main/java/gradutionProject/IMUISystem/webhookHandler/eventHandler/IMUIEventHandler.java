package gradutionProject.IMUISystem.webhookHandler.eventHandler;

import gradutionProject.IMUISystem.webhookHandler.instantMessagingPlatform.InstantMessagingPlatform;

public interface IMUIEventHandler {
    void messageEventHandler(InstantMessagingPlatform IMPlatform, String IMUserId, String message);
}
