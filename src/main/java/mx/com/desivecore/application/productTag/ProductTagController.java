package mx.com.desivecore.application.productTag;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.productTag.models.ProductTag;
import mx.com.desivecore.domain.productTag.ports.ProductTagServicePort;

@Log
@RestController
@RequestMapping("/product-tag")
public class ProductTagController {
	
	@Autowired
	private ProductTagServicePort productTagServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createTag(@RequestBody ProductTag productTag){
		log.info("INIT createTag()");
		log.info(String.format("PARAMS: [%s]", productTag.toString()));
		ResponseModel response = productTagServicePort.createTag(productTag);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/view-all")
	public ResponseEntity<?> viewProductTagList(){
		log.info("INIT viewProductTagList()");
		ResponseModel response = productTagServicePort.viewProductTagList();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/view-detail/{tagId}")
	public ResponseEntity<?> viewTagById(@PathVariable Long tagId){
		log.info("INIT viewTagById()");
		log.info(String.format("PARAMS: [tagId: %s]", tagId.toString()));
		ResponseModel response = productTagServicePort.viewTagById(tagId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateTagById(@RequestBody ProductTag productTag){
		log.info("INIT updateTagById()");
		log.info(String.format("PARAMS: [%s]", productTag.toString()));
		ResponseModel response = productTagServicePort.updateTag(productTag);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{tagId}")
	public ResponseEntity<?> deleteTagById(@PathVariable Long tagId){
		log.info("INIT deleteTagById()");
		log.info(String.format("PARAMS: [tagId: %s]", tagId.toString()));
		ResponseModel response = productTagServicePort.deleteTabById(tagId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewProductActiveList(){
		log.info("INIT viewProductActiveList()");
		ResponseModel response = productTagServicePort.viewProductActiveList();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/generate/document/{tagId}")
	public ResponseEntity<?> generateDocumentTag(@PathVariable Long tagId){
		log.info("INIT generateDocumentTag()");
		log.info(String.format("PARAMS: [tagId: %s]", tagId.toString()));
		ResponseModel response = productTagServicePort.generateDocumentTag(tagId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}
}
