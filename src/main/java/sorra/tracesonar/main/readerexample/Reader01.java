package sorra.tracesonar.main.readerexample;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public class Reader01 {
    public static void main(String[] args) throws Exception {
        ClassReader classReader = new ClassReader(People.class.getName());

        System.out.println(classReader.getClassName());
        System.out.println(Arrays.toString(classReader.getInterfaces()));
        System.out.println(classReader.getSuperName());
        System.out.println(classReader.getAccess());
        System.out.println("count: " + classReader.getItemCount());
        System.out.println("Max length: " + classReader.getMaxStringLength());
    }
}
