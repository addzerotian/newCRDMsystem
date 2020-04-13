package bll.service;

import dal.model.StandardDateFormat;

import java.util.Date;

public interface DateService {
    void DfSpecify(StandardDateFormat df);
    String getStringFromDate(Date date, StandardDateFormat df);
    Date getDateFromString(String strDate, StandardDateFormat df);
}
