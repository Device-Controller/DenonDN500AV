package no.ntnu.vislab.denon.communicationstates;

import java.io.IOException;

import no.ntnu.vislab.denon.driver.CommunicationContext;

public interface CommunicationState {
    void execute(final CommunicationContext context) throws IOException;
}
