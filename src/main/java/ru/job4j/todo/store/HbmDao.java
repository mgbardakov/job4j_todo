package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Serializable;
import java.util.List;

public abstract class HbmDao <T, I extends Serializable> implements DAO<T, I>, AutoCloseable {

    private final StandardServiceRegistry registry;
    protected final SessionFactory sf;

    public HbmDao() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    @Override
    public T create(T model) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            session.save(model);
            transaction.commit();
            return model;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public T read(Class<T> type, I id) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        T rsl;
        try {
            rsl = session.get(type, id);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(T model) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            session.update(model);
            transaction.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(T model) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            session.delete(model);
            transaction.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<T> findAll(Class<T> type) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        List<T> rslList;
        try {
            var cr = session.getCriteriaBuilder().createQuery(type);
            cr.select(cr.from(type));
            rslList = session.createQuery(cr).getResultList();
            transaction.commit();
            return rslList;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
