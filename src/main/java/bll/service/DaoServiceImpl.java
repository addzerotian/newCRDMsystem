package bll.service;

import dal.dao.ModelDao;
import dal.dao.ModelDaoImpl;
import dal.model.Admin;
import dal.model.Customer;
import dal.model.DispatchInfo;
import dal.model.Staff;

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

    @Override
    public Staff searchStaff(String sid) {
        Staff staff;
        try {
            staff = modelDao.getStaff(sid);
        } catch (Exception e) {
            return null;
        }

        return staff;
    }

    @Override
    public DispatchInfo searchDispatchInfo(String did) {
        DispatchInfo dispatchInfo;
        try {
            dispatchInfo = modelDao.getDispatchInfo(did);
        } catch (Exception e) {
            return null;
        }

        return dispatchInfo;
    }

    @Override
    public int modifyStaff(Staff staff) {
        try {
            modelDao.updateStaff(staff);
        } catch (Exception e) {
            return -1;
        }

        return 0;
    }
}
