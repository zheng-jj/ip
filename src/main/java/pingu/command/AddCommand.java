package pingu.command;

import pingu.PinguException;
import pingu.Storage;
import pingu.Task;
import pingu.TaskList;
import pingu.Ui;

public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task task) {
        this.taskToAdd = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PinguException {
        tasks.addTask(taskToAdd);
        ui.showMessage("Got it. I've added this task:\n  " + taskToAdd);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }
}
