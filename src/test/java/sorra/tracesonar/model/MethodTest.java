package sorra.tracesonar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MethodTest {
  @Test
  public void test_formatString() throws NoSuchMethodException {
    Method method = new Method("Lcom/lang/Object", "equals", "(Lcom/lang/Object;)Z");
    assertEquals("boolean Object.equals(Object)", method.formatString());
  }

}