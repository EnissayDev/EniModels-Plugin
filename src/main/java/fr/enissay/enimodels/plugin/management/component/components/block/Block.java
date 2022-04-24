package fr.enissay.enimodels.plugin.management.component.components.block;

import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.utils.Mode;

public class Block extends Component {

    public Block() {
        super(Mode.getString(6, Mode.ALPHANUMERIC), "/components/icons/cube.png", "Block");
        setBlockLocation(null);
    }

    /*public Block(final Box box) {
        super(Mode.getString(6, Mode.ALPHANUMERIC), "/components/icons/cube.png", "Block");
        this.box = box;
        setBlockLocation(new Managers().getBlocksManager().getBlockLocation(box));
    }*/
}
