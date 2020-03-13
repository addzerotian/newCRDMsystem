package dal.model;

public class Customer extends User {
    private String cid;
    private int totalRequestTimes;

    public Customer(String uid, String password, String cid) {
        super(uid, password);
        this.cid = cid;
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
}
