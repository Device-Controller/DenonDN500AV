package no.ntnu.vislab.denon.driver;

import java.io.InputStream;
import java.io.OutputStream;

import no.ntnu.vislab.denon.communicationstates.CommunicationState;
import no.ntnu.vislab.denon.communicationstates.Idle;

import static java.lang.Thread.sleep;

public class CommunicationContext {
    private CommunicationState currentState;
    private long lastSentTime = 0;
    private int timeout = 200;
    public CommunicationContext(){
        currentState = new Idle();
    }

    private OutputStream out;
    private InputStream in;


    public CommunicationContext(OutputStream outputStream, InputStream inputStream) {
        lastSentTime = System.currentTimeMillis();
        this.out = outputStream;
        this.in = inputStream;
    }

    public boolean hasTimedOut(){
        try {
            sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return lastSentTime + timeout < System.currentTimeMillis();
    }
}
