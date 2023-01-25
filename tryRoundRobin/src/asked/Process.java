package asked;

public class Process {

    public int proccessID;
    static public int arrivalTime;
    static public int completionTime;
    private int cpuBurstTime;
    private int waitTime;

    private int turnaroundTime;
    private int cpuBurstLeft;


    public Process(int proccessID, int arrival, int cpuBurstTime) {
        this.proccessID = proccessID;
        this.arrivalTime = arrival;
        setCpuBurstTime(cpuBurstTime);
        setCPUBurstLeft(cpuBurstTime);
    }
    public int getCpuBurstTime(){
        return cpuBurstTime;
    }

    public void setCpuBurstTime(int cpub){
        cpuBurstTime = cpub;
    }
    public int getWaitingTime(){
        return waitTime;
    }

    public void setWaitingTime(int wt){
        waitTime = wt;
    }
    

    public int getTurnaroundTime(){
        return turnaroundTime;
    }

    public void setturnaroundTime(int tt){
        turnaroundTime = tt;
    }

    public int getBurstLeft(){
        return cpuBurstLeft;
    }

    public void setCPUBurstLeft(int bl){
        cpuBurstLeft = bl;
    }


}