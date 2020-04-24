package bll.controller;

import javax.servlet.http.HttpServletResponse;

public interface DispatchController {
    void doDispatch(HttpServletResponse response, String rid, String sid);
}
