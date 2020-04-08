package bll.controller;

import dal.model.Staff;

import javax.servlet.http.HttpServletResponse;

public interface StaffController {
    void addStaff(HttpServletResponse response, String sid);
    void searchStaff(HttpServletResponse response, String sid);
    void modifyStaff(HttpServletResponse response, Staff staff);
}
