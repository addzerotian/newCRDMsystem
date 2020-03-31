package dal.model;

import java.util.Date;
import java.util.HashMap;

public class Request {
    private float longitude;
    private float latitude;
    private String cid;
    private Date startTime;
    private String status;
    private String location;

    public Request() {
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public HashMap<String, Object> getMapRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("startTime", startTime);
        map.put("status", status);
        map.put("location", location);
        map.put("cid", cid);

        return map;
    }
}
