package bll.controller;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import bll.service.DateService;
import bll.service.DateServiceImpl;
import dal.model.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WxControllerImpl implements WxController {
    private static DaoService daoService;
    private static DateService dateService;

    public WxControllerImpl() {
        daoService = new DaoServiceImpl();
        dateService = new DateServiceImpl();
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
        //从请求数据中获取相应信息
        float latitude = Float.parseFloat(request.getParameter("latitude"));
        float longitude = Float.parseFloat(request.getParameter("longitude"));
        String status = request.getParameter("status");
        String location = request.getParameter("location");
        String startTimeStr = request.getParameter("start");
        String cid = request.getParameter("cid");

        //将时间字符串转换为Date
        Date startTime = dateService.getDateFromString(startTimeStr, StandardDateFormat.WX_DF);

        //生成新Request，添加到Request队列
        Request customerRequest = new Request();
        customerRequest.setLatitude(latitude);
        customerRequest.setLongitude(longitude);
        customerRequest.setStatus(status);
        customerRequest.setLocation(location);
        customerRequest.setStartTime(startTime);
        customerRequest.setCid(cid);
        RequestList.getInstance().appendRequest(customerRequest);
    }
}
