package ru.nalimov.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.tools.internal.ws.processor.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.nalimov.model.Entity;
import ru.nalimov.service.ModBusService;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class WebSocketController {

    private static List<Double> values = Arrays.asList(10.0, 20.0, 30.0, 40.0);

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ModBusService modBusService;

    @MessageMapping(value = "/startInitialize")
    @SendTo("/startAnswer")
    public String start(String input) throws JsonProcessingException {
        log.info("start new session");
        log.info("connection to controller");
        //modBusService.connect();
        log.info("get from controller: " + values.toString());
       return mapper.writeValueAsString(values);
    }

    @MessageMapping(value = "/sendValue")
    @SendTo("/changeValue")
    public ResponseEntity sendValue(Entity get) {
        System.out.println("registr");
        System.out.println(get.getRegistr());
        System.out.println("value");
        System.out.println(get.getValue());
        return ResponseEntity.ok().build();
    }


}
