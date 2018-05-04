package no.ntnu.vislab.denon.driver;

import no.ntnu.vislab.vislabcontroller.annotations.DeviceSPI;
import no.ntnu.vislab.vislabcontroller.providers.Device;

@DeviceSPI
public class DN500AVDevice implements Device, DN500AVInterface{
    private static String MAKE = "Denon";
    private static String MODEL = "DN500-AV";
    private int volume;
    private int mute;
    private int inputSource;

    public int powerOn() {
        return 0;
    }

    public int powerOff() {
        return 0;
    }

    public String getMake() {
        return MAKE;
    }

    public String getModel() {
        return MODEL;
    }

    public String getDeviceName() {
        return getMake() + " " + getModel();
    }

    public String getHostAddress() {
        return null;
    }

    public int getPortNumber() {
        return 0;
    }

    public boolean setIpAddress(String s) {
        return false;
    }

    public void setPort(int i) {

    }

    public int volumeUp(int amount) {
        return 0;
    }

    public int volumeDown(int amount) {
        return 0;
    }

    public int setVolume(int setting) {
        return 0;
    }

    public int mute() {
        return 0;
    }

    public int unMute() {
        return 0;
    }

    public int selectInputSource(int num) {
        return 0;
    }

    public int getVolume() {
        return volume;
    }

    public int getMuteValue() {
        return mute;
    }

    public int getInputSource() {
        return inputSource;
    }
}
