package sorra.tracesonar.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class FileWalkerTest {

    @Test
    public void test_walkAll_will_fill_class_info_to_ClassMap_and_GraphStore() {
        String rootPath = getClass().getClassLoader().getResource("sorra/tracesonar/core/data").getPath();
        FileWalker.walkAll(Collections.singletonList(rootPath), new QualifierFilter(Collections.emptyList(), Collections.emptyList()));


        ClassMap.ClassOutline outline = ClassMap.INSTANCE.getClassOutline("sorra/tracesonar/core/data/ClassA");
        // except methodA, there is a default method <init> in each class
        Assert.assertEquals(outline.getMethods().size(), 2);
        Assert.assertEquals(outline.getMethods().get(0).getMethodName(), "<init>");
        Assert.assertEquals(outline.getMethods().get(1).getMethodName(), "methodA");

        outline = ClassMap.INSTANCE.getClassOutline("sorra/tracesonar/core/data/ClassB");
        Assert.assertEquals(outline.getMethods().size(), 2);
    }
}