import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    // public final int PICTURE_RANGE = 498;

    // Constructor
    public SeamCarver(Picture pic) {
        this.picture = new Picture(pic);
        this.width = pic.width();
        this.height = pic.height();

    }

    public Picture picture() {
        // Current picture
        return new Picture(this.picture);
    }

    public int width() {
        // width of the current picture
        return this.width;
    }
    public int height() {
        // height of the current picture
        return this.height;
    }
    public double energy(int x, int y) {
        // energy of pixel at column x and row y
        if (x < 0 || y < 0 || x > this.width || y > this.height) {
            throw new IndexOutOfBoundsException("The x, y index out of bounds");
        }
        Color left = new Color(0, 0, 0);
        Color right = new Color(0, 0, 0);
        Color upper = new Color(0, 0, 0);
        Color lower = new Color(0, 0, 0);
        // For x, go to left and right
        if (x - 1 >= 0) { // has left
            left = this.picture.get(x - 1, y);
        } else {
            left = this.picture.get(width - 1, y);
            // if (width - 1 > PICTURE_RANGE) {
            //     left = this.picture.get(PICTURE_RANGE, y);
            // } else {
            //     left = this.picture.get(width - 1, y);
            // }
        }
        if (x + 1 < width) {
            right = this.picture.get(x + 1, y);
        } else {
            right = this.picture.get(0, y);
        }
        double horizontal = Math.pow(left.getRed() - right.getRed(), 2) 
                          + Math.pow(left.getGreen() - right.getGreen(), 2) 
                          + Math.pow(left.getBlue() - right.getBlue(), 2);
        if (y - 1 >= 0) {
            upper = this.picture.get(x, y - 1);
        } else {
            upper = this.picture.get(x, this.height - 1);
            // if (this.height - 1 > PICTURE_RANGE) {
            //     upper = this.picture.get(x, PICTURE_RANGE);
            // } else {
            //     upper = this.picture.get(x, this.height - 1);
            // }
        }
        if (y + 1 < this.height) {
            lower = this.picture.get(x, y + 1);
        } else {
            lower = this.picture.get(x, 0);
        }
        double vertical = Math.pow(upper.getRed() - lower.getRed(), 2) 
                        + Math.pow(upper.getGreen() - lower.getGreen(), 2) 
                        + Math.pow(upper.getBlue() - lower.getBlue(), 2);
        return horizontal + vertical;
        
    }
    public int[] findVerticalSeam() {
        // Sequence of the indices for vertical seam
        int[] path = new int[height];
        double[][] energySaver = new double[width][height];
        if (this.width == 1) {
            Arrays.fill(path, 0);
            return path;
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (j == 0) {
                    energySaver[i][j] = energy(i, j);
                } else if (this.width == 1) {
                    energySaver[i][j] = energy(i, j) + energySaver[i - 1][j];
                } else if (i - 1 >= 0 && i + 1 < width) {
                    double upperLeft = energySaver[i - 1][j - 1];
                    double upperMiddle = energySaver[i][j - 1];
                    double upperRight = energySaver[i + 1][j - 1];
                    energySaver[i][j] = energy(i, j) 
                                      + Math.min(Math.min(upperLeft, upperMiddle), upperRight);
                } else if (i == 0) {
                    energySaver[i][j] = energy(i, j) 
                        + Math.min(energySaver[i][j - 1], energySaver[i + 1][j - 1]);
                } else if (i == width - 1) {
                    energySaver[i][j] = energy(i, j) 
                                      + Math.min(energySaver[i - 1][j - 1], energySaver[i][j - 1]);
                }
                // System.out.print(energySaver[i][j] + "  ");
            }
            // System.out.println(" ");
        }

        // After constructing the 2d array, find the minimum at the bottom line and goes up
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < this.width; i++) {
            if (energySaver[i][this.height - 1] < min) {
                min = energySaver[i][this.height - 1];
                index = i;
            }
        }
        path[this.height - 1] = index;
        // Then go up from this index
        for (int i = this.height - 1; i > 0; i--) {
            index = path[i];
            // System.out.println("the current row index: " + index);
            if (index == 0) { // The base row
                if (energySaver[index][i - 1] <= energySaver[index + 1][i - 1]) {
                    path[i - 1] = index;
                } else if (energySaver[index][i - 1] > energySaver[index + 1][i - 1]) {
                    path[i - 1] = index + 1;
                }
            } else if (index > 0 && index <  width - 1) {
                double upperMiddle = energySaver[index][i - 1];
                double upperRight = energySaver[index + 1][i - 1];
                double upperLeft = energySaver[index - 1][i - 1];
                if (upperLeft == Math.min(upperLeft, Math.min(upperMiddle, upperRight))) {
                    path[i - 1] = index - 1;
                } else if (upperMiddle == Math.min(upperMiddle, Math.min(upperLeft, upperRight))) {
                    path[i - 1] = index;
                } else if (upperRight == Math.min(upperRight, Math.min(upperLeft, upperMiddle))) {
                    path[i - 1] = index + 1;
                }
            } else if (index == width - 1) {
                if (energySaver[index - 1][i - 1] <= energySaver[index][i - 1]) {
                    path[i - 1] = index - 1;
                } else if (energySaver[index][i - 1] < energySaver[index - 1][i - 1]) {
                    path[i - 1] = index;
                }
            }
        }
        // for (int x : path) {
        //     System.out.println(x);
        // }
        return path;
    }
    public int[] findHorizontalSeam() {
        // Sequence of indices for horizontal seam
        // Create a transposed picture
        Picture temp = new Picture(this.height, this.width);
        for (int i = 0; i < temp.width(); i++) {
            for (int j = 0; j < temp.height(); j++) {
                temp.set(i, j, this.picture.get(j, i));
            }
        }
        return new SeamCarver(temp).findVerticalSeam();
    }
    public void removeHorizontalSeam(int[] seam) {
        // Remove horizontal seam from picture
        if (seam.length < 0 || seam.length > this.width) {
            throw new IllegalArgumentException("The size of the seam is not correct.");
        }
        for (int i = 1; i < seam.length - 1; i++) {
            if (Math.abs(seam[i - 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("Not a valid seam.");
            }
        }
        this.picture = SeamRemover.removeHorizontalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }
    public void removeVerticalSeam(int[] seam) {
        // Remove vertical seam from picture
        if (seam.length < 0 || seam.length > this.height) {
            throw new IllegalArgumentException("The size of the seam is not correct.");
        }
        for (int i = 1; i < seam.length - 1; i++) {
            if (Math.abs(seam[i - 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("Not a valid seam.");
            }
        }
        this.picture = SeamRemover.removeVerticalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }

}
