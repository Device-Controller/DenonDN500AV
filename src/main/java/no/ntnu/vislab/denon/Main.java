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
import no.ntnu.vislab.denon.driver.DN500AVDevice;
import no.ntnu.vislab.denon.exception.DN500AVException;

public class Main {
    static DN500AVDevice device = new DN500AVDevice();
    public static void main(String[] args) throws IOException {
            device.setIpAddress("158.38.65.60");
            device.setPort(23);
            device.initialize();
            while(device != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                state(reader.readLine().toUpperCase());
            }
    }

    private static void state(String s) {
        switch (s){
            case "ON":
                device.powerOn();
                break;
            case "OFF":
                device.powerOff();
                break;
            case "MUTE":
                device.mute();
                break;
            case "UNMUTE":
                device.unMute();
                break;
            case "Q":
                device = null;
                break;
        }
    }

    private static synchronized void wai() {
        long time = System.currentTimeMillis();
        while (time + 1000 > System.currentTimeMillis()) ;
    }
}
