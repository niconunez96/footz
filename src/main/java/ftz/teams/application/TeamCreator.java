package ftz.teams.application;

import ftz.teams.domain.*;
import org.springframework.stereotype.Service;
import shared.domain.EventBus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamCreator {

    private TeamRepository teamRepository;
    private PlayerMetadataRepository playerMetadataRepository;
    private EventBus eventBus;

    public TeamCreator(TeamRepository teamRepository, PlayerMetadataRepository playerMetadataRepository, EventBus eventBus) {
        this.teamRepository = teamRepository;
        this.playerMetadataRepository = playerMetadataRepository;
        this.eventBus = eventBus;
    }

    private PlayerMetadata obtainPlayer(String email, String identification, String name) {
        Optional<PlayerMetadata> player = playerMetadataRepository.findOne(identification, email);
        return player.orElseGet(() -> {
            PlayerMetadata newPlayerMetadata = new PlayerMetadata(email, identification, name);
            playerMetadataRepository.store(newPlayerMetadata);
            return newPlayerMetadata;
        });
    }

    private Player createNewPlayer(NewPlayer newPlayer) {
        PlayerMetadata playerMetadata = this.obtainPlayer(newPlayer.getEmail(), newPlayer.getIdentification(), newPlayer.getName());
        return new Player(new PlayerId(), playerMetadata, newPlayer.getShirtNumber());
    }

    public void validateIdentifications(List<NewPlayer> newPlayers) {
        long distinctIdentifications = newPlayers.stream().map(NewPlayer::getIdentification).distinct().count();
        if (distinctIdentifications != newPlayers.size())
            throw new DuplicateIdentificationsError();
    }

    public TeamResponse createTeam(String name, List<NewPlayer> newPlayers) {
        validateIdentifications(newPlayers);
        Set<Player> players = newPlayers.stream()
                .map(this::createNewPlayer).collect(Collectors.toSet());

        TeamId teamId = new TeamId();
        Team newTeam = new Team(teamId, name, players);
        teamRepository.store(newTeam);

        eventBus.publish(new TeamCreatedEvent(teamId.getValue().toString(), name));

        return new TeamResponse(teamId.getValue().toString(), name);
    }
}
