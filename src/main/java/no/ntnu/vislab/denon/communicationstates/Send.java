package no.ntnu.vislab.denon.communicationstates;

import java.io.IOException;

import no.ntnu.vislab.denon.driver.CommunicationContext;

public class Send implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) throws IOException {
        context.getPrinter().write(context.getCommand().toString());
        context.getPrinter().flush();
        context.resetTimer();
        context.incrementTries();
        if(context.getTries() > 3){
            context.changeState(new SendError());
        } else {
            context.changeState(new Idle());
        }
    }
}
