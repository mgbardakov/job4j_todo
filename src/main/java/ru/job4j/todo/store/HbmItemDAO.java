package ru.job4j.todo.store;

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
        return txFunc(session -> {
            var query = session.createQuery(
                    "select distinct i from Item i left join fetch i.categories" +
                       " where i.done = :done", Item.class);
            query.setParameter("done", false);
            var rsl = query.list();
            rsl.sort(Comparator.comparing(Item::getId));
            return rsl;
        });
    }

    @Override
    public List<Item> findAll(Class<Item> type) {
          var rsl = txFunc(session -> session.createQuery(
                      "select distinct i from Item i left join fetch i.categories", Item.class)
                         .list());
          rsl.sort(Comparator.comparing(Item::getId));
          return rsl;
    }
}
