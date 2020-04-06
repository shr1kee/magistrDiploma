package ru.nalimov.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Scanner;

@Controller
public class WebSocketController {

    private static int count = 0;
    @MessageMapping(value = "/topicTo")
    @SendTo("/topicFrom")
    public String send(String input) throws InterruptedException {
        return ("from server msg " + count++);
    }


}
