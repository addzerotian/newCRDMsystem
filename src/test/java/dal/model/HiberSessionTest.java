package dal.model;

import org.json.JSONObject;
import org.junit.Test;


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
        double latitude = 29.850848576175064;
        double longitude = 106.0628333345878;
        final double deltaLatPerKM = 1.0 / 111.0;
        System.out.println(Math.cos(Math.toRadians(latitude)));
        final double deltaLngPerKM = 1.0 / (111.0 * Math.cos(latitude));

        double staffLng = Math.random() * deltaLngPerKM * Math.pow(-1, (int)(Math.random() * 2)) + longitude;
        double staffLat = Math.random() * deltaLatPerKM * Math.pow(-1, (int)(Math.random() * 2)) + latitude;
        System.out.println(staffLng);
        System.out.println(staffLat);
        //System.out.println(map.get("longitude") + ", " + map.get("latitude"));
    }
}
