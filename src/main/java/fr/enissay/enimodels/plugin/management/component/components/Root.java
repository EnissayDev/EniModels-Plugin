package fr.enissay.enimodels.plugin.management.component.components;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.utils.Mode;

public class Root extends Component {

    public Root() {
        super(Mode.getString(6, Mode.ALPHANUMERIC), "/components/icons/root.png", "Root");
        setBlockLocation(null);
    }
}
