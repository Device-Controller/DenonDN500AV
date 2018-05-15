package vislab.no.ntnu.denon.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.denon.commands.DN500AVCommand;
import vislab.no.ntnu.denon.driver.CommunicationContext;

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
