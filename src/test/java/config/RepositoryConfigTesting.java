package config;

import ftz.teams.domain.PlayerRepository;
import ftz.teams.domain.TeamPlayerInfoRepository;
import ftz.teams.domain.TeamRepository;
import ftz.teams.infrastructure.PlayerMySQLRepository;
import ftz.teams.infrastructure.TeamMySQLRepository;
import ftz.teams.infrastructure.TeamPlayerInfoMySQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class RepositoryConfigTesting {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    public TeamRepository teamRepository(){
        return new TeamMySQLRepository(entityManagerFactory);
    }

    @Bean
    public PlayerRepository playerRepository(){
        return new PlayerMySQLRepository(entityManagerFactory);
    }

    @Bean
    public TeamPlayerInfoRepository teamPlayerInfoRepository(){
        return new TeamPlayerInfoMySQLRepository(entityManagerFactory);
    }
}