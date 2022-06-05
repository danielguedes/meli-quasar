package io.devsecoops.topsecret.application.service.application;

import io.devsecoops.topsecret.application.dto.DecipherRequestDTO;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;
import io.devsecoops.topsecret.application.exception.PositionCantBeDeterminedException;
import io.devsecoops.topsecret.application.exception.SatelliteNotFoundException;
import io.devsecoops.topsecret.application.port.input.StatelessDecipherPort;
import io.devsecoops.topsecret.application.service.domain.DecipherService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

@ApplicationScoped
@Named(value = "statelessService")
public class StatelessDecipherUseCase implements StatelessDecipherPort {

  @Inject
  @Named("decipherService")
  DecipherService decipherService;

  @Override
  public DecipherResponseDTO decipher(@Valid DecipherRequestDTO req) throws SatelliteNotFoundException, PositionCantBeDeterminedException {
    return decipherService.decipher(req.getSatellites());
  }
}
