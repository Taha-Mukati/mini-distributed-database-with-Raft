package raft.net;
import raft.rpc.RaftMessage;
import raft.rpc.RequestVoteRequest;
import raft.rpc.RequestVoteResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;



public class InMemoryTransportTest {
    

    private String nodeId1 = "node1";
    private String nodeId2 = "node2";

    private List<RaftMessage> node1Received;
    private List<RaftMessage> node2Received; 

    private MessageHandler handler1;
    private MessageHandler handler2;

    private InMemoryNetwork network;
    
    private InMemoryTransport transport1;
    private InMemoryTransport transport2;


    @BeforeEach 
    public void setUp() {

        node1Received = new ArrayList<>();
        node2Received = new ArrayList<>();

        handler1 = message -> node1Received.add(message);
        handler2 = message -> node2Received.add(message);

        network = new InMemoryNetwork();
    
        transport1 = new InMemoryTransport(nodeId1, network);
        transport2 = new InMemoryTransport(nodeId2, network);
    }


    @Test
    public void deliverMessageToDestinationOnly(){

        RequestVoteRequest request = new RequestVoteRequest(1, nodeId1, nodeId1, 0, 0);

        transport1.start(handler1);
        transport2.start(handler2);

        
        transport1.send(nodeId2, request);

        assertEquals(1, node2Received.size());
        assertEquals(request, node2Received.get(0));
        assertEquals(0, node1Received.size());
        
    }

    @Test
    public void stoppedNodeDropsMessages(){

        RequestVoteRequest request = new RequestVoteRequest(1, nodeId1, nodeId1, 0, 0);

        transport1.start(handler1);
        transport2.start(handler2);

        transport2.stop();

        transport1.send(nodeId2, request);


        assertEquals(0, node2Received.size());
        assertEquals(0, node1Received.size());


    }


    @Test
    public void silentlyDropUnregisteredDestination() {

        RequestVoteRequest request = new RequestVoteRequest(1, nodeId1, nodeId1, 0, 0);

        transport1.start(handler1);

        assertDoesNotThrow(() -> transport1.send(nodeId2, request));

        transport2.start(handler2);

        assertEquals(0, node1Received.size());
        assertEquals(0, node2Received.size());


    }

    @Test
    public void restartWorks() {
        
        RequestVoteRequest request = new RequestVoteRequest(1, nodeId1, nodeId1, 0, 0);
        RequestVoteResponse response = new RequestVoteResponse(2,nodeId1, true);

        transport1.start(handler1);
        transport2.start(handler2);

        transport2.stop();

        transport1.send(nodeId2, request);

        assertEquals(0, node2Received.size());

        transport2.start(handler2);

        transport1.send(nodeId2, response);

        assertEquals(1, node2Received.size());
        assertEquals(response, node2Received.get(0));


    }

    @Test
    public void orderingWorks() {
        
        RequestVoteRequest request1 = new RequestVoteRequest(1, nodeId1, nodeId1, 0, 0);
        RequestVoteResponse response1 = new RequestVoteResponse(2,nodeId1, true);
        RequestVoteRequest request2 = new RequestVoteRequest(3, nodeId1, nodeId1, 0, 0);
        RequestVoteResponse response2 = new RequestVoteResponse(4,nodeId1, true);

        transport1.start(handler1);
        transport2.start(handler2);

        
        transport1.send(nodeId2, request1);
        transport1.send(nodeId2, response1);
        transport1.send(nodeId2, request2);
        transport1.send(nodeId2, response2);

        assertTrue(node1Received.isEmpty());
        assertEquals(List.of(request1, response1, request2, response2), node2Received);

    
    }

}
