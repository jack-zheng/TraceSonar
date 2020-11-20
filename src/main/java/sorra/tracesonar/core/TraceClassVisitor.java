package sorra.tracesonar.core;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import sorra.tracesonar.model.Method;

public class TraceClassVisitor extends ClassVisitor {
  private String className;
  private ClassMap.ClassOutline classOutline;
  private final QualifierFilter qualifierFilter;

  public TraceClassVisitor(QualifierFilter qualifierFilter) {
    super(Opcodes.ASM5);
    this.qualifierFilter = qualifierFilter;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    className = name;
    classOutline = new ClassMap.ClassOutline(name, superName, interfaces);
    ClassMap.INSTANCE.addClassOutline(className, classOutline);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    Method caller = new Method(className, name, desc);

    if ((access & Opcodes.ACC_PRIVATE) == 0 // Non-private
        && !(MethodInsnCollector.isObjectBuildInMethod(name, desc) || !qualifierFilter.filterClass(className))) {
      classOutline.addMethod(caller);
    }
    return new TraceMethodVisitor(caller, qualifierFilter);
  }
}
