import java.util.ArrayList;

public class FakeEmailSender_parcial implements EmailSender_parcial{
    private ArrayList<String> lista = new ArrayList<>();

    public void send(String to, String subject, String body){
        lista.add(to);
    }

    public int getEmailCount(){ return lista.size();}
}
