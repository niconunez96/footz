package config;

import ftz.teams.domain.PlayerMetadataRepository;
import ftz.teams.domain.PlayerRepository;
import ftz.teams.domain.TeamRepository;
import ftz.teams.infrastructure.PlayerMetadataMySQLRepository;
import ftz.teams.infrastructure.TeamMySQLRepository;
import ftz.teams.infrastructure.PlayerMySQLRepository;
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
    public PlayerMetadataRepository playerRepository(){
        return new PlayerMetadataMySQLRepository(entityManagerFactory);
    }

    @Bean
    public PlayerRepository teamPlayerInfoRepository(){
        return new PlayerMySQLRepository(entityManagerFactory);
    }
}
