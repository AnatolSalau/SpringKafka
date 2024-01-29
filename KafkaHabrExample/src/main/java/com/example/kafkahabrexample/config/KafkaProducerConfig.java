package com.example.kafkahabrexample.config;

import com.example.kafkahabrexample.dto.UserDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
      @Value("${config.kafka.server}")
      private  String KAFKA_SERVER;

      /**
       * Producer config for key - long value, value UserDto
       */
      @Bean
      public Map<String, Object> longProducerConfig() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return props;
      }

      /**
       * Producer factory for key - long value, value UserDto
       */
      @Bean
      public ProducerFactory<Long, UserDto> longProducerFactory() {
            return new DefaultKafkaProducerFactory<>(longProducerConfig());
      }

      @Bean
      public KafkaTemplate<Long, UserDto> longKafkaTemplate() {
            return new KafkaTemplate<>(longProducerFactory());
      }

}
