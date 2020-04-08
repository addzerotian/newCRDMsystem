package bll.controller;

import dal.model.Admin;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminController {
    Admin adminLogin(String aid, String password);
    void adminLogout(HttpServletResponse response, Admin admin);
    void addAdmin(JSONObject jsonRequest, HttpServletResponse response);
    void getInfo(HttpServletResponse response);
    void searchAdmin(HttpServletResponse response, String aid);
    void modifyAdmin(HttpServletResponse response, JSONObject jsonRequest);
}
