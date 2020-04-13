package bll.service;

import dal.model.StandardDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateServiceImpl implements DateService {
    private static SimpleDateFormat webDf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat wxDf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat specifiedDf;

    public DateServiceImpl() {
    }

    @Override
    public String getStringFromDate(Date date, StandardDateFormat df) {

        String strDate;

        this.DfSpecify(df);
        strDate = specifiedDf.format(date);

        return strDate;
    }

    @Override
    public Date getDateFromString(String strDate, StandardDateFormat df) {
        Date date = null;

        this.DfSpecify(df);
        try {
            date = specifiedDf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void DfSpecify(StandardDateFormat df) {
        switch (df) {
            case WEB_DF: specifiedDf = webDf; break;
            case WX_DF: specifiedDf = wxDf; break;

            default: break;
        }
    }
}
