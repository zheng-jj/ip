import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pingu {

    private static final Path DATA_FILE_PATH = Paths.get("data", "pingu.txt");

    public enum Tasks {
                TODO,
                DEADLINE,
                EVENT
    }

    public static Task createTask(Tasks taskType, String taskString, String divider) {
    String[] taskData;
    try {
        switch (taskType) {
            case TODO:
                String todoDescription = taskString.substring(5);
                if (todoDescription.trim().isEmpty()) {
                    throw new StringIndexOutOfBoundsException();
                }
                Task newTodo = new Task(todoDescription);
                System.out.println("Got it. I've added this task:");
                System.out.println(newTodo.toString());
                return newTodo;
                
            case DEADLINE:
                taskData = taskString.substring(9).split(" /by ");
                if (taskData.length < 2 || taskData[0].trim().isEmpty() || taskData[1].trim().isEmpty()) {
                    throw new StringIndexOutOfBoundsException();
                }
                Deadline newDeadline = new Deadline(taskData[0], taskData[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println(newDeadline.toString());
                return newDeadline;
                
            case EVENT:
                taskData = taskString.substring(6).split(" /from | /to ");
                if (taskData.length < 3 || taskData[0].trim().isEmpty() || 
                    taskData[1].trim().isEmpty() || taskData[2].trim().isEmpty()) {
                    throw new StringIndexOutOfBoundsException();
                }
                Event newEvent = new Event(taskData[0], taskData[1], taskData[2]);
                System.out.println("Got it. I've added this task:");
                System.out.println(newEvent.toString());
                return newEvent;
                
            default:
                return null;
        }
    } catch (DateTimeParseException e) {
        printMsg("Invalid date format. Please use d/M/yyyy HHmm format (e.g., 2/12/2019 1800).", divider);
        return null;
    } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
        printMsg("Invalid task details. Try again.", divider);
        return null;
    } catch (Exception e) {
        printMsg("Error: " + e.toString(), divider);
        return null;
    }
}

    /**
     * Saves the current list of tasks to the data file.
     *
     * @param taskList The list of tasks to save.
     */
    public static void saveTasks(ArrayList<Task> taskList) {
        try {
            // Ensure the parent directory exists
            Files.createDirectories(DATA_FILE_PATH.getParent());
            FileWriter writer = new FileWriter(DATA_FILE_PATH.toFile());
            for (Task task : taskList) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the data file into the task list.
     * Handles file/directory creation and corrupted data.
     *
     * @return An ArrayList of tasks loaded from the file.
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        File dataFile = DATA_FILE_PATH.toFile();
        DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        try {
            if (!Files.exists(DATA_FILE_PATH.getParent())) {
                Files.createDirectories(DATA_FILE_PATH.getParent());
            }
            
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                return taskList;
            }
            
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                try {
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task = null;
                    
                    switch (type) {
                        case "T":
                            task = new Task(description);
                            break;
                        case "D":
                            LocalDateTime by = LocalDateTime.parse(parts[3], fileFormat);
                            task = new Deadline(description, by);
                            break;
                        case "E":
                            LocalDateTime from = LocalDateTime.parse(parts[3], fileFormat);
                            LocalDateTime to = LocalDateTime.parse(parts[4], fileFormat);
                            task = new Event(description, from, to);
                            break;
                    }
                    
                    if (task != null) {
                        if (isDone) {
                            task.toggleStatus();
                        }
                        taskList.add(task);
                    }
                } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                    System.out.println("Warning: Skipping corrupted or malformed task line in data file: " + line);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
        
        return taskList;
    }


    public static void printMsg(String msg, String divider) {
        System.out.println(msg);
        System.out.println(divider);
    }

    public static String getInput(Scanner scanner, String divider) {
        String input = scanner.nextLine();
        System.out.println(divider);
        return input;
    }

    /**
     * Generates a formatted string of all tasks in the list.
     *
     * @param taskList The list of tasks.
     * @return A formatted string for display.
     */
    public static String listText(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder combinedString = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            combinedString.append(i + 1).append(".").append(taskList.get(i).toString()).append("\n");
        }
        return combinedString.toString().trim();
    }

    /**
     * Main loop to handle user input and commands.
     *
     * @param scanner The scanner to read user input.
     * @param divider A string for formatting output.
     */
    public static void handleInput(Scanner scanner, String divider) {
        String input;
        String breakCon = "bye";
        ArrayList<Task> taskList = loadTasks();

        while (true) {
            input = getInput(scanner, divider);
            if (input.equals(breakCon)) {
                break;
            }

            String[] inputSplit = input.split(" ", 2);
            String command = inputSplit[0];
            int taskIndex;

            try {
                switch (command) {
                case "list":
                    printMsg(listText(taskList), divider);
                    break;
                case "mark":
                    taskIndex = Integer.parseInt(inputSplit[1]) - 1;
                    taskList.get(taskIndex).toggleStatus();
                    System.out.println("Nice! I've marked this task as done:");
                    printMsg(taskList.get(taskIndex).toString(), divider);
                    saveTasks(taskList);
                    break;
                case "unmark":
                    taskIndex = Integer.parseInt(inputSplit[1]) - 1;
                    taskList.get(taskIndex).toggleStatus();
                    System.out.println("OK, I've marked this task as not done yet:");
                    printMsg(taskList.get(taskIndex).toString(), divider);
                    saveTasks(taskList);
                    break;
                case "delete":
                    taskIndex = Integer.parseInt(inputSplit[1]) - 1;
                    Task removedTask = taskList.remove(taskIndex);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(removedTask.toString());
                    printMsg("Now you have " + taskList.size() + " tasks in the list.", divider);
                    saveTasks(taskList);
                    break;
                case "event":
                case "deadline":
                case "todo":
                    Tasks taskType = Tasks.valueOf(command.toUpperCase());
                    Task newTask = createTask(taskType, input, divider);
                    if (newTask != null) {
                        taskList.add(newTask);
                        printMsg("Now you have " + taskList.size() + " tasks in the list.", divider);
                        saveTasks(taskList);
                    }
                    break;
                default:
                    printMsg("OOPS!!! I'm sorry, but I don't know what that means :-(", divider);
                    break;
                }
            } catch (NumberFormatException e) {
                printMsg("Invalid task number. Please provide a valid number.", divider);
            } catch (IndexOutOfBoundsException e) {
                printMsg("Task number is out of bounds. Please enter a number from the list.", divider);
            } catch (Exception e) {
                printMsg("An unexpected error occurred: " + e.getMessage(), divider);
            }
        }
    }

    public static void main(String[] args) {
        String chatbotName = "Pingu";
        String divider = "_".repeat(60);
        String introMessage = "Hello! I'm " + chatbotName + "\n"
                + "What can I do for you?";
        String closeMessage = "Bye. Hope to see you again soon!";
        Scanner scanner = new Scanner(System.in);

        System.out.println(divider);
        printMsg(introMessage, divider);
        handleInput(scanner, divider);
        printMsg(closeMessage, divider);
        scanner.close();
    }
}
