package bll.controller;

import bll.service.*;
import dal.model.ActiveAdminList;
import dal.model.Admin;
import dal.model.MultiEnvStandardFormat;
import dal.model.StandardDateFormat;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class AdminControllerImpl implements AdminController {
    private static DaoService daoService;
    private static FileRequestService fileRequestService;
    private static DateService dateService;
    private static MapModel mapModel;

    public AdminControllerImpl() {
        daoService = new DaoServiceImpl();
        fileRequestService = new FileRequestServiceImpl();
        dateService = new DateServiceImpl();
        mapModel = new MapModelImpl();
        MultiEnvStandardFormat.getInstance().changeToWindowsFileSeparator();
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
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        try {
            ActiveAdminList.getInstance().removeActiveAdmin(admin);
            jsonResponse.append("status", 0);
        } catch (Exception e) {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void addAdmin(Map<String, Object> mapRequest, HttpServletResponse response) {
        //设置请求和响应数据
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = (JSONObject) mapRequest.get("request-data");

        //初始化数据
        String name = null;
        String gender = null;
        String telephone = null;
        int authority = Integer.parseInt(jsonRequest.getString("authority"));
        int requestAdminAuth = Integer.parseInt(jsonRequest.getString("requestAdminAuth"));
        Date birth = null;
        String email = null;
        byte[] avatar;
        String avatarURL = null;

        //从请求中获取相应数据
        if(jsonRequest.getString("aname") != null && !"".equals(jsonRequest.getString("aname"))) name = jsonRequest.getString("aname");
        if(jsonRequest.getString("email") != null && !"".equals(jsonRequest.getString("email"))) email = jsonRequest.getString("email");
        if(jsonRequest.getString("telephone") != null && !"".equals(jsonRequest.getString("telephone"))) telephone = jsonRequest.getString("telephone");
        if(jsonRequest.getString("gender") != null && !"".equals(jsonRequest.getString("gender"))) gender = jsonRequest.getString("gender");
        if(authority <= requestAdminAuth) authority = -1;
        if(jsonRequest.getString("birth") != null && !"".equals(jsonRequest.getString("birth")) && Pattern.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", jsonRequest.getString("birth"))) {
            birth = dateService.getDateFromString(jsonRequest.getString("birth"), StandardDateFormat.WEB_DF);
        }
        if(mapRequest.get("avatar") != null && !"".equals(mapRequest.get("avatar"))) {
            avatar = (byte[]) mapRequest.get("avatar");
            //使用文件md5值作为文件名
            avatarURL = fileRequestService.calcMD5OfFile(avatar);
            String realPath = mapRequest.get("web-path") + "img" + MultiEnvStandardFormat.getInstance().getFileSeparator() + "userAvatar";
            fileRequestService.parseImageBySpecifiedURL(avatar, realPath, avatarURL);
        }

        //使用获得数据生成admin对象
        Admin admin = new Admin(jsonRequest.getString("aid"));
        admin.setName(name);
        admin.setAuthority(authority);
        admin.setEmail(email);
        admin.setGender(gender);
        admin.setTelephone(telephone);
        admin.setBirth(birth);
        admin.setAvatarURL(avatarURL);

        //将生成的admin写入数据库，设置response内容并发送
        if(admin.getAid() == null || "".equals(admin.getAid()) || daoService.searchAdmin(admin.getAid()) != null || admin.getAuthority() == -1) {
            jsonResponse.append("status", -1);
        } else {
            try {
                daoService.getDao().addAdmin(admin);
                jsonResponse.append("status", 0);
            } catch (Exception e) {
                jsonResponse.accumulate("status", -1);
            }
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void getInfo(HttpServletResponse response) {
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        jsonResponse.append("status", 0);
        jsonResponse.append("total", daoService.getDao().getAllAdmins().size());
        jsonResponse.append("active", ActiveAdminList.getInstance().getLength());

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void searchAdmin(HttpServletResponse response, String aid, int requestAuth) {
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        Admin admin = daoService.searchAdmin(aid);
        if(admin != null) {
            jsonResponse.append("status", 0);
            jsonResponse.append("admin", mapModel.getMapAdmin(admin));
        } else {
            jsonResponse.append("status", -1);
        }
        jsonResponse.append("requestAdminAuth", requestAuth);

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void modifyAdmin(HttpServletResponse response, Map<String, Object> mapRequest) {
        //设置请求和响应数据
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = (JSONObject) mapRequest.get("request-data");

        //初始化数据
        int authority = Integer.parseInt(jsonRequest.getString("authority"));
        int requestAdminAuth = Integer.parseInt(jsonRequest.getString("requestAdminAuth"));
        byte[] avatar;
        String avatarURL;

        //获取旧的admin数据
        Admin oldAdmin = daoService.searchAdmin(jsonRequest.getString("aid"));

        if(oldAdmin != null) {
            //从请求中获取相应数据对旧数据更新
            if(jsonRequest.getString("aname") != null && !"".equals(jsonRequest.getString("aname"))) oldAdmin.setName(jsonRequest.getString("aname"));
            if(jsonRequest.getString("email") != null && !"".equals(jsonRequest.getString("email"))) oldAdmin.setEmail(jsonRequest.getString("email"));
            if(jsonRequest.getString("telephone") != null && !"".equals(jsonRequest.getString("telephone"))) oldAdmin.setTelephone(jsonRequest.getString("telephone"));
            if(jsonRequest.getString("gender") != null && !"".equals(jsonRequest.getString("gender"))) oldAdmin.setGender(jsonRequest.getString("gender"));
            if(oldAdmin.getAuthority() <= requestAdminAuth || authority <= requestAdminAuth) authority = -1;
            else oldAdmin.setAuthority(authority);
            if(jsonRequest.getString("birth") != null && !"".equals(jsonRequest.getString("birth")) && Pattern.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", jsonRequest.getString("birth"))) {
                oldAdmin.setBirth(dateService.getDateFromString(jsonRequest.getString("birth"), StandardDateFormat.WEB_DF));
            }
            if(mapRequest.get("avatar") != null && !"".equals(mapRequest.get("avatar"))) {
                avatar = (byte[]) mapRequest.get("avatar");
                //使用文件md5值作为文件名
                avatarURL = fileRequestService.calcMD5OfFile(avatar);
                String realPath = mapRequest.get("web-path") + "img" + MultiEnvStandardFormat.getInstance().getFileSeparator() + "userAvatar";
                fileRequestService.parseImageBySpecifiedURL(avatar, realPath, avatarURL);
                oldAdmin.setAvatarURL(avatarURL);
            }

            //将数据更新后的oldAdmin写入数据库，设置response内容并发送
            if(authority == -1) {
                jsonResponse.append("status", -1);
            } else {
                try {
                    daoService.getDao().updateAdmin(oldAdmin);
                    jsonResponse.append("status", 0);
                } catch (Exception e) {
                    jsonResponse.append("status", -1);
                }
            }
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void flushInfo(HttpServletResponse response, Admin admin) {
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        admin = daoService.searchAdmin(admin.getAid());

        if(admin != null) {
            jsonResponse.append("status", 0);
            jsonResponse.append("admin", mapModel.getMapAdmin(admin));
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void deleteAdmin(HttpServletResponse response, Admin admin) {
        //响应数据
        JSONObject jsonResponse = new JSONObject();

        try {
            ActiveAdminList.getInstance().removeActiveAdmin(admin);
            daoService.getDao().deleteAdmin(admin);
            jsonResponse.append("status", 0);
        } catch (Exception e) {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void updateInfo(HttpServletResponse response, HttpSession session, Map<String, Object> mapRequest) {
        //设置请求和响应数据
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = (JSONObject) mapRequest.get("request-data");

        byte[] avatar;
        String avatarURL;
        boolean isIDExists = false;
        Admin oldAdmin = (Admin) session.getAttribute("admin");

        if (oldAdmin != null) {
            if (!jsonRequest.getString("aid").equals(oldAdmin.getAid())) {
                //判断修改id是否与已存在id重复
                if (daoService.searchAdmin(jsonRequest.getString("aid")) != null) {
                    isIDExists = true;
                    jsonResponse.append("status", -1);
                }
            }
            if(!isIDExists) {
                oldAdmin = daoService.searchAdmin(oldAdmin.getAid());

                if(oldAdmin != null) {
                    //从请求中获取相应数据对旧数据更新
                    if (!"".equals(jsonRequest.getString("aid"))) oldAdmin.setAid(jsonRequest.getString("aid"));
                    if (jsonRequest.getString("aname") != null && !"".equals(jsonRequest.getString("aname")))
                        oldAdmin.setName(jsonRequest.getString("aname"));
                    if (jsonRequest.getString("email") != null && !"".equals(jsonRequest.getString("email")))
                        oldAdmin.setEmail(jsonRequest.getString("email"));
                    if (jsonRequest.getString("telephone") != null && !"".equals(jsonRequest.getString("telephone")))
                        oldAdmin.setTelephone(jsonRequest.getString("telephone"));
                    if (jsonRequest.getString("gender") != null && !"".equals(jsonRequest.getString("gender")))
                        oldAdmin.setGender(jsonRequest.getString("gender"));
                    if (jsonRequest.getString("birth") != null && !"".equals(jsonRequest.getString("birth")) && Pattern.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", jsonRequest.getString("birth"))) {
                        oldAdmin.setBirth(dateService.getDateFromString(jsonRequest.getString("birth"), StandardDateFormat.WEB_DF));
                    }
                    if (mapRequest.get("avatar") != null && !"".equals(mapRequest.get("avatar"))) {
                        avatar = (byte[]) mapRequest.get("avatar");
                        //使用文件md5值作为文件名
                        avatarURL = fileRequestService.calcMD5OfFile(avatar);
                        String realPath = mapRequest.get("web-path") + "img" + MultiEnvStandardFormat.getInstance().getFileSeparator() + "userAvatar";
                        fileRequestService.parseImageBySpecifiedURL(avatar, realPath, avatarURL);
                        oldAdmin.setAvatarURL(avatarURL);
                    }
                    //将数据更新后的oldAdmin写入数据库，设置response内容并发送
                    try {
                        daoService.getDao().updateAdmin(oldAdmin);
                        oldAdmin = daoService.searchAdmin(oldAdmin.getAid());
                        session.setAttribute("admin", oldAdmin);
                        jsonResponse.append("status", 0);
                    } catch (Exception e) {
                        jsonResponse.append("status", -1);
                    }
                } else {
                    jsonResponse.append("status", -1);
                }
            }
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }
}
