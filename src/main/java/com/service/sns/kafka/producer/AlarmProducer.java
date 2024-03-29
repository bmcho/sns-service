package com.service.sns.kafka.producer;

import com.service.sns.model.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Integer, AlarmEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.alarm}")
    private String topic;


    public void send(AlarmEvent alarmEvent) {
        kafkaTemplate.send(topic, alarmEvent.getReceiveUserId(), alarmEvent);
        log.info("Send to Kafka finished!");
    }
}
