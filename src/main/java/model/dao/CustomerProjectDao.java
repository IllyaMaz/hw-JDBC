package model.dao;

public class CustomerProjectDao {
    private long customerId;
    private long projectID;

    public CustomerProjectDao(long customerId, long projectID){
        this.customerId=customerId;
        this.projectID=projectID;
    }

    public CustomerProjectDao(){

    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }
}
