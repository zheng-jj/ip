import pingu.command.Command;

import pingu.Parser;
import pingu.PinguException;
import pingu.Storage;
import pingu.TaskList;
import pingu.Ui;

public class Pingu {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructor for the Pingu application.
     * Initializes UI, Storage, and loads tasks from the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Pingu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (PinguException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the application.
     * Greets the user, reads commands, executes them, and says goodbye on exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (PinguException e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Pingu("data/pingu.txt").run();
    }
}
