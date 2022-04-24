package fr.enissay.enimodels.plugin.management.component.exceptions;

import fr.enissay.enimodels.plugin.management.Project;

public class ComponentsNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ComponentsNotFoundException(final Project project) {
        super("It seems that there are no components in the project '" + project.getProjectName() + "'");
    }
}
