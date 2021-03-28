package sap.faculty_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap.faculty_management_system.model.Credit;

@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {
}
