package fr.enissay.enimodels.plugin.management.component.exceptions;

import fr.enissay.enimodels.plugin.management.Project;

public class ComponentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * An Exception thrown when the components of a project are not found.
     * Mainly occurs in the class {@link Project}.
     * @param project
     * @param componentName
     */
    public ComponentNotFoundException(final Project project, final String componentName) {
        super("It seems that the component'" + componentName + "' does not exist in the project '" + project.getProjectName() + "'");
    }
}
