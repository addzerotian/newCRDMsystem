package bll.service;

import dal.model.Admin;
import dal.model.Staff;
import dal.model.StandardDateFormat;
import dal.model.User;

import java.util.HashMap;

public class MapModelImpl implements MapModel {
    private static DateService dateService;

    public MapModelImpl() {
        dateService = new DateServiceImpl();
    }

    @Override
    public HashMap<String, Object> getMapUser(User user) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("uid", user.getUid());
        if(user.getGender() != null) map.put("gender", user.getGender());
        else map.put("gender", "");
        map.put("password", user.getPassword());
        if(user.getName() != null)map.put("name", user.getName());
        else map.put("name", "");
        if(user.getBirth() != null) map.put("birth", dateService.getStringFromDate(user.getBirth(), StandardDateFormat.WEB_DF));
        else map.put("birth", "");
        if(user.getTelephone() != null)map.put("telephone", user.getTelephone());
        else map.put("telephone", "");
        if(user.getEmail() != null)map.put("email", user.getEmail());
        else map.put("email", "");
        if(user.getAvatarURL() != null)map.put("avatarURL", user.getAvatarURL());
        else map.put("avatarURL", "defaultAvatar01");

        return map;
    }

    @Override
    public HashMap<String, Object> getMapAdmin(Admin admin) {
        HashMap<String, Object> map = this.getMapUser(admin);

        map.put("aid", admin.getAid());
        map.put("authority", admin.getAuthority());

        return map;
    }

    @Override
    public HashMap<String, Object> getMapStaff(Staff staff) {
        HashMap<String, Object> map = this.getMapUser(staff);
        map.put("sid", staff.getSid());
        map.put("dutyTotalTimes", staff.getDutyTotalTimes());
        map.put("dutyTotalHours", staff.getDutyMonthHours());
        map.put("gradeTotal", staff.getGradeTotal());
        map.put("dutyMonthTimes", staff.getDutyMonthTimes());
        map.put("dutyMonthHours", staff.getDutyMonthHours());
        map.put("gradeMonth", staff.getGradeMonth());
        map.put("absenceTotal", staff.getAbsenceTotal());
        map.put("absenceMonth", staff.getAbsenceMonth());

        return map;
    }
}
