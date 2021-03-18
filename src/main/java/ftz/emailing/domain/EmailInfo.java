package ftz.emailing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmailInfo {

    private String to;
    private String from;
    private String subject;
}
