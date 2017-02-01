/**
 * Created by haoliangsky on 4/8/16.
 */
import java.util.ArrayList;

public class QuadTree {
    // Use recursion to make the QuadTree for the representation
    private QTreeNode root;
    public final double SIZE = 256;
    public final String IMG_ROOT =  "img/";
    public final String IMG_END = ".png";
    public final int MAX_DEPTH = 7;
    public static int row = 0;
    public static int col = 0;

    // Constructor fo recursively built up the QuadTree
    public QuadTree(double ullat, double ullon, double lrlat, double lrlon, String rootFile) {
        // Set up the root node
        root = new QTreeNode(null, ullat, ullon, lrlat, lrlon, rootFile);
        root.South = null;
        root.East = null;
        // Go all the way down to construct the entire tree
        root.SE = insert(root, (ullat + lrlat) / 2.0, (ullon + lrlon) / 2.0, lrlat, lrlon, "4");
        root.SW = insert(root, (ullat + lrlat) / 2.0, ullon, lrlat, (ullon + lrlon) / 2.0, "3");
        root.NE = insert(root, ullat, (ullon + lrlon) / 2.0, (ullat + lrlat) / 2.0, lrlon, "2");
        root.NW = insert(root, ullat, ullon, (ullat + lrlat) / 2.0, (ullon + lrlon) / 2.0, "1");
        root.NW.East = root.NE;
        root.NW.South = root.SW;
        root.NE.East = null;
        root.NE.South = root.SE;
        root.SW.East = root.SE;
        root.SW.South = null;
        root.SE.East = null;
        root.SE.South = null;
        setPointer(root.SE);
        setPointer(root.SW);
        setPointer(root.NE);
        setPointer(root.NW);
    }
    // This method sets the South and East pointers
    public void setPointer(QTreeNode x) {
        // Give the relation of the first four
        x.NW.East = x.NE;
        x.NW.South = x.SW;
        x.NE.South = x.SE;
        x.SW.East = x.SE;
        if (x.East != null) {
            x.NE.East = x.East.NW;
        }
        if (x.South != null) {
            x.SW.South = x.South.NW;
        }
        if (x.East != null) {
            x.SE.East = x.East.SW;
        }
        if (x.South != null) {
            x.SE.South = x.South.NE;
        }
        if (!x.NE.hasChild()) {
            return;
        }
        setPointer(x.NW);
        setPointer(x.NE);
        setPointer(x.SW);
        setPointer(x.SE);
    }
    // This method insert a QTreeNode doubleo the correct place, given a filename
    public QTreeNode insert(QTreeNode x, double ullat, double ullon, double lrlat, double lrlon, String filename) {
        if (filename.length() == MAX_DEPTH) {
            return new QTreeNode(x, ullat, ullon, lrlat, lrlon, filename);
        }
        QTreeNode node = new QTreeNode(x, ullat, ullon, lrlat, lrlon, filename);
        node.SE = insert(node, (ullat + lrlat) / 2.0, (ullon + lrlon) / 2.0, lrlat, lrlon, filename + "4");
        node.NE = insert(node, ullat, (ullon + lrlon) / 2.0, (ullat + lrlat) / 2.0, lrlon, filename + "2");
        node.SW = insert(node, (ullat + lrlat) / 2.0, ullon, lrlat, (ullon + lrlon) / 2.0, filename + "3");
        node.NW = insert(node, ullat, ullon, (ullat + lrlat) / 2.0, (ullon + lrlon) / 2.0, filename + "1");
        node.NW.East = node.NE;
        node.NW.South = node.SW;
        node.NE.South = node.SE;
        node.SW.East = node.SE;
        return node;
    }
    // A traversal method that returns the QTreeNodes that cover the query box
    // The x input is the root of the tree
    public ArrayList<QTreeNode> findRasterBox(QTreeNode x, double ullat, double ullon, double lrlat, double lrlon, double queryDistancePerPixel) {
        int level = 0;
        while (level <= MAX_DEPTH) {
            double tileDPP = (this.root.getLRLON() - this.root.getULLON()) / (SIZE * Math.pow(2, level));
            if (tileDPP <= queryDistancePerPixel) {
                break;
            }
            level += 1;
        }
        if (level > MAX_DEPTH) {
            level = MAX_DEPTH;
        }
        // Find the node that contains the upper left point and take the East and South
        x = this.root;
        while(x.getDepth() < level) {
            if (x.NW.contains(ullat, ullon)) {
                x = x.NW;
            } else if (x.NE.contains(ullat, ullon)) {
                x = x.NE;
            } else if (x.SW.contains(ullat, ullon)) {
                x = x.SW;
            } else if (x.SE.contains(ullat, ullon)) {
                x = x.SE;
            } else {
                break;
            }
        } // Now x should be the node at the upper left corner at the proper level, then go east and south to fill the query box
        // Use the box's contains method to see whether a node is in it:
        ArrayList<QTreeNode> tempList = new ArrayList<>();
        QTreeNode tempNode = x;
        // Vertical
        if (level == 0) {
            ArrayList<QTreeNode> initial = new ArrayList<>();
            initial.add(this.getRoot());
            return initial;
        }
        while (tempNode != null) {
            if (tempNode.ULLAT > lrlat) {
                tempList.add(tempNode);
            } else {
                break;
            }
            tempNode = tempNode.South;
        }
        row = tempList.size();
        ArrayList<QTreeNode> list = new ArrayList<>();
        for (QTreeNode tile : tempList) {
            while (tile != null) {
                if (tile.getULLON() < lrlon) {
                    list.add(tile);
                } else {
                    break;
                }
                tile = tile.East;
            }
        }
        col = list.size() / row;
        return list;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

    public QTreeNode getRoot() {
        return this.root;
    }

    public class QTreeNode implements Comparable<QTreeNode>{
        // The dimensional attributes
        private double ULLAT;
        private double ULLON;
        private double LRLAT;
        private double LRLON;
        private int depth;
        private String fileName;
        public QTreeNode parent;
        public QTreeNode NW; // Top-left
        public QTreeNode NE; // Top-right
        public QTreeNode SE; // Bottom-right
        public QTreeNode SW; // Bottom-left
        // For later traverse
        public QTreeNode East = null;
        public QTreeNode South = null;

        // The constructor that takes in argument and make a node for the QuadTree
        public QTreeNode(QTreeNode x, double ullat, double ullon, double lrlat, double lrlon, String filename) {
            this.ULLAT = ullat;
            this.ULLON = ullon;
            this.LRLAT = lrlat;
            this.LRLON = lrlon;
            this.parent = x;
            if (filename.equals("root")) {
                this.depth = 0;
            } else {
                this.depth = filename.length();
            }
            this.fileName = IMG_ROOT + filename + IMG_END;
        }
        public boolean hasChild() {
            return this.getDepth() != MAX_DEPTH;
        }
        // Return true if the QTreeNode contains the coordinate
        public boolean contains(double ullat, double ullon) {
            if (this.ULLON <= ullon && ullon < this.LRLON && this.ULLAT >= ullat && ullat > this.LRLAT) {
                return true;
            } else {
                return false;
            }
        }
        public String getFileName() {
            return this.fileName;
        }
        public int getDepth() {
            return this.depth;
        }
        public double getULLAT() {
            return ULLAT;
        }
        public double getULLON() {
            return this.ULLON;
        }
        public double getLRLAT() {
            return this.LRLAT;
        }
        public double getLRLON() {
            return this.LRLON;
        }
        public QTreeNode NW() {
            return this.NW;
        }
        public QTreeNode NE() {
            return this.NE;
        }
        public QTreeNode SE() {
            return this.SE;
        }
        public QTreeNode SW() {
            return this.SW;
        }
        public QTreeNode parent() {
            return this.parent;
        }
        // Implement a compareTo method to tell where a QTreeNode should go
        public int compareTo(QTreeNode other) {
            return Double.compare(this.ULLAT, other.ULLAT);
        }
    }

}
