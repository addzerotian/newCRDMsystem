package bll.controller;

import bll.service.*;
import dal.model.*;
import org.json.JSONObject;

import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WxControllerImpl implements WxController {
    private static DaoService daoService;
    private static DateService dateService;
    private static FileRequestService fileRequestService;
    private static MapModel mapModel;

    public WxControllerImpl() {
        daoService = new DaoServiceImpl();
        dateService = new DateServiceImpl();
        fileRequestService = new FileRequestServiceImpl();
        mapModel = new MapModelImpl();
    }

    @Override
    public void customerLogin(HttpServletResponse response, String code) {
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        JSONObject jsonResponse = new JSONObject();
        try{
            URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?appid=wxca22aeeaf36ccae7&secret=79f9e13949ccd6d8baf6fc78a2bb878b&js_code="
                    + code + "&grant_type=authorization_code");

            //通过远程url连接对象打开一个连接，强转成HTTPURLConnection类
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");
            //发送请求
            conn.connect();
            //通过conn取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()){
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null){
                    result.append(line);
                }
                JSONObject jsResult  = new JSONObject(result.toString());
                jsonResponse.append("openid", jsResult.getString("openid"));
                jsonResponse.append("session_key", jsResult.getString("session_key"));
                Customer customer = daoService.searchCustomer(jsResult.getString("openid"));
                if(customer != null) {
                    jsonResponse.append("status", 1);
                }
                else jsonResponse.append("status", 0);
            }else{
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
                jsonResponse = new JSONObject();
                jsonResponse.append("status", -1);
            }
        } catch (Exception e){
            e.printStackTrace();
            jsonResponse = new JSONObject();
            jsonResponse.append("status", -1);
        } finally {
            try{
                if(br != null){
                    br.close();
                }
                if(is != null){
                    is.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
            assert conn != null;
            conn.disconnect();
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void customerRegister(HttpServletResponse response, HttpServletRequest request) {
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        //获取请求数据
        String cid = request.getParameter("cid");
        String avatarURL = request.getParameter("avatarUrl");
        String gender;
        String name = request.getParameter("name");
        switch (Integer.parseInt(request.getParameter("gender"))) {
            case 1: gender = "male"; break;
            case 2: gender = "female"; break;
            default: gender = null;
        }

        Customer customer = daoService.searchCustomer(cid);

        if(customer != null) {
            jsonResponse.append("status", 0);
        } else {
            try {
                customer = new Customer(cid);
                customer.setName(name);
                customer.setAvatarURL(avatarURL);
                customer.setGender(gender);
                daoService.getDao().addCustomer(customer);
                jsonResponse.append("status", 1);
            } catch (Exception e) {
                jsonResponse.append("status", -1);
            }
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void customerInfo(HttpServletResponse response, String cid) {
        JSONObject jsonResponse = new JSONObject();

        try {
            Customer customer = daoService.searchCustomer(cid);
            if(customer != null) {
                jsonResponse.append("telephone", customer.getTelephone());
                jsonResponse.append("email", customer.getEmail());
                jsonResponse.append("birth", dateService.getStringFromDate(customer.getBirth(), StandardDateFormat.WEB_DF));
                jsonResponse.append("totalRequestTimes", customer.getTotalRequestTimes());
                jsonResponse.append("status", 1);
            } else {
                jsonResponse.append("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.append("status", -1);
        }


        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void sendRequest(HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonResponse = new JSONObject();

        //从请求数据中获取相应信息
        double latitude = Float.parseFloat(request.getParameter("latitude"));
        double longitude = Float.parseFloat(request.getParameter("longitude"));
        String status = request.getParameter("status");
        String location = request.getParameter("location");
        String startTimeStr = request.getParameter("start");
        Customer customer = daoService.searchCustomer(request.getParameter("cid"));

        if(customer == null) {
            jsonResponse.append("status", -1);
        } else {
            //对微信定位坐标进行转换为BD09坐标
            HttpURLConnection conn = null;
            InputStream is = null;
            BufferedReader br = null;
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://api.map.baidu.com/geoconv/v1/?coords="
                        +longitude + "," + latitude + "&from=3&to=5&ak=Lm1k2R6SybtdXGL0bFhbdQM8rG6DQDvs");

                //通过远程url连接对象打开一个连接，强转成HTTPURLConnection类
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                //设置连接超时时间和读取超时时间
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(60000);
                conn.setRequestProperty("Accept", "application/json");
                //发送请求
                conn.connect();
                //通过conn取得输入流，并使用Reader读取
                if (200 == conn.getResponseCode()) {
                    is = conn.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    JSONObject jsonResult = new JSONObject(result.toString());
                    if((int)jsonResult.get("status") == 0) {
                        JSONObject res = (JSONObject) jsonResult.getJSONArray("result").get(0);
                        longitude = (double) res.get("x");
                        latitude = (double) res.get("y");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try{
                    if(br != null){
                        br.close();
                    }
                    if(is != null){
                        is.close();
                    }
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                assert conn != null;
                conn.disconnect();
            }
            try {
                //将时间字符串转换为Date
                Date startTime = dateService.getDateFromString(startTimeStr, StandardDateFormat.WX_DF);
                //根据请求的用户id和请求发起时间计算MD5值作为该请求id
                String rid = fileRequestService.calMD5OfRequest(customer.getCid()+startTimeStr);

                //生成新Request，添加到Request队列
                Request customerRequest = new Request();
                customerRequest.setLatitude(latitude);
                customerRequest.setLongitude(longitude);
                customerRequest.setStatus(Integer.parseInt(status));
                customerRequest.setLocation(location);
                customerRequest.setStartTime(startTime);
                customerRequest.setCustomer(customer);
                customerRequest.setRid(rid);
                RequestList.getInstance().appendRequest(customerRequest);

                jsonResponse.append("startTime", startTimeStr);
                jsonResponse.append("location", location);
                jsonResponse.append("rid", rid);
                jsonResponse.append("request-status", status);
                jsonResponse.append("status", 1);
            } catch (Exception e ) {
                jsonResponse.append("status", 0);
            }
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void updateInfo(HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonResponse = new JSONObject();

        Customer customer = daoService.searchCustomer(request.getParameter("cid"));

        if(customer != null) {
            customer.setGender(request.getParameter("gender"));
            customer.setName(request.getParameter("name"));
            customer.setBirth(dateService.getDateFromString(request.getParameter("birth"), StandardDateFormat.WEB_DF));
            customer.setEmail(request.getParameter("email"));
            customer.setTelephone(request.getParameter("telephone"));

            try {
                daoService.getDao().updateCustomer(customer);
                jsonResponse.append("status", 1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse.append("status", 0);
            }
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void getRequestInfo(HttpServletResponse response, String rid) {
        JSONObject jsonResponse = new JSONObject();

        Request request = RequestList.getInstance().getRequest(rid);
        if(request != null) {
            jsonResponse.append("request",mapModel.getMapRequest(request));
            jsonResponse.append("status", 1);
        } else {
            jsonResponse.append("status", 0);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void getCustomerRequests(HttpServletResponse response, String cid) {
        JSONObject jsonResponse = new JSONObject();

        if(RequestList.getInstance().getLength() < 1) {
            jsonResponse.append("status", -1);
        } else {
            for(int i = 0; i < RequestList.getInstance().getLength(); i++) {
                if(cid.equals(RequestList.getInstance().getRequest(i).getCustomer().getCid())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("rid", RequestList.getInstance().getRequest(i).getRid());
                    map.put("startTime", dateService.getStringFromDate(RequestList.getInstance().getRequest(i).getStartTime(), StandardDateFormat.WX_DF));
                    map.put("status", RequestList.getInstance().getRequest(i).getStatus());
                    if(jsonResponse.isNull("requests"))
                        jsonResponse.append("requests", map);
                    else
                        jsonResponse.accumulate("requests", map);
                }
            }

            if(jsonResponse.isNull("requests"))
                jsonResponse.append("status", 0);
            else
                jsonResponse.append("status", 1);
        }



        fileRequestService.setResponse(response, jsonResponse);
    }
}
