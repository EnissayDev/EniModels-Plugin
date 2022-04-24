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

    public Project(final String projectName){
        this.projectName = projectName;
    }

    public void addComponent(final Component component, final BlockLocation blockLocation){
        this.treeComponent.put(component,blockLocation);
    }

    public Component getComponentById(final String id) throws ComponentNotFoundException {
        final Component componentById = this.treeComponent.keySet().stream()
                .filter(component -> component.getId().equals(id)).findAny().orElse(null);
        if (componentById != null) {
            return componentById;
        }else throw new ComponentNotFoundException(this, id);
    }

    public LinkedList<Component> getComponentsByType(final BlockType blockType) throws ComponentsNotFoundException {
        final LinkedList<Component> componentByType = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getBlockLocation() != null && component.getBlockLocation().getType() != null && component.getBlockLocation().getType().equals(blockType))
                .collect(java.util.stream.Collectors.toList()));
        if (componentByType.size() > 0) {
            return componentByType;
        }else throw new ComponentsNotFoundException(this);
    }

    public LinkedList<Component> getComponentsBySize(final BlockSize blockSize) throws ComponentsNotFoundException {
        final LinkedList<Component> componentBySize = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getBlockLocation() != null && component.getBlockLocation().getSize() != null && component.getBlockLocation().getSize().equals(blockSize))
                .collect(java.util.stream.Collectors.toList()));
        if (componentBySize.size() > 0) {
            return componentBySize;
        }else throw new ComponentsNotFoundException(this);
    }

    public LinkedList<Component> getComponentsByChildName(final String childName) throws ComponentsNotFoundException {
        final LinkedList<Component> componentByChildName = new LinkedList<Component>(this.treeComponent.keySet().stream()
                .filter(component -> component.getChildName().equals(childName))
                .collect(java.util.stream.Collectors.toList()));
        if (componentByChildName.size() > 0) {
            return componentByChildName;
        }else throw new ComponentsNotFoundException(this);
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void removeComponent(final BlockLocation blockLocation){
        this.treeComponent.remove(blockLocation);
    }

    public LinkedList<Component> getComponents() {
        return new LinkedList<Component>(this.treeComponent.keySet());
    }
}