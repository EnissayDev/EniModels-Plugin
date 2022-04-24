package fr.enissay.enimodels.plugin.management;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockSize;
import fr.enissay.enimodels.plugin.management.component.components.block.BlockType;
import fr.enissay.enimodels.plugin.management.component.exceptions.ComponentNotFoundException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ComponentsNotFoundException;
import javafx.scene.control.TreeItem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Project {

    private String projectName;
    private Map<Component, BlockLocation> treeComponent = new HashMap<>();

    /**
     * Constructor of the Project class with the project name.
     * @param projectName
     */
    public Project(final String projectName){
        this.projectName = projectName;
    }

    /**
     * Method to add a component to the project
     * which adds the component to the treeComponent map.
     * @param component
     * @param blockLocation
     */
    public void addComponent(final Component component, final BlockLocation blockLocation){
        this.treeComponent.put(component,blockLocation);
    }

    /**
     * Method to get the component from the treeComponent map
     * with a specific String ID exemple: "o1yRGP".
     * @param id
     * @return The specific component.
     * @throws ComponentNotFoundException
     */
    public Component getComponentById(final String id) throws ComponentNotFoundException {
        final Component componentById = this.treeComponent.keySet().stream()
                .filter(component -> component.getId().equals(id)).findAny().orElse(null);
        if (componentById != null) {
            return componentById;
        }else throw new ComponentNotFoundException(this, id);
    }

    /**
     * Method to get a list of components from the treeComponent map
     * with a specific BlockType. Exemple: BlockType.STONE
     * @param blockType
     * @return List of components.
     * @throws ComponentsNotFoundException
     */
    public LinkedList<Component> getComponentsByType(final BlockType blockType) throws ComponentsNotFoundException {
        final LinkedList<Component> componentByType = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getBlockLocation() != null && component.getBlockLocation().getType() != null && component.getBlockLocation().getType().equals(blockType))
                .collect(java.util.stream.Collectors.toList()));
        if (componentByType.size() > 0) {
            return componentByType;
        }else throw new ComponentsNotFoundException(this);
    }

    /**
     * Method to get a list of components from the treeComponent map
     * with a specific BlockType exemple: BlockSize.LARGE
     * sizes: LARGE, MEDIUM, SMALL
     * @param blockSize
     * @return List of components.
     * @throws ComponentsNotFoundException
     */
    public LinkedList<Component> getComponentsBySize(final BlockSize blockSize) throws ComponentsNotFoundException {
        final LinkedList<Component> componentBySize = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getBlockLocation() != null && component.getBlockLocation().getSize() != null && component.getBlockLocation().getSize().equals(blockSize))
                .collect(java.util.stream.Collectors.toList()));
        if (componentBySize.size() > 0) {
            return componentBySize;
        }else throw new ComponentsNotFoundException(this);
    }

    /**
     * Method to get a list of components from the treeComponent map
     * with a specific String childName exemple: "Block"
     * Important childNames: Block, Root, Group
     * @param childName
     * @return List of components.
     * @throws ComponentsNotFoundException
     */
    public LinkedList<Component> getComponentsByChildName(final String childName) throws ComponentsNotFoundException {
        final LinkedList<Component> componentByChildName = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getChildName().equals(childName))
                .collect(java.util.stream.Collectors.toList()));
        if (componentByChildName.size() > 0) {
            return componentByChildName;
        }else throw new ComponentsNotFoundException(this);
    }

    /**
     * @return Name of the Project.
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Remove a Component from its {@link BlockLocation}
     * @param blockLocation
     */
    public void removeComponent(final BlockLocation blockLocation){
        this.treeComponent.remove(blockLocation);
    }

    /**
     * @return List of components.
     */
    public LinkedList<Component> getComponents() {
        return new LinkedList<Component>(this.treeComponent.keySet());
    }
}