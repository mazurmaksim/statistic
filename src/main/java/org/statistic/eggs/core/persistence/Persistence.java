package org.statistic.eggs.core.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.statistic.eggs.handler.ErrorHandler;

public class Persistence <T> {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            ErrorHandler.showErrorDialog("Error happened while connection to the database, connection failed" ,e);
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + e.getMessage());
        }
    }

    public void persist(T o) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(o);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
           ErrorHandler.showErrorDialog("Persisting error. Database Error" ,e);
        }
    }
}
