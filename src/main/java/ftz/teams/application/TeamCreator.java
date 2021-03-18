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
    private PlayerRepository playerRepository;
    private EventBus eventBus;

    public TeamCreator(TeamRepository teamRepository, PlayerRepository playerRepository, EventBus eventBus) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.eventBus = eventBus;
    }

    private Player obtainPlayer(String email, String identification, String name) {
        Optional<Player> player = playerRepository.findOne(identification, email);
        return player.orElseGet(() -> {
            Player newPlayer = new Player(new PlayerId(), email, identification, name);
            playerRepository.store(newPlayer);
            return newPlayer;
        });
    }

    private TeamPlayerInfo createTeamPlayerInfo(NewPlayer newPlayer) {
        Player player = this.obtainPlayer(newPlayer.getEmail(), newPlayer.getIdentification(), newPlayer.getName());
        return new TeamPlayerInfo(player, newPlayer.getShirtNumber());
    }

    public void validateIdentifications(List<NewPlayer> newPlayers) {
        long distinctIdentifications = newPlayers.stream().map(NewPlayer::getIdentification).distinct().count();
        if (distinctIdentifications != newPlayers.size())
            throw new DuplicateIdentificationsError();
    }

    public TeamResponse createTeam(String name, List<NewPlayer> newPlayers) {
        validateIdentifications(newPlayers);
        Set<TeamPlayerInfo> teamPlayerInfos = newPlayers.stream()
                .map(this::createTeamPlayerInfo).collect(Collectors.toSet());

        TeamId teamId = new TeamId();
        Team newTeam = new Team(teamId, name, teamPlayerInfos);
        teamRepository.store(newTeam);

        eventBus.publish(new TeamCreatedEvent(teamId.getValue().toString(), name));

        return new TeamResponse(teamId.getValue().toString(), name);
    }
}
