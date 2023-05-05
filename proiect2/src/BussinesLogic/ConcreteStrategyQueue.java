package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(ArrayList<Server> servers, Task t) {

        Server minServer=null;
        int minNumTasks=Integer.MAX_VALUE;

        for(Server s: servers) {
            int numTasks=s.getTasks().size();
            if(numTasks<minNumTasks) {
                minServer=s;
                minNumTasks=numTasks;
            }
        }

        if(minServer!=null) {
            minServer.addTask(t);
        }

    }

}
