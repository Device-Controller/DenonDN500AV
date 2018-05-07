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
        muteStates.addAll(Arrays.asList("ON,OFF".split(",")));
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
}
