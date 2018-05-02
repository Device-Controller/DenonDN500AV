package no.ntnu.vislab.denon.controller;

import no.ntnu.vislab.denon.driver.DN500AVDevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/DenonDN500AV")
public class DN500AVController  extends DeviceManager {

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam("id") int id) {
        DN500AVDevice device = get
        return new ResponseEntity<>();
    }

    @RequestMapping("/echo")
    public ResponseEntity<String> yolo(@RequestParam ("echo") String echo){
        return new ResponseEntity<>(echo, HttpStatus.OK);
    }
}
