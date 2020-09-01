package sorra.tracesonar.main;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileInputStream;
import java.util.List;

public class ASMPrintWithNodeAPI {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("target/classes/sorra/tracesonar/main/ByteCodeDemo.class");
        ClassReader classReader = new ClassReader(fileInputStream);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //忽略调试信息
        ClassNode classNode = new ClassNode(org.objectweb.asm.Opcodes.ASM5);


        classReader.accept(classNode, ClassReader.SKIP_DEBUG);
        List<MethodNode>  nodes =  classNode.methods;
        for (MethodNode methodNode : nodes) {
            System.out.println(methodNode.name);
        }
        classNode.accept(cw);
    }
}
