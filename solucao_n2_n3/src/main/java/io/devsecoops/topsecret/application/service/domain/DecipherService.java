package io.devsecoops.topsecret.application.service.domain;

import io.devsecoops.topsecret.application.domain.Position;
import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.domain.Satellite;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;
import io.devsecoops.topsecret.application.exception.Messages;
import io.devsecoops.topsecret.application.exception.PositionCantBeDeterminedException;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;
import io.devsecoops.topsecret.application.port.output.SatelliteRepositoryPort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Named(value = "decipherService")
public class DecipherService {

  @Inject
  @Named("satelliteRepository")
  SatelliteRepositoryPort satelliteRepository;

  LocationDecipher locationDecipher;

  public DecipherService(SatelliteRepositoryPort satelliteRepository) {
    List<Position> positionsList = satelliteRepository.findAll().stream().map(Satellite::getPosition).collect(Collectors.toList());
    float[][] positions = positionsList.stream().map(p -> new float[]{p.getX(), p.getY()}).toArray(float[][]::new);
    this.locationDecipher = new LocationDecipher(positions);
  }

  public DecipherResponseDTO decipher(List<ReceivedMessage> receivedMessage) throws SatelliteNotFoundException, PositionCantBeDeterminedException {
    validateExistingSatellites(receivedMessage);

    List<ReceivedMessage> sortedMessagesList = sortBasedOnSatellites(receivedMessage);

    validateAtLeast3Positions(receivedMessage);

    float[] distances = getDistances(sortedMessagesList);
    String[][] messages = getMessages(sortedMessagesList);

    float[] location = locationDecipher.getLocation(distances);
    String message = MessageDecipher.getMessage(messages);

    Position position = Position.builder().x(location[0]).y(location[1]).build();
    return DecipherResponseDTO.builder()
        .position(position)
        .message(message)
        .build();
  }

  private String[][] getMessages(List<ReceivedMessage> list) {
    int msgCount = list.size();
    String[][] messages = new String[msgCount][];

    int i = 0;
    for (ReceivedMessage msg : list) {
      messages[i] = msg.getMessage();
      i++;
    }
    return messages;
  }

  private float[] getDistances(List<ReceivedMessage> list) {
    int msgCount = list.size();
    float[] distances = new float[msgCount];

    int i = 0;
    for (ReceivedMessage msg : list) {
      distances[i] = msg.getDistance();
      i++;
    }
    return distances;
  }

  private List<ReceivedMessage> sortBasedOnSatellites(List<ReceivedMessage> receivedMessage) {
    List<Satellite> satelliteList = satelliteRepository.findAll();
    Map<String, ReceivedMessage> messagesMap = new LinkedHashMap<>();
    receivedMessage.forEach(message -> messagesMap.put(message.getName(), message));

    return satelliteList
        .stream()
        .map(sat -> messagesMap.get(sat.getName()))
        .peek(msg -> {
          if (msg == null) {
            throw new PositionCantBeDeterminedException();
          }
        }).collect(Collectors.toList());
  }

  private void validateExistingSatellites(List<ReceivedMessage> receivedMessage) {
    receivedMessage.forEach(msg -> {
      satelliteRepository.findByName(msg.getName());
    });
  }

  private void validateAtLeast3Positions(List<ReceivedMessage> messages) {
    if (messages.size() < 3) {
      throw new PositionCantBeDeterminedException(Messages.LOCATION_COULD_NOT_BE_DETERMINED);
    }
  }
}
