package vislab.no.ntnu.denon.driver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import vislab.no.ntnu.denon.commands.DN500AVCommand;

public class DN500AVDriver implements Runnable {
    private Socket host;
    private ArrayList<DN500AVCommand> outgoingBuffer;
    private final CommunicationContext communicator;
    private boolean running = false;

    public interface OnCommandReady {
        void onCommandReady(DN500AVCommand command);
    }

    public interface OnConnectionIssue {
        void onError(DN500AVCommand cmd);
    }

    private OnConnectionIssue issueCallback;
    private OnCommandReady listener;


    public DN500AVDriver(Socket host) throws IOException {
        this.host = host;
        this.outgoingBuffer = new ArrayList<>();
        this.communicator = new CommunicationContext(host.getOutputStream(), host.getInputStream(), outgoingBuffer);
        this.communicator.setOnCommandListener(this::handleCommand);
        this.communicator.setOnConnectionIssueListener(this::onError);
        this.running = true;
    }

    private void onError(DN500AVCommand command) {
        if (listener != null) {
            issueCallback.onError(command);
        } else {
            System.err.println("Listener was null, command not handled");
        }
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
            issueCallback.onError(null);
        }
        try {
            host.close();
        } catch (IOException e) {
            host = null;
        }
        running = false;
        return running;
    }
}
