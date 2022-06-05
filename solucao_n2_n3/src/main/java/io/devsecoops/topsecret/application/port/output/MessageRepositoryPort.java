package io.devsecoops.topsecret.application.port.output;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;

import java.util.List;

public interface
MessageRepositoryPort {
  ReceivedMessage add(ReceivedMessage obj);

  ReceivedMessage findByName(String name);

  List<ReceivedMessage> findAll();

  void deleteItem(ReceivedMessage msg);

  void deleteAll();

}
