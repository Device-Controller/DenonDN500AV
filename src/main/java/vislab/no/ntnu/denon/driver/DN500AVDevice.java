package vislab.no.ntnu.denon.driver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import vislab.no.ntnu.denon.commands.DN500AVCommand;
import vislab.no.ntnu.denon.commands.InputSource;
import vislab.no.ntnu.denon.commands.MasterVolume;
import vislab.no.ntnu.denon.commands.Mute;
import vislab.no.ntnu.denon.commands.Power;
import vislab.no.ntnu.denon.exception.DN500AVException;
import vislab.no.ntnu.annotations.DeviceSPI;
import vislab.no.ntnu.providers.Device;
import vislab.no.ntnu.providers.PowerStates;

@DeviceSPI
public class DN500AVDevice implements Device, DN500AVInterface {
    private static String MAKE = "Denon";
    private static String MODEL = "DN500-AV";
    private HashMap<String, String> fields = new HashMap<>();
    private InetAddress hostAddress;
    private int portNumber = 0;
    private Thread driverThread;
    private DN500AVDriver driver;

    private DN500AVDriver setUpDriver() throws IOException {
        DN500AVDriver communicationDriver = null;
        communicationDriver = new DN500AVDriver(new Socket(hostAddress, portNumber));
        communicationDriver.setOnCommandReady(this::processCommand);
        communicationDriver.setOnIssueCallback(this::handleError);
        driverThread = new Thread(communicationDriver);
        driverThread.start();
        return communicationDriver;
    }

    private void handleError(DN500AVCommand cmd) {
        if (cmd != null) {
            cmd.setResponse("E:ERROR");
            fields.remove(cmd.getField());
        }
    }

    private void processCommand(DN500AVCommand dn500AVCommand) {
        fields.put(dn500AVCommand.getField(), dn500AVCommand.getValue());
    }


    @Override
    public boolean initialize() {
        try {
            driver = setUpDriver();
            driver.queueCommand(new Power());
            driver.queueCommand(new Mute());
            driver.queueCommand(new MasterVolume());
            driver.queueCommand(new InputSource());
        } catch (IOException e) {
            return false;
        }
        return driver != null;
    }

    private synchronized void send(DN500AVCommand cmd) {
        String field = fields.get(cmd.getField());
        boolean error = false;
        while (!(driver != null || error)) {
            try {
                driver = setUpDriver();
            } catch (IOException e) {
                error = true;
            }
        }
        if ((field == null || !field.equals(cmd.getParameter()))) {
            if(driver != null) {
                driver.queueCommand(cmd);
                long timeout = System.currentTimeMillis();
                int waitTime = (cmd.extendedWaitTime() ? 1000 : 200);
                while (cmd.getResponse() == null && timeout + (waitTime * 3) > System.currentTimeMillis()) {
                    try {
                        wait(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (cmd.getResponse() == null) {
                    handleError(cmd);
                }
            }
        }
    }

    public int powerOn() {

        Power power = new Power(Power.ON);
        send(power);
        return power.getPowerSetting();
    }

    public int powerOff() {
        Power power = new Power(Power.OFF);
        send(power);
        return power.getPowerSetting();
    }

    public int setVolume(int setting) {
        String value = "" + setting;
        if (setting > 99) {
            value = "995";
        } else if (setting >= 0 && setting < 10) {
            value = "0" + setting;
        } else if (setting < 0) {
            value = "00";
        }
        try {
            MasterVolume masterVolume = new MasterVolume(value);
            send(masterVolume);
            return masterVolume.getMasterVolumeSetting();
        } catch (DN500AVException e) {
            return -1;
        }
    }

    public int getVolume() {
        MasterVolume mv = new MasterVolume();
        send(mv);
        return mv.getMasterVolumeSetting();
    }


    public int mute() {
        Mute mute = new Mute(Mute.ON);
        send(mute);
        return mute.getMuteSetting();
    }

    public int unMute() {
        Mute mute = new Mute(Mute.OFF);
        send(mute);
        return mute.getMuteSetting();
    }

    public String selectInputSource(String source) {
        InputSource is = new InputSource(source);
        send(is);
        return is.getInputSourceSetting();
    }


    public String getInputSource() {
        InputSource is = new InputSource();
        send(is);
        return is.getInputSourceSetting();
    }

    public int getVolumeValue() {
        String field = MasterVolume.VOLUME;
        return Integer.parseInt((fields.get(field) != null) ? fields.get(field) : "-1");
    }

    public String getMuteValue() {
        String field = Mute.MUTE;
        return (fields.get(field) != null) ? fields.get(field) : "-1";
    }

    public String getPowerValue() {
        String field = Power.POWER;
        return (fields.get(field) != null) ? fields.get(field) : "-1";
    }

    public String getInputSourceValue() {
        String field = InputSource.INPUT_SOURCE;
        return (fields.get(field) != null) ? fields.get(field) : "NONE";
    }

    public List<String> getInputSources() {
        InputSource is = new InputSource();
        return is.inputSources;
    }

    public String getMake() {
        return MAKE;
    }

    public String getModel() {
        return MODEL;
    }

    public String getDeviceName() {
        return getMake() + " " + getModel();
    }

    @Override
    public String getHostAddress() {
        return hostAddress.toString();
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public boolean setIpAddress(String ipAddress) {
        try {
            hostAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }

    @Override
    public void setPort(int port) {
        portNumber = port;
    }

    @Override
    public int getPowerState() {
        String powerState = fields.get(Power.POWER);
        if (powerState != null) {
            switch (powerState) {
                case Power.OFF:
                    return PowerStates.OFF;

                case Power.ON:
                    return PowerStates.ON;
            }
        }
        return -1;
    }

}
