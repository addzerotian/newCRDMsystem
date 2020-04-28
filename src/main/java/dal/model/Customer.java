package dal.model;

public class Customer extends User {
    private String cid;
    private int totalRequestTimes;
    private int totalDispatchTimes;

    public Customer(String cid, String password) {
        super(password);
        this.cid = cid;
        totalRequestTimes = 0;
        totalDispatchTimes = 0;
    }

    public Customer(String cid) {
        super();
        this.cid = cid;
        totalRequestTimes = 0;
        totalDispatchTimes = 0;
    }

    public Customer() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getTotalRequestTimes() {
        return totalRequestTimes;
    }

    public void setTotalRequestTimes(int totalRequestTimes) {
        this.totalRequestTimes = totalRequestTimes;
    }

    public int getTotalDispatchTimes() {
        return totalDispatchTimes;
    }

    public void setTotalDispatchTimes(int totalDispatchTimes) {
        this.totalDispatchTimes = totalDispatchTimes;
    }
}
