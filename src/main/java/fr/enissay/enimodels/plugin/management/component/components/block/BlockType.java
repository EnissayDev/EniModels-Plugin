package fr.enissay.enimodels.plugin.management.component.components.block;

public enum BlockType {

    STONE("/blocks/stone.png"),
    BEDROCK("/blocks/bedrock.png"),
    BRICK("/blocks/brick.png"),
    CLAY("/blocks/clay.png"),
    COAL_BLOCK("/blocks/coal_block.png"),
    COAL_ORE("/blocks/coal_ore.png"),
    COARSE_DIRT("/blocks/coarse_dirt.png"),
    COBBLESTONE("/blocks/cobblestone.png"),
    COBBLESTONE_MOSSY("/blocks/cobblestone_mossy.png"),
    DIAMOND_BLOCK("/blocks/diamond_block.png"),
    DIAMOND_ORE("/blocks/diamond_ore.png"),
    DIRT("/blocks/dirt.png"),
    GLASS_SILVER("/blocks/glass_silver.png"),
    WOOD_PLANK("/blocks/wood_plank.png");

    private String texture;

    BlockType(final String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}