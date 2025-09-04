public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void toggleStatus() {
        this.isDone = !this.isDone;
    }

    /**
     * Returns a string representation of the task for saving to a file.
     * Format: T | 1/0 | description
     *
     * @return A formatted string for file storage.
     */
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + getDescription();
    }
}
