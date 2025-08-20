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

    public static String listText(ArrayList<String> arrL, String divider) {
        if (arrL.size() == 0) {
            return "";
        }
        String combinedString = "";
        int counter = 0;
        for (String x : arrL){
            combinedString += (counter + 1) + ". " + x + "\n";
            counter += 1;
        }
        combinedString = combinedString.substring(0, combinedString.length() - 1);
        return combinedString;
    }

    public static void handleInput(Scanner scanner, String divider) {
        String input = "";
        String breakCon = "bye";
        ArrayList<String> inputList = new ArrayList<>();

        while (!input.equals(breakCon)) {
            input = getInput(scanner, divider);
            if (input.equals(breakCon)) {
                return;
            } else if (input.equals("list")) {
                printMsg(listText(inputList, divider), divider);
            } else {
                inputList.add(input);
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
