package vislab.no.ntnu.denon.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.denon.driver.CommunicationContext;

public interface CommunicationState {
    void execute(final CommunicationContext context) throws IOException;
}
