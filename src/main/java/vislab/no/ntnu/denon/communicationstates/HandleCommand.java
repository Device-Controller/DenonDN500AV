package vislab.no.ntnu.denon.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.denon.commands.DN500AVCommand;
import vislab.no.ntnu.denon.driver.CommunicationContext;

public class HandleCommand implements CommunicationState {
    private DN500AVCommand cmd;

    public HandleCommand(DN500AVCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute(CommunicationContext context) throws IOException {
        if(context.getListener() != null) {
            context.getListener().onCommandReady(cmd);
        }
        context.changeState(new Idle());
    }
}
