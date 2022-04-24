package fr.enissay.enimodels.plugin.management;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.management.component.components.Group;
import fr.enissay.enimodels.plugin.management.component.components.Root;
import fr.enissay.enimodels.plugin.management.component.components.block.Block;

public class ParsingManager {

    /**
     * Method to get a component from its name.
     * @param name
     * @return type of the component
     */
    public static Class<? extends Component> getTypeOfComponent(final String name){
        Class<? extends Component> newType = null;

        if (name.contains("Block")) newType = Block.class;
        else if (name.contains("Group")) newType = Group.class;
        else if (name.contains("Root")) newType = Root.class;

        return newType;
    }
}
