package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{


    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
        Server minServer=null;
        int minTimeTasks=Integer.MAX_VALUE;

        for(Server s: servers) {
            int timeTasks = s.getWaitingPeriod().intValue();
            if (timeTasks < minTimeTasks) {
                minServer = s;
                minTimeTasks = timeTasks;
            }
        }

        if(minServer!=null) {
            minServer.addTask(t);
        }


    }
}
