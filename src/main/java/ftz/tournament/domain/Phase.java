package ftz.tournament.domain;

public enum Phase {
    SIXTEENTHS,
    EIGHTHS,
    QUARTERS,
    SEMIFINAL,
    FINAL;

    public Phase nextPhase(){
        switch(this){
            case SIXTEENTHS: return EIGHTHS;
            case EIGHTHS: return QUARTERS;
            case QUARTERS: return SEMIFINAL;
            case SEMIFINAL: return FINAL;
            default: throw new RuntimeException("There isn't another phase from FINAL");
        }
    }
}
