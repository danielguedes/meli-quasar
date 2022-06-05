package io.devsecoops.topsecret.application.service.application;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;
import io.devsecoops.topsecret.application.exception.Messages;
import io.devsecoops.topsecret.application.exception.PositionCantBeDeterminedException;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;
import io.devsecoops.topsecret.application.port.input.StatefulDecipherPort;
import io.devsecoops.topsecret.application.port.output.MessageRepositoryPort;
import io.devsecoops.topsecret.application.port.output.SatelliteRepositoryPort;
import io.devsecoops.topsecret.application.service.domain.DecipherService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import java.util.List;

@ApplicationScoped
@Named(value = "statefulService")
public class StatefulDecipherUseCase implements StatefulDecipherPort {

  @Inject
  @Named("decipherService")
  DecipherService decipherService;

  @Inject
  @Named("messageRepository")
  MessageRepositoryPort messageRepository;

  @Inject
  @Named("satelliteRepository")
  SatelliteRepositoryPort satelliteRepository;

  @Override
  public ReceivedMessage register(@Valid ReceivedMessage msg) throws SatelliteNotFoundException {
    if (!satelliteRepository.isPresent(msg.getName())) {
      throw new SatelliteNotFoundException(Messages.SATELLITE_NOT_FOUND);
    }
    return messageRepository.add(msg);
  }

  @Override
  public DecipherResponseDTO retrieve() throws PositionCantBeDeterminedException {
    List<ReceivedMessage> receivedMessage = messageRepository.findAll();
    return decipherService.decipher(receivedMessage);
  }

  @Override
  public void deleteMessages() {
    messageRepository.deleteAll();
  }
}
