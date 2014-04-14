package com.scopely.challenge;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TreeTest extends AbstractTest {

    @Test
    public void constructor_FullPathIsNull() {
        //given
        String fullPath = null;

        try {
            //when
            new Tree(fullPath);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPath cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_FullPathIsEmpty() {
        //given
        String fullPath = "";

        try {
            //when
            new Tree(fullPath);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPath cannot be empty", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_FullPathContainsMultipleNodes() {
        //given
        String fullPath = "root1|root2";

        try {
            //when
            new Tree(fullPath);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("multiple root nodes are not allowed", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_FullPathListIsNull() {
        //given
        List<String> fullPathList = null;

        try {
            //when
            new Tree(fullPathList);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPathList cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertSingleNodes_FullPath() {
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
    public void insertSingleNodes_FullPathList() {
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
            tree.insertSingleNodes(fullPathList);

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertSingleNodes_FullPathListIsNull() {
        //given
        Tree tree = new Tree("/home/sports/basketball/NCAA");

        try {
            //when
            List<String> fullPathList = null;
            tree.insertSingleNodes(fullPathList);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPathList cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertSingleNodes_RootValueIsDifferent() {
        //given
        Tree tree = new Tree("/home/sports/basketball/NCAA");

        try {
            //when
            tree.insertSingleNodes("/root/sports/basketball/NCAA");
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("root value is different", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertDualLeafNodes_FullPath() {
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
    public void insertDualLeafNodes_FullPathList() {
        //given
        List<String> initialFullPathList = Arrays.asList(
                "/home/sports/basketball/NCAA",
                "/home/sports/football",
                "/home/music/rap",
                "/home/music/rock");
        Tree tree = new Tree(initialFullPathList);
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
        List<String> insertFullPathList = Arrays.asList("/home/sports/football/NFL|NCAA");
        tree.insertDualLeafNodes(insertFullPathList);

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertDualLeafNodes_FullPathListIsNull() {
        //given
        Tree tree = new Tree("/home/sports/basketball/NCAA");

        try {
            //when
            List<String> fullPathList = null;
            tree.insertDualLeafNodes(fullPathList);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPathList cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertComboNodes_FullPath_CombinatorialLeafNodes() {
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
        tree.insertComboNodes("/home/music/rap|rock|pop");

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertComboNodes_FullPath_CombinatorialNodesAtAnyLevel() {
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
        tree.insertComboNodes("/home/sports|music/misc|favorites");

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertComboNodes_FullPathList() {
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
        List<String> fullPathList = Arrays.asList("/home/music/rap|rock|pop");
        tree.insertComboNodes(fullPathList);

        //then
        assertEquals(expectedTree, tree.toString());
        System.out.println(tree);
    }

    @Test
    public void insertComboNodes_FullPathListIsNull() {
        //given
        Tree tree = new Tree("/home/sports/basketball/NCAA");

        try {
            //when
            List<String> fullPathList = null;
            tree.insertComboNodes(fullPathList);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("fullPathList cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void collapseCombinatorialTreeToPath_Simple() {
        //given
        String expectedPath = "/home/sports/favorites";
        Tree tree = new Tree(expectedPath, Tree.COMBO_MODE);

        //when
        String actualPath = tree.collapseCombinatorialTreeToPath();

        //then
        assertEquals(expectedPath, actualPath);
        System.out.println(tree.collapseCombinatorialTreeToPath());
    }

    @Test
    public void collapseCombinatorialTreeToPath_Complex() {
        //given
        String expectedPath = "/home/sports|music/misc|favorites";
        Tree tree = new Tree(expectedPath, Tree.COMBO_MODE);

        //when
        String actualPath = tree.collapseCombinatorialTreeToPath();

        //then
        assertEquals(expectedPath, actualPath);
        System.out.println(tree.collapseCombinatorialTreeToPath());
    }

    @Test
    public void collapseCombinatorialTreeToPath_TreeIsNotPureCombinatorialTree() {
        //given
        Tree tree = new Tree("/home/sports|music/misc|favorites", Tree.COMBO_MODE);
        tree.insertSingleNodes("/home/entertainment/television");

        try {
            //when
            tree.collapseCombinatorialTreeToPath();
            fail();
        } catch (UnsupportedOperationException uoe) {
            //then
            assertEquals("tree is not a pure combinatorial tree", uoe.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void pathsAreSynonyms() {
        //given
        Tree tree = new Tree("/home/sports|music/misc|favorites", Tree.COMBO_MODE);

        //when
        boolean pathsAreSynonyms = tree.pathsAreSynonyms("/home/sports", "/home/music");

        //then
        assertEquals(true, pathsAreSynonyms);
        System.out.println(pathsAreSynonyms);
    }

    @Test
    public void pathsAreNotSynonyms() {
        //given
        Tree tree = new Tree("/home/sports|music/misc|favorites", Tree.COMBO_MODE);
        tree.insertComboNodes("/home/entertainment/misc");

        //when
        boolean pathsAreSynonyms = tree.pathsAreSynonyms("/home/sports", "/home/entertainment");

        //then
        assertEquals(false, pathsAreSynonyms);
        System.out.println(pathsAreSynonyms);
    }

}
