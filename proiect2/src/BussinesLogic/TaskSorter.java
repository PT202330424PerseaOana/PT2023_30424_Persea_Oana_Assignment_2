package BussinesLogic;

import Model.Task;

import java.util.Collections;
import java.util.List;

public class TaskSorter {
    public static void sortByArrivalTime(List<Task> tasks) {
        Collections.sort(tasks, (t1, t2) -> t1.getArrivalTime() - t2.getArrivalTime());
    }
}