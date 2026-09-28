
interface EmailService        { void sendCertificate(String userId, String course); }
interface CertificateProvider { String generateUrl(String userId, String courseId); }

class CompletionService {
    // constructor con las 3 dependencias por inyección
    AuditLogger auditLogger;

    public CompletionService(AuditLogger auditLogger){
        this.auditLogger = auditLogger;
    }

    public String complete(String userId, String courseId, String course, boolean isPremium, double score) {
        if (score < 6.0) { 
            auditLogger.log(userId, "FAILED", false); 
            return null; 
        }
        auditLogger.log(userId, "PASSED", true);
        if (isPremium) {
            //String url = certProvider.generateUrl(userId, courseId);
            //emailService.sendCertificate(userId, course);
            //return url;
        }
        //emailService.sendCertificate(userId, course); 
        return null;
    }
}