package io.devsecoops.topsecret.adapter.input.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.devsecoops.topsecret.application.domain.ReceivedMessage;
import io.devsecoops.topsecret.application.port.input.StatefulDecipherPort;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;

@Named("register")
public class RegisterFunction implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Inject
  @Named(value = "statefulService")
  StatefulDecipherPort service;

  @Override
  public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
    UtilLambda.logInvocation(this.getClass(), context, event);

    ReceivedMessage receivedMessage = gson.fromJson(event.getBody(), ReceivedMessage.class);
    String satelliteName = event.getPathParameters().get("satellite");
    receivedMessage.setName(satelliteName);

    APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
    response.setIsBase64Encoded(false);
    HashMap<String, String> headers = new HashMap<String, String>();

    try {
      service.register(receivedMessage);
      headers.put("Content-Type", "application/json");
      response.setStatusCode(201);
      response.setBody(gson.toJson(receivedMessage));
    } catch (IllegalArgumentException e) {
      headers.put("Content-Type", "text/plain");
      response.setStatusCode(400);
      response.setBody(e.getMessage());
      UtilLambda.logException(this.getClass(), context, e);
    } catch (Exception e) {
      headers.put("Content-Type", "text/plain");
      response.setStatusCode(500);
      response.setBody(e.getMessage());
      UtilLambda.logException(this.getClass(), context, e);
    }
    response.setHeaders(headers);

    UtilLambda.logResponse(this.getClass(), context, response);
    return response;
  }
}
