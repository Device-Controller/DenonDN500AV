package no.ntnu.vislab.denon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/denondn500av")
public class DN500AVController {


    @RequestMapping("/echo")
    public ResponseEntity<String> yolo(@RequestParam ("echo") String echo){
        return new ResponseEntity<>(echo, HttpStatus.OK);
    }
}
