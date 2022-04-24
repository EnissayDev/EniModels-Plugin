package fr.enissay.enimodels.plugin.management.component.components.block;

import java.io.Serializable;

public class BlockLocation implements Serializable {

    private double x,y,z;
    private double rotateX,rotateY,rotateZ;
    private BlockSize size;
    private BlockType type;

    /**
     *
     * Create a new Instance of {@link BlockLocation}
     * with its given parameters
     *
     * @param x
     * @param y
     * @param z
     * @param rotateX
     * @param rotateY
     * @param rotateZ
     * @param size
     * @param type
     */
    public BlockLocation(double x, double y, double z, double rotateX, double rotateY, double rotateZ, BlockSize size, BlockType type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
        this.size = size;
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getRotateX() {
        return rotateX;
    }

    public double getRotateY() {
        return rotateY;
    }

    public double getRotateZ() {
        return rotateZ;
    }

    public BlockSize getSize() {
        return size;
    }

    public BlockType getType() {
        return type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setRotateX(double rotateX) {
        this.rotateX = rotateX;
    }

    public void setRotateY(double rotateY) {
        this.rotateY = rotateY;
    }

    public void setRotateZ(double rotateZ) {
        this.rotateZ = rotateZ;
    }

    public void setSize(BlockSize size) {
        this.size = size;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BlockLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", rotateX=" + rotateX +
                ", rotateY=" + rotateY +
                ", rotateZ=" + rotateZ +
                ", size=" + size +
                ", type=" + type +
                '}';
    }
}