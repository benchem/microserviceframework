package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
