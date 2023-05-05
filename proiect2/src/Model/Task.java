package Model;


public class Task{
    public int arrivalTime;
    public int serviceTime;
    public int ID;

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "("+ID+" "+arrivalTime+" "+serviceTime+")";
    }


    public void setServiceTime(int serviceTime) {
        this.serviceTime=serviceTime;
    }
}

