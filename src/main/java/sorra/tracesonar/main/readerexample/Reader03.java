package sorra.tracesonar.main.readerexample;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class Reader03 {
    public static void main(String[] args) throws Exception {
        ClassReader reader02 = new ClassReader(OuterClass.class.getName());
        reader02.accept(new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                System.out.println("inner class info: \n");
                System.out.println("name: " + name + ", outerName: " + outerName + ", innerName: " + innerName + ", access: " + access + "\n");
                super.visitInnerClass(name, outerName, innerName, access);
            }
        }, ClassReader.SKIP_DEBUG);
    }
}
