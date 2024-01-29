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

      private final KafkaTemplate<Long, String> longKafkaTemplate;

      /**
       * KafkaTemplate<Long, String> - <Key type, Message type>
       */
      @Autowired
      public MsgControllerProducer(KafkaTemplate<Long, String> longKafkaTemplate) {
            this.longKafkaTemplate = longKafkaTemplate;
      }

      /**
       * @param msgId - Long the same type as in KafkaTemplate
       * @param msg - String the same type as in KafkaTemplate
       */
      @PostMapping
      public void sendOrder(Long msgId, String msg){
            /**
             * .send(topic: "msg", msgId, msg); topic: - theme name in Kafka
             */
            CompletableFuture<SendResult<Long, String>> futureMessage = longKafkaTemplate.send("msg", msgId, msg);
            futureMessage.whenComplete((result, err) -> {
                  System.out.println("Message was send");
                  System.out.println(result);
            });
            longKafkaTemplate.flush();
      }
}
