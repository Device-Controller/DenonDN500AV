package no.ntnu.vislab.denon.commands;

import java.util.List;

import no.ntnu.vislab.vislabcontroller.providers.Command;

public abstract class DN500AVCommand extends Command {
    private static final String GET_CURRENT = "?";
    private static final String PREFIX = "";
    private static final String SUFFIX = "" + (char)0x0D;
    private final boolean GETTER;
    private final String FIELD;
    private String parameter;
    private DN500AVCommand(String command, String parameter, boolean getter) {
        super(PREFIX, SUFFIX);
        this.GETTER = getter;
        this.FIELD = command;
        this.parameter = parameter;
    }

    public DN500AVCommand (String command, String parameter){
        this(command, parameter, false);
    }

    public DN500AVCommand(String command){
        this(command, null, true);
    }
    public boolean checkAck() {
        String command = getResponse().substring(0,FIELD.length());
        String parameter = getResponse().substring(FIELD.length());
        if(isNumberRange()){
            try{
                int min = Integer.parseInt(getValidValues().get(0));
                int max = Integer.parseInt(getValidValues().get(1));
                int value = Integer.parseInt(parameter);
                return command.equals(FIELD) && (value >= min) && (value <= max);
            } catch (NumberFormatException ex){
                return false;
            }
        }else {
            return command.equals(FIELD) && getValidValues().contains(parameter);
        }
    }

    @Override
    public String toString() {
        return (GETTER) ? getPrefix() + FIELD + GET_CURRENT + getSuffix() : getPrefix() + FIELD + parameter + getSuffix();
    }
    public abstract boolean isNumberRange();
    public abstract List<String> getValidValues();
}
