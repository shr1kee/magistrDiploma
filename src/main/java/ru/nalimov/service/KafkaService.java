package ru.nalimov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private SimpMessagingTemplate template;

   // @KafkaListener(topics = "${data.topic.in}")
    public void listen(String in) {
        log.info("receive {}", in);
        template.convertAndSend("/topicFrom", in);
    }
}
