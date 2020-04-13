package bll.controller;

import dal.model.Staff;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface StaffController {
    void addStaff(HttpServletResponse response, Map<String, Object> mapRequest);
    void searchStaff(HttpServletResponse response, String sid);
    void modifyStaff(HttpServletResponse response, Map<String, Object> mapRequest);
}
