package org.statistic.eggs.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.statistic.eggs.core.entity.Counter;

import java.util.ArrayList;
import java.util.List;

public class StatisticDao {

    public static List<Counter> getAllData() {
        List<Counter> result = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                result = new ArrayList<Counter>(session.createQuery(" from Counter c ").list().stream().toList());
            } catch (Exception e) {
                System.err.println("Transaction failed: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("SessionFactory creation failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return result;
    }
}
