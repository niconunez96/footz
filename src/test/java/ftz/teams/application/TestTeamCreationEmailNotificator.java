package ftz.teams.application;

import ftz.emailing.domain.EmailInfo;
import ftz.emailing.domain.EmailSender;
import ftz.teams.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@ContextConfiguration(classes = FtzApplication.class)
public class TestTeamCreationEmailNotificator {

    @Mock
    EmailSender emailSender;
    @Mock
    TeamPlayerInfoRepository teamPlayerInfoRepository;
    @Captor
    ArgumentCaptor<List<EmailInfo>> emailInfosCaptor;

    public List<TeamPlayerInfo> threePlayersWithOneWithoutEmail() {
        TeamPlayerInfo teamPlayerInfo1 = new TeamPlayerInfo(
                new PlayerMetadata("test1@test.com", "1", "hon"), 10
        );
        TeamPlayerInfo teamPlayerInfo2 = new TeamPlayerInfo(
                new PlayerMetadata("", "2", "noh"), 5
        );
        TeamPlayerInfo teamPlayerInfo3 = new TeamPlayerInfo(
                new PlayerMetadata("test2@test.com", "3", "john"), 7
        );
        return Arrays.asList(teamPlayerInfo1, teamPlayerInfo2, teamPlayerInfo3);
    }

    public List<TeamPlayerInfo> threePlayers() {
        TeamPlayerInfo teamPlayerInfo1 = new TeamPlayerInfo(
                new PlayerMetadata("test1@test.com", "1", "hon"), 10
        );
        TeamPlayerInfo teamPlayerInfo2 = new TeamPlayerInfo(
                new PlayerMetadata("test2@test.com", "2", "noh"), 5
        );
        TeamPlayerInfo teamPlayerInfo3 = new TeamPlayerInfo(
                new PlayerMetadata("test3@test.com", "3", "john"), 7
        );
        return Arrays.asList(teamPlayerInfo1, teamPlayerInfo2, teamPlayerInfo3);
    }

    @Test
    public void itShouldSendEmailNotificationForEachPlayerInTheTeam() {
        TeamCreationEmailNotificator notificator = new TeamCreationEmailNotificator(teamPlayerInfoRepository, emailSender);
        Team team = new Team(new TeamId(), "New team");
        TeamCreatedEvent teamCreatedEvent = new TeamCreatedEvent(team.id().toString(), team.name());

        given(teamPlayerInfoRepository.findByTeamId(team.id()))
                .willReturn(threePlayers());

        notificator.sendEmailNotification(teamCreatedEvent);

        then(emailSender).should().sendEmails(emailInfosCaptor.capture());
        List<EmailInfo> emailInfosSent = emailInfosCaptor.getValue();
        assertThat(emailInfosSent.size()).isEqualTo(3);
    }

    @Test
    public void itShouldNotSendEmailForPlayerThatDoesNotHaveEmail() {
        TeamCreationEmailNotificator notificator = new TeamCreationEmailNotificator(teamPlayerInfoRepository, emailSender);
        Team team = new Team(new TeamId(), "New team");
        TeamCreatedEvent teamCreatedEvent = new TeamCreatedEvent(team.id().toString(), team.name());

        given(teamPlayerInfoRepository.findByTeamId(team.id()))
                .willReturn(threePlayersWithOneWithoutEmail());

        notificator.sendEmailNotification(teamCreatedEvent);

        then(emailSender).should().sendEmails(emailInfosCaptor.capture());
        List<EmailInfo> emailInfosSent = emailInfosCaptor.getValue();
        assertThat(emailInfosSent)
                .map(EmailInfo::getTo)
                .containsExactly("test1@test.com", "test2@test.com");
    }
}
