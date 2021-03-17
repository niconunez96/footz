package shared.infrastructure.eventbus;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import shared.domain.DomainEvent;
import shared.domain.EventBus;

@Primary
@Component
public class SpringEventBus implements EventBus {

    private ApplicationEventPublisher eventPublisher;

    public SpringEventBus(ApplicationEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
