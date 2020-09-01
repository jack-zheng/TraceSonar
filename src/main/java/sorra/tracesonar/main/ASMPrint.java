package sorra.tracesonar.main;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;

public class ASMPrint {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("target/classes/sorra/tracesonar/main/ByteCodeDemo.class");
        ClassReader classReader = new ClassReader(fileInputStream);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //Java8选择ASM5，
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println("field: " + name);
                return super.visitField(access, name, desc, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("method: " + name);
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        //忽略调试信息
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
    }
}
