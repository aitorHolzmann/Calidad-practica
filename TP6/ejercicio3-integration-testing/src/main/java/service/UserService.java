package service;

import model.User;
import repository.UserRepository;

public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = new User();
        user.setEmail(email);
        user.setActive(true);
        repo.save(user);
    }
}
