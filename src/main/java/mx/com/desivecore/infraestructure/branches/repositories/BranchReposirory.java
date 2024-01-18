package mx.com.desivecore.infraestructure.branches.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;

@Repository
public interface BranchReposirory extends JpaRepository<BranchEntity, Long> {

	@Query("SELECT b FROM BranchEntity b WHERE b.name=:name")
	Optional<BranchEntity> findByName(@Param("name") String name);

	@Query("SELECT b FROM BranchEntity b WHERE b.name=:name AND b.branchId!=:id")
	Optional<BranchEntity> findByNameAndIdNot(@Param("name") String name, @Param("id") Long id);

}
