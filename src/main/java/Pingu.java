import java.util.ArrayList;
import java.util.Scanner;
public class Pingu {
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
        String combinedString = "";
        int counter = 0;
        for (Task x : taskL){
            combinedString += (counter + 1) + ".[" + x.getStatusIcon() + "] " + x.description + "\n";
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
            if (input.equals(breakCon)) {
                return;
            } else if (input.equals("list")) {
                printMsg(listText(taskList, divider), divider);
            } else if (input.contains("mark")) {
                String[] inputSplit = input.split(" ");
                String potentialNum = inputSplit[inputSplit.length - 1];
                try {
                    int index = Integer.parseInt(potentialNum) - 1;
                    taskList.get(index).toggleStatus();
                    System.out.println("Nice! I've marked this task as done:");
                    printMsg("[" + taskList.get(index).getStatusIcon() + "] " + taskList.get(index).description, divider);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (input.contains("unmark")) {
                String[] inputSplit = input.split(" ");
                String potentialNum = inputSplit[inputSplit.length - 1];
                try {
                    int index = Integer.parseInt(potentialNum) - 1;
                    taskList.get(index).toggleStatus();
                    System.out.println("OK, I've marked this task as not done yet:");
                    printMsg("[" + taskList.get(index).getStatusIcon() + "] " + taskList.get(index).description, divider);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                Task newTask = new Task(input);
                taskList.add(newTask);
                printMsg("added: " + input, divider);
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
