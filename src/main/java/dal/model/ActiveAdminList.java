package dal.model;

import java.util.ArrayList;

public class ActiveAdminList {
    private static ArrayList<Admin> admins;
    private static ActiveAdminList instance = new ActiveAdminList();

    public ActiveAdminList() {
        admins = new ArrayList<>();
    }

    public static ActiveAdminList getInstance() {
        return instance;
    }

    public int getLength() {return admins.size(); }

    public void appendActiveAdmin(Admin admin) { if(!admins.contains(admin)) admins.add(admin); }

    public Admin getActiveAdmin(int index) { return admins.get(index); }

    public Admin getActiveAdmin(Admin admin) { return admins.get(admins.indexOf(admin)); }

    public void removeActiveAdmin(int index) { admins.remove(index);}

    public void removeActiveAdmin(Admin admin) { admins.remove(admin);}
}
