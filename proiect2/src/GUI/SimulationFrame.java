package GUI;

import BussinesLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JPanel panel1;
    private JLabel title;
    public  JTextField textField1;
    public  JTextField textField2;
    public  JTextField textField3;
    private JLabel queues;
    private JLabel clients;
    private JLabel simTime;
    private JPanel queuePan;
    private JPanel simulationPan;
    private JPanel clientPan;
    public  JTextField textField4;
    public  JTextField textField5;
    public  JTextField textField6;
    public  JTextField textField7;
    public  JButton startButton;
    private JLabel maxArrival;
    private JLabel minService;
    private JLabel maxService;
    private JLabel minArrival;
    private JPanel minArrivalPan;
    private JPanel maxArrivalPan;
    private JPanel minServicePan;
    private JPanel maxServicePan;
    public JTextField textField8;
    private JLabel strategy;
    public JTextArea textArea;
    private JPanel strategyPan;

    public JFrame SimulationFrame;



    public SimulationFrame() {
        SimulationFrame = new JFrame("Simulation");
        SimulationFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        SimulationFrame.setPreferredSize(new Dimension(1000, 800));
        SimulationFrame.setResizable(false);
        SimulationFrame.add(panel1);
        SimulationFrame.pack();
        SimulationFrame.setLocationRelativeTo(null);
        SimulationFrame.setVisible(true);
        textArea.setEditable(false);
        queuePan.setBorder(new RoundedBorder(10));
        queuePan.setSize(25, 10);
        clientPan.setBorder(new RoundedBorder(10));
        clientPan.setSize(25, 10);
        minArrivalPan.setBorder(new RoundedBorder(10));
        minArrivalPan.setSize(25, 10);
        maxArrivalPan.setBorder(new RoundedBorder(10));
        maxArrivalPan.setSize(25, 10);
        minServicePan.setBorder(new RoundedBorder(10));
        minServicePan.setSize(25, 10);
        maxServicePan.setBorder(new RoundedBorder(10));
        maxServicePan.setSize(25, 10);
        simulationPan.setBorder(new RoundedBorder(10));
        simulationPan.setSize(25, 10);
        title.setPreferredSize(new Dimension(50, 50));
        strategyPan.setBorder(new RoundedBorder(10));
        strategyPan.setSize(25, 10);
        startButton.setPreferredSize(new Dimension(100, 30));

        startButton.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e){
                    int timeLimit = 0;
                    int maxProcessingTime=0;
                    int minProcessingTime=0;
                    int minArrivalTime=0;
                    int maxArrivalTime=0;
                    int numberOfQueues=0;
                    int numberOfClients=0;
                    String policy = "";
                    timeLimit = Integer.parseInt(textField3.getText());
                    maxProcessingTime = Integer.parseInt(textField5.getText());
                    minProcessingTime = Integer.parseInt(textField6.getText());
                    minArrivalTime = Integer.parseInt(textField4.getText());
                    maxArrivalTime = Integer.parseInt(textField7.getText());
                    numberOfClients = Integer.parseInt(textField2.getText());
                    numberOfQueues = Integer.parseInt(textField1.getText());
                    policy = textField8.getText();
                    SimulationManager simulationManager = new SimulationManager(timeLimit,minProcessingTime,maxProcessingTime,minArrivalTime,maxArrivalTime,numberOfClients,numberOfQueues,policy);

                    Thread t = new Thread(simulationManager);
                    t.start();

                }
            });



    }

    public  JTextArea textArea() {
        return textArea;
    }




    }










