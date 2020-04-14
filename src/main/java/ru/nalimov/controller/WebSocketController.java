package ru.nalimov.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static int count = 0;

    @Autowired
    ObjectMapper mapper;

    @MessageMapping(value = "/topicTo")
    @SendTo("/topicFrom")
    public String send(String input) throws InterruptedException, JsonProcessingException {
        ArrayNode result = mapper.createArrayNode(); 
        ObjectNode tab1 =(ObjectNode) mapper.readTree("{\"title\": \"Кухня\", \"types\":[{\"title\":\"Свет\", \"value\":45}," +
                " {\"title\":\"Вода\", \"value\":86},{\"title\":\"Температура\", \"value\":20}]}");
        ObjectNode tab2 =(ObjectNode) mapper.readTree("{\"title\": \"Гостиная\", \"types\":[{\"title\":\"light\", \"value\":66}," +
                " {\"title\":\"water\", \"value\":124}]}");
        result.add(tab1);
        result.add(tab2);
        return (mapper.writeValueAsString(result));
    }


}
