package vislab.no.ntnu.denon.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import vislab.no.ntnu.denon.SimpleLineReader;
import vislab.no.ntnu.denon.commands.DN500AVCommand;
import vislab.no.ntnu.denon.commands.InputSource;
import vislab.no.ntnu.denon.commands.MasterVolume;
import vislab.no.ntnu.denon.commands.Mute;
import vislab.no.ntnu.denon.commands.Power;
import vislab.no.ntnu.denon.communicationstates.CommunicationState;
import vislab.no.ntnu.denon.communicationstates.Idle;
import vislab.no.ntnu.denon.driver.DN500AVDriver.OnCommandReady;
import vislab.no.ntnu.denon.driver.DN500AVDriver.OnConnectionIssue;

import static java.lang.Thread.sleep;

public class CommunicationContext {
    private CommunicationState currentState;
    private long lastSentTime = 0;
    private int timeout = 200;
    private final PrintWriter printer;
    private final BufferedReader reader;
    private int tries;
    private List<DN500AVCommand> outgoing;

    private OnConnectionIssue issueListener;
    private OnCommandReady listener;


    public CommunicationContext(OutputStream outputStream, InputStream inputStream, List<DN500AVCommand> outgoing) {
        this.lastSentTime = System.currentTimeMillis();
        this.printer = new PrintWriter(outputStream, true);
        this.reader = new SimpleLineReader(new InputStreamReader(inputStream));
        this.currentState = new Idle();
        this.outgoing = outgoing;
    }

    public void changeState(CommunicationState nextState) {
        this.currentState = nextState;
    }

    public void setOnConnectionIssueListener(OnConnectionIssue listener) {
        this.issueListener = listener;
    }

    public void setOnCommandListener(OnCommandReady listener) {
        this.listener = listener;
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

    public void execute() throws IOException {
            currentState.execute(this);
    }

    public boolean commandAvailable() {
        return !outgoing.isEmpty();
    }

    public DN500AVCommand getCommand() {
        DN500AVCommand cmd = (outgoing.isEmpty()) ? null : outgoing.get(0);
        timeout = (cmd != null && cmd.extendedWaitTime()) ? 1000 : 220;
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

    public DN500AVCommand isCommand(String line) {
        List<DN500AVCommand> validCmds = Arrays.asList(new Power(), new InputSource(), new MasterVolume(), new Mute());
        DN500AVCommand cmd = null;
        for (int i = 0; i< validCmds.size(); i++){
            if(validCmds.get(i).isMatchingCommand(line)){
                cmd = validCmds.get(i);
            }
        }
        return cmd;
    }
}
