package raft.log;

import java.util.List;

public interface LogStore {

    public void appendLog(List<LogEntry> enrty);

    public void readByIndex(long index);

    public void truncate(long index);

    public void getLastIndex();

    public void getLastTerm();

} 
    
