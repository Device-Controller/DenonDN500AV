package no.ntnu.vislab.denon.driver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.vislab.denon.commands.DN500AVCommand;
import vislab.no.ntnu.providers.Command;

public class DN500AVDriver implements Runnable {
    private Socket host;
    private ArrayList<DN500AVCommand> outgoingBuffer;
    private final CommunicationContext communicator;
    private boolean running = false;

    public interface OnCommandReady {
        void onCommandReady(DN500AVCommand command);
    }

    public interface OnConnectionIssue {
        void onError();
    }

    private OnConnectionIssue issueCallback;
    private OnCommandReady listener;


    public DN500AVDriver(Socket host) throws IOException {
        this.host = host;
        this.outgoingBuffer = new ArrayList<>();
        this.communicator = new CommunicationContext(host.getOutputStream(), host.getInputStream(), outgoingBuffer);
        this.communicator.setOnCommandListener(this::handleCommand);
        this.running = true;
    }

    private void handleCommand(DN500AVCommand command) {
        if (listener != null) {
            listener.onCommandReady(command);
        } else {
            System.err.println("Listener was null, command not handled");
        }
    }

    public void setOnCommandReady(OnCommandReady listener) {
        this.listener = listener;
    }

    public void setOnIssueCallback(OnConnectionIssue callback) {
        this.issueCallback = callback;
    }

    @Override
    public void run() {
        try {
            while (running) {
                try {
                    communicator.execute();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    stopThread();

                }
            }
            stopThread();
        } catch (Exception e) {
            System.out.println("LUL EXCEPTION");
            e.printStackTrace();
        }
    }

    public boolean queueCommand(DN500AVCommand command) {
        if (running) {
            outgoingBuffer.add(command);
        }
        return running;
    }

    public boolean stopThread() {
        if (issueCallback != null) {
            issueCallback.onError();
        }
        running = false;
        return running;
    }
}
