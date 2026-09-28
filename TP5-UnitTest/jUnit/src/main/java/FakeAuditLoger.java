public class FakeAuditLoger implements AuditLogger {
    public int contador = 0;

    public void log (String userId, String event, boolean success){
        contador++;
    }
}
