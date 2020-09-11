package sorra.tracesonar.main.readerexample;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

import java.util.Arrays;

public class Reader02 {
    public static void main(String[] args) throws Exception {
        ClassReader classReader = new ClassReader(People.class.getName());
        classReader.accept(new ClassVisitor(Opcodes.ASM5) {
            {
                System.out.println("Init ClassVisitor...");
            }

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.out.println("version " + version + ", access: " + access + ", name: " + name + ", signature: " + signature + ", superName: " + superName + ", interfaces: " + Arrays.toString(interfaces)+ "\n");
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public void visitSource(String source, String debug) {
                System.out.println("source: " + source + ", debug: " + debug + "\n");
                super.visitSource(source, debug);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                System.out.println("desc: " + desc + ", visible: " + visible + "\n");
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
                System.out.println("typeRef: " + typeRef + ", typePath: " + typePath + ", desc: " + desc + ", visible: " + visible + "\n");
                return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
            }

            @Override
            public void visitAttribute(Attribute attr) {
                System.out.println(attr.getClass());
                super.visitAttribute(attr);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println("access: " + access + ", name: " + name + ", desc: " + desc + ", signature: " + signature + ", value: " + value + "\n");
                return super.visitField(access, name, desc, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("access: " + access + ", name: " + name + ", desc: " + desc + ", signaure: " + signature + ", exps: " + Arrays.toString(exceptions) + "\n");
                return super.visitMethod(access, name, desc, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                System.out.println("End visitor...");
                super.visitEnd();
            }
        }, ClassReader.SKIP_DEBUG);


    }
}
