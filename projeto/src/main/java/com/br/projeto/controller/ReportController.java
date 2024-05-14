package com.br.projeto.controller;

import com.br.projeto.dto.report.ReportRequestDTO;
import com.br.projeto.dto.report.ReportResponseDTO;
import com.br.projeto.dto.report.ReportUpdateRequestDTO;
import com.br.projeto.entity.Report;
import com.br.projeto.service.ReportServiceImpl;
import com.br.projeto.utils.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {

    private final MapperUtils mapperUtils = new MapperUtils();

    private final ReportServiceImpl reportService;

    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Page<ReportResponseDTO>> getUserReports(Pageable pageable) {
        Page<Report> reports = reportService.getAllReportsByUser(pageable);
        Page<ReportResponseDTO> response = mapperUtils.mapEntityPageIntoDtoPage(reports, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Page<ReportResponseDTO>> getReports(Pageable pageable) {
        Page<Report> reports = reportService.getAllReports(pageable);
        Page<ReportResponseDTO> response = mapperUtils.mapEntityPageIntoDtoPage(reports, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReportResponseDTO> getReport(@PathVariable Long id) {
        Report reports = reportService.getReportById(id);
        ReportResponseDTO response = mapperUtils.map(reports, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/protocol/{protocolNumber}")
    public ResponseEntity<ReportResponseDTO> getReport(@PathVariable String protocolNumber) {
        Report reports = reportService.getReportByProtocolNumber(protocolNumber);
        ReportResponseDTO response = mapperUtils.map(reports, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ReportResponseDTO> createReport(@RequestBody ReportRequestDTO reportRequestDTO) {
        Report createdReport = reportService.createReport(reportRequestDTO);
        ReportResponseDTO response = mapperUtils.map(createdReport, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReportResponseDTO> setReportResponse(@RequestBody ReportUpdateRequestDTO reportUpdateRequestDTO, @PathVariable Long id) {
        Report createdReport = reportService.setReportResponse(reportUpdateRequestDTO, id);
        ReportResponseDTO response = mapperUtils.map(createdReport, ReportResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

