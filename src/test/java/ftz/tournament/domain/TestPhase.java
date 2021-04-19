package ftz.tournament.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Comparator;
import java.util.stream.Stream;

import static ftz.tournament.domain.Phase.FINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestPhase {

    @ParameterizedTest
    @CsvSource(value = {
            "SIXTEENTHS, EIGHTHS",
            "EIGHTHS, QUARTERS",
            "QUARTERS, SEMIFINAL",
            "SEMIFINAL, FINAL",
    })
    public void itShouldReturnNextPhaseForEachPhase(Phase previousPhase, Phase expectedPhase){
        assertThat(previousPhase.nextPhase()).isEqualTo(expectedPhase);
    }

    @Test
    public void itShouldRaiseErrorWhenAskForNextPhaseInFinal(){
        assertThatThrownBy(FINAL::nextPhase)
                .hasMessage("There isn't another phase from FINAL");
    }
}
