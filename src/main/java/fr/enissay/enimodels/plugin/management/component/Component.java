package fr.enissay.enimodels.plugin.management.component;

import fr.enissay.enimodels.plugin.management.component.components.block.BlockLocation;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class Component implements Serializable {

    private String id, iconTexture, childName;
    private BlockLocation blockLocation;
    private transient LinkedList<Component> children = new LinkedList<>();

    /**
     * Constructor of the Component class.
     * @param id
     * @param iconTexture
     * @param childName
     */
    public Component(String id, String iconTexture, String childName) {
        this.id = id;
        this.iconTexture = iconTexture;
        this.childName = childName;
    }

    /**
     * Method to specify the block location of the component.
     * @param blockLocation
     */
    public void setBlockLocation(BlockLocation blockLocation) {
        this.blockLocation = blockLocation;
    }

    /**
     * Method to specify the the children of the component.
     * @param children
     */
    public void setChildren(LinkedList<Component> children) {
        this.children = children;
    }

    /**
     * @return The BlockLocation of the component.
     */
    public BlockLocation getBlockLocation() {
        return blockLocation;
    }

    /**
     * @return The ID of the component.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The children of the component.
     */
    public LinkedList<Component> getChildren() {
        return children;
    }

    /**
     * @return The childName of the component.
     */
    public String getChildName() {
        return childName;
    }

    /**
     * @param id
     */
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