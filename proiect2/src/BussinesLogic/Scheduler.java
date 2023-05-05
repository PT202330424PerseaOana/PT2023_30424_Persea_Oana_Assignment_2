package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class Scheduler {

    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private static Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {

        //for maxNoServers
        //create server object
        //create thread with the object

        servers=new ArrayList<Server>();
        for(int i=0; i<maxNoServers; i++) {
            Server server=new Server();
            servers.add(server);
            Thread t=new Thread(server);
            t.start();
        }
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;

    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void changeStrategy(SelectionPolicy policy) {

        //apply strategy
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();

        }

        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }

    }

    public void dispatchTask(Task t) {
        strategy.addTask(servers,t);

    }



}
