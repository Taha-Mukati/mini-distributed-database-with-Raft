package raft.rpc;

public record RequestVoteRequest(long term, String from, String candidateId, long lastLogIndex, long lastLogTerm) implements RaftMessage {


}
