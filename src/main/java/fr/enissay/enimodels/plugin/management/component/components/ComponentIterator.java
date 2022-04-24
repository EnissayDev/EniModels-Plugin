package fr.enissay.enimodels.plugin.management.component.components;

import fr.enissay.enimodels.plugin.management.component.Component;
import javafx.scene.control.TreeItem;

import java.util.Iterator;
import java.util.Stack;

public class ComponentIterator  implements Iterator<Component> {
    private Stack<Component> stack = new Stack<>();

    public ComponentIterator(Component root) {
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Component next() {
        Component nextItem = stack.pop();
        if (nextItem.getChildren() != null)
            nextItem.getChildren().forEach(stack::push);
        return nextItem;
    }
}
