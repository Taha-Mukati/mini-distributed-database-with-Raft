package raft.rpc;

public record AppendEntriesResponse(long term, String from, boolean success, long matchIndex) implements RaftMessage {
    
}