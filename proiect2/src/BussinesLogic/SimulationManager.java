package BussinesLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    public static SimulationFrame simulationFrame;
    private static ArrayList<Task> generatedTasks;
    int totalWaitingTime=0;




    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime, int numberOfClients, int numberOfServers, String policy) {
        System.out.println(timeLimit);
        this.timeLimit = timeLimit;
        this.maxArrivalTime = maxArrivalTime;
        System.out.println(maxArrivalTime);
        this.minArrivalTime = minArrivalTime;
        System.out.println(minArrivalTime);
        this.maxProcessingTime = maxProcessingTime;
        System.out.println(maxProcessingTime);
        this.minProcessingTime = minProcessingTime;
        System.out.println(minProcessingTime);
        this.numberOfClients = numberOfClients;
        System.out.println(numberOfClients);
        this.numberOfServers = numberOfServers;
        System.out.println(numberOfServers);
        this.selectionPolicy = SelectionPolicy.valueOf(policy);
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.scheduler.changeStrategy(SelectionPolicy.valueOf(policy));
        generatedTasks=new ArrayList<Task>();
        generateNRandomTasks(numberOfClients);

    }

    public static void main(String[] args) {

        simulationFrame = new SimulationFrame();

    }


    public void generateNRandomTasks(int n) {

        for (int i = 0; i < n; i++) {
            Task auxiliaryTask = new Task();
            Random processTime = new Random();
            auxiliaryTask.serviceTime = processTime.nextInt(maxProcessingTime - minProcessingTime);
            auxiliaryTask.serviceTime = auxiliaryTask.serviceTime + minProcessingTime;
            totalWaitingTime=totalWaitingTime+auxiliaryTask.getServiceTime();
            Random arrivalTime = new Random();
            auxiliaryTask.arrivalTime = arrivalTime.nextInt(maxArrivalTime - minArrivalTime);
            auxiliaryTask.arrivalTime = auxiliaryTask.arrivalTime + minArrivalTime;
            generatedTasks.add(i, auxiliaryTask);
        }
        int i=1;
        TaskSorter.sortByArrivalTime(generatedTasks);
        for (Task task : generatedTasks) {
            task.ID=i;
            i++;
        }


    }


    @Override
    public void run() {
        int maxClients=0;
        int peak=0;
        int currentTime = 1;

        FileWriter fileWriter=null;
        try {
            fileWriter=new FileWriter("test.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter=new PrintWriter(fileWriter);


        System.out.println(timeLimit);
        while (currentTime <= timeLimit) {

            printWriter.println("Time " + currentTime);
            simulationFrame.textArea().append("Simulation time: "+currentTime);
            simulationFrame.textArea().append("\n\n");
            simulationFrame.textArea().append("Waiting clients: ");
            printWriter.print("Waiting clients: ");
            int i = 0;
            if (!generatedTasks.isEmpty()) {
                while (i < generatedTasks.size()) {
                    Task task = generatedTasks.get(i);
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(task);
                        generatedTasks.remove(i);
                    } else i++;

                }
                for (Task task : generatedTasks) {
                    printWriter.print("("+task.getID()+','+task.getArrivalTime()+','+task.getServiceTime()+");" + ' ');
            simulationFrame.textArea().append("("+task.getID()+','+task.getArrivalTime()+','+task.getServiceTime()+')' + ' ');
        }

            }
            simulationFrame.textArea().append("\n\n");


            for (int k = 0; k < numberOfServers; k++) {
                Server server = scheduler.getServers().get(k);
                StringBuilder outputBuilder = new StringBuilder();
                outputBuilder.append("Queue ").append(k + 1).append(": ");

                BlockingQueue<Task> tasks = server.getTasks();
                if (!tasks.isEmpty()) {
                    for (Task task : tasks) {
                        outputBuilder.append(task.toString());
                    }
                } else {
                    outputBuilder.append("empty");
                }

                String output = outputBuilder.toString();
                System.out.println(output);

                simulationFrame.textArea().append(output);
                simulationFrame.textArea().append("\n\n");
                printWriter.print('\n');
                printWriter.print(output);
            }


            int sum = 0;
            for(int x=0;x< scheduler.getServers().size();x++){

                sum=sum+scheduler.getServers().get(x).getTasks().size() ;}


            if(sum>maxClients) {

                maxClients=sum;
                peak=currentTime;

            }

            for (Server server : scheduler.getServers()) {
                Task firstTask = server.getTasks().peek();
                if (firstTask != null) {
                    int serviceTime = firstTask.getServiceTime();
                    if (serviceTime > 1) {
                        firstTask.setServiceTime(serviceTime - 1);
                    } else {
                        server.remove();
                    }
                }
            }


            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted while sleeping: " + e.getMessage());
            }
            simulationFrame.textArea.append("\n");
            printWriter.println('\n');



        }

        float totalWaitingTime2=Float.valueOf(totalWaitingTime);
        float avg=totalWaitingTime2/numberOfClients;

        simulationFrame.textArea().append("Average service time is: "+avg+"\n\n");
        simulationFrame.textArea().append("Average waiting time is: "+avg/numberOfServers+"\n\n");
        simulationFrame.textArea().append("Peak hour is "+peak+ "\n\n");
        printWriter.println("Average service time: "+avg+'\n');
        printWriter.println("Average waiting time: "+avg/numberOfServers+'\n');
        printWriter.println("Peak hour: "+ peak+'\n');

        printWriter.close();

        }

    }





