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
        String result = "{\"status\":0,\"result\":[{\"x\":114.2307519546763,\"y\":29.57908428837437}]}";

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject res = (JSONObject) jsonObject.getJSONArray("result").get(0);
            System.out.println(res.get("x"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(map.get("longitude") + ", " + map.get("latitude"));
    }
}
