package fr.enissay.enimodels.plugin.management.component.exceptions;

import fr.enissay.enimodels.plugin.management.Project;

public class ComponentsNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * An Exception thrown when the components of a project are not found.
     * Mainly occurs in the class {@link Project}.
     * @param project
     */
    public ComponentsNotFoundException(final Project project) {
        super("It seems that there are no components in the project '" + project.getProjectName() + "'");
    }
}
