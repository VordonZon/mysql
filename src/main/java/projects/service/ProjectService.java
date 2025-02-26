package projects.service;

import java.util.List;
import java.util.NoSuchElementException; //Is this supposed to be here?

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

public class ProjectService {
	// This method calls the DAO class to insert a new project row.
	private ProjectDao projectDao = new ProjectDao();

	//adds a project to the database
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

	//returns a list of all the projects in the database
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	//retrieves project and throws error if project id does not match any in the database
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));

	}
	
	//Updates project if project exists
	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID=" + project.getProjectId() + " does not exist.");
		}
	}

	//Deletes project if project exists
	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
	}
	
	
}
