package ftz.teams;

import ftz.teams.application.TeamCreator;
import ftz.teams.domain.Team;
import ftz.teams.domain.TeamCreatedEvent;
import ftz.teams.domain.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;
import shared.domain.EventBus;
import shared.domain.validator.UUIDValidator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@ContextConfiguration(classes = FtzApplication.class)
public class TestTeamCreator {

    @Mock
    private TeamRepository mockRepository;
    @Mock
    private EventBus mockEventBus;
    @Captor
    ArgumentCaptor<Team> teamArgumentCaptor;
    @Captor
    ArgumentCaptor<TeamCreatedEvent> teamCreatedEventArgumentCaptor;

    @Test
    public void itShouldStoreNewTeamWithSpecifiedName(){
        TeamCreator creator = new TeamCreator(mockRepository, mockEventBus);

        creator.createTeam("New team");

        then(mockRepository).should().store(teamArgumentCaptor.capture());
        Team teamStored = teamArgumentCaptor.getValue();
        assertThat(teamStored.name()).isEqualTo("New team");
        assertThat(teamStored.id().getValue()).isInstanceOf(UUID.class);
    }

    @Test
    public void itShouldPublishTeamCreatedEvent(){
        TeamCreator creator = new TeamCreator(mockRepository, mockEventBus);

        creator.createTeam("Test team");

        then(mockEventBus).should().publish(teamCreatedEventArgumentCaptor.capture());
        TeamCreatedEvent teamCreatedEvent = teamCreatedEventArgumentCaptor.getValue();
        assertThat(teamCreatedEvent.getTeamName()).isEqualTo("Test team");
        assertThat(UUIDValidator.isValid(teamCreatedEvent.getAggregateId())).isTrue();
    }
}
