package dal.dao;

import dal.model.*;

import java.util.List;
public interface ModelDao {

    //User表数据库操作
    List<User> getAllUsers();
    User getUser(long uid);
    void updateUser(User user);
    void deleteUser(User user);

    //Admin表数据库操作
    List<Admin> getAllAdmins();
    Admin getAdmin(String aid);
    void addAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(Admin admin);

    //Customer表数据库操作
    List<Customer> getAllCustomers();
    Customer getCustomer(String cid);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);

    //Staff表数据库操作
    List<Staff> getAllStaffs();
    Staff getStaff(String sid);
    void addStaff(Staff staff);
    void updateStaff(Staff staff);
    void deleteStaff(Staff staff);

    //DispatchInfo表数据库操作
    List<DispatchInfo> getAllDispatchInfos();
    DispatchInfo getDispatchInfo(String did);
    List<DispatchInfo> searchDispatchesByCid(String cid);
    void addDispatchInfo(DispatchInfo dispatch);
    void updateDispatchInfo(DispatchInfo dispatch);
    void deleteDispatchInfo(DispatchInfo dispatch);
}
