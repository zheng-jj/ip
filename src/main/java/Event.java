public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event for saving to a file.
     * Format: E | 1/0 | description | from_date | to_date
     *
     * @return A formatted string for file storage.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + this.description + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDescription() + " (from: " + from + " to: " + to + ")";
    }
}
