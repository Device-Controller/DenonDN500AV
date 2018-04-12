package no.ntnu.vislab.denon.driver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.vislab.denon.commands.DN500AVCommand;

public class DN500AVDriver {
    private Socket host;
    private ArrayList<DN500AVCommand> idleCommands;
    private ArrayList<DN500AVCommand> outgoingBuffer;
    private final CommunicationContext communcator;

    public interface OnCommandReady{
        void onCommandReady(DN500AVCommand command);
    }

    private OnCommandReady listener;

    public DN500AVDriver(Socket host, List<DN500AVCommand> idleCommands) throws IOException {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.communcator = new CommunicationContext(host.getOutputStream(), host.getInputStream());
    }
}
