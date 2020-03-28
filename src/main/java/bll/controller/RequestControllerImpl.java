package bll.controller;

import dal.model.Request;
import dal.model.RequestList;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestControllerImpl implements RequestController {
    private RequestList requestList;
    private int requestNumber;

    public RequestControllerImpl() {
        requestList = RequestList.getInstance();
        requestNumber = requestList.getLength();
    }

    @Override
    public void requestFlush(HttpServletResponse response) {
        if(requestList.getLength() != requestNumber) {
            requestNumber = requestList.getLength();
            Request customerRequest = requestList.getRequest(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter send = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("status", 0);
            jsonObject.append("longitude", customerRequest.getLongitude());
            jsonObject.append("latitude", customerRequest.getLatitude());
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
        else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter send = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("status", -1);
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
    }
}
