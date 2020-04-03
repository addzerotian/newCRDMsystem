package bll.controller;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import dal.model.Staff;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StaffControllerImpl implements StaffController {
    private static DaoService daoService;

    public StaffControllerImpl() {
        daoService = new DaoServiceImpl();
    }

    @Override
    public void addStaff(HttpServletResponse response, String sid) {
        Staff staff = daoService.searchStaff(sid);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        if(staff == null) {
            staff = new Staff(sid, "123456");
            try {
                daoService.getDao().addStaff(staff);
                jsonObject.append("status", 0);
            } catch (Exception e) {
                jsonObject.append("status", -1);
            }
        } else {
            jsonObject.append("status", -1);
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
    public void searchStaff(HttpServletResponse response, String sid) {
        Staff staff = daoService.searchStaff(sid);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        if(staff == null) {
            jsonObject.append("status", -1);
        } else {
            jsonObject.append("status", 0);
            jsonObject.append("staff", staff.getMapStaff());
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
}
