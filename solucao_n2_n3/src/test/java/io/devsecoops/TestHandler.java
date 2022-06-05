package io.devsecoops;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Named;

@Named("test")
public class TestHandler implements RequestHandler<Object, String> {

  @Override
  public String handleRequest(Object obj, Context context) {
    return "TESTE";
  }
}
