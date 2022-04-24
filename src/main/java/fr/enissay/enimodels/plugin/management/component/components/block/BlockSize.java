package fr.enissay.enimodels.plugin.management.component.components.block;

public enum BlockSize {

    SMALL(5, "SMALL"),
    MEDIUM(7, "MEDIUM"),
    LARGE(10, "LARGE");

    private int pixel;
    private String name;

    BlockSize(final int pixel, final String name) {
        this.pixel = pixel;
        this.name = name;
    }

    public int getPixel() {
        return pixel;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}