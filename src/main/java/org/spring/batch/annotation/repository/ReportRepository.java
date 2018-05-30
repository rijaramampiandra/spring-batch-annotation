package org.spring.batch.annotation.repository;

import org.spring.batch.annotation.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository DB.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 * @param <Report>
 */
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
