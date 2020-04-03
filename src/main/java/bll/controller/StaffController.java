package bll.controller;

import javax.servlet.http.HttpServletResponse;

public interface StaffController {
    void addStaff(HttpServletResponse response, String sid);
    void searchStaff(HttpServletResponse response, String sid);
}
