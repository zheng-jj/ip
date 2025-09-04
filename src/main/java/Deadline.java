import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public Deadline(String description, String byString) throws DateTimeParseException {
        super(description);
        this.by = LocalDateTime.parse(byString, INPUT_FORMAT);
    }
    
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline for saving to a file.
     * Format: D | 1/0 | description | by_date
     *
     * @return A formatted string for file storage.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.description + " | " + by.format(FILE_FORMAT);
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
