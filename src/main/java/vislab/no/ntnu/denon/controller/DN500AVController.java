package vislab.no.ntnu.denon.controller;

import vislab.no.ntnu.denon.driver.DN500AVDevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Device;

import java.util.List;

@Controller
@RequestMapping("/api/DenonDN500AV")
public class DN500AVController  extends DeviceManager {

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/powerOff")
    public ResponseEntity<Integer> powerOff(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.powerOff(), HttpStatus.OK);
    }

    @RequestMapping("/getPower")
    public ResponseEntity<String> getPower(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getPowerValue(), HttpStatus.OK);
    }

    @RequestMapping("/getMute")
    public ResponseEntity<String> getMute(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getMuteValue(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> mute(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.mute(), HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unmute(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.unMute(), HttpStatus.OK);
    }

    @RequestMapping("/getVolume")
    public ResponseEntity<Integer> getVolume(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getVolume(), HttpStatus.OK);
    }

    @RequestMapping("/setVolume")
    public ResponseEntity<Integer> setVolume(@RequestParam("id") int id
            , @RequestParam("value") int value) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.setVolume(value), HttpStatus.OK);
    }

    @RequestMapping("/setSource")
    public ResponseEntity<String> setSource(@RequestParam("id") int id
            , @RequestParam("value") String value) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.selectInputSource(value), HttpStatus.OK);
    }

    @RequestMapping("/getSource")
    public ResponseEntity<String> getSource(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getInputSource(), HttpStatus.OK);
    }

    @RequestMapping("/getSources")
    public ResponseEntity<List<String>> getSources(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getInputSources(), HttpStatus.OK);
    }

    @Override
    public String getDevicePage(int id){
        if(getSoundSystem(id) != null){
            return "forward:/dn500av/dn500av.html";
        }
        return super.getDevicePage(id);
    }
    protected Device getSoundSystem(int id) {
        Device device = getActiveDevices().get(id);
        return device;
    }
}
