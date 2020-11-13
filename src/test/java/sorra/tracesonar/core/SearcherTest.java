package sorra.tracesonar.core;

import org.junit.Assert;
import org.junit.Test;
import sorra.tracesonar.model.Query;

import java.util.Collections;
import java.util.stream.Stream;

public class SearcherTest {

    @Test
    public void test_search() {
        String rootPath = getClass().getClassLoader().getResource("sorra/tracesonar/core/data").getPath();
        FileWalker.walkAll(Collections.singletonList(rootPath), new QualifierFilter(Collections.emptyList(), Collections.emptyList()));

        Stream<TreeNode> nodes = new Searcher(false, false, Collections.emptyList()).search(new Query("sorra/tracesonar/core/data/ClassB", "methodB", "*"));
        Assert.assertEquals(nodes.count(), 1);
    }
}