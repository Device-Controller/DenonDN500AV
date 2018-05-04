package no.ntnu.vislab.denon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mute extends DN500AVCommand {
    private static final String MUTE = "MU";
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    private List<String> muteStates = new ArrayList<>();

    {
        muteStates.addAll(Arrays.asList(ON,OFF));
    }

    public Mute() {
        super(MUTE);
    }

    public Mute(String parameter){
        super(MUTE, parameter);
    }

    @Override
    public boolean isNumberRange() {
        return false;
    }

    @Override
    public List<String> getValidValues() {
        return muteStates;
    }
    @Override
    public boolean checkAck() {
        return isMatchingCommand(getResponse());
    }

    @Override
    public boolean isMatchingCommand(String cmd){
        return cmd.startsWith(MUTE) && muteStates.contains(cmd.substring(2));
    }
}
