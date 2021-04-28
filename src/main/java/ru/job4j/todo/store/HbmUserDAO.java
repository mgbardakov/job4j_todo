package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import javax.persistence.TypedQuery;

public class HbmUserDAO extends HbmDao<User, Integer> implements UserDAO {

    private HbmUserDAO() {
    }

    private static final class Lazy {
        private static final UserDAO INST = new HbmUserDAO();
    }

    public static UserDAO instOf() {
        return HbmUserDAO.Lazy.INST;
    }

    @Override
    public User getUserByCredentials(String login, String password) {
        var user = getUserByLogin(login);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean isUserExists(User user) {
        return getUserByLogin(user.getLogin()) != null;
    }

    private User getUserByLogin(String login) {
        return txFunc((session -> {
            TypedQuery<User> query = session.createQuery(
                    "from User u where u.login = :login");
            query.setParameter("login", login);
            var list = query.getResultList();
            return list.size() > 0 ? list.get(0) : null;
        }));
    }
}
