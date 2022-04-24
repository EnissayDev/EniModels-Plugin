package fr.enissay.enimodels.plugin.management.component.components;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.utils.Mode;

/**
 * @author Enis.S
 */
public class Group extends Component {

    /**
     * A Component that is the Group of a specific model.
     * Created by the abstract class {@link Component}
     */
    public Group() {
        super(Mode.getString(6, Mode.ALPHANUMERIC), "/components/icons/file.png", "Group");
        setBlockLocation(null);
    }
}
