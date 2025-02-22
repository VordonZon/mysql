package projects;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import java.math.BigDecimal;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

//This class is for the menu-driven application with user input
//Also performs CRUD operations on the project tables

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;

	//@formatter:off
	private List<String> operations = List.of(
		"1) Add a project",
		"2) List all projects",
		"3) Select Project"
	);
	//@formatter:on

	// where the program starts
	public static void main(String[] args) {
		new ProjectsApp().processUserSelection();
	}

	// the main loop of the program
	private void processUserSelection() {
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();

				switch (selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;

				case 2:
					listProjects();
					break;

				case 3:
					selectProject();
					break;

				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
				}

			} catch (Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}
	}

	//Selects project by id
	private void selectProject() {
		Integer projectId = getIntInput("Enter a project ID to select a project");
		curProject = null;
		curProject = projectService.fetchProjectById(projectId);
		
		if(Objects.isNull(curProject)) {
			System.out.println("That was not a valid project. Select a valid project.");
		}
	}

	//prints a list of all the projects in the database
	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		System.out.println("\nProjects:");
		projects.forEach( project -> System.out.println("   " + project.getProjectId() + ": " + project.getProjectName()));
	}

	// sets up and sends the project to be saved to the database
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the project hours");
		Integer difficulty = checkDifficulty();
		String notes = getStringInput("Enter the project notes");
		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}

	// checks if input Int is valid 1 - 5
	private Integer checkDifficulty() {
		Integer num = getIntInput("Enter the project difficulty (1-5)");

		if (!(num > 0 && num <= 5)) {
			System.out.println("Enter a number between 1-5");
			checkDifficulty();
		}
		return num;
	}

	// exits menu
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}

	// gets and checks input during menu selection
	private int getUserSelection() {
		printOperations();

		Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}

	// checks and returns BigDecimal input
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		if (Objects.isNull(input)) {
			return null;
		}

		try {
			return new BigDecimal(input).setScale(2);

		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	// checks and returns valid Int input
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		if (Objects.isNull(input)) {
			return null;
		}

		try {
			return Integer.valueOf(input);

		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}

	// checks and returns valid string input
	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();

		return input.isBlank() ? null : input.trim();
	}

	// prints menu options
	private void printOperations() {
		System.out.println("\nSelect an option. Press the Enter key to quit:");

		for (String line : operations) {
			System.out.println("   " + line);
		}

		if (Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		} else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}

}