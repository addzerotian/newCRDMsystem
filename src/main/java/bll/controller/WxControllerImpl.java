package bll.controller;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import dal.model.Customer;
import dal.model.Request;
import dal.model.RequestList;
import dal.model.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WxControllerImpl implements WxController {
    private static DaoService daoService;

    public WxControllerImpl() {
        daoService = new DaoServiceImpl();
    }

    @Override
    public void customerLogin(HttpServletResponse response, String cid, String password) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();
        Customer customer = daoService.searchCustomer(cid);
        if(customer != null) {
            if(customer.getPassword().equals(password)) {
                jsonObject.append("login-status", 1);
            } else {
                jsonObject.append("login-status", 0);
            }
        } else {
            jsonObject.append("login-status", -1);
        }
        try {
            send = response.getWriter();
            send.append(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert send != null;
            send.close();
        }
    }

    @Override
    public void customerRegister(HttpServletResponse response, String cid, String password) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();
        Customer customer = daoService.searchCustomer(cid);
        if(customer != null) {
            jsonObject.append("register-status", -1);
        } else {
            customer = new Customer(cid, password);
            daoService.getDao().addCustomer(customer);
            jsonObject.append("register-status", 1);
        }
        try {
            send = response.getWriter();
            send.append(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert send != null;
            send.close();
        }
    }

    @Override
    public void sendRequest(HttpServletRequest request) {
        float latitude = Float.parseFloat(request.getParameter("latitude"));
        float longitude = Float.parseFloat(request.getParameter("longitude"));
        System.out.println("Latitude: " + latitude + " Longitude: " + longitude);
        Request customerRequest = new Request();
        customerRequest.setLatitude(latitude);
        customerRequest.setLongitude(longitude);
        RequestList.getInstance().appendRequest(customerRequest);
    }
}
