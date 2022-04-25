package fr.enissay.enimodels.plugin.management;

import fr.enissay.enimodels.plugin.EniModels;
import fr.enissay.enimodels.plugin.management.component.components.block.Block;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockSize;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockType;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class ModelManager {

    public static LinkedList<ArmorStand> getArmorStandsOfProject(final World world, final Project project){
        final LinkedList<ArmorStand> armorStands = new LinkedList<>();

        world.getEntitiesByClass(ArmorStand.class).forEach(armorStand -> {
            if (armorStand.hasMetadata("ID")) {
                final String ID = armorStand.getMetadata("ID").get(0).asString();
                if (project.getComponents().stream().anyMatch(component -> component.getId().equals(ID))) {
                    armorStands.add(armorStand);
                }
            }
        });
        return armorStands;
    }

    public static void teleportProjectToACenter(final Location center, final Project project){
        getArmorStandsOfProject(center.getWorld(), project).forEach(armorStand -> {
            final BlockLocation blockLocation = project.getComponents().stream().filter(component -> component.getId().equals(armorStand.getMetadata("ID").get(0).asString())).findFirst().get().getBlockLocation();
            int offset = 16;
            switch (blockLocation.getSize()) {
                case MEDIUM:
                    offset = 8;
                case SMALL:
                    offset = 4;
            }
            final Location componentLocation = center.clone().add((blockLocation.getX() / offset), (blockLocation.getY() / offset), (blockLocation.getZ() / offset));
            componentLocation.setYaw(0.0f);
            componentLocation.setPitch(0.0f);

            armorStand.teleport(componentLocation);
        });
    }

    public static void spawnModel(final Project project, final Location location){
        project.getComponents().forEach(component -> {
            if (component instanceof Block) {
                final BlockLocation blockLocation = component.getBlockLocation();
                int offset = 16;
                switch (blockLocation.getSize()) {
                    case MEDIUM:
                        offset = 10;
                    case SMALL:
                        offset = 8;
                }

                final Location componentLocation = location.clone().add((blockLocation.getX() / (blockLocation.getSize() == BlockSize.SMALL ? offset : 16)), (blockLocation.getY() / offset), blockLocation.getZ() / (blockLocation.getSize() == BlockSize.SMALL ? offset : 16));
                componentLocation.setYaw(0.0f);
                componentLocation.setPitch(0.0f);
                final EulerAngle rotation = new EulerAngle(Math.toRadians(blockLocation.getRotateX()), Math.toRadians(blockLocation.getRotateY()), Math.toRadians(blockLocation.getRotateZ()));

                spawnArmorStand(component.getId(), blockLocation, componentLocation, rotation);

                //Bukkit.broadcastMessage("Spawned " + component.getId() + " rotation: " + rotation.getX() + " " + rotation.getY() + " " + rotation.getZ());
            }
        });
    }

    public static Vector rotateModelAroundAxisX(final Vector v, double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;

        return v.setY(y).setZ(z);
    }

    private static void spawnArmorStand(final String ID, final BlockLocation blockLocation, final Location location, final EulerAngle headPose){
        final ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARMOR_STAND);
        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setArms(false);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        armorStand.setSmall(blockLocation.getSize() == BlockSize.MEDIUM);
        armorStand.setHeadPose(headPose);
        armorStand.setMetadata("ID", new FixedMetadataValue(EniModels.getInstance(), ID));
        switch (blockLocation.getSize()){
            case LARGE:
            case MEDIUM:
                armorStand.getEquipment().setHelmet(new ItemStack(translateType(blockLocation.getType())));
                break;
            case SMALL:
                armorStand.setArms(true);
                armorStand.setItemInHand(new ItemStack(translateType(blockLocation.getType())));
                armorStand.setRightArmPose(headPose);
                break;
        }
    }

    public static Material translateType(final BlockType type){
        switch(type){
            case COAL_BLOCK:
                return Material.COAL_BLOCK;
            case STONE:
                return Material.STONE;
            case WOOD_PLANK:
                return Material.WOOD;
            case BRICK:
                return Material.BRICK;
            case CLAY:
                return Material.CLAY;
        }
        return null;
    }
}
