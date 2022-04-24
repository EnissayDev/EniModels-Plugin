package fr.enissay.enimodels.plugin.management.component.exceptions;

public class ProjectNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProjectNotFoundException(String projectName) {
        super("It seems that the project " + projectName + " does not exist.");
    }
}
