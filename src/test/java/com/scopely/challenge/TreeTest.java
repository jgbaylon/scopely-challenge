package com.scopely.challenge;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TreeTest {

    @Test
    public void insertSingleNodes() {
        //given
        Tree tree = new Tree("/home/sports/basketball/NCAA");
        String expectedTree =
                "home\n" +
                "  -sports\n" +
                "    -basketball\n" +
                "      -NCAA\n" +
                "    -football\n" +
                "  -music\n" +
                "    -rap\n" +
                "      -gangster\n" +
                "    -rock";
        //when
        List<String> fullPathList = Arrays.asList(
                "/home/sports/football",
                "/home/music/rap",
                "/home/music/rock",
                "/home/music/rap/gangster");
        for (String fullPath : fullPathList) {
            tree.insertSingleNodes(fullPath);
        }

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertDualLeafNodes() {
        //given
        List<String> fullPathList = Arrays.asList(
                "/home/sports/basketball/NCAA",
                "/home/sports/football",
                "/home/music/rap",
                "/home/music/rock");
        Tree tree = new Tree(fullPathList);
        String expectedTree =
                "home\n" +
                "  -sports\n" +
                "    -basketball\n" +
                "      -NCAA\n" +
                "    -football\n" +
                "      -NFL\n" +
                "      -NCAA\n" +
                "  -music\n" +
                "    -rap\n" +
                "    -rock";

        //when
        tree.insertDualLeafNodes("/home/sports/football/NFL|NCAA");

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertMultipleNodes_CombinatorialLeafNodes() {
        //given
        Tree tree = new Tree("/home/music");
        String expectedTree =
                "home\n" +
                "  -music\n" +
                "    -rap\n" +
                "    -rap-rock\n" +
                "    -rap-rock-pop\n" +
                "    -rap-pop\n" +
                "    -rock\n" +
                "    -rock-pop\n" +
                "    -pop";

        //when
        tree.insertMultipleNodes("/home/music/rap|rock|pop");

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertMultipleNodes_CombinatorialNodesAtAnyLevel() {
        //given
        Tree tree = new Tree("/home");
        String expectedTree =
                "home\n" +
                "  -sports\n" +
                "    -misc\n" +
                "    -misc-favorites\n" +
                "    -favorites\n" +
                "  -sports-music\n" +
                "    -misc\n" +
                "    -misc-favorites\n" +
                "    -favorites\n" +
                "  -music\n" +
                "    -misc\n" +
                "    -misc-favorites\n" +
                "    -favorites";

        //when
        tree.insertMultipleNodes("/home/sports|music/misc|favorites");

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void collapseCombinatorialTreeToPath() {
        //given
        String expectedPath = "/home/sports|music/misc|favorites";
        Tree tree = new Tree("/home");
        tree.insertMultipleNodes(expectedPath);

        //when
        String actualPath = tree.collapseCombinatorialTreeToPath();

        //then
        assertEquals(expectedPath, actualPath);
        System.out.println(tree.collapseCombinatorialTreeToPath());
    }

    @Test
    public void pathsAreSynonyms() {
        //given
        Tree tree = new Tree("/home");
        tree.insertMultipleNodes("/home/sports|music/misc|favorites");

        //when
        boolean pathsAreSynonyms = tree.pathsAreSynonyms("/home/sports", "/home/music");

        assertTrue(pathsAreSynonyms);
        System.out.println(pathsAreSynonyms);
    }

}
