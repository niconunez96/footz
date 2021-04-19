package ftz.teams.application;

import ftz.teams.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import shared.domain.EventBus;
import shared.domain.validator.UUIDValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class TestTeamCreator {

    TeamRepository mockTeamRepository = mock(TeamRepository.class);
    PlayerMetadataRepository mockPlayerMetadataRepository = mock(PlayerMetadataRepository.class);
    EventBus mockEventBus = mock(EventBus.class);
    ArgumentCaptor<Team> teamArgumentCaptor = ArgumentCaptor.forClass(Team.class);
    ArgumentCaptor<TeamCreatedEvent> teamCreatedEventArgumentCaptor = ArgumentCaptor.forClass(TeamCreatedEvent.class);

    @Test
    public void itShouldStoreNewTeamWithSpecifiedName() {
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerMetadataRepository, mockEventBus);

        TeamResponse response = creator.createTeam("New team", new ArrayList<>());

        then(mockTeamRepository).should().store(teamArgumentCaptor.capture());

        assertThat(response.getName()).isEqualTo("New team");
        assertThat(UUIDValidator.isValid(response.getId())).isTrue();
    }

    @Test
    public void itShouldPublishTeamCreatedEvent() {
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerMetadataRepository, mockEventBus);

        creator.createTeam("Test team", new ArrayList<>());

        then(mockEventBus).should().publish(teamCreatedEventArgumentCaptor.capture());
        TeamCreatedEvent teamCreatedEvent = teamCreatedEventArgumentCaptor.getValue();
        assertThat(teamCreatedEvent.getTeamName()).isEqualTo("Test team");
        assertThat(UUIDValidator.isValid(teamCreatedEvent.getAggregateId())).isTrue();
    }

    @Test
    public void itShouldRaiseDuplicateIdentificationsError() {
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerMetadataRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>() {{
            add(new NewPlayer("12345", "john", 10, "john@mail.com"));
            add(new NewPlayer("12345", "cristiano", 7, "cr7@mail.com"));
        }};

        assertThatThrownBy(() -> creator.createTeam("Test team", newPlayers))
                .isInstanceOf(DuplicateIdentificationsError.class);
    }

    @Test
    public void itShouldNotDuplicatePlayersThatAlreadyExistWithSameIdentificationAndEmail() {
        PlayerMetadata existentPlayerMetadata = new PlayerMetadata("john@mail.com", "1234", "john");
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerMetadataRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>() {{
            add(new NewPlayer("1234", "john", 10, "john@mail.com"));
            add(new NewPlayer("1235", "cristiano", 7, "cr7@mail.com"));
        }};

        given(mockPlayerMetadataRepository.findOne("1234", "john@mail.com"))
                .willReturn(Optional.of(existentPlayerMetadata));
        creator.createTeam("Test team", newPlayers);

        verify(mockPlayerMetadataRepository, times(1)).store(any(PlayerMetadata.class));
    }

    @Test
    public void itShouldCreateTeamPlayerInfoForEachNewPlayer() {
        TeamCreator creator = new TeamCreator(mockTeamRepository, mockPlayerMetadataRepository, mockEventBus);
        List<NewPlayer> newPlayers = new ArrayList<>() {{
            add(new NewPlayer("12346", "john", 10, "john@mail.com"));
            add(new NewPlayer("12345", "cristiano", 7, "cr7@mail.com"));
        }};

        creator.createTeam("Test team", newPlayers);

        then(mockTeamRepository).should().store(teamArgumentCaptor.capture());
        Team teamStored = teamArgumentCaptor.getValue();
        assertThat(teamStored.players().size()).isEqualTo(2);
    }
}
