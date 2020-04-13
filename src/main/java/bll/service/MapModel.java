package bll.service;

import dal.model.Admin;
import dal.model.Staff;
import dal.model.User;

import java.util.HashMap;

public interface MapModel {
    HashMap<String, Object> getMapUser(User user);
    HashMap<String, Object> getMapAdmin(Admin admin);
    HashMap<String, Object> getMapStaff(Staff staff);
}
