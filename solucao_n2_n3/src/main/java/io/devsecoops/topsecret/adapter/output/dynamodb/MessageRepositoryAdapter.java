package io.devsecoops.topsecret.adapter.output.dynamodb;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.port.output.MessageRepositoryPort;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Named(value = "messageRepository")
public class MessageRepositoryAdapter implements MessageRepositoryPort {

  final static String TABLE_NAME = "TopSecretReceivedMessages";
  final static String MESSAGE_ID_COL = "satellite";
  final static String MESSAGE_MSG_COL = "message";
  final static String MESSAGE_DISTANCE_COL = "distance";

  @Inject
  DynamoDbClient dynamoDB;

  @Override
  public ReceivedMessage add(ReceivedMessage msg) {
    Map<String, AttributeValue> item = ReceivedMessageMapper.from(msg);
    dynamoDB.putItem(PutItemRequest.builder()
        .tableName(TABLE_NAME)
        .item(item)
        .build());
    return msg;
  }

  @Override
  public ReceivedMessage findByName(String name) {
    return null;
  }

  @Override
  public List<ReceivedMessage> findAll() {
    return dynamoDB.scan(ScanRequest.builder()
            .tableName(TABLE_NAME)
            .attributesToGet(MESSAGE_ID_COL, MESSAGE_MSG_COL, MESSAGE_DISTANCE_COL)
            .build())
        .items()
        .stream()
        .map(ReceivedMessageMapper::from)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteItem(ReceivedMessage msg) {
    dynamoDB.deleteItem(DeleteItemRequest.builder()
        .tableName(TABLE_NAME)
        .key(ReceivedMessageMapper.keyFrom(msg))
        .build());
  }

  @Override
  public void deleteAll() {
    List<ReceivedMessage> all = findAll();
    all.forEach(this::deleteItem);
  }
}

