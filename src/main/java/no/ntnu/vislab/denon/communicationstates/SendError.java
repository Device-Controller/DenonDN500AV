package no.ntnu.vislab.denon.communicationstates;

import java.io.IOException;

import no.ntnu.vislab.denon.commands.DN500AVCommand;
import no.ntnu.vislab.denon.driver.CommunicationContext;
import no.ntnu.vislab.denon.driver.DN500AVDriver;

public class SendError implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) throws IOException {
        DN500AVCommand cmd = context.getAndRemove();
        if(context.getOnConnectionErrorListener() != null) {
            context.getOnConnectionErrorListener().onError(cmd);
        }
        context.resetTries();
        context.changeState(new Idle());
    }
}
