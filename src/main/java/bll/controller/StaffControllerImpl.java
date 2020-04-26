package bll.controller;

import bll.service.*;
import dal.model.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class StaffControllerImpl implements StaffController {
    private static DaoService daoService;
    private static FileRequestService fileRequestService;
    private static DateService dateService;
    private static MapModel mapModel;

    public StaffControllerImpl() {
        daoService = new DaoServiceImpl();
        fileRequestService = new FileRequestServiceImpl();
        dateService = new DateServiceImpl();
        mapModel = new MapModelImpl();
    }

    @Override
    public void addStaff(HttpServletResponse response, Map<String, Object> mapRequest) {
        //设置请求和响应数据
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = (JSONObject) mapRequest.get("request-data");

        //初始化数据
        String name = null;
        String gender = null;
        String telephone = null;
        Date birth = null;
        String email = null;
        byte[] avatar;
        String avatarURL = null;

        //从请求中获取相应数据
        if(jsonRequest.getString("sname") != null && !"".equals(jsonRequest.getString("sname"))) name = jsonRequest.getString("sname");
        if(jsonRequest.getString("email") != null && !"".equals(jsonRequest.getString("email"))) email = jsonRequest.getString("email");
        if(jsonRequest.getString("telephone") != null && !"".equals(jsonRequest.getString("telephone"))) telephone = jsonRequest.getString("telephone");
        if(jsonRequest.getString("gender") != null && !"".equals(jsonRequest.getString("gender"))) gender = jsonRequest.getString("gender");
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

        //使用获得数据生成staff对象
        Staff staff = new Staff(jsonRequest.getString("sid"));
        staff.setName(name);
        staff.setEmail(email);
        staff.setGender(gender);
        staff.setTelephone(telephone);
        staff.setBirth(birth);
        staff.setAvatarURL(avatarURL);

        //将生成的staff写入数据库，设置response内容并发送
        if(staff.getSid() == null || "".equals(staff.getSid()) || daoService.searchAdmin(staff.getSid()) != null) {
            jsonResponse.append("status", -1);
        } else {
            try {
                daoService.getDao().addStaff(staff);
                jsonResponse.append("status", 0);
            } catch (Exception e) {
                jsonResponse.accumulate("status", -1);
            }
        }

        fileRequestService.setResponse(response, jsonResponse);
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
            jsonObject.append("staff", mapModel.getMapStaff(staff));
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
    public void questDone(String sid, DispatchInfo dispatchInfo) {
        Staff staff = daoService.searchStaff(sid);

        if(staff != null) {
            float totalStars = staff.getDutyTotalTimes() * staff.getGradeTotal() + dispatchInfo.getStar();
            float monthStars = staff.getDutyMonthTimes() * staff.getGradeMonth() + dispatchInfo.getStar();
            double dutyHours = (dispatchInfo.getEndTime().getTime() - dispatchInfo.getStartTime().getTime()) / (1000.0 * 60.0 * 60.0);

            staff.setDutyTotalTimes(staff.getDutyTotalTimes() + 1);
            staff.setGradeTotal(totalStars / staff.getDutyTotalTimes());
            staff.setDutyMonthTimes(staff.getDutyMonthTimes() + 1);
            staff.setGradeMonth(monthStars / staff.getDutyMonthTimes());
            staff.setDutyTotalHours(staff.getDutyTotalHours() + (float) dutyHours);
            staff.setDutyMonthHours(staff.getDutyMonthHours() + (float) dutyHours);

            try {
                daoService.getDao().updateStaff(staff);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modifyStaff(HttpServletResponse response, Map<String, Object> mapRequest) {
        //设置请求和响应数据
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = (JSONObject) mapRequest.get("request-data");

        byte[] avatar;
        String avatarURL;

        //获取旧的staff数据
        Staff oldStaff = daoService.searchStaff(jsonRequest.getString("sid"));

        if (oldStaff != null) {
            //从请求中获取相应数据对旧数据更新
            if (jsonRequest.getString("sname") != null && !"".equals(jsonRequest.getString("sname")))
                oldStaff.setName(jsonRequest.getString("sname"));
            if (jsonRequest.getString("email") != null && !"".equals(jsonRequest.getString("email")))
                oldStaff.setEmail(jsonRequest.getString("email"));
            if (jsonRequest.getString("telephone") != null && !"".equals(jsonRequest.getString("telephone")))
                oldStaff.setTelephone(jsonRequest.getString("telephone"));
            if (jsonRequest.getString("gender") != null && !"".equals(jsonRequest.getString("gender")))
                oldStaff.setGender(jsonRequest.getString("gender"));
            if (jsonRequest.getString("birth") != null && !"".equals(jsonRequest.getString("birth")) && Pattern.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", jsonRequest.getString("birth"))) {
                oldStaff.setBirth(dateService.getDateFromString(jsonRequest.getString("birth"), StandardDateFormat.WEB_DF));
            }
            if (mapRequest.get("avatar") != null && !"".equals(mapRequest.get("avatar"))) {
                avatar = (byte[]) mapRequest.get("avatar");
                //使用文件md5值作为文件名
                avatarURL = fileRequestService.calcMD5OfFile(avatar);
                String realPath = mapRequest.get("web-path") + "img" + MultiEnvStandardFormat.getInstance().getFileSeparator() + "userAvatar";
                fileRequestService.parseImageBySpecifiedURL(avatar, realPath, avatarURL);
                oldStaff.setAvatarURL(avatarURL);
            }

            //将数据更新后的oldStaff写入数据库，设置response内容并发送
            try {
                daoService.getDao().updateStaff(oldStaff);
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
    public void simuAroundStaffs(HttpServletResponse response, double longitude, double latitude) {
        JSONObject jsonResponse = new JSONObject();
        ArrayList<String> sids = new ArrayList<>();
        sids.add("addzero1");
        sids.add("addzero2");

        System.out.println(longitude + ", " + latitude);
        Staff staff = daoService.searchStaff("addzero");
        jsonResponse.append("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
        for (String sid : sids) {
            staff = daoService.searchStaff(sid);
            jsonResponse.accumulate("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
        }

        jsonResponse.append("status", 0);
        jsonResponse.append("longitude", longitude);
        jsonResponse.append("latitude", latitude);
        jsonResponse.append("staffNum", sids.size() + 1);

        fileRequestService.setResponse(response, jsonResponse);
    }
}
