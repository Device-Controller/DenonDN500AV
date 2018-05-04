package no.ntnu.vislab.denon.communicationstates;

import java.io.IOException;

import no.ntnu.vislab.denon.commands.DN500AVCommand;
import no.ntnu.vislab.denon.driver.CommunicationContext;

public class HandleCommand implements CommunicationState {
    private DN500AVCommand cmd;

    public HandleCommand(DN500AVCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute(CommunicationContext context) throws IOException {
        System.out.println(cmd.toString());
        System.out.println(cmd.getResponse());
        if(context.getListener() != null) {
            context.getListener().onCommandReady(cmd);
        }
        context.changeState(new Idle());
    }
}
