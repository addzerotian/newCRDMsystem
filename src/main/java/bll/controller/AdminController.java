package bll.controller;

import dal.model.Admin;

public interface AdminController {
    Admin adminLogin(String aid, String password);
}
