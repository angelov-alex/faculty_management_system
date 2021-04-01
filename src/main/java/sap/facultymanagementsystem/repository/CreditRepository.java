package sap.facultymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap.facultymanagementsystem.model.Credit;

@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {
}
