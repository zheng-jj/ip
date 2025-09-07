package pingu.command;

import pingu.PinguException;
import pingu.Storage;
import pingu.Task;
import pingu.TaskList;
import pingu.Ui;

public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PinguException {
        Task task = tasks.unmarkTask(taskIndex);
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + task);
        storage.save(tasks.getTasks());
    }
}
