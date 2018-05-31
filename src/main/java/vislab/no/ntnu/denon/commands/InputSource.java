package vislab.no.ntnu.denon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputSource extends DN500AVCommand {
    public static final String INPUT_SOURCE = "SI";
    public List<String> inputSources = new ArrayList<>();

    {
        inputSources.addAll(Arrays.asList("CD,DVD,BD,TV,SAT/CBL,GAME,GAME2,DOCK,V.AUX,IPOD,NET/USB,SERVER,FAVORITES,USB/IPOD,USB,IPD".split(",")));
    }

    public InputSource() {
        super(INPUT_SOURCE);
    }

    public InputSource(String parameter) {
        super(INPUT_SOURCE, parameter);
    }

    @Override
    public boolean isNumberRange() {
        return false;
    }

    @Override
    public List<String> getValidValues() {
        return inputSources;
    }

    @Override
    public boolean extendedWaitTime() {
        return !isGETTER();
    }

    @Override
    public boolean checkAck() {
        return isMatchingCommand(getResponse());
    }

    @Override
    public boolean isMatchingCommand(String cmd){
        String[] str = cmd.split("\\r?\\n");
        boolean matches = false;
        for(int i = 0; i< str.length; i++){
            matches = matches || str[i].startsWith(INPUT_SOURCE) && inputSources.contains(str[i].substring(2));
        }
        return matches;
    }

    public String getInputSourceSetting() {
        return getValue();
    }
}
