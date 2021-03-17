package ftz.teams.infrastructure;

import ftz.teams.domain.Team;
import ftz.teams.domain.TeamRepository;
import org.springframework.stereotype.Repository;
import shared.infrastructure.jpa.JPARepository;

import java.util.Optional;

@Repository
public class TeamMySQLRepository extends JPARepository<Team, Long> implements TeamRepository{

    @Override
    public Optional<Team> findOne(Long id) {
        return this.findById(id);
    }

    @Override
    public void store(Team team) {
        this.persist(team);
    }

    @Override
    protected Class<Team> getEntityClass() {
        return Team.class;
    }
}
