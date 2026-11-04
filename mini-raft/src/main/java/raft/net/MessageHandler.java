package raft.net;
import raft.rpc.RaftMessage;

@FunctionalInterface 
public interface MessageHandler {
    public void onMessage(RaftMessage message);
}
