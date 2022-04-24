package fr.enissay.enimodels.plugin.management;

import fr.enissay.enimodels.plugin.management.component.Component;

import java.util.Arrays;
import java.util.LinkedList;

public class ComponentManager {
    //create a LinkedList of Component
    private static LinkedList<Component> componentsType = new LinkedList<Component>();

    /**
     * Add a component to the LinkedList of component.
     * @param component
     */
    public static void addComponentType(Component component) {
        componentsType.add(component);
    }

    /**
     * @return LinkedList of the component types.
     */
    public static LinkedList<Component> getComponentTypes() {
        return componentsType;
    }

    /**
     * Add multiple of components to the LinkedList.
     * @param components
     */
    public static void addComponentTypes(Component... components) {
        Arrays.stream(components).forEach(ComponentManager::addComponentType);
    }
}
