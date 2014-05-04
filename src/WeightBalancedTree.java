/**
 * An implementation of a static BST backed by a weight-balanced tree.
 */
public class WeightBalancedTree implements BST {
    WeightBalancedTree leftTree;
    WeightBalancedTree rightTree;
    int key;

    public WeightBalancedTree(double[] elements) {
        double sum = 0;

        /* Compute initial sum. */
        for (int i = 0; i < elements.length; i++) {
            sum += elements[i];
        }

        buildWeightBalancedTree(elements, 0, elements.length, sum);
    }

    /**
     * Constructs a new tree from the specified array of weights. The array entry
     * at position 0 specifies the weight of 0, the entry at position 1 specifies
     * the weight of 1, etc.
     *
     * @param elements The weights on the elements.
     * @param start    inclusive start index
     * @param end      exclusive ending index
     */
    public WeightBalancedTree(double[] elements, int start, int end, double rightSum) {
        buildWeightBalancedTree(elements, start, end, rightSum);
    }

    private void buildWeightBalancedTree(double[] elements, int start, int end, double rightSum) {
        int left = start;

        double leftSum = 0;
        double difference = 0;

        /* At the termination of this loop, left is pointing at one past the optimal splitting point. */
        while (left < end) {
            double oldDifference = difference;
            leftSum += elements[left];
            rightSum -= elements[left];

            if (Math.abs(rightSum - leftSum) > Math.abs(oldDifference)) {
                break;
            }

            difference = rightSum - leftSum;
        }

        this.key = left - 1;

        /* Doin' some arm's length recursion here. */
        if (0 != left) {
            leftTree = new WeightBalancedTree(elements, 0, left, rightSum);
        }
        if (left + 1 != end) {
            rightTree = new WeightBalancedTree(elements, left + 1, end, rightSum);
        }
    }


    /**
     * Returns whether the specified key is in the BST.
     *
     * @param key The key to test.
     * @return Whether it's in the BST.
     */
    public boolean contains(int key) {
        if (this.key == key) {
            return true;
        }

        if (key < this.key) {
            return leftTree != null && leftTree.contains(key);
        } else {
            return rightTree != null && rightTree.contains(key);
        }
    }
}
