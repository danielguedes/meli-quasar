package io.devsecoops.topsecret.application.service.domain;

import io.devsecoops.topsecret.application.service.domain.MessageDecipher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageDecipherTest {

  @Test
  void givenThreeIdenticalMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"este", "es", "un", "mensaje"},
        {"este", "es", "un", "mensaje"},
        {"este", "es", "un", "mensaje"}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenOneMissingWordOfThreeMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"este", "es", "", "mensaje"},
        {"este", "es", "un", "mensaje"},
        {"este", "es", "un", "mensaje"}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenOneCompleteMessageOfThreeMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"este", "es", "un", ""},
        {"este", "es", "un", "mensaje"},
        {"", "es", "un", "mensaje"}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenThreeIncompleteMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "es", "un", "mensaje"},
        {"este", "", "un", "mensaje"},
        {"este", "es", "un", ""}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenThreeUnphasedCompleteMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "", "es", "un", "mensaje"},
        {"este", "es", "un", "mensaje"},
        {"", "", "este", "es", "un", "mensaje"}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenThreeUnphasedAndIncompleteMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "", "es", "un", ""},
        {"este", "", "un", "mensaje"},
        {"", "", "", "es", "un", "mensaje"}
    };

    String expected = "este es un mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenOneIncompleteMessage_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "", "es", "un", ""}
    };

    String expected = "es un";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenFirstMessageLarger_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "mensaje"},
        {""}
    };

    String expected = "mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenSecondMessageLarger_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {""},
        {"", "mensaje"}
    };

    String expected = "mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenFirstMessageMuchLarger_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "es", "mensaje"},
        {""}
    };

    String expected = "es mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenSecondMessageMuchLarger_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {""},
        {"", "es", "mensaje"}
    };

    String expected = "es mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenFirstMessageEmpty_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {},
        {"", "mensaje"}
    };

    String expected = "mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenSecondMessageEmpty_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {"", "mensaje"},
        {}
    };

    String expected = "mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenTwoEmptyMessages_whenGetMessagee_thenReturnCompleteMessage() {
    String[][] messages = {
        {},
        {}
    };

    String expected = "";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenNoMessages_whenGetMessagee_thenReturnEmptyMessage() {
    String[][] messages = {};

    String expected = "";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenOneWord_whenGetMessagee_thenReturnMessageWithOneWord() {
    String[][] messages = {
        {""},
        {"", "", "", "mensaje"}
    };

    String expected = "mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenTwoWordsUnphasedMessages_whenGetMessagee_thenReturnMessageWithOneWord() {
    String[][] messages = {
        {""},
        {"", "esta", "", "mensaje"},
        {"", "", "esta", "", "mensaje"}
    };

    String expected = "esta mensaje";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

  @Test
  void givenConflictingWordsUnphasedMessages_whenGetMessagee_thenReturnMessageWithOneWord() {
    String[][] messages = {
        {""},
        {"", "esta", "", "mensaje"},
        {"", "", "esta", "", "conflictingWord"}
    };

    String expected = "esta";
    String actual = MessageDecipher.getMessage(messages);
    assertEquals(expected, actual);
  }

}