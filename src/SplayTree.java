/**
 * An implementation of a BST backed by a splay tree.
 */
public class SplayTree implements BST {
    private TreeNode root;

    /**
     * Constructs a new tree from the specified array of weights. Since splay
     * trees don't care about access probabilities, you should only need
     * to read the length of the weights array and not the weights themselves.
     * This tree should store the values 0, 1, 2, ..., n - 1, where n is the length
     * of the input array.
     *
     * @param keys weights on the elements.
     */
    public SplayTree(double[] keys) {
        // TODO: Implement this!
    }


    /**
     * Returns whether the specified key is in the BST.
     *
     * @param key The key to test.
     * @return Whether it's in the BST.
     */
    public boolean contains(int key) {
        TreeNode node;

        for (node = root; node != null && node.getKey() != key; ) {
            if (key < node.getKey()) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        if (node == null) {
            return false;
        }

        while (root != node) {
            node.splay();
        }

        return true;
    }

    private class TreeNode {
        private int key;
        private double weight;

        private TreeNode left;
        private TreeNode right;
        private TreeNode parent;

        public TreeNode(TreeNode parent, int key, double weight) {
            this.parent = parent;
            this.key = key;
            this.weight = weight;
        }

        public void splay() {
            assert(parent != null);
            TreeNode grandFather = getGrandfather();

            if (getGrandfather() == null) {
                // zig
                this.rotate(parent);
            } else if (getGrandfather().getRight() == parent) {
                // zig-zag
                this.rotate(parent);
                // The above line makes the original grandfather the parent.
                this.rotate(grandFather);
            } else {
                // zig-zig
                parent.rotate(grandFather);
                this.rotate(parent);
            }
        }

        /* Properly detects whether or not we need left/right rotation */
        private void rotate(TreeNode parent) {
            if (parent == root) {
                root = this;
            }

            if (parent.getLeft() == this) {
                /* We're doing a right-rotation */
                parent.setLeft(this.getRight());
                this.setRight(parent);
            } else {
                parent.setRight(this.getLeft());
                this.setLeft(parent);
            }
        }

        public TreeNode getGrandfather() {
            assert (parent != null);
            return parent.getParent();
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
    }
}
