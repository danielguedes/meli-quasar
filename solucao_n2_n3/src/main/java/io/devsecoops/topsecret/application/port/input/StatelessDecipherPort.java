package io.devsecoops.topsecret.application.port.input;

import io.devsecoops.topsecret.application.dto.DecipherRequestDTO;
import io.devsecoops.topsecret.application.dto.DecipherResponseDTO;

import javax.validation.Valid;

public interface StatelessDecipherPort {
  DecipherResponseDTO decipher(@Valid DecipherRequestDTO req);

}
