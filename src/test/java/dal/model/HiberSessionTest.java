package dal.model;

import dal.dao.ModelDao;
import dal.dao.ModelDaoImpl;
import org.junit.Test;


public class HiberSessionTest {

    @Test
    public void test1() {
        ModelDao modelDao = new ModelDaoImpl();
        Admin admin = new Admin("user20200312180801", "123456", "admin0000001");
        admin.setAuthority(2);
        admin.setName("田佳琳");

        admin = modelDao.getAdmin("admin0000001");
        //设置生日
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(1998, 1, 26);
        //admin.setBirth(calendar.getTime());

        //存储头像
        //FileInputStream fileInputStream = new FileInputStream("D:\\addzero\\Documents\\JavaProjects\\newCRDMsystem\\src\\main\\resources\\06841613.jpg");
        //int size = fileInputStream.available();
        //byte[] photo = new byte[size];
        //System.out.println(fileInputStream.read(photo));
        //admin.setAvatar(photo);
        //userDao.updateUser(admin);

        //读取头像
        //FileOutputStream fileOutputStream = new FileOutputStream("D:\\addzero\\Documents\\JavaProjects\\newCRDMsystem\\src\\main\\resources\\new.jpg");
        //admin = (Admin) userDao.getUser("user20200312180801");
        //fileOutputStream.write(admin.getAvatar());
        //fileOutputStream.close();

        //查询方法
        //CriteriaQuery<Course> criteriaQuery = hiberSession.getCriteriaBuilder().createQuery(Course.class);
        //criteriaQuery.from(Course.class);
        //List<Course> courseList = hiberSession.createQuery(criteriaQuery).getResultList();
        //for(Course course: courseList) {
        //    System.out.println(course.getId() + " " + course.getName());
        //}
    }
}
