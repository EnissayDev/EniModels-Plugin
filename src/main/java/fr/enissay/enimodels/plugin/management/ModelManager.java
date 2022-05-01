package fr.enissay.enimodels.plugin.management;

import com.google.common.collect.Lists;
import fr.enissay.enimodels.plugin.EniModels;
import fr.enissay.enimodels.plugin.management.component.components.block.Block;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockSize;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockType;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
                    offset = 16;
                /*case SMALL:
                    offset = 4;*/
            }
            final Location componentLocation = center.clone().add((blockLocation.getX() / offset), (blockLocation.getY() / offset), (blockLocation.getZ() / offset));
            componentLocation.setYaw(0.0f);
            componentLocation.setPitch(0.0f);

            Vector v = componentLocation.toVector().subtract(center.toVector());
            ModelManager.rotateModelAroundAxisX(v, armorStand.getLocation().getPitch() * 0.017453292F);
            Location result = componentLocation.clone().add(v);
            armorStand.teleport(result);

            //armorStand.teleport(componentLocation);
        });
    }

    public static void spawnModel(final Project project, final Location location){
        project.getComponents().forEach(component -> {
            if (component instanceof Block) {
                final BlockLocation blockLocation = component.getBlockLocation();
                /*blockLocation.setRotateX(0);
                blockLocation.setRotateY(0);
                blockLocation.setRotateZ(0);*/
                final Location componentLocation = location.clone().add((blockLocation.getX() / 16), (blockLocation.getY() / (blockLocation.getSize() == BlockSize.MEDIUM ? 16 : 16)) , blockLocation.getZ() / 16);
                componentLocation.setYaw(0.0f);
                componentLocation.setPitch(0.0f);

                /*if (blockLocation.getSize() == BlockSize.MEDIUM) {
                    Bukkit.broadcastMessage(ChatColor.BLUE + "*size: " + blockLocation.getSize() + " OG X: " + blockLocation.getX() + " OG Y: " + blockLocation.getY() + " OG Z: " + blockLocation.getZ());
                    Bukkit.broadcastMessage(ChatColor.AQUA + "-> X: " + (blockLocation.getX() / 16) + " Y: " + (blockLocation.getY() / (blockLocation.getSize() == BlockSize.MEDIUM ? 10 : 16)) + " Z: " + (blockLocation.getZ() / 16));
                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "--> X: " + componentLocation.getX() + " Y: " + componentLocation.getY() + " Z: " + componentLocation.getZ());

                }*/
                final EulerAngle rotation = new EulerAngle(Math.toRadians(blockLocation.getRotateX()), Math.toRadians(blockLocation.getRotateY()), Math.toRadians(blockLocation.getRotateZ()));

                spawnArmorStand(component.getId(), blockLocation, componentLocation.add(/*(blockLocation.getSize() == BlockSize.SMALL ? 0.2 : 0)*/0, (blockLocation.getSize() == BlockSize.MEDIUM ? 0.75 : 0), 0)/*.subtract(0, (blockLocation.getSize() == BlockSize.MEDIUM ? 4.19365 : 0), 0)*/, rotation);
                //Bukkit.broadcastMessage("Spawned " + component.getId() + " rotation: " + rotation.getX() + " " + rotation.getY() + " " + rotation.getZ());
                /*new BukkitRunnable(){

                    @Override
                    public void run() {
                        //get highest Y value in blocklocation from project.getComponents()
                        final double highestY = project.getComponents().stream()
                                .filter(component1 -> component1 instanceof Block && component1.getBlockLocation() != null && Double.valueOf(component1.getBlockLocation().getY()) != null)
                                .mapToDouble(component1 -> component1.getBlockLocation().getY()).max().orElse(0);
                        getHollowCube(highestY, 0, location, 0.3).forEach(block -> {
                            location.getWorld().playEffect(block, Effect.COLOURED_DUST, 1);
                        });
                    }
                }.runTaskTimer(EniModels.getInstance(), 0, 10);*/
            }
        });
    }

    public static List<Location> getHollowCube(double max, double min, Location loc, double particleDistance) {
        List<Location> result = Lists.newArrayList();
        World world = loc.getWorld();
        double minX = loc.getBlockX();
        double minY = loc.getBlockY();
        double minZ = loc.getBlockZ();
        double maxX = loc.getBlockX()+(max/7);
        double maxY = loc.getBlockY()+(max/7);
        double maxZ = loc.getBlockZ()+(max/7);

        for (double x = minX; x <= maxX; x = Math.round((x + particleDistance) * 1e2) / 1e2) {
            for (double y = minY; y <= maxY; y = Math.round((y + particleDistance) * 1e2) / 1e2) {
                for (double z = minZ; z <= maxZ; z = Math.round((z + particleDistance) * 1e2) / 1e2) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }

    public static Vector rotateModelAroundAxisX(final Vector v, double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;

        return v.setX(x).setZ(z);
    }

    private static void spawnArmorStand(final String ID, final BlockLocation blockLocation, final Location location, final EulerAngle headPose){
        final ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARMOR_STAND);
        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setArms(false);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        armorStand.setCanPickupItems(false);
        armorStand.setSmall(blockLocation.getSize() == BlockSize.MEDIUM);
        armorStand.setHeadPose(headPose);
        armorStand.setMetadata("ID", new FixedMetadataValue(EniModels.getInstance(), ID));
        switch (blockLocation.getSize()){
            case LARGE:
            case MEDIUM:
                armorStand.getEquipment().setHelmet(new ItemStack(translateType(blockLocation.getType())));
                break;
            /*case SMALL:
                armorStand.setArms(true);
                armorStand.setItemInHand(new ItemStack(translateType(blockLocation.getType())));
                //DEFAULT:
                //BLOCK_ANGLE: -43, -41.5, 19.5
                //ITEM_ANGLE: -20, 0, 0
                armorStand.setRightArmPose(new EulerAngle(Math.toRadians(-43), Math.toRadians(-40.5), Math.toRadians(19.5)));
                break;*/
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
