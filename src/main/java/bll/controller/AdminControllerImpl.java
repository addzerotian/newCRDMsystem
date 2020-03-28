package bll.controller;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import dal.model.Admin;

public class AdminControllerImpl implements AdminController {
    private static DaoService daoService;

    public AdminControllerImpl() {
        daoService = new DaoServiceImpl();
    }

    @Override
    public Admin adminLogin(String aid, String password) {
        Admin admin = daoService.searchAdmin(aid);
        if(admin != null) {
            if(admin.getPassword().equals(password)) return admin;
            else return null;
        } else return null;
    }
}
