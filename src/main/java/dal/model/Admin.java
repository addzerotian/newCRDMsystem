package dal.model;

public class Admin extends User {
    private String aid;
    private int authority;

    public Admin(String uid, String password, String aid) {
        super(uid, password);
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
}
