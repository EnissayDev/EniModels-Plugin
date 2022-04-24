package fr.enissay.enimodels.plugin.management.component;

import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class Component implements Serializable {

    private String id, iconTexture, childName;
    private BlockLocation blockLocation;
    private transient LinkedList<Component> children = new LinkedList<>();

    public Component(String id, String iconTexture, String childName) {
        this.id = id;
        this.iconTexture = iconTexture;
        this.childName = childName;
    }

    public void setBlockLocation(BlockLocation blockLocation) {
        this.blockLocation = blockLocation;
    }

    public void setChildren(LinkedList<Component> children) {
        this.children = children;
    }

    public BlockLocation getBlockLocation() {
        return blockLocation;
    }

    public String getId() {
        return id;
    }

    public LinkedList<Component> getChildren() {
        return children;
    }

    public String getChildName() {
        return childName;
    }

    protected void setId(final String id){
        this.id = id;
    }

    public String getIconTexture() {
        return iconTexture;
    }

    @Override
    public String toString() {
        return getChildName() + "#" + getId();
        /*return "Component{" +
                "id='" + id + '\'' +
                ", iconTexture='" + iconTexture + '\'' +
                ", childName='" + childName + '\'' +
                '}';*/
    }
}