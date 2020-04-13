package bll.controller;

import dal.model.Admin;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminController {
    Admin adminLogin(String aid, String password);
    void adminLogout(HttpServletResponse response, Admin admin);
    void addAdmin(Map<String, Object> mapRequest, HttpServletResponse response);
    void getInfo(HttpServletResponse response);
    void searchAdmin(HttpServletResponse response, String aid, int requestAuth);
    void modifyAdmin(HttpServletResponse response, Map<String, Object> mapRequest);
    void flushInfo(HttpServletResponse response, Admin admin);
    void deleteAdmin(HttpServletResponse response, Admin admin);
    void updateInfo(HttpServletResponse response, HttpSession session, Map<String, Object> mapRequest);
}
