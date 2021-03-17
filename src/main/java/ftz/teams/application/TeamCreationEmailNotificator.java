package ftz.teams.application;

import ftz.teams.domain.TeamCreatedEvent;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TeamCreationEmailNotificator {

    @EventListener
    public void sendEmailNotification(TeamCreatedEvent event){
        LoggerFactory.getLogger(this.getClass())
                .info(String.format("Id: %s, Name: %s", event.getAggregateId(), event.getTeamName()));
    }
}
