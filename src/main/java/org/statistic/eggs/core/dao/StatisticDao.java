package org.statistic.eggs.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.core.entity.FeedComposition;
import org.statistic.eggs.core.entity.Settings;
import org.statistic.eggs.handler.ErrorHandler;

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
            ErrorHandler.showErrorDialog("Could not connect to the database",e);
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + e.getMessage());
        }
    }

    public static List<Counter> getStatisticData() {
        List<Counter> result = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from Counter", Counter.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            ErrorHandler.showErrorDialog("Could not load statistic data from database",e);
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
            ErrorHandler.showErrorDialog("Could not get statistic by date" ,e);
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
            ErrorHandler.showErrorDialog("Could not delete day statistic for date {} " + date.toString(), e);
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
            ErrorHandler.showErrorDialog("Could not update statistic for date " + selectedEntry.getDateTime().toString() ,e);
        }
    }

    public static void saveFeedComposition(FeedComposition feedComposition) {
        // Now save the FeedComposition (which will also save FeedComponents and Vitamins due to cascade)
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Save the FeedComposition, which will cascade to FeedComponents and Vitamins
            session.merge(feedComposition);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ErrorHandler.showErrorDialog("Error happened while saving to the database feed composition", e);
        }
    }

    public static List<FeedComposition> getFeedComposition() {
        List<FeedComposition> result = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from FeedComposition", FeedComposition.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            ErrorHandler.showErrorDialog("Error happened while getting composition", e);
        }
        return result;
    }

    public static FeedComposition getFeedCompositionByName(String name) {
        FeedComposition result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from FeedComposition where name=:name", FeedComposition.class)
                    .setParameter("name", name)
                    .getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            ErrorHandler.showErrorDialog("Could not load composition from database", e);
        }
        return result;
    }

    public static void saveSettings(Settings settings) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(settings);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ErrorHandler.showErrorDialog("Could not save weather settings to the database" ,e);
        }
    }

    public static List<Settings> getAllSettings() {
        List<Settings> result = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from Settings", Settings.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            ErrorHandler.showErrorDialog("Could not load settings from database" ,e);
        }
        return result;
    }
}
