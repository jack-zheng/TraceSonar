package sorra.tracesonar.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

public class MethodInsnCollectorTest {

  /**
   * isObjectBuildInMethod will ignore Object's hashCode, equals and toString methods.
   */
  @Test
  public void test_isObjectBuildInMethod_will_ignore_object_build_in_methods() throws NoSuchMethodException {
    Method equals = Object.class.getMethod("equals", Object.class);
    assertTrue(MethodInsnCollector.isObjectBuildInMethod(equals.getName(), Type.getType(equals).toString()));

    Method hashCode = Object.class.getMethod("hashCode");
    assertTrue(MethodInsnCollector.isObjectBuildInMethod(hashCode.getName(), Type.getType(hashCode).toString()));

    Method toString = Object.class.getMethod("toString");
    assertTrue(MethodInsnCollector.isObjectBuildInMethod(toString.getName(), Type.getType(toString).toString()));
  }

  /**
   * Parse ClassA check if ClassMap and GraphStore setup properly
   */
  @Test
  public void integration_MethodInsnCollector_setup_ClassMap_GraphStore_properly() throws URISyntaxException, IOException {
    FileInputStream stream = new FileInputStream(new File(getClass().getClassLoader().getResource("sorra/tracesonar/core/data/ClassA.class").toURI()));
    new MethodInsnCollector(stream, new QualifierFilter(Collections.emptyList(), Collections.emptyList()));

    assertEquals(1, ClassMap.INSTANCE.getClassOutlines().size());
    // every class get a default method <init>
    assertEquals(2, ClassMap.INSTANCE.getClassOutlines().get("sorra/tracesonar/core/data/ClassA").getMethods().size());
    List<sorra.tracesonar.model.Method> methods = ClassMap.INSTANCE.getClassOutline("sorra/tracesonar/core/data/ClassA").getMethods();
    assertEquals("<init>", methods.get(0).getMethodName());
    assertEquals("methodA", methods.get(1).getMethodName());

    sorra.tracesonar.model.Method methodB = new sorra.tracesonar.model.Method("sorra/tracesonar/core/data/ClassB", "methodB", "()V");
    sorra.tracesonar.model.Method methodA = new sorra.tracesonar.model.Method("sorra/tracesonar/core/data/ClassA", "methodA", "()V");
    Set<sorra.tracesonar.model.Method> methodBCallers = GraphStore.INSTANCE.getCallerCollector(methodB).getCallers();
    // <init>, mehtodA, methodB
    assertEquals(3, GraphStore.INSTANCE.getCallerCollectors().size());
    assertEquals(1, methodBCallers.size());
    assertTrue(methodBCallers.contains(methodA));

  }
}