package dal.model;

import bll.service.DateService;
import bll.service.DateServiceImpl;
import dal.dao.ModelDao;
import dal.dao.ModelDaoImpl;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;


public class HiberSessionTest {

    @Test
    public void test1() {
        //设置生日
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(1998, 1, 26);
        //admin.setBirth(calendar.getTime());

        //存储头像
        //FileInputStream fileInputStream = new FileInputStream("D:\\addzero\\Documents\\JavaProjects\\newCRDMsystem\\src\\main\\resources\\06841613.jpg");
        //int size = fileInputStream.available();
        //byte[] photo = new byte[size];
        //System.out.println(fileInputStream.read(photo));
        //admin.setAvatarURL(photo);
        //userDao.updateUser(admin);

        //读取头像
        //FileOutputStream fileOutputStream = new FileOutputStream("D:\\addzero\\Documents\\JavaProjects\\newCRDMsystem\\src\\main\\resources\\new.jpg");
        //admin = (Admin) userDao.getUser("user20200312180801");
        //fileOutputStream.write(admin.getAvatarURL());
        //fileOutputStream.close();

        //查询方法
        //CriteriaQuery<Course> criteriaQuery = hiberSession.getCriteriaBuilder().createQuery(Course.class);
        //criteriaQuery.from(Course.class);
        //List<Course> courseList = hiberSession.createQuery(criteriaQuery).getResultList();
        //for(Course course: courseList) {
        //    System.out.println(course.getId() + " " + course.getName());
        //}
    }

    @Test
    public void test2() {

    }
}
