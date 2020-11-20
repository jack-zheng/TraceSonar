package sorra.tracesonar.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassReader;

import sorra.tracesonar.util.Pair;

/**
 * This class defins the parse rule when access java class file.
 * when access class, it will fill {@link ClassMap} which store relationship of class and method.
 * when access method, it will fill {@link GraphStore} which store relationship of method and method caller.
 */
public class MethodInsnCollector {

  public MethodInsnCollector(InputStream classInput, QualifierFilter qualifierFilter) throws IOException {
    ClassReader classReader = new ClassReader(classInput);
    classReader.accept(new TraceClassVisitor(qualifierFilter), 0);
  }

  public static boolean isObjectBuildInMethod(String name, String desc) {
    for (Pair<String, String> meth : IGNORE_METHODS) { // Ignore Object methods
      if (meth._1.equals(name) && meth._2.equals(desc)) {
        return true;
      }
    }

    return false;
  }

  private static final Set<Pair<String, String>> IGNORE_METHODS = new HashSet<>();
  static {
    IGNORE_METHODS.add(Pair.of("equals", "(Ljava/lang/Object;)Z"));
    IGNORE_METHODS.add(Pair.of("hashCode", "()I"));
    IGNORE_METHODS.add(Pair.of("toString", "()Ljava/lang/String;"));
  }
}