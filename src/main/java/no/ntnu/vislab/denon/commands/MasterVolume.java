package no.ntnu.vislab.denon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.denon.exception.DN500AVException;

public class MasterVolume extends DN500AVCommand {
    private static final String VOLUME = "MV";
    private List<String> minMaxVolumeValues = new ArrayList<>();

    {
        minMaxVolumeValues.addAll(Arrays.asList("0","980"));
    }

    public MasterVolume(){
        super(VOLUME);
    }
    public MasterVolume(String parameter) throws DN500AVException {
        super(VOLUME, checkVolume(parameter));
    }
    public MasterVolume(int value) throws DN500AVException {
        this("" + value);
    }

    private static String checkVolume(String vol) throws DN500AVException {
        if(vol.length() == 1 || vol.length() == 2){
            return vol;
        } else if(vol.length() == 3 && (vol.endsWith("" + 0) || vol.endsWith(""+5))){
            return vol;
        }
        throw new DN500AVException("Invalid volume setting: " + vol + " is not valid");
    }
    @Override
    public boolean isNumberRange() {
        return true;
    }

    @Override
    public List<String> getValidValues() {
        return minMaxVolumeValues;
    }
    @Override
    public boolean checkAck() {
        return isMatchingCommand(getResponse());
    }

    @Override
    public boolean isMatchingCommand(String cmd){
        if(cmd == null || cmd.isEmpty()){
            return false;
        }
        String s1 = cmd.split("\\r")[0].substring(2);
        try {
            return cmd.startsWith(VOLUME)
                    && (Integer.parseInt(minMaxVolumeValues.get(0)) < Integer.parseInt(s1)
                    && Integer.parseInt(minMaxVolumeValues.get(1)) > Integer.parseInt(s1));
        } catch (NumberFormatException ex){
            return false;
        }
    }

    public int getMasterVolumeSetting() {
        if(!getResponse().isEmpty()) {
            try {
                return Integer.parseInt(getValue());
            } catch (NumberFormatException e){
                return -1;
            }
        }
        return -1;
    }
}
