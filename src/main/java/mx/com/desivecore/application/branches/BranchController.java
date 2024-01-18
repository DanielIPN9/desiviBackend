package mx.com.desivecore.application.branches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchServicePort;

@Log
@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	private BranchServicePort branchServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createBranch(@RequestBody Branch branch) {
		log.info("INIT createBranch()");
		log.info(String.format("PARAMS: [%s]", branch.toString()));
		ResponseModel responseModel = branchServicePort.createBranch(branch);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateBranchById(@RequestBody Branch branch) {
		log.info("INIT updateBranchById()");
		log.info(String.format("PARAMS: [%s]", branch.toString()));
		ResponseModel responseModel = branchServicePort.updateBranchById(branch);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllBranch() {
		log.info("INIT viewAllBranch()");
		ResponseModel responseModel = branchServicePort.viewAllBranch();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{id}")
	public ResponseEntity<?> viewBranchById(@PathVariable Long id) {
		log.info("INIT updateBranchById()");
		log.info(String.format("PARAMS: [branchId: %s]", id.toString()));
		ResponseModel responseModel = branchServicePort.viewBranchDetailById(id);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
