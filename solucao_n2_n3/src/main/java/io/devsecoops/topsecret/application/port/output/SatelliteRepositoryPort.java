package io.devsecoops.topsecret.application.port.output;

import io.devsecoops.topsecret.application.domain.Satellite;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;

import java.util.List;

public interface
SatelliteRepositoryPort {
  Satellite findByName(String name) throws SatelliteNotFoundException;

  List<Satellite> findAll();

  Boolean isPresent(String name);

}
