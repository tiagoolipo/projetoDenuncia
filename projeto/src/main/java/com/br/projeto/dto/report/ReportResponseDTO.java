package com.br.projeto.dto.report;

import com.br.projeto.dto.user.ReportUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO implements Serializable {

    private Long reportId;
    private String protocolNumber;
    private String office;
    private String status;
    private String description;
    private String response;
    private Boolean anonymous;
    private LocalDate dateOfOccurrence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ReportUserDTO user;
    private ReportUserDTO manager;
}
