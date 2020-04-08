package dal.dao;

import bll.service.DaoService;
import bll.service.DaoServiceImpl;
import dal.model.HiberSession;
import dal.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ModelDaoImplTest {

    @Test
    public void getAllAdmins() {
        HiberSession hiberSession = new HiberSession();
        Session session = hiberSession.createSession();
        CriteriaQuery<User> userQuery = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRoot = userQuery.from(User.class);
        userQuery.select(userRoot);
        List<User> users = null;
        try {
            users = session.createQuery(userQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
