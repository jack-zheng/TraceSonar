package sorra.tracesonar.core;

import java.util.Map;

import sorra.tracesonar.model.Method;

/**
 * Data store of method->caller pair. it would be filled when {@link MethodInsnCollector} access class method.
 *
 * What we mean when we say caller?
 *  for example: in methodA we invoke methodB, then methodA is the caller of methodB.
 *
 */
public class GraphStore {
  public static final GraphStore INSTANCE = new GraphStore();

  private Map<Method, CallerCollector> callerCollectors = Factory.infoMap();

  public CallerCollector getCallerCollector(Method callee) {
    return callerCollectors.computeIfAbsent(callee, CallerCollector::new);
  }
}
