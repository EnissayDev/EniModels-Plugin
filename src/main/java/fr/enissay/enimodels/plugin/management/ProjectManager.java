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

    public static void addProject(Project project) throws ProjectNotFoundException, ParsingErrorException {
        final String file;
        try {
            file = FileUtils.readFile(EniModels.class.getResourceAsStream("/" + project.getProjectName() + ".json"));
        } catch (IOException e) {
            throw new ProjectNotFoundException(project.getProjectName());
        }
        final JSONObject jsonObject = new JSONObject(file);
        final Gson gson = new Gson();
        final JSONObject search = jsonObject.getJSONObject("value");

        Component component = null;
        final String childName = search.getString("childName");
        final Class<? extends Component> type = ParsingManager.getTypeOfComponent(childName);

        component = gson.fromJson(search.toString(), type);

        try {
            ParseTreeItem.parseTreeView(jsonObject, component);
        } catch (JSONException e) {
            throw new ParsingErrorException(project, e.getMessage());
        }
        project.getComponents().add(component);

        final ComponentIterator iterator = new ComponentIterator(component);
        while (iterator.hasNext()) {
            final Component foundComponent = iterator.next();
            project.addComponent(foundComponent, foundComponent.getBlockLocation());
        }
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public Project getProject(String name) {
        return projects.stream().filter(project -> project.getProjectName().equals(name)).findFirst().orElse(null);
    }

    public static LinkedList<Project> getProjects() {
        return projects;
    }

    public void removeAllProjects() {
        projects.clear();
    }

    public void removeProject(String name) {
        projects.stream().filter(project -> project.getProjectName().equals(name)).findFirst().ifPresent(projects::remove);
    }
}
