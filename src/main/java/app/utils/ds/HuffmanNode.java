package app.utils.ds;

public class HuffmanNode
{
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;
    private HuffmanNode parent;
    private int colorValue;
    private int frequency;

    public HuffmanNode(int colorValue, int frequency)
    {
        this.colorValue = colorValue;
        this.frequency = frequency;
    }

    public void setParent(HuffmanNode parent)
    {
        this.parent = parent;
    }

    public void setLeftChild(HuffmanNode child)
    {
        leftChild = child;
        if (leftChild != null) {
            frequency += leftChild.getFrequency();
            child.setParent(this);
        }
    }

    public void setRightChild(HuffmanNode child)
    {
        rightChild = child;
        if (rightChild != null) {
            frequency += rightChild.getFrequency();
            child.setParent(this);
        }
    }

    public HuffmanNode getParent()
    {
        return parent;
    }

    public HuffmanNode getLeftChild()
    {
        return leftChild;
    }

    public HuffmanNode getRightChild()
    {
        return rightChild;
    }

    public int getColorValue()
    {
        return colorValue;
    }

    public boolean isLeaf()
    {
        return (leftChild == null) && (rightChild == null);
    }

    public int getFrequency()
    {
        return frequency;
    }
}