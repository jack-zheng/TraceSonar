package sorra.tracesonar.core;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Filters classes by specified qualifiers and a matcher predicate.
 * Effective like:
 * filtered = (all & included) - excluded
 *
 * QualifierFilter will accept path that split with dot mark(com.abc.def) and transfer them into slash format(com/abc/def) in constructor.
 */
public class QualifierFilter {
  private final Collection<String> includedQualifiers;
  private final Collection<String> excludedQualifiers;

  public QualifierFilter(Collection<String> includedQualifiers, Collection<String> excludedQualifiers) {
    this.includedQualifiers = includedQualifiers.stream().map(p -> p.replace(".", "/")).collect(Collectors.toList());
    this.excludedQualifiers = excludedQualifiers.stream().map(p -> p.replace(".", "/")).collect(Collectors.toList());
  }

  public Collection<String> getIncludedQualifiers() {
    return includedQualifiers;
  }

  public Collection<String> getExcludedQualifiers() {
    return excludedQualifiers;
  }

  public boolean filterClass(String classQname) {
    return filter(classQnameMatcher(classQname));
  }

  public boolean filterClassFile(String path) {
    return filter(classFileMatcher(path));
  }

  private boolean filter(Predicate<String> qualifierMatcher) {
    boolean included = includedQualifiers.isEmpty() || includedQualifiers.stream().anyMatch(qualifierMatcher);
    return included && excludedQualifiers.stream().noneMatch(qualifierMatcher);
  }

  /**
   * Generate predicate type lambda expression, this predicate can test if give string start with classQname
   *
   * @param classQname
   * @return
   */
  public static Predicate<String> classQnameMatcher(String classQname) {
    return q ->
        classQname.equals(q) || classQname.startsWith(q + '/') || classQname.startsWith(q + '$');
  }

  public static Predicate<String> classFileMatcher(String path) {
    return q ->
        path.endsWith(q + ".class") || path.contains(q + '/') || path.contains(q + '$');
  }
}
