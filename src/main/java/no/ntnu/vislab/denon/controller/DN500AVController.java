package no.ntnu.vislab.denon.controller;

import no.ntnu.vislab.denon.driver.DN500AVDevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vislab.no.ntnu.DeviceManager;

@Controller
@RequestMapping("/DenonDN500AV")
public class DN500AVController  extends DeviceManager {

    @RequestMapping("/echo")
    public ResponseEntity<String> yolo(@RequestParam ("marco") String marco){
        return new ResponseEntity<>("yolo", HttpStatus.OK);
    }

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

    @RequestMapping("/getMute")
    public ResponseEntity<Integer> getMute(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getMuteValue(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> mute(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.mute(), HttpStatus.OK);
    }

    @RequestMapping("/unmute")
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

    @RequestMapping("/incrementVolume")
    public ResponseEntity<Integer> incrementVolume(@RequestParam("id") int id
            , @RequestParam("value") int value) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.volumeUp(value), HttpStatus.OK);
    }

    @RequestMapping("/decrementVolume")
    public ResponseEntity<Integer> decrementVolume(@RequestParam("id") int id
            , @RequestParam("value") int value) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.volumeDown(value), HttpStatus.OK);
    }

    @RequestMapping("/setSource")
    public ResponseEntity<Integer> setSource(@RequestParam("id") int id
            , @RequestParam("value") int value) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.selectInputSource(value), HttpStatus.OK);
    }

    @RequestMapping("/getSource")
    public ResponseEntity<Integer> getSource(@RequestParam("id") int id) {
        DN500AVDevice device = (DN500AVDevice) getDevice(id);
        return new ResponseEntity<>(device.getInputSource(), HttpStatus.OK);
    }
}
