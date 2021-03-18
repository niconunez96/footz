package ftz.teams.application;

import ftz.emailing.domain.EmailInfo;
import ftz.emailing.domain.EmailSender;
import ftz.teams.domain.TeamCreatedEvent;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.TeamPlayerInfo;
import ftz.teams.domain.TeamPlayerInfoRepository;
import ftz.teams.infrastructure.TeamPlayerInfoMySQLRepository;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamCreationEmailNotificator {

    private EmailSender emailSender;
    private TeamPlayerInfoRepository teamPlayerInfoRepository;

    public TeamCreationEmailNotificator(TeamPlayerInfoRepository teamPlayerInfoRepository, EmailSender emailSender){
        this.teamPlayerInfoRepository = teamPlayerInfoRepository;
        this.emailSender = emailSender;
    }

    @EventListener
    public void sendEmailNotification(TeamCreatedEvent event){
        LoggerFactory.getLogger(this.getClass())
                .info(String.format("Id: %s, Name: %s", event.getAggregateId(), event.getTeamName()));
        List<TeamPlayerInfo> teamPlayerInfos = teamPlayerInfoRepository
                .findByTeamId(TeamId.fromString(event.getAggregateId()));

        List<EmailInfo> emailInfos = teamPlayerInfos.stream()
                .filter(teamPlayerInfo -> !teamPlayerInfo.email().isBlank())
                .map(teamPlayerInfo -> new EmailInfo(
                        teamPlayerInfo.email(), "ftz@mail.com", "FTZ: You were invited to a team!"
                ))
                .collect(Collectors.toList());
        emailSender.sendEmails(emailInfos);
    }
}
