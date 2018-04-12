package no.ntnu.vislab.denon.commands;

import no.ntnu.vislab.vislabcontroller.providers.Command;

public class DN500AVCommand extends Command {
    public DN500AVCommand() {
        super("", "");
    }

    public String toString() {
        return null;
    }

    public boolean checkAck() {
        return false;
    }
}
