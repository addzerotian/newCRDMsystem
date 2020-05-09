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
    private FileRequestService fileRequestService;
    private static MapModel mapModel;

    public RequestControllerImpl() {
        fileRequestService = new FileRequestServiceImpl();
        mapModel = new MapModelImpl();
    }

    @Override
    public void requestFlush(HttpServletResponse response, int trigger) {
        JSONObject jsonResponse = new JSONObject();

        if(trigger == 0) {
            if(RequestList.getInstance().isListChanged()) {
                int requestNumber = RequestList.getInstance().getWaitingRequests();

                if(requestNumber > 0) {
                    for(int i = 0; i < RequestList.getInstance().getLength(); i++) {
                        Request request = RequestList.getInstance().getRequest(i);
                        if(request.getStatus() == 0) {
                            if(jsonResponse.isNull("request")) {
                                jsonResponse.append("request", mapModel.getMapRequest(request));
                            }
                            else {
                                jsonResponse.accumulate("request", mapModel.getMapRequest(request));
                            }
                        }
                    }
                    jsonResponse.append("requestNumber", requestNumber);
                    jsonResponse.append("status", 0);
                } else {
                    jsonResponse.append("status", 1);
                }

                RequestList.getInstance().setListChanged(false);
            }
            else {
                jsonResponse.append("status", -1);
            }
        } else if (trigger == 1) {
            int requestNumber = RequestList.getInstance().getWaitingRequests();

            if(requestNumber > 0) {
                for(int i = 0; i < RequestList.getInstance().getLength(); i++) {
                    Request request = RequestList.getInstance().getRequest(i);
                    if(request.getStatus() == 0) {
                        if(jsonResponse.isNull("request")) {
                            jsonResponse.append("request", mapModel.getMapRequest(request));
                        }
                        else {
                            jsonResponse.accumulate("request", mapModel.getMapRequest(request));
                        }
                    }
                }
                jsonResponse.append("requestNumber", requestNumber);
                jsonResponse.append("status", 0);
            } else {
                jsonResponse.append("status", 1);
            }

        }



        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void requestReject(HttpServletResponse response, String rid, String reason) {
        JSONObject jsonResponse = new JSONObject();

        Request request = RequestList.getInstance().getRequest(rid);

        if(request == null) {
            jsonResponse.append("status", -1);
        } else {
            request.setRejectReason(reason);
            request.setStatus(-2);
            RequestList.getInstance().appendRequest(request);
            jsonResponse.append("status", 0);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }
}
