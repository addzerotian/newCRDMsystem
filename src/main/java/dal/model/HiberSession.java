package dal.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HiberSession {
    private static SessionFactory factory;

    public HiberSession() {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable e) {
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public Session createSession() {
        try {
            return factory.openSession();
        }catch (Throwable e) {
            System.err.println("Failed to create session object." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
