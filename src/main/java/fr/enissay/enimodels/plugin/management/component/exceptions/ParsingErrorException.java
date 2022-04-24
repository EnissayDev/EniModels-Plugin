package fr.enissay.enimodels.plugin.management.component.exceptions;

import fr.enissay.enimodels.plugin.management.Project;

public class ParsingErrorException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * An exception that happens when an attempt is made to load a JSONProject
     * with an unexpected malfunction.
     * @param project
     * @param error
     */
    public ParsingErrorException(final Project project, final String error) {
        super("Parsing error in project " + project.getProjectName() + ": " + error);
    }
}
