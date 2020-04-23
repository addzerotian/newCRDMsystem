package bll.controller;

import dal.model.RequestList;

import javax.servlet.http.HttpServletResponse;

public interface RequestController {
    void requestFlush(HttpServletResponse response, int trigger);
}
