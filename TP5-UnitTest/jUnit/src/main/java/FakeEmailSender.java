import java.util.ArrayList;

public class FakeEmailSender implements EmailSender {
    ArrayList<String> historial = new ArrayList<>();

    public void send(String to, String subject, String body){
        String registro = "To: " + to + " subject: " + subject + ", body: " + body;
        historial.add(registro);
    }

    public int getNumberEmails(){
        return historial.size();
    }
}
