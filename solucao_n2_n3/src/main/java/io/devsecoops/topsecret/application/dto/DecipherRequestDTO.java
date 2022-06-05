package io.devsecoops.topsecret.application.dto;

import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecipherRequestDTO {
  @Size(min = 3)
  List<ReceivedMessage> satellites;
}
