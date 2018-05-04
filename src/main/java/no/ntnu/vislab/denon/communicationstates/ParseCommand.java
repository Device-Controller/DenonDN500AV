package no.ntnu.vislab.denon.communicationstates;

import java.io.IOException;

import no.ntnu.vislab.denon.commands.DN500AVCommand;
import no.ntnu.vislab.denon.driver.CommunicationContext;

public class ParseCommand implements CommunicationState {
    private final String line;
    public ParseCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(CommunicationContext context) throws IOException {
        CommunicationState nextState = new Idle();
        if(context.getCommand() != null && context.getCommand().isMatchingCommand(line)){
            DN500AVCommand cmd = context.getAndRemove();
            cmd.setResponse(line);
            nextState = new HandleCommand(cmd);
        }
        context.changeState(nextState);
    }
}
