package ftz.emailing.infrastructure;

import ftz.emailing.domain.EmailInfo;
import ftz.emailing.domain.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringEmailSender implements EmailSender {
    private JavaMailSender mailSender;

    public SpringEmailSender(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailInfo emailInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailInfo.getTo());
        message.setFrom(emailInfo.getFrom());
        message.setSubject(emailInfo.getSubject());
        message.setText("Welcome to the new team");
        mailSender.send(message);
    }

    @Override
    public void sendEmails(List<EmailInfo> emailInfos) {
        emailInfos.forEach(this::sendEmail);
    }
}
