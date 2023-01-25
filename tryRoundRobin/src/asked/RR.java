package asked;

import java.util.ArrayList;

public class RR extends Scheduler{

    private int timmer;
    private int idleCount;

    public RR(){
        super();
        timmer = 0;
        idleCount = 0;
    }


    public void runRoundRobin(ArrayList<Process> proccessArray, int timeQuantum){
        double avgWaitingTime;
        double avgTurnroundTime;
        System.out.println();
        roundRobin(proccessArray, timeQuantum);
        System.out.println("time " + timmer + " All finished...");
        System.out.println("----------------------------------------------------");
        avgWaitingTime = averageWaitTime(proccessArray);
        System.out.print("Average waiting time: ");
        System.out.println(avgWaitingTime);
        System.out.println();
        avgTurnroundTime = averageTurnaroundTime(proccessArray);
        System.out.print("Average turnaround time: ");
        System.out.println(avgTurnroundTime);
        System.out.println();
        System.out.println("----------------------------------------------------");
    }

    public void roundRobin(ArrayList<Process> proccessArray, int quantumTime){
        int numOfProccesses = proccessArray.size();
        int proc=0;
        Process currentProccess;


        while(proccessArray.get(proc).arrivalTime != timmer){
            System.out.println("system at time   " + timmer + " is idle");
            idleCount++;
            timmer++;
        }

        super.addProcess(proccessArray.get(proc));
        proc++;
        currentProccess = super.getProcess(0);

        while((proc != numOfProccesses) && (proccessArray.get(proc).arrivalTime == timmer)){
            super.addProcess(proccessArray.get(proc)); //add job(s) to the ReadyQueue
            proc++;
        }

        while(true){
            while((proc != numOfProccesses) && super.readyQueueEmpty()){
                if(proccessArray.get(proc).arrivalTime == timmer){
                    super.addProcess(proccessArray.get(proc));
                    currentProccess = super.getProcess(0);
                    proc++;
                    break;
                }
                else{

                    System.out.println("system at time   " + timmer + " is idle");
                    idleCount++;
                    timmer++;
                }
            }
            for(int i=0; i<quantumTime; i++){

                if(currentProccess.getBurstLeft() != 0){
                    System.out.println("time " + timmer + " process " + currentProccess.proccessID +" is running" );
                    timmer++;
                    currentProccess.setCPUBurstLeft(currentProccess.getBurstLeft()-1);

                    while((proc != numOfProccesses) && (proccessArray.get(proc).arrivalTime == timmer)){
                        super.addProcess(proccessArray.get(proc));
                        proc++;
                    }
                    if(currentProccess.getBurstLeft() == 0){
                        currentProccess.completionTime= timmer; //set complete time of process
                        currentProccess.setturnaroundTime(currentProccess.completionTime - currentProccess.arrivalTime); //set turnaround time
                        currentProccess.setWaitingTime(currentProccess.completionTime - (currentProccess.arrivalTime + currentProccess.getCpuBurstTime())); //set wait time
                        System.out.println("time " + timmer + " process " + currentProccess.proccessID +" is finished....." );
                        break;
                    }
                }
            }

            if(!super.readyQueueEmpty()){


                if(super.readyQueueSize() > 1){
                    System.out.println("time " + timmer + " switching from process " + currentProccess.proccessID + " to process " +super.getProcess(1).proccessID);
                }

                if(currentProccess.getBurstLeft() == 0){
                    super.removeProcess(0);
                    if(!super.readyQueueEmpty()){
                        currentProccess = super.getProcess(0);

                    }
                }
                else{

                    super.rotateReadyQueue();
                    currentProccess = super.getProcess(0);
                }
            }
            else if(super.readyQueueEmpty() && proc == numOfProccesses){
                break;
            }
        }
    }


    private double averageWaitTime(ArrayList<Process> proccessArray){
        int sum=0;
        double avgWaitingTime = 0.0;
        for(int i=0; i<proccessArray.size(); i++){
            System.out.println("waiting time for proccess "+proccessArray.get(i).proccessID+" = "+proccessArray.get(i).getWaitingTime());
        }
        for(int i=0; i<proccessArray.size(); i++){
            sum = sum + proccessArray.get(i).getWaitingTime();
        }
        avgWaitingTime = (double)sum / proccessArray.size();
        return avgWaitingTime;
    }


    private double averageTurnaroundTime(ArrayList<Process> proccessArray){
        int sum=0;
        double avgTurnroundTime = 0.0;
        for(int i=0; i<proccessArray.size(); i++){
            System.out.println("Turnaround time for proccess "+proccessArray.get(i).proccessID+" = "+proccessArray.get(i).getTurnaroundTime());
        }
        for(int i=0; i<proccessArray.size(); i++){
            sum = sum + proccessArray.get(i).getTurnaroundTime();
        }
        avgTurnroundTime = (double)sum / proccessArray.size();
        return avgTurnroundTime;
    }
}