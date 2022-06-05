package io.devsecoops.topsecret.adapter.input.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UtilLambda {

  static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public static void logInvocation(Class clazz, Context context, APIGatewayV2HTTPEvent event) {
    LambdaLogger logger = context.getLogger();
    logger.log(String.format("RequestID: %1$s %2$s invoked.", context.getAwsRequestId(), clazz.getSimpleName()));
    logger.log(String.format("RequestID: %1$s ENVIRONMENT VARIABLES: %2$s", context.getAwsRequestId(), gson.toJson(System.getenv())));
    logger.log(String.format("RequestID: %1$s CONTEXT: %2$s", context.getAwsRequestId(), gson.toJson(context)));
    logger.log(String.format("RequestID: %1$s EVENT: %2$s", context.getAwsRequestId(), gson.toJson(event)));
  }

  public static void logResponse(Class clazz, Context context, APIGatewayV2HTTPResponse response) {
    LambdaLogger logger = context.getLogger();
    logger.log(String.format("RequestID: %1$s RESPONSE: %2$s", context.getAwsRequestId(), gson.toJson(response)));
  }

  public static void logException(Class clazz, Context context, Exception e) {
    LambdaLogger logger = context.getLogger();
    logger.log(String.format("RequestID: %1$s EXCEPTION: %3$s", context.getAwsRequestId(), clazz.getSimpleName(), e));
  }
}
