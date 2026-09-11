public class UserService {
    private final EmailSender emailSender;
    public UserService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void register(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    // Simula lógica de registro...
        emailSender.send(email, "Welcome", "Thanks for registering!");
    }

}