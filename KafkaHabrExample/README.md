Kafka HABR example

link:
    https://habr.com/ru/articles/496182/

1. Start zookeeper

    start zookeeper-server-start.bat C:\JAVA\kafka_2.13-3.6.1\config\zookeeper.properties

2. Start KAFKA

   start kafka-server-start.bat C:\JAVA\kafka_2.13-3.6.1\config\server.properties

Example Kafka Producer with String key

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
      private final KafkaTemplate<String, String> kafkaTemplate;

      @Autowired
      public MsgControllerProducer(KafkaTemplate<String, String> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
      }

      /**
       * @param msgId - String the same type as in KafkaTemplate
       * @param msg - String the same type as in KafkaTemplate
       */
      @PostMapping
      public void sendOrder(String msgId, String msg){
            /**
             * .send(topic: "msg", msgId, msg); topic: - theme name in Kafka
             */
            CompletableFuture<SendResult<String, String>> futureMessage = kafkaTemplate.send("msg", msgId, msg);
            futureMessage.whenComplete((result, err) -> {
                  System.out.println();
                  System.out.println(result);
            });
            kafkaTemplate.flush();
      }
    }

Example Kafka producer config (Long, String)
    
    package com.example.kafkahabrexample.config;

    import org.apache.kafka.clients.producer.ProducerConfig;
    import org.apache.kafka.common.serialization.LongSerializer;
    import org.apache.kafka.common.serialization.StringSerializer;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.kafka.core.DefaultKafkaProducerFactory;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.kafka.core.ProducerFactory;
    
    import java.util.HashMap;
    import java.util.Map;
    
    @Configuration
    public class KafkaProducerConfig {
    @Value("${config.kafka.server}")
    private  String KAFKA_SERVER;

      /**
       * Producer config for key - long value
       */
      @Bean
      public Map<String, Object> longProducerConfig() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return props;
      }

      /**
       * Producer factory for key - long value
       */
      @Bean
      public ProducerFactory<Long, String> longProducerFactory() {
            return new DefaultKafkaProducerFactory<>(longProducerConfig());
      }

      @Bean
      public KafkaTemplate<Long, String> longKafkaTemplate() {
            return new KafkaTemplate<>(longProducerFactory());
      }

    }
