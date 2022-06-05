package io.devsecoops.topsecret.application.dto;

import io.devsecoops.topsecret.application.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecipherResponseDTO {
  Position position;
  String message;
}
