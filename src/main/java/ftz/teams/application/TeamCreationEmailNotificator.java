package ftz.teams.application;

import ftz.emailing.domain.EmailInfo;
import ftz.emailing.domain.EmailSender;
import ftz.teams.domain.TeamCreatedEvent;
import ftz.teams.domain.TeamId;
import ftz.teams.domain.Player;
import ftz.teams.domain.PlayerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamCreationEmailNotificator {

    private EmailSender emailSender;
    private PlayerRepository playerRepository;

    public TeamCreationEmailNotificator(PlayerRepository playerRepository, EmailSender emailSender){
        this.playerRepository = playerRepository;
        this.emailSender = emailSender;
    }

    @EventListener
    public void sendEmailNotification(TeamCreatedEvent event){
        LoggerFactory.getLogger(this.getClass())
                .info(String.format("Id: %s, Name: %s", event.getAggregateId(), event.getTeamName()));
        List<Player> players = playerRepository
                .findByTeamId(TeamId.fromString(event.getAggregateId()));

        List<EmailInfo> emailInfos = players.stream()
                .filter(teamPlayerInfo -> !teamPlayerInfo.email().isBlank())
                .map(teamPlayerInfo -> new EmailInfo(
                        teamPlayerInfo.email(), "ftz@mail.com", "FTZ: You were invited to a team!"
                ))
                .collect(Collectors.toList());
        emailSender.sendEmails(emailInfos);
    }
}
