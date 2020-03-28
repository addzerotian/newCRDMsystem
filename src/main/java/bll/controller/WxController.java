package bll.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxController {
    void customerLogin(HttpServletResponse response, String cid, String password);
    void customerRegister(HttpServletResponse response, String cid, String password);
    void sendRequest(HttpServletRequest request);
}
