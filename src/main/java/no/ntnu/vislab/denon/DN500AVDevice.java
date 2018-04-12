package no.ntnu.vislab.denon;

import no.ntnu.vislab.vislabcontroller.annotations.DeviceSPI;
import no.ntnu.vislab.vislabcontroller.providers.Device;

@DeviceSPI
public class DN500AVDevice implements Device, DN500AVInterface{
    private int volume;
    private int mute;
    private int inputSrouce;

    public int powerOn() {
        return 0;
    }

    public int powerOff() {
        return 0;
    }

    public String getMake() {
        return null;
    }

    public String getModel() {
        return null;
    }

    public String getDeviceName() {
        return null;
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
}
