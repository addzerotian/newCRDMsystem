package dal.model;

import java.util.HashMap;

public class Staff extends User {
    private String sid;
    private int dutyTotalTimes;
    private float dutyTotalHours;
    private float gradeTotal;
    private int dutyMonthTimes;
    private float dutyMonthHours;
    private float gradeMonth;
    private int absenceTotal;
    private int absenceMonth;

    public Staff(String sid, String password) {
        super(password);
        this.sid = sid;
    }

    public Staff(String sid) {
        super();
        this.sid = sid;
    }

    public Staff() {

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getDutyTotalTimes() {
        return dutyTotalTimes;
    }

    public void setDutyTotalTimes(int dutyTotalTimes) {
        this.dutyTotalTimes = dutyTotalTimes;
    }

    public float getDutyTotalHours() {
        return dutyTotalHours;
    }

    public void setDutyTotalHours(float dutyTotalHours) {
        this.dutyTotalHours = dutyTotalHours;
    }

    public float getGradeTotal() {
        return gradeTotal;
    }

    public void setGradeTotal(float gradeTotal) {
        this.gradeTotal = gradeTotal;
    }

    public int getDutyMonthTimes() {
        return dutyMonthTimes;
    }

    public void setDutyMonthTimes(int dutyMonthTimes) {
        this.dutyMonthTimes = dutyMonthTimes;
    }

    public float getDutyMonthHours() {
        return dutyMonthHours;
    }

    public void setDutyMonthHours(float dutyMonthHours) {
        this.dutyMonthHours = dutyMonthHours;
    }

    public float getGradeMonth() {
        return gradeMonth;
    }

    public void setGradeMonth(float gradeMonth) {
        this.gradeMonth = gradeMonth;
    }

    public int getAbsenceTotal() {
        return absenceTotal;
    }

    public void setAbsenceTotal(int absenceTotal) {
        this.absenceTotal = absenceTotal;
    }

    public int getAbsenceMonth() {
        return absenceMonth;
    }

    public void setAbsenceMonth(int absenceMonth) {
        this.absenceMonth = absenceMonth;
    }

    public HashMap<String, Object> getMapStaff() {
        HashMap<String, Object> map = this.getMapUser();
        map.put("sid", sid);
        map.put("dutyTotalTimes", dutyTotalTimes);
        map.put("dutyTotalHours", dutyTotalHours);
        map.put("gradeTotal", gradeTotal);
        map.put("dutyMonthTimes", dutyMonthTimes);
        map.put("dutyMonthHours", dutyMonthHours);
        map.put("gradeMonth", gradeMonth);
        map.put("absenceTotal", absenceTotal);
        map.put("absenceMonth", absenceMonth);

        return map;
    }
}
