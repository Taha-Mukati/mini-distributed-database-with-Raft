package raft.rpc;

public record RequestVoteResponse(long term, String from, boolean voteGranted) implements RaftMessage {
    
}