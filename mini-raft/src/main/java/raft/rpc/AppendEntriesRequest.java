package raft.rpc;
import raft.log.LogEntry;
import java.util.List;

public record AppendEntriesRequest(long term, String from, long leaderId, long prevLogIndex, long prevLogTerm, List<LogEntry> entries, long leaderCommit) implements RaftMessage {
    
}
