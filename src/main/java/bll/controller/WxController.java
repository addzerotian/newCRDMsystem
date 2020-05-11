package bll.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxController {
    void customerLogin(HttpServletResponse response, String code);
    void customerRegister(HttpServletResponse response, HttpServletRequest request);
    void customerInfo(HttpServletResponse response, String cid);
    void sendRequest(HttpServletResponse response, HttpServletRequest request);
    void updateInfo(HttpServletResponse response, HttpServletRequest request);
    void getRequestInfo(HttpServletResponse response, String rid);
    void getCustomerRequests(HttpServletResponse response, String cid);
    void getDispatchInfo(HttpServletResponse response, String did);
    void feedback(HttpServletResponse response, String did, int star, String comment);
    void getCustomerDispatches(HttpServletResponse response, String cid);
    void getAllCustomers(HttpServletResponse response);
}
