public class Pingu {
    public static void printMsg(String msg, String divider) {
        System.out.println(msg);
        System.out.println(divider);
    }
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String chatbotName = "Pingu";
        String divider = "_".repeat(60);
        String introMessage = "Hello! I'm " + chatbotName + "\n"
                + "What can I do for you?";
        String closeMessage = "Bye. Hope to see you again soon!";
        
        // hi bye
        System.out.println(divider);
        printMsg(introMessage, divider);
        printMsg(closeMessage, divider);
    }
}
