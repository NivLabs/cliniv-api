package br.com.nivlabs.gp.controller.external;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.FeeDTO;
import br.com.nivlabs.gp.models.dto.MaterialDTO;
import br.com.nivlabs.gp.models.dto.MedicalProcedureDTO;
import br.com.nivlabs.gp.models.dto.MedicineInfoDTO;
import br.com.nivlabs.gp.service.external.GTISSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Endpoint - GTISS")
@RestController
@RequestMapping("/gtiss")
public class GTISSController {

    @Autowired
    private GTISSService service;

    @ApiOperation(nickname = "medicine-get-id", value = "Consulta de medicamentos por código")
    @GetMapping("/medicine/{id}")
    public ResponseEntity<MedicineInfoDTO> getMedicineByCode(@PathVariable("code") String code, HttpServletResponse response) {

        return ResponseEntity.ok(service.getMedicineByCode(code));
    }
    
    @ApiOperation(nickname = "medicine-get-description", value = "Consulta medicamentos por descrição")
    @GetMapping("/medicine/")
    public ResponseEntity<Page<MedicineInfoDTO>> getMedicineByDescription(@RequestParam(name = "description", required = false, defaultValue = "") String description,
    																   @PageableDefault(sort = "description", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable pageSettings,
    																   HttpServletResponse response) {
        return ResponseEntity.ok(service.getMedicineByDescription(description, pageSettings));
    }
    
    @ApiOperation(nickname = "medicine-get-laboratory", value = "Consulta medicamentos por laboratório")
    @GetMapping("/laboratory/")
    public ResponseEntity<Page<MedicineInfoDTO>> getMedicineByLaboratory(@PathVariable("laboratory")String laboratory, 
    																  Pageable pageSettings,
    																  HttpServletResponse response) {
        return ResponseEntity.ok(service.getMedicineByLaboratory(laboratory, pageSettings));
    }
    
    @ApiOperation(nickname = "procedures-get-id", value = "Consulta de procedimentos por código")
    @GetMapping("/medical-procedure/{id}")
    public ResponseEntity<MedicalProcedureDTO> getMedicalProcedureByCode(@PathVariable("id") String code, HttpServletResponse response) {

        return ResponseEntity.ok(service.getMedicalProcedureByCode(code));
    }
    
    @ApiOperation(nickname = "procedures-get-description", value = "Consulta de procedimentos por descrição")
    @GetMapping("medical-procedure/")
    public ResponseEntity<Page<MedicalProcedureDTO>> getMedicalProceduresByDescription(@RequestParam(name = "description", required = false, defaultValue = "") String description,
			   																			@PageableDefault(sort = "description", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable pageSettings,
																						HttpServletResponse response) {
        return ResponseEntity.ok(service.getMedicalProceduresByDescription(description, pageSettings));
    }
    
    @ApiOperation(nickname = "material-get-id", value = "Consulta de materiais por código")
    @GetMapping("/material/{id}")
    public ResponseEntity<MaterialDTO> getMaterialByCode(@PathVariable("id") String code, HttpServletResponse response) {

        return ResponseEntity.ok(service.getMaterialByCode(code));
    }
    
    @ApiOperation(nickname = "material-get-description", value = "Consulta de materiais por descrição")
    @GetMapping("/material/")
    public ResponseEntity<Page<MaterialDTO>> getMaterialByDescription(@RequestParam(name = "description", required = false, defaultValue = "") String description,
			   															@PageableDefault(sort = "description", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable pageSettings,
    																	HttpServletResponse response) {

        return ResponseEntity.ok(service.getMaterialByDescription(description, pageSettings));
    }
    
    @ApiOperation(nickname = "fee-get-by-id", value = "Consulta taxas por código")
    @GetMapping("/fee/{id}")
    public ResponseEntity<FeeDTO> getFeeByCode(@PathVariable("id") String code, HttpServletResponse response) {
        return ResponseEntity.ok(service.getFeeByCode(code));
    }
    
    @ApiOperation(nickname = "fee-get-by-description", value = "Consulta taxas por descrição")
    @GetMapping("/fee/")
    public ResponseEntity<Page<FeeDTO>> getFeeByDescription(@RequestParam(name = "description", required = false, defaultValue = "") String description,
    														@PageableDefault(sort = "description", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable pageSettings,
    														HttpServletResponse response) {
        return ResponseEntity.ok(service.getFeeByDescription(description, pageSettings));
    }
}
