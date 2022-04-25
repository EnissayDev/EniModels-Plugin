package fr.enissay.enimodels.plugin;

import fr.enissay.enimodels.plugin.commands.EniModelsCommand;
import fr.enissay.enimodels.plugin.listeners.JoinListener;
import fr.enissay.enimodels.plugin.management.ComponentManager;
import fr.enissay.enimodels.plugin.management.ModelManager;
import fr.enissay.enimodels.plugin.management.Project;
import fr.enissay.enimodels.plugin.management.ProjectManager;
import fr.enissay.enimodels.plugin.management.component.components.Group;
import fr.enissay.enimodels.plugin.management.component.components.Root;
import fr.enissay.enimodels.plugin.management.component.components.block.Block;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ProjectNotFoundException;
import fr.enissay.enimodels.plugin.utils.commands.EniCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class EniModels extends JavaPlugin {

    private static EniModels instance;
    private final LinkedList<EniCommand> commands = new LinkedList<>();

    @Override
    public void onEnable() {
        instance = this;

        ComponentManager.addComponentTypes(new Block(), new Group(), new Root());
        ComponentManager.getComponentTypes().forEach(componentType -> {
            System.out.println(componentType.getClass().getSimpleName());
        });

        /*final Project projectTest = new Project("BRUH");
        try {
            ProjectManager.addProject(projectTest);
        } catch (ProjectNotFoundException | ParsingErrorException e) {
            e.printStackTrace();
        }*/
        //Commands
        commands.addAll(Arrays.asList(new EniModelsCommand(this)));

        commands.forEach(command -> command.register());

        //Listeners
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        /*ProjectManager.getProjects().forEach(project -> {
            final ComponentIterator iterator = new ComponentIterator(project.getComponents().stream().filter(component -> component instanceof Root).findFirst().get());
            while (iterator.hasNext()) {
                final Component foundComponent = iterator.next();
                final BlockLocation blockLocation = foundComponent.getBlockLocation();
                if (blockLocation != null) {
                    final double x = blockLocation.getX();
                    final double y = blockLocation.getY();
                    final double z = blockLocation.getZ();
                    final BlockSize blockSize = blockLocation.getSize();
                    final BlockType blockType = blockLocation.getType();

                    System.out.println("FOUND: " + foundComponent.toString() + " X: " + x + " Y: " + y + " Z: " + z + " W: " + " TYPE: " + blockType.toString() + " SIZE: " + blockSize.toString());
                }
            }
            project.getComponents().forEach(component -> {
                System.out.println(component.toString() + " " + component.getChildren().size());
            });
        });*/
        //Setup files
        File folder = new File("plugins/EniModels/models/");
        if (folder.exists() && folder.listFiles() != null && folder.listFiles().length > 0) {
            Arrays.asList(folder.listFiles()).stream().filter(file -> file.getName().contains(".json")).forEach(file -> {
                final Project project = new Project(file.getName().replace(".json", ""));
                try {
                    ProjectManager.addProject(project);
                } catch (ProjectNotFoundException | ParsingErrorException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Finished: Found " + ProjectManager.getProjects().size() + " projects which has " + ProjectManager.getProjects().stream().mapToInt(project -> project.getComponents().size()).sum() + " components and " + ProjectManager.getProjects().stream().mapToInt(project -> project.getComponents().stream().mapToInt(component -> component.getChildren().size()).sum()).sum() + " children");
    }

    @Override
    public void onDisable() {
        ProjectManager.getProjects().forEach(project -> {
           //get all the worlds
            Bukkit.getWorlds().forEach(world -> {
               ModelManager.getArmorStandsOfProject(world, project).forEach(armorStand -> {
                  armorStand.remove();
               });
            });
        });
    }

    public static void main(String[] args) {
        ComponentManager.addComponentTypes(new Block(), new Group(), new Root());

        ComponentManager.getComponentTypes().forEach(componentType -> {
            System.out.println(componentType.getClass().getSimpleName());
        });

        final Project projectTest = new Project("BRUH");
        try {
            ProjectManager.addProject(projectTest);
        } catch (ProjectNotFoundException | ParsingErrorException e) {
            e.printStackTrace();
        }

        /*ProjectManager.getProjects().forEach(project -> {
            final ComponentIterator iterator = new ComponentIterator(project.getComponents().stream().filter(component -> component instanceof Root).findFirst().get());
            while (iterator.hasNext()) {
                final Component foundComponent = iterator.next();
                final BlockLocation blockLocation = foundComponent.getBlockLocation();
                if (blockLocation != null) {
                    final double x = blockLocation.getX();
                    final double y = blockLocation.getY();
                    final double z = blockLocation.getZ();
                    final BlockSize blockSize = blockLocation.getSize();
                    final BlockType blockType = blockLocation.getType();

                    System.out.println("FOUND: " + foundComponent.toString() + " X: " + x + " Y: " + y + " Z: " + z + " W: " + " TYPE: " + blockType.toString() + " SIZE: " + blockSize.toString());
                }
            }
            project.getComponents().forEach(component -> {
                System.out.println(component.toString() + " " + component.getChildren().size());
            });
        });*/

        System.out.println("Finished: Found " + ProjectManager.getProjects().size() + " projects which has " + ProjectManager.getProjects().stream().mapToInt(project -> project.getComponents().size()).sum() + " components and " + ProjectManager.getProjects().stream().mapToInt(project -> project.getComponents().stream().mapToInt(component -> component.getChildren().size()).sum()).sum() + " children");
    }

    public static EniModels getInstance() {
        return instance;
    }
}
