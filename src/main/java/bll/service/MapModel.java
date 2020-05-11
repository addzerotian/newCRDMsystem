package bll.service;

import dal.model.*;

import java.util.HashMap;

public interface MapModel {
    HashMap<String, Object> getMapUser(User user);
    HashMap<String, Object> getMapAdmin(Admin admin);
    HashMap<String, Object> getMapStaff(Staff staff);
    HashMap<String, Object> getMapCustomer(Customer customer);
    HashMap<String, Object> getMapRequest(Request request);
    HashMap<String, Object> getMapDispatchInfo(DispatchInfo dispatchInfo);
}
