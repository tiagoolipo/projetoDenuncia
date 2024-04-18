package com.br.projeto.repository;

import com.br.projeto.entity.Report;
import com.br.projeto.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByProtocolNumber(String protocolNumber);

    Page<Report> findAllByUser(Pageable pageable, User user);

    Optional<Report> getReportByProtocolNumber(String protocolNumber);
}
