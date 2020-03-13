package dal.model;

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

    public Staff(String uid, String password, String sid) {
        super(uid, password);
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
}
