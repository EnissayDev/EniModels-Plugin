package fr.enissay.enimodels.plugin.management.component.exceptions;

public class ProjectNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * An exception that happens when an attempt is made to access a project that does not exist.
     * @param projectName
     */
    public ProjectNotFoundException(String projectName) {
        super("It seems that the project " + projectName + " does not exist.");
    }
}
