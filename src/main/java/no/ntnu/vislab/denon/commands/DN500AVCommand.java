package no.ntnu.vislab.denon.commands;

import java.util.List;

import vislab.no.ntnu.providers.Command;


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
    public boolean isMatchingCommand(String command) {
        return false;
    }



    @Override
    public String toString() {
        return (GETTER) ? getPrefix() + FIELD + GET_CURRENT + getSuffix() : getPrefix() + FIELD + parameter + getSuffix();
    }
    public abstract boolean isNumberRange();
    public abstract List<String> getValidValues();
    public boolean isPowerOnCommand(){
        return false;
    }
    public String getValue(){
        if(getResponse() != null && !getResponse().isEmpty()) {
            return getResponse().substring(FIELD.length());
        }
        return "";
    }

    public String getField() {
        return FIELD;
    }

    public String getParameter(){
        return parameter;
    }
}
