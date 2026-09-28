public class UserService_parcial {
    private final EmailSender_parcial emailSender;
    
    public UserService_parcial(EmailSender_parcial emailSender) {
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
