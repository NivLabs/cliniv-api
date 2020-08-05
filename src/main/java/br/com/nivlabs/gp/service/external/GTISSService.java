package br.com.nivlabs.gp.service.external;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.client.gtiss.Fee;
import br.com.nivlabs.gp.client.gtiss.GTISSClient;
import br.com.nivlabs.gp.client.gtiss.Material;
import br.com.nivlabs.gp.client.gtiss.MedicalProcedure;
import br.com.nivlabs.gp.client.gtiss.Medicine;
import br.com.nivlabs.gp.models.dto.FeeDTO;
import br.com.nivlabs.gp.models.dto.MaterialDTO;
import br.com.nivlabs.gp.models.dto.MedicalProcedureDTO;
import br.com.nivlabs.gp.models.dto.MedicineInfoDTO;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Service para disponibilizar o GTISS Client
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 */
@Service
public class GTISSService {
	
	public Page<MedicineInfoDTO> getMedicineByDescription(String description, Pageable pageSettings) {
		Page<Medicine> medicines = new GTISSClient().getMedicineByDescription(description, pageSettings);
		return getMedicinesDTO(medicines);
	}
	
	public MedicineInfoDTO getMedicineByCode(String code) {
		Medicine medicine = new GTISSClient().getMedicineByCode(code);
		return getMedicineDTO(medicine);
	}
	
	public Page<MedicineInfoDTO> getMedicineByLaboratory(String laboratory, Pageable pageSettings) {
		Page<Medicine> medicines = new GTISSClient().getMedicineByLaboratory(laboratory, pageSettings);
		return getMedicinesDTO(medicines);
	}
	
	private Page<MedicineInfoDTO> getMedicinesDTO(Page<Medicine> medicines) {
		Page<MedicineInfoDTO> medicinesDTO = new PageImpl<MedicineInfoDTO>(new ArrayList<>());
		for (Medicine medicine : medicines) {
			medicinesDTO.and(getMedicineDTO(medicine));
		}
		return medicinesDTO;
	}
	
	private MedicineInfoDTO getMedicineDTO(Medicine medicine) {
		MedicineInfoDTO medicineDTO = new MedicineInfoDTO();
		BeanUtils.copyProperties(medicine, medicineDTO);
		medicineDTO.setId(Long.valueOf(medicine.getId()));
		medicineDTO.setExpirationDate(StringUtils.convertStringToDate(medicine.getExpirationDate()));
		medicineDTO.setExpirationStartDate(StringUtils.convertStringToDate(medicine.getExpirationStartDate()));
		medicineDTO.setImplantationEndDate(StringUtils.convertStringToDate(medicine.getExpirationDate()));
		return medicineDTO;
	}
	
	
	public Page<MedicalProcedureDTO> getMedicalProceduresByDescription(String description, Pageable pageSettings) {
		Page<MedicalProcedure> medicalProcedures = new GTISSClient().getMedicalProcedureByDescription(description, pageSettings);
		return getMedicalProceduresDTO(medicalProcedures);
	}
	

	public MedicalProcedureDTO getMedicalProcedureByCode(String code) {
		MedicalProcedure medicalProcedure = new GTISSClient().getMedicalProcedureByCode(code);
		return getMedicalProcedureDTO(medicalProcedure);
	}

	private Page<MedicalProcedureDTO> getMedicalProceduresDTO(Page<MedicalProcedure> medicalProcedures) {
		Page<MedicalProcedureDTO> medicalProceduresDTO = new PageImpl<MedicalProcedureDTO>(new ArrayList<>());
		for (MedicalProcedure medicalProcedure : medicalProcedures) {
			medicalProceduresDTO.and(getMedicalProcedureDTO(medicalProcedure));
		}
		return medicalProceduresDTO;
	}
	
	private MedicalProcedureDTO getMedicalProcedureDTO(MedicalProcedure medicalProcedure) {
		MedicalProcedureDTO medicalProcedureDTO = new MedicalProcedureDTO();
		BeanUtils.copyProperties(medicalProcedure, medicalProcedureDTO);
		medicalProcedureDTO.setExpirationDate(StringUtils.convertStringToDate(medicalProcedure.getExpirationDate()));
		medicalProcedureDTO.setExpirationStartDate(StringUtils.convertStringToDate(medicalProcedure.getExpirationStartDate()));
		medicalProcedureDTO.setImplantationEndDate(StringUtils.convertStringToDate(medicalProcedure.getExpirationDate()));
		return medicalProcedureDTO;
	}
	
	public Page<MaterialDTO> getMaterialByDescription(String description, Pageable pageSettings) {
		Page<Material> materials = new GTISSClient().getMaterialByDescription(description, pageSettings);
		return getMaterialsDTO(materials);
	}
	

	public MaterialDTO getMaterialByCode(String code) {
		Material material = new GTISSClient().getMaterialByCode(code);
		return getMaterialDTO(material);
	}

	private Page<MaterialDTO> getMaterialsDTO(Page<Material> medicalProcedures) {
		Page<MaterialDTO> materialsDTO = new PageImpl<MaterialDTO>(new ArrayList<>());
		for (Material material : medicalProcedures) {
			materialsDTO.and(getMaterialDTO(material));
		}
		return materialsDTO;
	}
	
	private MaterialDTO getMaterialDTO(Material material) {
		MaterialDTO materialDTO = new MaterialDTO();
		BeanUtils.copyProperties(material, materialDTO);
		materialDTO.setExpirationDate(StringUtils.convertStringToDate(material.getExpirationDate()));
		materialDTO.setExpirationStartDate(StringUtils.convertStringToDate(material.getExpirationStartDate()));
		materialDTO.setImplantationEndDate(StringUtils.convertStringToDate(material.getExpirationDate()));
		return materialDTO;
	}
	
	public Page<FeeDTO> getFeeByDescription(String description, Pageable pageSettings) {
		Page<Fee> fee = new GTISSClient().getFeeByDescription(description, pageSettings);
		return getFeesDTO(fee);
	}
	

	public FeeDTO getFeeByCode(String code) {
		Fee fee = new GTISSClient().getFeeByCode(code);
		return getFeeDTO(fee);
	}

	private Page<FeeDTO> getFeesDTO(Page<Fee> fees) {
		Page<FeeDTO> feesDTO = new PageImpl<FeeDTO>(new ArrayList<>());
		for (Fee fee : fees) {
			feesDTO.and(getFeeDTO(fee));
		}
		return feesDTO;
	}
	
	private FeeDTO getFeeDTO(Fee fee) {
		FeeDTO feeDTO = new FeeDTO();
		BeanUtils.copyProperties(fee, feeDTO);
		feeDTO.setExpirationDate(StringUtils.convertStringToDate(fee.getExpirationDate()));
		feeDTO.setExpirationStartDate(StringUtils.convertStringToDate(fee.getExpirationStartDate()));
		feeDTO.setImplantationEndDate(StringUtils.convertStringToDate(fee.getExpirationDate()));
		return feeDTO;
	}
}
