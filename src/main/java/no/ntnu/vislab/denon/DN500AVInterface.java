package no.ntnu.vislab.denon;

public interface DN500AVInterface {

    int volumeUp(int amount);

    int volumeDown(int amount);

    int setVolume(int setting);

    int mute();

    int unMute();

    int selectInputSource(int num);
}
