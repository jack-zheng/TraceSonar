package sorra.tracesonar.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TraceClassVisitorTest {
  private String className = "com.abc.ClassA";

  @BeforeEach
  public void setUp() {
    ClassMap.INSTANCE.cleanMap();
  }

  @Test
  public void test_visit_will_add_class_into_ClassMap() {
    TraceClassVisitor visitor = new TraceClassVisitor(new QualifierFilter(Collections.emptyList(), Collections.emptyList()));
    visitor.visit(0, 0, className, null, "com/lang/Object", new String[] { "A", "B" });
    assertEquals(1, ClassMap.INSTANCE.getClassOutlines().size());
    assertEquals(2, ClassMap.INSTANCE.getClassOutline(className).getIntfs().size());
  }

  @Test
  public void test_visitMethod_will_skip_private_method() {
    TraceClassVisitor visitor = new TraceClassVisitor(null);
    visitor.visitMethod(Opcodes.ACC_PRIVATE, "", "", null, null);
    assertEquals(0, ClassMap.INSTANCE.getClassOutlines().size());
  }

  @Test
  public void test_visitMethod_will_add_method_to_ClassOutline() {
    TraceClassVisitor visitor = new TraceClassVisitor(new QualifierFilter(Collections.emptyList(), Collections.emptyList()));
    visitor.visit(0, 0, className, null, "com/lang/Object", new String[] { "A", "B" });
    visitor.visitMethod(Opcodes.ACC_PUBLIC, "method01", "()V", null, null);
    assertEquals(1, ClassMap.INSTANCE.getClassOutlines().size());
    assertEquals(1, ClassMap.INSTANCE.getClassOutline(className).getMethods().size());
    assertEquals("method01", ClassMap.INSTANCE.getClassOutline(className).getMethods().get(0).getMethodName());
  }
}