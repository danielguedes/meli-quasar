package io.devsecoops.topsecret.adapter.output.dynamodb;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceivedMessageMapper {
  static ReceivedMessage from(Map<String, AttributeValue> attributes) {
    ReceivedMessage msg = new ReceivedMessage();
    msg.setName(attributes.get(MessageRepositoryAdapter.MESSAGE_ID_COL).s());
    msg.setDistance(Float.valueOf(attributes.get(MessageRepositoryAdapter.MESSAGE_DISTANCE_COL).n()));
    msg.setMessage(attributes.get(MessageRepositoryAdapter.MESSAGE_MSG_COL).l().stream().map(AttributeValue::s).toArray(String[]::new));
    return msg;
  }

  static Map<String, AttributeValue> from(ReceivedMessage msg) {
    Map<String, AttributeValue> item = new HashMap<>();
    Collection<AttributeValue> messageList = Arrays.stream(msg.getMessage()).map(m -> AttributeValue.builder().s(m).build()).collect(Collectors.toList());
    item.put(MessageRepositoryAdapter.MESSAGE_ID_COL, AttributeValue.builder().s(msg.getName()).build());
    item.put(MessageRepositoryAdapter.MESSAGE_DISTANCE_COL, AttributeValue.builder().n(msg.getDistance().toString()).build());
    item.put(MessageRepositoryAdapter.MESSAGE_MSG_COL, AttributeValue.builder().l(messageList).build());
    return item;
  }

  static Map<String, AttributeValue> keyFrom(ReceivedMessage msg) {
    Map<String, AttributeValue> item = new HashMap<>();
    item.put(MessageRepositoryAdapter.MESSAGE_ID_COL, AttributeValue.builder().s(msg.getName()).build());
    return item;
  }
}