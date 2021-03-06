package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.Role;
import pl.kowalska.filmek.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    Role findRoleByName(String name);
}