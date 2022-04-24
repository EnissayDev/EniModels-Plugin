package fr.enissay.enimodels.plugin.management;

import com.google.gson.Gson;
import fr.enissay.enimodels.plugin.EniModels;
import fr.enissay.enimodels.plugin.management.component.Component;
import fr.enissay.enimodels.plugin.management.component.components.ComponentIterator;
import fr.enissay.enimodels.plugin.management.component.exceptions.ParsingErrorException;
import fr.enissay.enimodels.plugin.management.component.exceptions.ProjectNotFoundException;
import fr.enissay.enimodels.plugin.utils.FileUtils;
import fr.enissay.enimodels.plugin.utils.ParseTreeItem;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class ProjectManager {

    private final static LinkedList<Project> projects = new LinkedList<>();

    /**
     * Load a project from a json file
     * with parseTreeView method from the
     * {@link ParseTreeItem} class and
     * handle the parsing errors {@link ParsingErrorException}.
     * @param project
     * @throws ProjectNotFoundException
     * @throws ParsingErrorException
     */
    public static void addProject(Project project) throws ProjectNotFoundException, ParsingErrorException {
        //Create a file
        final String file;
        try {
            file = FileUtils.readFile(EniModels.class.getResourceAsStream("/" + project.getProjectName() + ".json"));
        } catch (IOException e) {
            throw new ProjectNotFoundException(project.getProjectName());
        }
        //Create a JSONObject from the file
        final JSONObject jsonObject = new JSONObject(file);
        final Gson gson = new Gson();
        final JSONObject search = jsonObject.getJSONObject("value");

        //Create Temporary Component
        Component component = null;
        final String childName = search.getString("childName");
        final Class<? extends Component> type = ParsingManager.getTypeOfComponent(childName);

        //Set the component
        component = gson.fromJson(search.toString(), type);

        try {
            //Handle everything with parseTreeView method
            ParseTreeItem.parseTreeView(jsonObject, component);
        } catch (JSONException e) {
            throw new ParsingErrorException(project, e.getMessage());
        }
        //Add the component to the project
        project.getComponents().add(component);

        //Search if the component has children
        final ComponentIterator iterator = new ComponentIterator(component);
        while (iterator.hasNext()) {
            final Component foundComponent = iterator.next();
            project.addComponent(foundComponent, foundComponent.getBlockLocation());
        }
        //Finally add the result to the projects list
        projects.add(project);
    }

    /**
     * Search the project with the given name.
     * @param name
     * @return The specific Project.
     */
    public Project getProject(String name) {
        return projects.stream().filter(project -> project.getProjectName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Get the list of all the projects
     * @return List of all the projects
     */
    public static LinkedList<Project> getProjects() {
        return projects;
    }

    /**
     * Remove all the projects
     */
    @Deprecated
    public void removeAllProjects() {
        projects.clear();
    }

    /**
     * Remove the project with the given name
     * @param project
     */
    public void removeProject(Project project) {
        projects.remove(project);
    }

    /**
     * Remove the project with the given name
     * @param projectName
     */
    public static void removeProject(String projectName) {
        projects.removeIf(project -> project.getProjectName().equals(projectName));
    }
}
