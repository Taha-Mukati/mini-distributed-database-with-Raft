package src.main.java.raft;

public record Command(Type type, String key, String value) {
    public enum Type {PUT, DELETE}

    public static Command put(String key, String value) {
        return new Command(Type.PUT, key, value);
    }
    public static Command delete(String key) {
        return new Command(Type.DELETE, key, null);
    }
    
}
