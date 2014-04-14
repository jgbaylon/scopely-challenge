package com.scopely.challenge;

import java.util.LinkedHashSet;
import java.util.Set;

public class Node<T> {

    private static final String NEW_LINE = "\n";

    private final T value;
    private final int level;
    private final Set<Node<T>> children = new LinkedHashSet<Node<T>>();

    /**
     *
     * @param value
     * @param level
     */
    public Node(
            final T value,
            final int level) {
        if (value == null) {
            throw new IllegalArgumentException("node value cannot be null");
        }
        this.value = value;
        this.level = level;
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
    public Set<Node<T>> getChildren() {
        return children;
    }

    /**
     *
     * @param value
     * @param level
     * @return
     */
    public Node<T> addChild(
            final T value,
            final int level) {
        if (value == null) {
            throw new IllegalArgumentException("node value cannot be null");
        }

        for (Node<T> child : children) {
            if (child.getValue().equals(value)) {
                return child;
            }
        }

        Node<T> child = new Node<T>(value, level);
        children.add(child);
        return child;
    }

    @Override
    public String toString() {
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
