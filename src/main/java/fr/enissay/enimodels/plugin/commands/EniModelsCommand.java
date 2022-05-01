package fr.enissay.enimodels.plugin.commands;

import com.google.common.collect.Lists;
import fr.enissay.enimodels.plugin.EniModels;
import fr.enissay.enimodels.plugin.management.ModelManager;
import fr.enissay.enimodels.plugin.management.ProjectManager;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockSize;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ProjectNotFoundException;
import fr.enissay.enimodels.plugin.utils.commands.EniCommand;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EniModelsCommand extends EniCommand {

    public EniModelsCommand(Plugin plugin) {
        super(plugin, "enimodels", "enimodels.use", "CMD", new String[]{"enim", "em", "enimodel", "emodel", "emodels"});
        this.setMinArg(0);
        this.setAllowConsole(false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("spawn")) {
                if (args.length > 1) {
                    final String modelName = args[1];
                    try {
                        if (ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName)).size() <= 0) {
                            ModelManager.spawnModel(ProjectManager.getProject(modelName), getPlayer().getLocation());
                        }else ModelManager.teleportProjectToACenter(getPlayer().getLocation(), ProjectManager.getProject(modelName));

                        getPlayer().playSound(getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                        sendSuccess("Spawned model " + modelName + " at your location with " + ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName)).size() + " ArmorStands.");
                    } catch (ProjectNotFoundException e) {
                        sendError("The Project has not been found.");
                        e.printStackTrace();
                    } catch (ParsingErrorException e) {
                        sendError("There was an unexpected exception while parsing the model.");
                        sendError(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }else if (args[0].equalsIgnoreCase("list")) {
                sendSuccess("List of available models : ");
                ProjectManager.getProjects().forEach(project -> {
                    sendMessage(ChatColor.BLUE + "Project: " + ChatColor.AQUA + project.getProjectName());
                    sendMessage(ChatColor.DARK_GRAY + " - Components: " + ChatColor.GRAY + project.getComponents().size());
                    sendMessage(ChatColor.DARK_GRAY + " - ArmorStands: " + ChatColor.GRAY + ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), project).size());
                    sendMessage("");
                });
            }else if (args[0].equalsIgnoreCase("test")){
                final Location location = getPlayer().getLocation();
                final ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARMOR_STAND);
                final EulerAngle eulerAngle = armorStand.getHeadPose();

                armorStand.setCustomNameVisible(true);
                armorStand.setGravity(false);
                armorStand.setArms(false);
                armorStand.setVisible(false);
                armorStand.setBasePlate(false);
                armorStand.setCanPickupItems(false);
                armorStand.setSmall(false);
                armorStand.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));

                new BukkitRunnable() {
                    double angle = 0;
                    double result = 0;
                    double offset = 0;

                    @Override
                    public void run() {
                        //TESTING ONLY ON X AXIS
                        //height of head in blocks: 0.425
                        //height of head in blocks (small): 0.2375

                        armorStand.setCustomName("angle: " + angle);
                        armorStand.setHeadPose(new EulerAngle(0, 0, Math.toRadians(angle)));

                        if (angle <= 90) {
                            result = 90 - angle;
                            offset = -(1 - result)/90;
                            armorStand.teleport(location.clone().add(0, 0, offset));
                        }else if (angle <= 270){
                            result = 270 - angle;
                            offset = (1 - result)/270;
                            armorStand.teleport(location.clone().add(0, 0, offset));
                        }
                        angle++;
                        if (angle >= 360) angle = 0;
                    }
                }.runTaskTimer(EniModels.getInstance(), 2, 2);
            }else if (args[0].equalsIgnoreCase("showcase")) {
                if (args.length > 1) {
                    final String modelName = args[1];
                    try {
                        final LinkedList<ArmorStand> armorStands = ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                armorStands.forEach(armorStand -> {
                                    Vector v = armorStand.getVelocity().clone();
                                    ModelManager.rotateModelAroundAxisX(v, armorStand.getLocation().getPitch() * 0.017453292F);
                                    Location result = armorStand.getLocation().clone().add(v);
                                    armorStand.teleport(result);
                                });
                            }
                        }.runTaskTimer(EniModels.getInstance(), 2, 2);
                        sendSuccess("Showing model " + modelName + " at your location with " + ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName)).size() + " ArmorStands.");
                    } catch (ProjectNotFoundException e) {
                        sendError("The Project has not been found.");
                        e.printStackTrace();
                    } catch (ParsingErrorException e) {
                        sendError("There was an unexpected exception while parsing the model.");
                        sendError(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length > 1) {
                    final String modelName = args[1];
                    try {
                        final LinkedList<ArmorStand> armorStands = ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName));
                        armorStands.forEach(Entity::remove);

                        sendSuccess("Removed model " + modelName + " with " + ModelManager.getArmorStandsOfProject(getPlayer().getWorld(), ProjectManager.getProject(modelName)).size() + " ArmorStands.");
                    } catch (ProjectNotFoundException e) {
                        sendError("The Project has not been found.");
                        e.printStackTrace();
                    } catch (ParsingErrorException e) {
                        sendError("There was an unexpected exception while parsing the model.");
                        sendError(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Lists.newLinkedList(Arrays.asList("spawn", "list"));
    }
}
