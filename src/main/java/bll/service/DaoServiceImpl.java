package bll.service;

import dal.dao.ModelDao;
import dal.dao.ModelDaoImpl;
import dal.model.Admin;
import dal.model.Customer;

public class DaoServiceImpl implements DaoService {
    private static ModelDao modelDao;

    public DaoServiceImpl() {
        modelDao = new ModelDaoImpl();
    }

    @Override
    public ModelDao getDao() {
        return modelDao;
    }

    @Override
    public boolean searchUser(long uid) {
        try {
            modelDao.getUser(uid);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Customer searchCustomer(String cid) {
        Customer customer;
        try {
            customer = modelDao.getCustomer(cid);
        } catch (Exception e) {
            return null;
        }

        return customer;
    }

    @Override
    public Admin searchAdmin(String aid) {
        Admin admin;
        try {
            admin = modelDao.getAdmin(aid);
        } catch (Exception e) {
            return null;
        }

        return admin;
    }
}
