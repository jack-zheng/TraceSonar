package sorra.tracesonar.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

import sorra.tracesonar.model.Method;

public class TraceMethodVisitorTest {
  Method caller = new Method("com.org.MyClass", "fakeName", "()V");

  @BeforeEach
  public void setUp() {
    // clean GraphStore before each test
    GraphStore.INSTANCE.cleanMap();
  }

  @Test
  public void test_visitMethodInsn_will_add_caller_into_GraphStore() {
    Method callee = new Method("com.org.Callee", "callee", "()V");

    TraceMethodVisitor methodVisitor = new TraceMethodVisitor(caller, new QualifierFilter(Collections.emptyList(), Collections.emptyList()));
    methodVisitor.visitMethodInsn(0, callee.getOwner(), callee.getMethodName(), callee.getDesc(), false);
    assertEquals(1, GraphStore.INSTANCE.getCallerCollector(callee).getCallers().size());
    assertEquals(caller, GraphStore.INSTANCE.getCallerCollector(callee).getCallers().toArray()[0]);
  }

  @Test
  public void test_visitInvokeDynamicInsn_will_add_lambda_type_caller_into_GraphStore() throws Throwable {
    Handle handle = new Handle(5, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    MethodVisitor methodVisitor = new TraceMethodVisitor(caller, new QualifierFilter(Collections.emptyList(), Collections.emptyList()));
    methodVisitor.visitInvokeDynamicInsn(null, null, null, handle);

    Method expectedCallee = new Method(handle.getOwner(), handle.getName(), handle.getDesc());
    assertEquals(1, GraphStore.INSTANCE.getCallerCollector(expectedCallee).getCallers().size());
    assertEquals(caller, GraphStore.INSTANCE.getCallerCollector(expectedCallee).getCallers().toArray()[0]);
  }

  @Test
  public void test_visitInvokeDynamicInsn_no_handle_in_bsmArgs_no_set_to_GraphStore() throws Throwable {
    MethodVisitor methodVisitor = new TraceMethodVisitor(null, null);
    methodVisitor.visitInvokeDynamicInsn(null, null, null);
    assertEquals(0, GraphStore.INSTANCE.getCallerCollectors().size());
  }
}
