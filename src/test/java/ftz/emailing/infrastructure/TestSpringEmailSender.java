package ftz.emailing.infrastructure;

import com.icegreen.greenmail.util.GreenMail;
import config.SMTPConfigTesting;
import ftz.emailing.domain.EmailInfo;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ContextConfiguration(classes = SMTPConfigTesting.class)
public class TestSpringEmailSender {

    @Mock
    JavaMailSender mockMailSender;
    @Captor
    ArgumentCaptor<SimpleMailMessage> simpleMailMessageCaptor;

    @BeforeClass
    public static void setUpClass(){
        GreenMail testSmtp = new GreenMail();
        testSmtp.start();
    }

    @Test
    public void itShouldSendEmailWithInfoSpecified(){
        SpringEmailSender emailSender = new SpringEmailSender(mockMailSender);
        EmailInfo info = EmailInfo.builder()
                .from("john@doe.com")
                .to("testUser@test.com")
                .subject("Test subject").build();
        doNothing().when(mockMailSender).send(any(SimpleMailMessage.class));

        emailSender.sendEmail(info);

        then(mockMailSender).should().send(simpleMailMessageCaptor.capture());
        SimpleMailMessage messageSent = simpleMailMessageCaptor.getValue();
        assertThat(messageSent.getFrom()).isEqualTo("john@doe.com");
        assertThat(messageSent.getTo()).containsExactly("testUser@test.com");
        assertThat(messageSent.getSubject()).isEqualTo("Test subject");
    }

}
