package com.example.kafkahabrexample.controller;

import com.example.kafkahabrexample.dto.UserDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgControllerConsumer {

      @Autowired
      public MsgControllerConsumer() {
      }

      @KafkaListener(topics="msg")
      public void orderListener(ConsumerRecord<Long, UserDto> record){
            System.out.println("Message was received : ");
            System.out.println("partition : ");
            System.out.println(record.partition());
            System.out.println("key : ");
            System.out.println(record.key());
            System.out.println("value : ");
            System.out.println(record.value());
      }
}
