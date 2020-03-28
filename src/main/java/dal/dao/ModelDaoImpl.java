package dal.dao;

import dal.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ModelDaoImpl implements ModelDao {

    private static HiberSession hiberSession;

    public ModelDaoImpl() {
        hiberSession = new HiberSession();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = hiberSession.createSession();
        CriteriaQuery<User> userQuery = session.getCriteriaBuilder().createQuery(User.class);
        List<User> users = null;
        try {
            users = session.createQuery(userQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public User getUser(long uid) {
        Session session = hiberSession.createSession();
        CriteriaQuery<User> userQuery = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRoot = userQuery.from(User.class);
        userQuery.where(session.getCriteriaBuilder().equal(userRoot.get("uid"), uid));
        User user = null;
        try {
            user = session.createQuery(userQuery).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        Session session = hiberSession.createSession();
        CriteriaQuery<Admin> adminQuery = session.getCriteriaBuilder().createQuery(Admin.class);
        List<Admin> admins = null;
        try {
            admins = session.createQuery(adminQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admins;
    }

    @Override
    public Admin getAdmin(String aid) {
        Session session = hiberSession.createSession();
        CriteriaQuery<Admin> adminQuery = session.getCriteriaBuilder().createQuery(Admin.class);
        Root<Admin> adminRoot =adminQuery.from(Admin.class);
        adminQuery.where(session.getCriteriaBuilder().equal(adminRoot.get("aid"), aid));
        Admin admin = null;
        try {
            admin = session.createQuery(adminQuery).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admin;
    }

    @Override
    public void addAdmin(Admin admin) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(admin);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateAdmin(Admin admin) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(admin);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteAdmin(Admin admin) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(admin);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = hiberSession.createSession();
        CriteriaQuery<Customer> customerQuery = session.getCriteriaBuilder().createQuery(Customer.class);
        List<Customer> customers = null;
        try {
            customers = session.createQuery(customerQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customers;
    }

    @Override
    public Customer getCustomer(String cid) {
        Session session = hiberSession.createSession();
        CriteriaQuery<Customer> customerQuery = session.getCriteriaBuilder().createQuery(Customer.class);
        Root<Customer> customerRoot =customerQuery.from(Customer.class);
        customerQuery.where(session.getCriteriaBuilder().equal(customerRoot.get("cid"), cid));
        Customer customer = null;
        try {
            customer = session.createQuery(customerQuery).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }

    @Override
    public void addCustomer(Customer customer) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Staff> getAllStaffs() {
        Session session = hiberSession.createSession();
        CriteriaQuery<Staff> staffQuery = session.getCriteriaBuilder().createQuery(Staff.class);
        List<Staff> staffs = null;
        try {
            staffs = session.createQuery(staffQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return staffs;
    }

    @Override
    public Staff getStaff(String sid) {
        Session session = hiberSession.createSession();
        CriteriaQuery<Staff> staffQuery = session.getCriteriaBuilder().createQuery(Staff.class);
        Root<Staff> staffRoot =staffQuery.from(Staff.class);
        staffQuery.where(session.getCriteriaBuilder().equal(staffRoot.get("sid"), sid));
        Staff staff = null;
        try {
            staff = session.createQuery(staffQuery).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return staff;
    }

    @Override
    public void addStaff(Staff staff) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(staff);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateStaff(Staff staff) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(staff);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteStaff(Staff staff) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(staff);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<DispatchInfo> getAllDispatchInfos() {
        Session session = hiberSession.createSession();
        CriteriaQuery<DispatchInfo> dispatchQuery = session.getCriteriaBuilder().createQuery(DispatchInfo.class);
        List<DispatchInfo> dispatches = null;
        try {
            dispatches = session.createQuery(dispatchQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dispatches;
    }

    @Override
    public DispatchInfo getDispatchInfo(String did) {
        Session session = hiberSession.createSession();
        CriteriaQuery<DispatchInfo> dispatchQuery = session.getCriteriaBuilder().createQuery(DispatchInfo.class);
        Root<DispatchInfo> dispatchRoot =dispatchQuery.from(DispatchInfo.class);
        dispatchQuery.where(session.getCriteriaBuilder().equal(dispatchRoot.get("did"), did));
        DispatchInfo dispatch = null;
        try {
            dispatch = session.createQuery(dispatchQuery).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dispatch;
    }

    @Override
    public void addDispatchInfo(DispatchInfo dispatch) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(dispatch);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateDispatchInfo(DispatchInfo dispatch) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(dispatch);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteDispatchInfo(DispatchInfo dispatch) {
        Session session = hiberSession.createSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(dispatch);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}