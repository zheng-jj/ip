import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Event(String description, String fromString, String toString) throws DateTimeParseException {
        super(description);
        this.from = LocalDateTime.parse(fromString, INPUT_FORMAT);
        this.to = LocalDateTime.parse(toString, INPUT_FORMAT);
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    
    public LocalDateTime getFrom() {
        return from;
    }
    
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event for saving to a file.
     * Format: E | 1/0 | description | from_date | to_date
     *
     * @return A formatted string for file storage.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + this.description + " | " + 
                from.format(FILE_FORMAT) + " | " + to.format(FILE_FORMAT);
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDescription() + 
                " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
