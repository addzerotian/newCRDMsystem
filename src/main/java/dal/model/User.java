package dal.model;

import java.util.Date;
import java.util.HashMap;

public class User {
    private long uid;
    private String password;
    private String name;
    private Date birth;
    private String telephone;
    private String email;
    private byte[] avatar;
    private String gender;

    public User(String password) {
        this.password = password;
    }

    public User() {
        password = "123456";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HashMap<String, Object> getMapUser() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("uid", uid);
        map.put("sex", gender);
        map.put("password", password);
        map.put("name", name);
        map.put("birth", birth);
        map.put("telephone", telephone);
        map.put("email", email);
        map.put("avatar", avatar);

        return map;
    }
}
