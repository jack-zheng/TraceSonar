package sorra.tracesonar.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class QualifierFilterTest {
  @Test
  public void test_QualifierFilter_will_transfer_path_from_dot_format_to_slash_format() {
    List<String> arr = Arrays.asList("com.abc.def", "com.qwe.asd");
    List<String> arrTransfer = Arrays.asList("com/abc/def", "com/qwe/asd");
    QualifierFilter filter = new QualifierFilter(arr, arr);

    Set<String> includeSet = new HashSet<>(filter.getIncludedQualifiers());
    for (String s : arrTransfer) {
      assertTrue(includeSet.contains(s));
    }

    Set<String> excludeSet = new HashSet<>(filter.getExcludedQualifiers());
    for (String s : arrTransfer) {
      assertTrue(excludeSet.contains(s));
    }
  }

  @Test
  public void test_filterClass_both_include_exclude_as_empty() {
    QualifierFilter filter = new QualifierFilter(Collections.emptyList(), Collections.emptyList());
    assertTrue(filter.filterClass(""));
    assertTrue(filter.filterClassFile(""));
  }

  @Test
  public void test_filterClass_with_include() {
    QualifierFilter filter = new QualifierFilter(Collections.singletonList("com.abc.def"), Collections.emptyList());
    assertTrue(filter.filterClass("com/abc/def/ClassA"));
    assertTrue(filter.filterClass("com/abc/def"));
    assertTrue(filter.filterClassFile("com/abc/def/ClassA.class"));
  }

  @Test
  public void test_filterClass_with_exclude() {
    QualifierFilter filter = new QualifierFilter(Collections.emptyList(), Collections.singletonList("com.abc.def"));
    assertFalse(filter.filterClass("com/abc/def/ClassA"));
    assertFalse(filter.filterClass("com/abc/def"));
    assertFalse(filter.filterClassFile("com/abc/def/ClassA.class"));
  }
}