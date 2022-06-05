package io.devsecoops.topsecret.adapter.output.satellite;

import io.devsecoops.topsecret.application.domain.Position;
import io.devsecoops.topsecret.application.domain.Satellite;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;
import io.devsecoops.topsecret.application.port.output.SatelliteRepositoryPort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("satelliteRepository")
public class SatelliteRepositoryAdapter implements SatelliteRepositoryPort {

  List<Satellite> satellites = new ArrayList<>();

  public SatelliteRepositoryAdapter() {
    this.satellites.add(
        io.devsecoops.topsecret.application.domain.Satellite.builder()
            .name("kenobi")
            .position(Position.builder().x(-500f).y(-200f).build())
            .build());

    this.satellites.add(
        io.devsecoops.topsecret.application.domain.Satellite.builder()
            .name("skywalker")
            .position(Position.builder().x(100f).y(-100f).build())
            .build());

    this.satellites.add(
        io.devsecoops.topsecret.application.domain.Satellite.builder()
            .name("sato")
            .position(Position.builder().x(500f).y(100f).build())
            .build());
  }

  @Override

  public Satellite findByName(String name) {
    Optional<Satellite> result = satellites.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findAny();
    return result.orElseThrow(SatelliteNotFoundException::new);
  }

  @Override
  public List<Satellite> findAll() {
    return satellites;
  }

  @Override
  public Boolean isPresent(String name) {
    try {
      findByName(name);
      return true;
    } catch (SatelliteNotFoundException e) {
      return false;
    }
  }
}
