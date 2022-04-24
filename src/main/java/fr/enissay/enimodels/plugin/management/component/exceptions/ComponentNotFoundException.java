package fr.enissay.enimodels.plugin.management.component.exceptions;

import fr.enissay.enimodels.plugin.management.Project;

public class ComponentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ComponentNotFoundException(final Project project, final String componentName) {
        super("It seems that the component'" + componentName + "' does not exist in the project '" + project.getProjectName() + "'");
    }
}
