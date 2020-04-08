package bll.controller;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import dal.model.ActiveAdminList;
import dal.model.Admin;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AdminControllerImpl implements AdminController {
    private static DaoService daoService;

    public AdminControllerImpl() {
        daoService = new DaoServiceImpl();
    }

    @Override
    public Admin adminLogin(String aid, String password) {
        Admin admin = daoService.searchAdmin(aid);
        if(admin != null) {
            if(admin.getPassword().equals(password)) return admin;
            else return null;
        } else return null;
    }

    @Override
    public void adminLogout(HttpServletResponse response, Admin admin) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        try {
            ActiveAdminList.getInstance().removeActiveAdmin(admin);
            jsonObject.append("status", 0);
        } catch (Exception e) {
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
    public void addAdmin(JSONObject jsonRequest, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        Admin admin = new Admin(jsonRequest.getString("aid"));
        admin.setName(jsonRequest.getString("aname"));
        admin.setAuthority(Integer.parseInt(jsonRequest.getString("authority")));

        if(daoService.searchAdmin(admin.getAid()) != null) {
            jsonObject.append("status", -1);
        } else {
            try {
                daoService.getDao().addAdmin(admin);
                jsonObject.append("status", 0);
            } catch (Exception e) {
                jsonObject.append("status", -1);
            }
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
    public void getInfo(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.append("status", 0);
        jsonObject.append("total", daoService.getDao().getAllAdmins().size());
        jsonObject.append("active", ActiveAdminList.getInstance().getLength());

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
    public void searchAdmin(HttpServletResponse response, String aid) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        Admin admin = daoService.searchAdmin(aid);
        if(admin != null) {
            jsonObject.append("status", 0);
            jsonObject.append("admin", admin.getMapAdmin());
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
    public void modifyAdmin(HttpServletResponse response, JSONObject jsonRequest) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;
        JSONObject jsonObject = new JSONObject();

        String aid = null;
        String name = null;
        String gender = null;
        String telephone = null;
        int authority = -1;
        Date birth = null;
        String email = null;
        byte[] avatar = null;

        aid = jsonRequest.getString("aid");
        Admin oldAdmin = daoService.searchAdmin(aid);

        if(oldAdmin != null) {
            Admin newAdmin = oldAdmin;
            name = jsonRequest.getString("aname");
            gender = jsonRequest.getString("sex");
            telephone = jsonRequest.getString("telephone");
            authority = Integer.parseInt(jsonRequest.getString("authority"));

            if(name != null && !"".equals(name) && !name.equals(oldAdmin.getName())) newAdmin.setName(name);
            if(gender != null && !"".equals(gender) && !gender.equals(oldAdmin.getGender())) newAdmin.setGender(gender);
            if(telephone != null && !"".equals(telephone) && !telephone.equals(oldAdmin.getTelephone())) newAdmin.setTelephone(telephone);
            if(authority != -1 && authority != oldAdmin.getAuthority()) newAdmin.setAuthority(authority);
            if(birth != null && !birth.equals(oldAdmin.getBirth())) newAdmin.setBirth(birth);
            if(email != null && !"".equals(email) && !email.equals(oldAdmin.getEmail())) newAdmin.setEmail(email);
            if(avatar != null && avatar != oldAdmin.getAvatar()) newAdmin.setAvatar(avatar);

            try {
                daoService.getDao().updateAdmin(newAdmin);
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
}
