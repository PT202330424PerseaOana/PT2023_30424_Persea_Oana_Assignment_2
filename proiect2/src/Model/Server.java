package Model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {
        //init queue and waiting period
        tasks=new LinkedBlockingQueue<Task>();
        waitingPeriod = new AtomicInteger(0);

    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void remove() {
        tasks.remove();
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while(true) {
            if(!tasks.isEmpty()) {
                Task newTask=tasks.peek();
                try {
                    Thread.sleep(newTask.getServiceTime()*1000);
                    waitingPeriod.addAndGet((-1)*newTask.getServiceTime());
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tasks.remove(newTask);
            }
            else {
                try {
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }

    }


}
