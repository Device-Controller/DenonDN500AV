package no.ntnu.vislab.denon.driver;

public interface DN500AVInterface {
    int setVolume(int setting);

    int mute();

    int unMute();

    String selectInputSource(String num);
}
