package com.scopely.challenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NodeTest extends AbstractTest {

    private Node<String> node;

    @Test
    public void constructor_ValueIsNull() {
        try {
            //when
            new Node<String>(null, null);
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("node value cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getValue() {
        //expect
        assertEquals("root", node.getValue());
    }

    @Test
    public void getParentAndGetLevel() {
        //given
        Node<String> node0 = new Node<String>("root", null);
        Node<String> node1 = new Node<String>("first", node0);
        Node<String> node2 = new Node<String>("second", node1);

        //expect
        assertEquals(node1, node2.getParent());
        assertEquals(node0, node2.getParent().getParent());
        assertEquals(null, node2.getParent().getParent().getParent());

        //and
        assertEquals(0, node0.getLevel());
        assertEquals(1, node1.getLevel());
        assertEquals(2, node2.getLevel());
    }

    @Test
    public void addChildAndGetLevel() {
        //given
        Node<String> node1 = node.addChild("first");
        Node<String> node2 = node1.addChild("second");

        //expect
        assertEquals(1, node.getChildren().size());
        assertEquals(node1, node.getChildren().iterator().next());

        //and
        assertEquals(1, node1.getChildren().size());
        assertEquals(node2, node1.getChildren().iterator().next());
    }

    @Test
    public void addChild_ValueIsNull() {
        try {
            //when
            node.addChild(null);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("node value cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getChildren_Remove() {
        //given
        Node<String> node1 = node.addChild("first");

        try {
            //when
            node.getChildren().remove(node1);
            fail();
        } catch (UnsupportedOperationException uoe) {
            //then
            assertEquals(null, uoe.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Before
    public void setup() {
        node = new Node<String>("root", null);
    }

}
