package app.utils.ds;

import app.utils.enums.Move;

import java.util.PriorityQueue;

public class HuffmanTree
{
    private HuffmanNode root;
    private HuffmanNode currentNode;

    public HuffmanTree()
    {
        root = new HuffmanNode(0, 0);
        currentNode = root;
    }

    public HuffmanNode getRoot()
    {
        return root;
    }

    public void generateTree(HuffmanDistribution distribution)
    {
        PriorityQueue<HuffmanNode> nodes = distribution.getPrioritizedDistribution();
        while (!nodes.isEmpty()) {
            if (nodes.size() > 1) {
                nodes.add(createSubtree(nodes.remove(), nodes.remove()));
            } else {
                root = nodes.remove();
            }
        }
    }

    public HuffmanNode moveNode(Move direction)
    {
        currentNode = (direction == Move.LEFT) ? currentNode.getLeftChild() : currentNode.getRightChild();
        HuffmanNode retrievedNode = currentNode;
        currentNode = (currentNode.isALeaf()) ? root : currentNode;

        return retrievedNode;
    }

    private HuffmanNode createSubtree(HuffmanNode node1, HuffmanNode node2)
    {
        HuffmanNode subtreeRoot = new HuffmanNode(0, 0);
        subtreeRoot.setLeftChild(node1);
        subtreeRoot.setRightChild(node2);

        return subtreeRoot;
    }
}