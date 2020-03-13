package dal.model;

import java.util.Date;

public class DispatchInfo {
    private String did;
    private String sid;
    private String cid;
    private Date startTime;
    private Date dispatchTime;
    private Date endTime;
    private float waitMinutes;
    private String status;
    private float star;
    private String customerComment;
    private Date checkTime;
    private byte[] checkPhoto;
    private String checkLocation;

    public DispatchInfo(String did, String sid, String cid, Date startTime, Date dispatchTime) {
        this.did = did;
        this.sid = sid;
        this.cid = cid;
        this.startTime = startTime;
        this.dispatchTime = dispatchTime;
    }

    public DispatchInfo() {
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getWaitMinutes() {
        return waitMinutes;
    }

    public void setWaitMinutes(float waitMinutes) {
        this.waitMinutes = waitMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public byte[] getCheckPhoto() {
        return checkPhoto;
    }

    public void setCheckPhoto(byte[] checkPhoto) {
        this.checkPhoto = checkPhoto;
    }

    public String getCheckLocation() {
        return checkLocation;
    }

    public void setCheckLocation(String checkLocation) {
        this.checkLocation = checkLocation;
    }
}
