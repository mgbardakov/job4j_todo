package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.todo.model.Item;

import java.util.Comparator;
import java.util.List;

public class HbmItemDAO extends HbmDao<Item, Integer> implements ItemDAO {

    private HbmItemDAO() {
    }

    private static final class Lazy {
        private static final ItemDAO INST = new HbmItemDAO();
    }

    public static ItemDAO instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Item> findUndoneItems() {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            var query = session.createQuery("from ru.job4j.todo.model.Item i where i.done = :done");
            query.setParameter("done", false);
            session.getTransaction().commit();
            var rsl = query.list();
            rsl.sort(Comparator.comparing(Item::getId));
            return rsl;
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAll(Class<Item> type) {
        var rsl = super.findAll(type);
        rsl.sort(Comparator.comparing(Item::getId));
        return rsl;
    }
}
