package no.ntnu.vislab.denon.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import no.ntnu.vislab.denon.SimpleLineReader;
import no.ntnu.vislab.denon.commands.DN500AVCommand;
import no.ntnu.vislab.denon.communicationstates.CommunicationState;
import no.ntnu.vislab.denon.communicationstates.Idle;
import no.ntnu.vislab.denon.driver.DN500AVDriver.OnCommandReady;

import static java.lang.Thread.sleep;

public class CommunicationContext {
    private CommunicationState currentState;
    private long lastSentTime = 0;
    private int timeout = 200;
    private final PrintWriter printer;
    private final BufferedReader reader;
    private int tries;
    private List<DN500AVCommand> outgoing;

    public interface OnConnectionIssue {
        void onError(DN500AVCommand cmd);
    }

    private OnConnectionIssue issueListener;
    private OnCommandReady listener;


    public CommunicationContext(OutputStream outputStream, InputStream inputStream, OnCommandReady listener, List<DN500AVCommand> outgoing) {
        this.lastSentTime = System.currentTimeMillis();
        this.printer = new PrintWriter(outputStream, true);
        this.reader = new SimpleLineReader(new InputStreamReader(inputStream));
        this.currentState = new Idle();
        this.listener = listener;
        this.outgoing = outgoing;
    }

    public void changeState(CommunicationState nextState) {
        System.out.println(nextState);
        this.currentState = nextState;
    }

    public void setOnConnectionIssueListener(OnConnectionIssue listener) {
        this.issueListener = listener;
    }

    public boolean hasTimedOut() {
        try {
            sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return lastSentTime + timeout < System.currentTimeMillis();
    }

    public void resetTimer() {
        lastSentTime = System.currentTimeMillis();
    }

    public PrintWriter getPrinter() {
        return printer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void execute() {
        try {
            currentState.execute(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean commandAvailable() {
        return !outgoing.isEmpty();
    }

    public DN500AVCommand getCommand() {
        DN500AVCommand cmd = (outgoing.isEmpty()) ? null : outgoing.get(0);
        timeout = (cmd != null && cmd.isPowerOnCommand()) ? 1000 : 200;
        return cmd;
    }

    public int getTries() {
        return tries;
    }

    public void incrementTries() {
        this.tries++;
    }

    public void resetTries() {
        this.tries = 0;
    }

    public OnConnectionIssue getOnConnectionErrorListener() {
        return issueListener;
    }

    public OnCommandReady getListener() {
        return listener;
    }

    public synchronized DN500AVCommand getAndRemove() {
        return (outgoing.isEmpty()) ? null : outgoing.remove(0);
    }
}
