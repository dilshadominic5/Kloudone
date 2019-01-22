package com.xedflix.video.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mohamed Saleem
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${application.kafka.common.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${application.kafka.video-processing.properties.max-poll-records}")
    private String maxPollRecords;

    @Value("${application.kafka.video-processing.properties.session.timeout.ms}")
    private String sessionTimeout;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
            maxPollRecords);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

