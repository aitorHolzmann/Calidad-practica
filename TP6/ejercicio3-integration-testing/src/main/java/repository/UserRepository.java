package repository;

import model.User;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
