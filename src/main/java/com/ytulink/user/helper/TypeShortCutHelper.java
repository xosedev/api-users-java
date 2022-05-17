package com.ytulink.user.helper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ytulink.user.dto.TypeShortCutDTO;
import com.ytulink.user.entity.TypeShortCut;
import com.ytulink.user.mapper.TypeShortCutMapper;
import com.ytulink.user.repository.TypeShortCutRepository;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@Component
public class TypeShortCutHelper {
	static Logger logger = LogManager.getLogger(TypeShortCutHelper.class);

	@Autowired
	TypeShortCutMapper typeShortCutMapper;

	@Autowired
	TypeShortCutRepository typeShortCutRepository;

	private static final String NO_DATA = "No se encontraron registros";

	/**
	 * Metodo encargado registrar nuevo usuario
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> saveOrUpdate(TypeShortCutDTO newTypeShortCutDTO) throws Exception {
		logger.error("Inicio registrar tipo short cut");
		TypeShortCut typeShortCut =null;
		
		if (newTypeShortCutDTO == null) {
			logger.error("Debe completar los campos requeridos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe completar los campos requeridos");
		}

		/**
		 * Valdiaciones 
		 */
		if (!org.springframework.util.StringUtils.hasLength(newTypeShortCutDTO.getIcon())) {
			logger.error("Debe ingresar icon");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar icon");
		}
		if (!org.springframework.util.StringUtils.hasLength(newTypeShortCutDTO.getName())) {
			logger.error("Debe ingresar nombre");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar nombre");
		}
        
		if (!org.springframework.util.StringUtils.hasLength(newTypeShortCutDTO.getUrl())) {
			logger.error("Debe ingresar icon");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar url");
		}
        

        if(null==newTypeShortCutDTO.getId()) {
        	typeShortCut = 	typeShortCutMapper.toEntity(newTypeShortCutDTO);
        }else {
        	
            Optional<TypeShortCut> typeShortCutOut = typeShortCutRepository.findById(newTypeShortCutDTO.getId());
            
            if(!typeShortCutOut.isPresent()) {
         		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(NO_DATA);
            }
            typeShortCut = typeShortCutOut.get();
            typeShortCut.setUpdateDate(new Date());
			typeShortCut.setIcon(newTypeShortCutDTO.getIcon());
			typeShortCut.setName(newTypeShortCutDTO.getName());
			typeShortCut.setUrl(newTypeShortCutDTO.getUrl());
        }
		
		try {
			TypeShortCut out = typeShortCutRepository.save(typeShortCut);
			newTypeShortCutDTO =	typeShortCutMapper.toDto(out);
		} catch (Exception e) {
			logger.error("Ocurrió un error al typeShortCutDTO el saveOrUpdate"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			throw new Exception(e.getLocalizedMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(newTypeShortCutDTO);
	}
	

	public List<TypeShortCut> getAllTypeShortCut() throws Exception {
		logger.info("getAllTypeShortCut: {}");
		List<TypeShortCut> typeShortCutDTO = typeShortCutRepository.findAll();
		return typeShortCutDTO;
	}

	public TypeShortCutDTO activate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		TypeShortCut out = typeShortCutRepository.reactivate(id);
		return typeShortCutMapper.toDto(out);
	}

	public TypeShortCutDTO inactivate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		TypeShortCut out = typeShortCutRepository.deactivate(id);
		return typeShortCutMapper.toDto(out);

	}
}