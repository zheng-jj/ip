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

    public static void main(String[] args) {
        String chatbotName = "Pingu";
        String divider = "_".repeat(60);
        String introMessage = "Hello! I'm " + chatbotName + "\n"
                + "What can I do for you?";
        String closeMessage = "Bye. Hope to see you again soon!";
        Scanner scanner = new Scanner(System.in);
        // hi bye
        System.out.println(divider);
        printMsg(introMessage, divider);
        String input = "";
        String breakCon = "bye";
        while (!input.equals(breakCon)) {
            input = getInput(scanner, divider);
            if (input.equals(breakCon)) {
                break;
            }
            printMsg(input, divider);
        }
        printMsg(closeMessage, divider);
    }
}
