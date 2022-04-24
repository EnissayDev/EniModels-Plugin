package fr.enissay.enimodels.plugin.management;

import fr.enissay.enimodels.plugin.management.component.Component;

import java.util.Arrays;
import java.util.LinkedList;

public class ComponentManager {
    //create a LinkedList of Component
    private static LinkedList<Component> componentsType = new LinkedList<Component>();

    //add a componentsType to the LinkedList
    public static void addComponentType(Component component) {
        componentsType.add(component);
    }

    //return the componentsType
    public static LinkedList<Component> getComponentTypes() {
        return componentsType;
    }

    public static void addComponentTypes(Component... components) {
        Arrays.stream(components).forEach(ComponentManager::addComponentType);
    }
}
