package bll.controller;

import javax.servlet.http.HttpServletResponse;

public interface DispatchController {
    void doDispatch(HttpServletResponse response, String rid, String sid);
    int dispatchDone(String did, int star, String comment);
    void getDispatches(HttpServletResponse response);
}
