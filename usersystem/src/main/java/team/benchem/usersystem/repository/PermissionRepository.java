package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
