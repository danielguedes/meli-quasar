package io.devsecoops.topsecret.application.service.domain;

import io.devsecoops.topsecret.application.domain.Position;
import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;
import io.devsecoops.topsecret.application.exception.PositionCantBeDeterminedException;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class DecipherServiceTest {

  @Inject
  @Named(value = "decipherService")
  DecipherService service;

  Map<String, ReceivedMessage> messages;

  @BeforeEach
  void setUp() {
    this.messages = new HashMap<>();
    this.messages.put("kenobi",
        ReceivedMessage.builder()
            .name("kenobi")
            .distance(407.9215f)
            .message(new String[]{"", "este", "es", "un", "mensaje"})
            .build());

    this.messages.put("skywalker",
        ReceivedMessage.builder()
            .name("skywalker")
            .distance(200.9975f)
            .message(new String[]{"", "", "", "es", "", ""})
            .build());

    this.messages.put("sato",
        ReceivedMessage.builder()
            .name("sato")
            .distance(639.0618f)
            .message(new String[]{"", "", "este", "", "", "mensaje"})
            .build());

    this.messages.put("obi-wan", ReceivedMessage.builder()
        .name("obi-wan")
        .distance(1000.000f)
        .message(new String[]{"esta", "mensagem", "não", "deve", "ser", "lida"})
        .build());
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void givenThreeMessages_whenDeciphering_shouldReturnCorrectPositionAndMessage() {
    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("skywalker"));
    receivedMessages.add(this.messages.get("sato"));

    String expectedMessage = "este es un mensaje";
    Position expectedPosition = Position.builder().x(-100f).y(-120f).build();
    double acceptedDelta = 0.0001;

    DecipherResponseDTO response = service.decipher(receivedMessages);

    TestUtil.assertPositions(expectedPosition, response.getPosition(), acceptedDelta);
    assertEquals(expectedMessage, response.getMessage());
  }

  @Test
  void givenMessagesInDifferentOrder_whenDeciphering_shouldReturnCorrectPositionAndMessage() {
    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("sato"));
    receivedMessages.add(this.messages.get("skywalker"));

    String expectedMessage = "este es un mensaje";
    Position expectedPosition = Position.builder().x(-100f).y(-120f).build();
    double acceptedDelta = 0.0001;

    DecipherResponseDTO response = service.decipher(receivedMessages);

    TestUtil.assertPositions(expectedPosition, response.getPosition(), acceptedDelta);
    assertEquals(expectedMessage, response.getMessage());
  }

  @Test
  void givenNonExistingSatellite_whenDeciphering_shouldThrowSatelliteNotFound() {

    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("skywalker"));
    receivedMessages.add(this.messages.get("obi-wan"));
    assertThrows(SatelliteNotFoundException.class, () -> service.decipher(receivedMessages));
  }

  @Test
  void givenMoreThanThreeSatellite_whenDeciphering_shouldThrowSatelliteNotFound() {
    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("skywalker"));
    receivedMessages.add(this.messages.get("sato"));
    receivedMessages.add(this.messages.get("obi-wan"));
    assertThrows(SatelliteNotFoundException.class, () -> service.decipher(receivedMessages));
  }

  @Test
  void givenRepeatingSatellite_whenDeciphering_shouldThrowSatelliteNotFound() {
    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("skywalker"));
    receivedMessages.add(this.messages.get("skywalker"));
    // TODO Validar
    assertThrows(PositionCantBeDeterminedException.class, () -> service.decipher(receivedMessages));
  }
  @Test
  void givenTwoMessages_whenDeciphering_shouldThrowPositionCantBeDefinedException() {
    List<ReceivedMessage> receivedMessages = new ArrayList<>();
    receivedMessages.add(this.messages.get("kenobi"));
    receivedMessages.add(this.messages.get("sato"));
    assertThrows(PositionCantBeDeterminedException.class, () -> service.decipher(receivedMessages));
  }
}