package fr.enissay.enimodels.plugin.management.component.components;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.utils.Mode;

public class Group extends Component {

    public Group() {
        super(Mode.getString(6, Mode.ALPHANUMERIC), "/components/icons/file.png", "Group");
        setBlockLocation(null);
    }

}
