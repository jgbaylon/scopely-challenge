package com.scopely.challenge;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Node<T> {

    private static final String NEW_LINE = "\n";

    private final T value;
    private int level;
    private Node<T> parent;
    private final Set<Node<T>> children = new LinkedHashSet<Node<T>>();

    /**
     *
     * @param value
     * @param parent
     */
    public Node(
            final T value,
            final Node<T> parent) {
        if (value == null) {
            throw new IllegalArgumentException("node value cannot be null");
        }
        this.value = value;
        this.parent = parent;
        this.level = findLevel();
    }

    /**
     *
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return
     */
    public Node<T> getParent() {
        return parent;
    }

    /**
     *
     * @return
     */
    public Set<Node<T>> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     *
     * @return
     */
    private int findLevel() {
        int level = 0;
        Node<T> _parent = parent;
        while (_parent != null) {
            _parent = _parent.getParent();
            level++;
        }
        return level;
    }

    /**
     *
     * @param value
     * @return
     */
    public Node<T> addChild(final T value) {
        if (value == null) {
            throw new IllegalArgumentException("node value cannot be null");
        }

        for (Node<T> child : children) {
            if (child.getValue().equals(value)) {
                return child;
            }
        }

        Node<T> child = new Node<T>(value, this);
        children.add(child);
        return child;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (level != node.level) return false;
        if (!value.equals(node.value)) return false;

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + level;
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        int level = getLevel();
        StringBuilder builder = new StringBuilder();
        builder.append(value).append(NEW_LINE);
        for (Node<T> child : children) {
            for (int i = 0; i < (level + 1); i++) {
                builder.append("  ");
            }
            builder.append("-").append(child.toString());
        }
        return builder.toString();
    }

}
