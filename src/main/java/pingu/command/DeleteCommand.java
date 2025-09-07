package pingu.command;

import pingu.PinguException;
import pingu.Storage;
import pingu.Task;
import pingu.TaskList;
import pingu.Ui;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PinguException {
        Task removedTask = tasks.deleteTask(taskIndex);
        ui.showMessage("Noted. I've removed this task:\n  " + removedTask);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }
}
