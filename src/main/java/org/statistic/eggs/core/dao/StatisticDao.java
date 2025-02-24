package org.statistic.eggs.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.statistic.eggs.core.entity.Counter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticDao {

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
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + e.getMessage());
        }
    }

    public static List<Counter> getAllData() {
        List<Counter> result = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from Counter", Counter.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Transaction failed: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<Counter> getByDate(LocalDate date) {
        List<Counter> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            result = session.createQuery("FROM Counter c WHERE c.date = :date", Counter.class)
                    .setParameter("date", date)
                    .list();
        } catch (Exception e) {
            System.err.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteByDate(LocalDate date) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            int deletedCount = session.createQuery("DELETE FROM Counter c WHERE c.dateTime = :date")
                    .setParameter("date", date)
                    .executeUpdate();

            transaction.commit();

            System.out.println("Deleted " + deletedCount + " records for date: " + date);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Тільки якщо транзакція активна
            }
            System.err.println("Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void update(Counter selectedEntry) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(selectedEntry);
            transaction.commit();
            System.out.println("Updated record: " + selectedEntry);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
