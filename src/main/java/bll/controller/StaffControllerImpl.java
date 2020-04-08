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
            staff = new Staff(sid);
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

    @Override
    public void modifyStaff(HttpServletResponse response, Staff staff) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        Staff oldStaff = daoService.searchStaff(staff.getSid());
        Staff newStaff = oldStaff;
        if(staff.getName() != null && !staff.getName().equals("") && !staff.getName().equals(oldStaff.getName())) newStaff.setName(staff.getName());
        if(staff.getPassword() != null && !"123456".equals(staff.getPassword()) && !staff.getPassword().equals(oldStaff.getPassword())) newStaff.setPassword(staff.getPassword());
        if(staff.getBirth() != null && staff.getBirth() != oldStaff.getBirth()) newStaff.setBirth(staff.getBirth());
        if(staff.getTelephone() != null && !staff.getTelephone().equals("") && !staff.getTelephone().equals(oldStaff.getTelephone())) newStaff.setTelephone(staff.getTelephone());
        if(staff.getEmail() != null && !staff.getEmail().equals("") && !staff.getEmail().equals(oldStaff.getEmail())) newStaff.setEmail(staff.getEmail());
        if(staff.getAvatar() != null && staff.getAvatar() != oldStaff.getAvatar()) newStaff.setAvatar(staff.getAvatar());
        if(staff.getGender() != null && !staff.getGender().equals("") && !staff.getGender().equals(oldStaff.getGender())) newStaff.setGender(staff.getGender());
        if(oldStaff != null) {
            if(daoService.modifyStaff(newStaff) == 0) {
                jsonObject.append("status", 0);
            } else {
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
}
