package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class HbmDao <T, I extends Serializable> implements DAO<T, I> {

    protected static SessionFactory SESSION_FACTORY = new Configuration().configure()
                                                          .buildSessionFactory();

    protected <P> P txFunc(final Function<Session, P> command) {
        final Session session = SESSION_FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            var rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    protected void txCons(final Consumer<Session> command) {
        final Session session = SESSION_FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public T create(T model) {
            txFunc((session -> session.save(model)));
            return model;
    }

    @Override
    public T read(Class<T> type, I id) {
            return txFunc((session -> session.get(type, id)));
    }

    @Override
    public void update(T model) {
        txCons(session -> session.update(model));
    }

    @Override
    public void delete(T model) {
        txCons(session -> session.delete(model));
    }

    @Override
    public List<T> findAll(Class<T> type) {
        return txFunc(session -> {
            var cr = session.getCriteriaBuilder().createQuery(type);
            cr.select(cr.from(type));
            return session.createQuery(cr).getResultList();
        });
    }
}
