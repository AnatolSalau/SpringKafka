package com.example.kafkahabrexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("msg")
public class MsgControllerProducer {

      /**
       * KafkaTemplate<String, String> - <Key type, Message type>
       */
      private final KafkaTemplate<Long, String> kafkaTemplate;

      @Autowired
      public MsgControllerProducer(KafkaTemplate<Long, String> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
      }

      /**
       * @param msgId - String the same type as in KafkaTemplate
       * @param msg - String the same type as in KafkaTemplate
       */
      @PostMapping
      public void sendOrder(Long msgId, String msg){
            /**
             * .send(topic: "msg", msgId, msg); topic: - theme name in Kafka
             */
            CompletableFuture<SendResult<Long, String>> futureMessage = kafkaTemplate.send("msg", msgId, msg);
            futureMessage.whenComplete((result, err) -> {
                  System.out.println();
                  System.out.println(result);
            });
            kafkaTemplate.flush();
      }
}
