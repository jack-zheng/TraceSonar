package sorra.tracesonar.core;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import sorra.tracesonar.model.Method;

/**
 * This class will collect caller and callee info of methods and set to GraphStore.
 *
 * Say, we define a mehtod as below:
 *
 * void methodA() {
 *     methodB();
 * }
 *
 * then methodA is caller and method B is callee.
 */
public class TraceMethodVisitor extends MethodVisitor {
  private final QualifierFilter qualifierFilter;
  private final Method currentMethod;

  public TraceMethodVisitor(Method currentMethod, QualifierFilter qualifierFilter) {
    super(Opcodes.ASM5);
    this.qualifierFilter = qualifierFilter;
    this.currentMethod = currentMethod;
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    if (MethodInsnCollector.isObjectBuildInMethod(name, desc) || !qualifierFilter.filterClass(owner)) {
      return;
    }

    Method callee = new Method(owner, name, desc);
    GraphStore.INSTANCE.getCallerCollector(callee).addCaller(currentMethod);
  }

  // handle lambda expression in method
  @Override
  public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
    Handle handle = findInvokedHandle(bsmArgs);
    if (handle == null) {
      return;
    }
    if (MethodInsnCollector.isObjectBuildInMethod(handle.getName(), handle.getDesc()) || !qualifierFilter.filterClass(handle.getOwner())) {
      return;
    }

    Method callee = new Method(handle.getOwner(), handle.getName(), handle.getDesc());
    GraphStore.INSTANCE.getCallerCollector(callee).addCaller(currentMethod);
  }

  private Handle findInvokedHandle(Object[] bsmArgs) {
    for (Object arg : bsmArgs) {
      if (arg instanceof Handle)
        return (Handle) arg;
    }
    return null;
  }
}