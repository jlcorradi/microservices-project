package br.com.jlcorradi.commons.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static String toJson(Object object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

}
