package ftz.teams.application;

import ftz.teams.application.DuplicateIdentificationsError;
import ftz.teams.application.NewPlayer;
import ftz.teams.application.TeamCreator;
import ftz.teams.application.TeamResponse;
import ftz.teams.domain.*;
import ftz.teams.infrastructure.PlayerMySQLRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.FtzApplication;
import shared.domain.EventBus;
import shared.domain.validator.UUIDValidator;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = FtzApplication.class)
public class TestTeamCreator {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private PlayerMySQLRepository playerRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private PlayerRepository mockPlayerRepository;
    @Mock
    private EventBus mockEventBus;
    @Captor
    ArgumentCaptor<Team> teamArgumentCaptor;
    @Captor
    ArgumentCaptor<TeamCreatedEvent> teamCreatedEventArgumentCaptor;

    @Test
    public void itShouldStoreNewTeamWithSpecifiedName(){
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerRepository, mockEventBus);

        TeamResponse response = creator.createTeam("New team", new ArrayList<>());

        then(mockTeamRepository).should().store(teamArgumentCaptor.capture());

        assertThat(response.getName()).isEqualTo("New team");
        assertThat(UUIDValidator.isValid(response.getId())).isTrue();
    }

    @Test
    public void itShouldPublishTeamCreatedEvent(){
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerRepository, mockEventBus);

        creator.createTeam("Test team", new ArrayList<>());

        then(mockEventBus).should().publish(teamCreatedEventArgumentCaptor.capture());
        TeamCreatedEvent teamCreatedEvent = teamCreatedEventArgumentCaptor.getValue();
        assertThat(teamCreatedEvent.getTeamName()).isEqualTo("Test team");
        assertThat(UUIDValidator.isValid(teamCreatedEvent.getAggregateId())).isTrue();
    }

    @Test
    public void itShouldRaiseDuplicateIdentificationsError(){
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>(){{
            add(new NewPlayer("12345", "john", 10, "john@mail.com"));
            add(new NewPlayer("12345", "cristiano", 7, "cr7@mail.com"));
        }};

        assertThatThrownBy(() -> creator.createTeam("Test team", newPlayers))
                .isInstanceOf(DuplicateIdentificationsError.class);
    }

    @Test
    public void itShouldNotDuplicatePlayersThatAlreadyExistWithSameIdentificationAndEmail(){
        Player existentPlayer = new Player(new PlayerId(), "john@mail.com", "1234", "john");
        playerRepository.store(existentPlayer);
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>(){{
            add(new NewPlayer("1234", "john", 10, "john@mail.com"));
            add(new NewPlayer("1235", "cristiano", 7, "cr7@mail.com"));
        }};

        given(mockPlayerRepository.findOne("1234", "john@mail.com"))
                .willReturn(Optional.of(existentPlayer));
        creator.createTeam("Test team", newPlayers);

        verify(mockPlayerRepository, times(1)).store(any(Player.class));
    }

    @Test
    public void itShouldCreateTeamPlayerInfoForEachNewPlayer(){
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>(){{
            add(new NewPlayer("12346", "john", 10, "john@mail.com"));
            add(new NewPlayer("12345", "cristiano", 7, "cr7@mail.com"));
        }};

        creator.createTeam("Test team", newPlayers);

        then(mockTeamRepository).should().store(teamArgumentCaptor.capture());
        Team teamStored = teamArgumentCaptor.getValue();
        assertThat(teamStored.teamPlayerInfos().size()).isEqualTo(2);
    }
}