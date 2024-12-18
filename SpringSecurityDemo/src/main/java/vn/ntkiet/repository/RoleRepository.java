package vn.ntkiet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.ntkiet.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	@Query("SELECT u FROM roles u WHERE u.name = :name")
	public Role getUserByName(@Param("name") String name);
	Optional<Role> findByName(String name);
}
