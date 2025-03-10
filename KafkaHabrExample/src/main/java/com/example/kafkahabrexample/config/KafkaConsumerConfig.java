package com.example.kafkahabrexample.config;

import com.example.kafkahabrexample.dto.UserDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
      @Value("${spring.kafka.bootstrap-servers}")
      private String KAFKA_SERVER;

      @Value("${spring.kafka.consumer.group-id}")
      private String kafkaGroupId;

      @Bean
      public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
            return props;
      }

      @Bean
      public ConsumerFactory<Long, UserDto> longConsumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
      }

      @Bean
      public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<Long, UserDto> factory =
                  new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(longConsumerFactory());
            return factory;
      }
}
