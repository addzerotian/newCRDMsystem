package dal.model;

import java.util.HashMap;
import java.util.Objects;

public class Admin extends User {
    private String aid;
    private int authority;

    public Admin(String aid, String password) {
        super(password);
        this.aid = aid;
    }

    public Admin(String aid) {
        super();
        this.aid = aid;
    }

    public Admin() {
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return aid.equals(admin.aid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aid);
    }

    public HashMap<String, Object> getMapAdmin() {
        HashMap<String, Object> map = this.getMapUser();

        map.put("aid", aid);
        map.put("authority", authority);

        return map;
    }
}
