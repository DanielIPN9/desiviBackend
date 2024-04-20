package mx.com.desivecore.infraestructure.branches.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.branches.entities.BranchPhoneEntity;

@Repository
public interface BranchPhoneRepository extends JpaRepository<BranchPhoneEntity, Long> {

	@Query("SELECT bp FROM BranchPhoneEntity bp WHERE bp.branchId=:branchId")
	List<BranchPhoneEntity> findAllByBranchId(@Param("branchId") Long branchId);

}
