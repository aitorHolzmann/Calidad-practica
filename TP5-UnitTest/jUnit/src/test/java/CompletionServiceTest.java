import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.*;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith (MockitoExtension.class)
public class CompletionServiceTest {

    @Mock
    FakeAuditLoger auditLogger;

    @Test 
    public void complete_alumnoRegular_returnsNull(){
        //Arrange
        CompletionService completion = new CompletionService(auditLogger);
        String userId = "julian";
        String courseId = "programacion";
        String course = "programacion";

        boolean isPremium = false;
        double score = 7.0;

        //Act
        Object response = completion.complete(userId, courseId, course, isPremium, score);

        //Assert
        assertNull(response);
        verify(auditLogger, times(1)).log(anyString(), anyString(), true);
    }
}
