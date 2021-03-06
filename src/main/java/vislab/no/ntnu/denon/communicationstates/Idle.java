package vislab.no.ntnu.denon.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.denon.driver.CommunicationContext;

public class Idle implements CommunicationState {
    public void execute(CommunicationContext context) throws IOException {

        String line = context.getReader().readLine().trim();
        if(!line.isEmpty()) {
            context.changeState(new ParseCommand(line));
            context.resetTries();
        }
        if (context.hasTimedOut() && context.commandAvailable()) {
            context.changeState(new Send());
        }
    }
}
