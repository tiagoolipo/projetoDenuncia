package com.br.projeto.service;

import com.br.projeto.dto.report.ReportRequestDTO;
import com.br.projeto.dto.report.ReportUpdateRequestDTO;
import com.br.projeto.entity.Report;
import com.br.projeto.entity.user.User;
import com.br.projeto.exception.business.BusinessRuleException;
import com.br.projeto.exception.business.ObjectNotFoundException;
import com.br.projeto.repository.ReportRepository;
import com.br.projeto.repository.user.UserRepository;
import com.br.projeto.utils.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ReportServiceImpl implements ReportService {

    private final MapperUtils mapperUtils = new MapperUtils();
    private final ReportRepository reportRepository;

    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Report> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    @Override
    public Page<Report> getAllReportsByUser(Pageable pageable) {
        User user = getLoggedUser();
        return reportRepository.findAllByUser(pageable, user);
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Report not found"));
    }

    public Report getReportByProtocolNumber(String protocolNumber) {
        return reportRepository.getReportByProtocolNumber(protocolNumber).orElseThrow(() -> new ObjectNotFoundException("Report not found"));
    }

    @Override
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public Report createReport(ReportRequestDTO reportRequestDTO) {
        User user = getLoggedUser();

        Report reportEntity = mapperUtils.map(reportRequestDTO, Report.class);
        reportEntity.setProtocolNumber(generateProtocolNumber(12));
        reportEntity.setStatus("Em análise");
        reportEntity.setUser(user);
        return this.saveReport(reportEntity);
    }

    @Override
    public Report updateReport(Report report) {
        return reportRepository.save(report);
    }

    public Report setReportResponse(ReportUpdateRequestDTO reportUpdateRequestDTO, Long id) {
        User user = getLoggedUser();

        Report report = this.getReportById(id);

        if (!report.getStatus().equals("Em análise")) {
            throw new BusinessRuleException("This report is not in the right status to receive a response");
        }

        report.setResponse(reportUpdateRequestDTO.getResponse());
        report.setStatus("Respondido");
        report.setManager(user);
        return updateReport(report);
    }

    private static String generateProtocolNumber(int length) {
        StringBuilder protocol = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            protocol.append(digit);
        }

        return protocol.toString();
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return user;
    }
}
