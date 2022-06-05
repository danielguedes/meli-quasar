package io.devsecoops.topsecret.decipher;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class MessageDecipher {
  public static String getMessage(String[]... messages) {
    String[] result = new String[0];

    for (String[] message : messages) {
      result = mergeMessages(result, message);
    }

    return Arrays.stream(result)
        .filter(Objects::nonNull)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.joining(" "));
  }

  private static String[] mergeMessages(String[] arr1, String[] arr2) {
    String[] larger;
    String[] smaller;

    int diff = arr1.length - arr2.length;

    if (diff < 0) {
      larger = arr2;
      smaller = arr1;
    } else {
      larger = arr1;
      smaller = arr2;
    }

    for (int t = 0; t < smaller.length; t++) {
      String elem1 = larger[abs(diff) + t];
      String elem2 = smaller[t];
      larger[abs(diff) + t] = mergeWords(elem1, elem2);
    }

    return larger;
  }

  private static String mergeWords(String str1, String str2) {
    if (str1.equals(str2) || str2.isEmpty()) {
      return str1;
    }

    if (str1.isEmpty()) {
      return str2;
    }

    return "";
  }
}
