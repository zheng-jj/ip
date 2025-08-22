import java.util.ArrayList;
import java.util.Scanner;

public class Pingu {
    
    public enum Tasks {
                TODO,
                DEADLINE,
                EVENT
    }

    public static Task createTask(Tasks taskType, String taskString) {
        String[] taskData = null;
        switch (taskType) {
            case TODO:
                System.out.println("Got it. I've added this task:");
                Task newTodo = new Task(taskString.substring(5));
                System.out.println(newTodo.toString());
                return newTodo;
            case DEADLINE:
                System.out.println("Got it. I've added this task:");
                taskData = taskString.substring(9).split(" /by ");
                Deadline newDeadline = new Deadline(taskData[0], taskData[1]);
                System.out.println(newDeadline.toString());
                return newDeadline;
            case EVENT:
                System.out.println("Got it. I've added this task:");
                taskData = taskString.substring(6).split(" /to | /from ");
                Event newEvent = new Event(taskData[0], taskData[1], taskData[2]);
                System.out.println(newEvent.toString());
                return newEvent;
            default:
                System.out.println("Got it. I've added this task:");
                Task newTask = new Task(taskString.substring(5));
                System.out.println(newTask.toString());
                return newTask;
        }
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

    public static String listText(ArrayList<Task> taskL, String divider) {
        if (taskL.size() == 0) {
            return "";
        }
        String combinedString = "Here are the tasks in your list:\n";
        int counter = 0;
        for (Task x : taskL){
            combinedString += (counter + 1) + "." + x.toString() + "\n";
            counter += 1;
        }
        combinedString = combinedString.substring(0, combinedString.length() - 1);
        return combinedString;
    }

    public static void handleInput(Scanner scanner, String divider) {
        String input = "";
        String breakCon = "bye";
        ArrayList<Task> taskList = new ArrayList<>();

        while (!input.equals(breakCon)) {
            input = getInput(scanner, divider);
            String[] inputSplit = input.split(" ");
            String firstWord = inputSplit[0];
            if (input.equals(breakCon)) {
                return;
            } else if (firstWord.equals("list")) {
                printMsg(listText(taskList, divider), divider);
            } else if (firstWord.equals("mark")) {
                String potentialNum = inputSplit[inputSplit.length - 1];
                try {
                    int index = Integer.parseInt(potentialNum) - 1;
                    taskList.get(index).toggleStatus();
                    System.out.println("Nice! I've marked this task as done:");
                    printMsg(taskList.get(index).toString(), divider);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (firstWord.equals("unmark")) {
                String potentialNum = inputSplit[inputSplit.length - 1];
                try {
                    int index = Integer.parseInt(potentialNum) - 1;
                    taskList.get(index).toggleStatus();
                    System.out.println("OK, I've marked this task as not done yet:");
                    printMsg(taskList.get(index).toString(), divider);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (firstWord.equals("event")) {
                Event newTask = (Event) createTask(Tasks.EVENT, input);
                taskList.add(newTask);
                printMsg("Now you have " + taskList.size() + " tasks in the list.", divider);
            } else if (firstWord.equals("deadline")) {
                Deadline newTask = (Deadline) createTask(Tasks.DEADLINE, input);
                taskList.add(newTask);
                printMsg("Now you have " + taskList.size() + " tasks in the list.", divider);
            } else if (firstWord.equals("todo")) {
                Task newTask = createTask(Tasks.TODO, input);
                taskList.add(newTask);
                printMsg("Now you have " + taskList.size() + " tasks in the list.", divider);
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
    }
}
