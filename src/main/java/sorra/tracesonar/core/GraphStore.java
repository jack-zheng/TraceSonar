package sorra.tracesonar.core;

import java.util.Map;

import sorra.tracesonar.model.Method;

/**
 * Data store of method->caller pair. it would be filled when {@link MethodInsnCollector} access class method.
 */
public class GraphStore {
  public static final GraphStore INSTANCE = new GraphStore();

  private Map<Method, CallerCollector> callerCollectors = Factory.infoMap();

  public CallerCollector getCallerCollector(Method callee) {
    return callerCollectors.computeIfAbsent(callee, CallerCollector::new);
  }

  public void cleanMap() {
    callerCollectors.clear();
  }

  public Map<Method, CallerCollector> getCallerCollectors() {
    return callerCollectors;
  }
}
