package bll.service;

import dal.dao.ModelDao;
import dal.model.Admin;
import dal.model.Customer;

public interface DaoService {
    ModelDao getDao();
    boolean searchUser(long uid);
    Customer searchCustomer(String cid);
    Admin searchAdmin(String aid);
}
