package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public interface Strategy {

    public void addTask(ArrayList<Server> servers, Task t);


}
