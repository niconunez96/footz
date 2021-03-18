package ftz.emailing.domain;

import java.util.List;

public interface EmailSender {

    void sendEmail(EmailInfo emailInfo);

    void sendEmails(List<EmailInfo> emailInfos);
}
