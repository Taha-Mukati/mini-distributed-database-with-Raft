package raft.log;

public record LogEntry (long term, long index, String command) {


    public static final LogEntry DUMMY = new LogEntry(0, 0, "");

    public LogEntry {
        if (term < 0) throw new IllegalArgumentException("term must be >= 0");
        if (index < 0) throw new IllegalArgumentException("index must be >= 0");
        if (command == null) throw new IllegalArgumentException("command cannot be null");
    }
}
