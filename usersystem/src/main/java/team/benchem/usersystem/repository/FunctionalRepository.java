package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Functional;

@Repository
public interface FunctionalRepository extends JpaRepository<Functional, String> {
}
