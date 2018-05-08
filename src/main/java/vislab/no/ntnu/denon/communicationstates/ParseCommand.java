package vislab.no.ntnu.denon.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.denon.commands.DN500AVCommand;
import vislab.no.ntnu.denon.driver.CommunicationContext;

public class ParseCommand implements CommunicationState {
    private final String line;
    public ParseCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(CommunicationContext context) throws IOException {
        CommunicationState nextState = new Idle();
        DN500AVCommand tempCmd;
        if(context.getCommand() != null && context.getCommand().isMatchingCommand(line)){
            DN500AVCommand cmd = context.getAndRemove();
            cmd.setResponse(line);
            context.getListener().onCommandReady(cmd);
            nextState = new HandleCommand(cmd);
        } else if((tempCmd = context.isCommand(line)) != null){
            tempCmd.setResponse(line);
            nextState = new HandleCommand(tempCmd);
        }
        context.changeState(nextState);
    }
}
