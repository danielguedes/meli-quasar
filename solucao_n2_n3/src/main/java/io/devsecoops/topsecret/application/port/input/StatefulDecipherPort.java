package io.devsecoops.topsecret.application.port.input;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;
import io.devsecoops.topsecret.application.exception.PositionCantBeDeterminedException;

import javax.validation.Valid;

public interface StatefulDecipherPort {
  ReceivedMessage register(@Valid ReceivedMessage msg);

  DecipherResponseDTO retrieve() throws PositionCantBeDeterminedException;

  void deleteMessages();

}
