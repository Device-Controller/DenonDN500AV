package no.ntnu.vislab.denon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Power extends DN500AVCommand {
    private static final String POWER = "PW";
    public static final String ON = "ON";
    public static final String OFF = "STANDBY";
    private List<String> powerStates = new ArrayList<>();

    {
        powerStates.addAll(Arrays.asList("ON,OFF".split(",")));
    }

    public Power() {
        super(POWER);
    }

    public Power(String parameter){
        super(POWER, parameter);
    }

    @Override
    public boolean isNumberRange() {
        return false;
    }

    @Override
    public List<String> getValidValues() {
        return powerStates;
    }
}