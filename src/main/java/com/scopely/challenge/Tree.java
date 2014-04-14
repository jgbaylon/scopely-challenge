package com.scopely.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Tree {

    private static final String DELIMITER = "/";
    private static final String PIPE = "\\|";
    private static final String DASH = "-";
    private static final String DEFAULT_MODE = "SINGLE";
    private static final String COMBO_MODE = "COMBO";

    private Node<String> root;

    /**
     *
     * @param initialFullPath
     */
    public Tree(final String initialFullPath) {
        insertSingleNodes(initialFullPath);
    }

    /**
     *
     * @param fullPathList
     */
    public Tree(final List<String> fullPathList) {
        insertSingleNodes(fullPathList);
    }

    /**
     *
     * @param fullPath
     */
    public void insertSingleNodes(final String fullPath) {
        String[] valueArray = getValueArray(fullPath);
        insertValueArray(valueArray, DEFAULT_MODE);
    }

    /**
     *
     * @param fullPathList
     */
    public void insertSingleNodes(final List<String> fullPathList) {
        if (fullPathList == null) {
            throw new IllegalArgumentException("fullPathList cannot be null");
        }

        for (String fullPath : fullPathList) {
            insertSingleNodes(fullPath);
        }
    }

    /**
     *
     * @param fullPath
     */
    public void insertDualLeafNodes(final String fullPath) {
        String[] valueArray = getValueArray(fullPath);

        String[] leafNodes = valueArray[valueArray.length - 1].split(PIPE);
        if (leafNodes.length != 2) {
            throw new IllegalArgumentException("number of leaf nodes should be 2");
        }

        insertValueArray(valueArray, DEFAULT_MODE);
    }

    /**
     *
     * @param fullPath
     */
    public void insertMultipleNodes(final String fullPath) {
        String[] valueArray = getValueArray(fullPath);
        insertValueArray(valueArray, COMBO_MODE);
    }

    /**
     *
     * @return
     */
    public String collapseCombinatorialTreeToPath() {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(DELIMITER).append(root.getValue());

        Node<String> parent = root;
        while (parent != null) {
            StringBuilder comboBuilder = new StringBuilder();
            for (Node<String> child : parent.getChildren()) {
                if (!child.getValue().contains(DASH)) {
                    comboBuilder.append(child.getValue()).append("|");
                }

                if (!child.getChildren().isEmpty()) {
                    parent = child;
                } else {
                    parent = null;
                }
            }
            comboBuilder.deleteCharAt(comboBuilder.lastIndexOf("|"));
            pathBuilder.append(DELIMITER).append(comboBuilder.toString());
        }

        return pathBuilder.toString();
    }

    /**
     *
     * @param fullPath1
     * @param fullPath2
     * @return
     */
    public boolean pathsAreSynonyms(
            final String fullPath1,
            final String fullPath2) {
        if (fullPath1 == null | fullPath2 == null) {
            return false;
        }

        Node<String> node1 = findNode(fullPath1);
        Node<String> node2 = findNode(fullPath2);

        if (node1 != null && node2 != null && node1.getLevel() == node2.getLevel()
                && node1.getChildren().size() != 0 && node2.getChildren().size() != 0) {
            return areSubTreesIdentical(node1, node2);
        }

        return false;
    }

    /**
     *
     * @param node1
     * @param node2
     * @return
     */
    private boolean areSubTreesIdentical(Node<String> node1, Node<String> node2) {
        List<Node<String>> children1 = new ArrayList<Node<String>>(node1.getChildren());
        List<Node<String>> children2 = new ArrayList<Node<String>>(node2.getChildren());

        if (children1.size() != children2.size()) {
            return false;
        }

        for (int i = 0; i < children1.size(); i++) {
            if (!children1.get(i).getValue().equals(children2.get(i).getValue())) {
                return false;
            }

            if (!areSubTreesIdentical(children1.get(i), children2.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param fullPath
     * @return
     */
    private Node<String> findNode(final String fullPath) {
        String[] valueArray = getValueArray(fullPath);

        if (!root.getValue().equals(valueArray[0])) {
            return null;
        }

        Set<Node<String>> children = root.getChildren();
        int index = 1;
        while (children != null) {
            Node<String> childFound = null;
            Iterator<Node<String>> iterator = children.iterator();
            while (iterator.hasNext()) {
                Node<String> child = iterator.next();
                if (child.getValue().equals(valueArray[index])) {
                    childFound = child;
                    break;
                }
            }

            if (childFound != null) {
                children = childFound.getChildren();
            } else {
                children = null;
            }
            index++;

            if (index == valueArray.length) {
                return childFound;
            }
        }

        return null;
    }

    /**
     *
     * @param fullPath
     * @return
     */
    private String[] getValueArray(final String fullPath) {
        if (fullPath == null) {
            throw new IllegalArgumentException("fullPath cannot be null");
        }

        String[] valueArray = fullPath.trim().replaceFirst(DELIMITER, "").split(DELIMITER);
        if (valueArray.length == 0) {
            throw new IllegalArgumentException("fullPath cannot be empty");
        }

        if (valueArray[0].contains(PIPE)) {
            throw new IllegalArgumentException("multiple root nodes are not allowed");
        }

        return valueArray;
    }

    /**
     *
     * @param valueArray
     * @param mode
     */
    private void insertValueArray(
            final String[] valueArray,
            final String mode) {
        List<Node<String>> parentList = Arrays.asList(root);
        for (int level = 0; level < valueArray.length; level++) {
            String value = valueArray[level];
            if (level == 0) {
                if (root == null) {
                    root = new Node<String>(value, level);
                    parentList = new ArrayList<Node<String>>();
                    parentList.add(root);
                } else if (!root.getValue().equals(value)) {
                    throw new IllegalArgumentException("root value is different");
                }
            } else {
                List<Node<String>> newParentList = new ArrayList<Node<String>>();
                for (Node<String> parent : parentList) {
                    String[] explodedValueArray = explodeValue(value, mode);

                    for (String explodedValue : explodedValueArray) {
                        newParentList.add(parent.addChild(explodedValue, level));
                    }
                }
                parentList = newParentList;
            }
        }
    }

    /**
     *
     * @param value
     * @param mode
     * @return
     */
    private String[] explodeValue(
            final String value,
            final String mode) {
        if (DEFAULT_MODE.equals(mode)) {
            return value.split(PIPE);
        } else if (COMBO_MODE.equals(mode)) {
            Combiner combiner = new Combiner(value.split(PIPE));
            List<String> explodedElementList = combiner.getComboList();
            return explodedElementList.toArray(new String[]{});
        } else {
            throw new UnsupportedOperationException("unsupported mode: " + mode);
        }
    }

    @Override
    public String toString() {
        return root.toString().trim();
    }

}
