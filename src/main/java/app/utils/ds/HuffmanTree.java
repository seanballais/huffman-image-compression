package app.utils.ds;

import app.utils.enums.MovementDirection;

import java.util.HashMap;

public class HuffmanTree
{
    private HuffmanNode root;
    private HuffmanNode currentNode;

    public HuffmanTree()
    {
        root = new HuffmanNode(0, 0);
    }

    public HuffmanNode getRoot()
    {
        return root;
    }

    public void generateTree(HashMap<Integer, Integer> distribution)
    {

    }

    public HuffmanNode moveNode(MovementDirection direction)
    {
        
    }
}