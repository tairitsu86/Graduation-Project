package gradutionProject.IMUISystem.webhookHandler.eventHandler;

public interface IMUIEventHandler {
    void messageEventHandler(InstantMessagingPlatform IMPlatform,String IMUserId,String message);
}
