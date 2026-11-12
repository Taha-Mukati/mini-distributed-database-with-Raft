package raft.rpc;

public sealed interface RaftMessage
    permits RequestVoteRequest, RequestVoteResponse,
            AppendEntriesRequest, AppendEntriesResponse {
    
    long term();
    String from();
}