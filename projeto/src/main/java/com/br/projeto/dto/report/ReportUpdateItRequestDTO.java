package com.br.projeto.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportUpdateItRequestDTO implements Serializable {

    private String description;
    private LocalDate dateOfOccurrence;
    private String office;
    private Boolean anonymous;
}
