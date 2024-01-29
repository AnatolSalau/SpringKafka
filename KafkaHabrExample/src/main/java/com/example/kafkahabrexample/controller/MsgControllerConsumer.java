package com.example.kafkahabrexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgControllerConsumer {

      @Autowired
      public MsgControllerConsumer() {
      }

      @KafkaListener(topics="msg")
      public void msgListener(String msg){
            System.out.println("Message was received");
            System.out.println(msg);
      }
}
