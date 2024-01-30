package com.example.kafkahabrexample.controller;

import com.example.kafkahabrexample.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/msg")
public class MsgControllerProducer {

      private final KafkaTemplate<Long, UserDto> longKafkaTemplate;

      /**
       * KafkaTemplate<Long, UserDto> - <Key type, Message type>
       */
      @Autowired
      public MsgControllerProducer(KafkaTemplate<Long, UserDto> longKafkaTemplate) {
            this.longKafkaTemplate = longKafkaTemplate;
      }

      /**
       * @param msgId - Long the same type as in KafkaTemplate
       * @param userDto - UserDto the same type as in KafkaTemplate
       */
      @PostMapping(consumes = "application/json", produces = "application/json")
      public void sendOrder(Long msgId, @RequestBody UserDto userDto){
            /**
             * .send(topic: "msg", msgId, msg); topic: - theme name in Kafka
             */
            CompletableFuture<SendResult<Long, UserDto>> futureMessage = longKafkaTemplate.send("msg", msgId, userDto);
            futureMessage.whenComplete((result, err) -> {
                  System.out.println("Message was send");
                  System.out.println(result);
            });
            longKafkaTemplate.flush();
      }
}
