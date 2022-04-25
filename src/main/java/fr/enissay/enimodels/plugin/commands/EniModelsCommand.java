package fr.enissay.enimodels.plugin.commands;

import com.google.common.collect.Lists;
import fr.enissay.enimodels.plugin.EniModels;
import fr.enissay.enimodels.plugin.management.ModelManager;
import fr.enissay.enimodels.plugin.management.ProjectManager;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ProjectNotFoundException;
import fr.enissay.enimodels.plugin.utils.commands.EniCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
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
