package com.br.projeto.service;

import com.br.projeto.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    Page<Report> getAllReports(Pageable pageable);

    Page<Report> getAllReportsByUser(Pageable pageable);

    Report getReportById(Long id);

    Report saveReport(Report report);

    Report updateReport(Report report);
}
