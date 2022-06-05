package io.devsecoops.topsecret.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedMessage {
  String name;
  @Min(0)
  @NotNull
  Float distance;
  @NotNull
  String[] message;
}
