package bll.controller;

import bll.service.*;
import dal.model.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class DispatchControllerImpl implements DispatchController {
    private static DaoService daoService;
    private static FileRequestService fileRequestService;
    private static DateService dateService;
    private static MapModel mapModel;

    public DispatchControllerImpl() {
        daoService = new DaoServiceImpl();
        fileRequestService = new FileRequestServiceImpl();
        dateService = new DateServiceImpl();
        mapModel = new MapModelImpl();
    }

    @Override
    public void doDispatch(HttpServletResponse response, String rid, String sid) {
        JSONObject jsonResponse = new JSONObject();

        //获取请求和客服
        Request request = RequestList.getInstance().getRequest(rid);
        Staff staff = daoService.searchStaff(sid);

        if(request != null && staff != null) {
            //获取创建派工单所需信息，生成派工单号
            String cid = request.getCustomer().getCid();
            Date startTime = request.getStartTime();
            Date dispatchTime = new Date();
            String did = fileRequestService.calMD5OfRequest(rid + "," + sid + "," +
                    dateService.getStringFromDate(startTime, StandardDateFormat.WX_DF) + "," +
                    dateService.getStringFromDate(dispatchTime, StandardDateFormat.WX_DF));

            //创建新的派工单
            DispatchInfo dispatchInfo = new DispatchInfo(did, sid, cid, startTime, dispatchTime);
            //修改客服状态为已派工
            staff.setStatus("onduty");
            //将派工单写入数据库并将单号发送给客户/客服
            try {
                daoService.getDao().addDispatchInfo(dispatchInfo);
                daoService.getDao().updateStaff(staff);
                //将请求状态设定为已派工(1)，并与派工单号绑定
                request.setStatus(1);
                request.setDispatchID(did);
                RequestList.getInstance().appendRequest(request);

                //统计客户派工次数
                Customer customer = daoService.searchCustomer(request.getCustomer().getCid());
                customer.setTotalDispatchTimes(customer.getTotalDispatchTimes() + 1);
                daoService.getDao().updateCustomer(customer);

                jsonResponse.append("status", 0);
            } catch (Exception e) {
                jsonResponse.append("status", -1);
            }
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);

    }

    @Override
    public int dispatchDone(String did, int star, String comment) {
        DispatchInfo dispatchInfo = daoService.searchDispatchInfo(did);

        if(dispatchInfo == null) return -1;

        dispatchInfo.setStar(star);
        dispatchInfo.setCustomerComment(comment);
        dispatchInfo.setStatus("closed");
        dispatchInfo.setEndTime(new Date());

        try {
            daoService.getDao().updateDispatchInfo(dispatchInfo);
            StaffController staffController = new StaffControllerImpl();
            staffController.questDone(dispatchInfo.getSid(), dispatchInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }
}
