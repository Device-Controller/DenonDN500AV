package no.ntnu.vislab.denon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.vislab.denon.commands.MasterVolume;
import no.ntnu.vislab.denon.driver.CommunicationContext;
import no.ntnu.vislab.denon.exception.DN500AVException;

public class Main {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("158.38.65.60", 23);
            CommunicationContext context = new CommunicationContext(s.getOutputStream(),s.getInputStream(), null, new ArrayList<>(Arrays.asList(new MasterVolume("405" ),new MasterVolume("405" ),new MasterVolume(), new MasterVolume("40"))));
            while(context != null){
                context.execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DN500AVException e) {
            e.printStackTrace();
        }
    }
}
