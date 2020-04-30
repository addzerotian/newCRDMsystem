package bll.controller;

import bll.service.*;
import dal.model.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        JSONObject jsonResponse = new JSONObject();

        Staff staff = daoService.searchStaff(sid);

        if(staff == null) {
            jsonResponse.append("status", -1);
        } else {
            jsonResponse.append("status", 0);
            jsonResponse.append("staff", mapModel.getMapStaff(staff));
        }

        fileRequestService.setResponse(response, jsonResponse);
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
            staff.setStatus("idle");

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
        /*ArrayList<String> sids = new ArrayList<>();
        sids.add("addzero");
        sids.add("addzero1");
        sids.add("addzero2");

        Staff staff;
        int staffNum = 0;
        for (String sid : sids) {
            staff = daoService.searchStaff(sid);
            if(staff == null || !"idle".equals(staff.getStatus())) break;
            if(jsonResponse.isNull("staffs"))
                jsonResponse.append("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
            else
                jsonResponse.accumulate("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
            staffNum++;
        }*/
        List<Staff> staffs = daoService.getDao().getStaffsByProperty("status", "idle");
        if(staffs != null) {
            for(Staff staff: staffs) {
                if(jsonResponse.isNull("staffs"))
                    jsonResponse.append("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
                else
                    jsonResponse.accumulate("staffs", fileRequestService.calcSimuLocationOfStaff(mapModel.getMapStaff(staff), longitude, latitude));
            }
        }

        if(jsonResponse.isNull("staffs")) {
            jsonResponse.append("status", -1);
        }
        else {
            jsonResponse.append("status", 0);
            jsonResponse.append("longitude", longitude);
            jsonResponse.append("latitude", latitude);
            assert staffs != null;
            jsonResponse.append("staffNum", staffs.size());
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void flushStaff(HttpServletResponse response) {
        JSONObject jsonResponse = new JSONObject();

        List<Staff> staffs = daoService.getDao().getAllStaffs();

        if(staffs != null) {
            jsonResponse.append("staffNumber", staffs.size());
            for(Staff staff: staffs) {
                if(jsonResponse.isNull("staffs")) {
                    jsonResponse.append("staffs", mapModel.getMapStaff(staff));
                } else {
                    jsonResponse.accumulate("staffs", mapModel.getMapStaff(staff));
                }
            }

            if(jsonResponse.isNull("staffs")) {
                jsonResponse.append("status", -1);
            } else {
                jsonResponse.append("status", 0);
            }
        } else {
            jsonResponse.append("status", -1);
        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void searchStaffByName(HttpServletResponse response, String name) {
        JSONObject jsonResponse = new JSONObject();

        List<Staff> staffs = daoService.getDao().getStaffsByProperty("name", name);

        if(staffs == null) {
            jsonResponse.append("status", -1);
        } else {
            for(Staff staff:staffs) {
                if(jsonResponse.isNull("staffs"))
                    jsonResponse.append("staffs", mapModel.getMapStaff(staff));
                else
                    jsonResponse.accumulate("staffs", mapModel.getMapStaff(staff));
            }
            if(jsonResponse.isNull("staffs"))
                jsonResponse.append("status", -1);
            else {
                jsonResponse.append("staffNumber", staffs.size());
                jsonResponse.append("status", 1);
            }

        }

        fileRequestService.setResponse(response, jsonResponse);
    }

    @Override
    public void searchStaffsByPropGreaterThan(HttpServletResponse response, String propName, Object value) {
        JSONObject jsonResponse = new JSONObject();

        List<Staff> staffs = daoService.getDao().getStaffsByPropertyGreater(propName, value);

        if(staffs == null) {
            jsonResponse.append("status", -1);
        } else {
            for(Staff staff:staffs) {
                if(jsonResponse.isNull("staffs"))
                    jsonResponse.append("staffs", mapModel.getMapStaff(staff));
                else
                    jsonResponse.accumulate("staffs", mapModel.getMapStaff(staff));
            }
            if(jsonResponse.isNull("staffs"))
                jsonResponse.append("status", -1);
            else {
                jsonResponse.append("staffNumber", staffs.size());
                jsonResponse.append("status", 1);
            }

        }

        fileRequestService.setResponse(response, jsonResponse);
    }
}
