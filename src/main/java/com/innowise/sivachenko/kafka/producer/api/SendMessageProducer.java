package com.innowise.sivachenko.kafka.producer.api;

import com.innowise.sivachenko.kafka.avro.AuditActionRequest;

public interface SendMessageProducer {
    void sendMessage(AuditActionRequest actionRequest);
}
