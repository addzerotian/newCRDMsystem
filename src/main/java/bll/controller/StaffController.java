package bll.controller;

import dal.model.DispatchInfo;
import dal.model.Staff;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface StaffController {
    void addStaff(HttpServletResponse response, Map<String, Object> mapRequest);
    void searchStaff(HttpServletResponse response, String sid);
    void questDone(String sid, DispatchInfo dispatchInfo);
    void modifyStaff(HttpServletResponse response, Map<String, Object> mapRequest);
    void simuAroundStaffs(HttpServletResponse response, double longitude, double latitude);
    void flushStaff(HttpServletResponse response);
    void searchStaffByName(HttpServletResponse response, String name);
    void searchStaffsByPropGreaterThan(HttpServletResponse response, String propName, Object value);
}
