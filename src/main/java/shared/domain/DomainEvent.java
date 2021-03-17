package shared.domain;

import shared.domain.validator.UUIDValidator;

public abstract class DomainEvent {

    private String aggregateId;

    public DomainEvent(String aggregateId){
        this.aggregateId = aggregateId;
    }

    public String getAggregateId(){ return aggregateId; }
}
