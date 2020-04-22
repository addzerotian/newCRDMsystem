package bll.controller;

import bll.service.FileRequestService;
import bll.service.FileRequestServiceImpl;
import bll.service.MapModel;
import bll.service.MapModelImpl;
import dal.model.Request;
import dal.model.RequestList;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestControllerImpl implements RequestController {
    private RequestList requestList;
    private FileRequestService fileRequestService;
    private static MapModel mapModel;

    public RequestControllerImpl() {
        requestList = RequestList.getInstance();
        fileRequestService = new FileRequestServiceImpl();
        mapModel = new MapModelImpl();
    }

    @Override
    public void requestFlush(HttpServletResponse response) {
        JSONObject jsonResponse = new JSONObject();

        if(requestList.isListChanged()) {
            int requestNumber = requestList.getWaitingRequests();

            jsonResponse.append("request", mapModel.getMapRequest(requestList.getRequest(0)));
            for(int i = 1; i < requestNumber; i++) {
                Request customerRequest = requestList.getRequest(i);
                jsonResponse.accumulate("request", mapModel.getMapRequest(customerRequest));
            }
            jsonResponse.append("requestNumber", requestNumber);
            jsonResponse.append("status", 0);
            requestList.setListChanged(false);
        }
        else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }
}
