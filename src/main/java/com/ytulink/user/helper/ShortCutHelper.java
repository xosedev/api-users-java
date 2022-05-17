package com.ytulink.user.helper;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ytulink.user.dto.ShortCutDTO;
import com.ytulink.user.entity.ShortCut;
import com.ytulink.user.entity.TypeShortCut;
import com.ytulink.user.entity.User;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.mapper.ShortCutMapper;
import com.ytulink.user.repository.IUserRepository;
import com.ytulink.user.repository.ShortCutRepository;
import com.ytulink.user.repository.TypeShortCutRepository;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@Component
public class ShortCutHelper {
	static Logger logger = LogManager.getLogger(ShortCutHelper.class);

	@Autowired
	ShortCutMapper shortCutMapper;

	@Autowired
	ShortCutRepository shortCutRepository;

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	TypeShortCutRepository typeShortCutRepository;

	@Transactional(rollbackOn = Exception.class)
	public ShortCutDTO  create(ShortCutDTO shortCutDTO) throws Exception {


		
		Optional<User> user = userRepository.findById(shortCutDTO.getUserId());

		if (user.isPresent()) {
			ShortCut shortCut = shortCutMapper.toEntity(shortCutDTO);
			shortCut.setUser(user.get());
			Optional<TypeShortCut> typeShortCut = typeShortCutRepository.findById(shortCutDTO.getTypeShortcutId());
			
			if(!typeShortCut.isPresent()) {
				throw new YtulinkException("El id tipo short cut.");	
			}
			
	        if(null==shortCutDTO.getId()) {
	        	shortCut = 	shortCutMapper.toEntity(shortCutDTO);
	        }else {
	        	
	            Optional<ShortCut> shortCutOut = shortCutRepository.findById(shortCutDTO.getId());
	            
	            if(!shortCutOut.isPresent()) {
	        		throw new YtulinkException("El shrot cut id no existe.");
	            }
	            shortCut = shortCutOut.get();
	            shortCut.setUpdateDate(new Date());
	            shortCut.setName(shortCutDTO.getName());
	            shortCut.setUser(user.get());
	        }
			shortCut.setTypeShortCut(typeShortCut.get());
			ShortCut out = shortCutRepository.save(shortCut);
			return shortCutMapper.toDto(out);
		}

		throw new YtulinkException("El user id no existe.");

	}

	public Page<ShortCutDTO> findAllByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
		logger.info("Request to get findAll");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<ShortCutDTO> page = shortCutRepository.findAllByUserId(userId, pageable).map(shortCutMapper::toDto);
		return page;
	}

	public ShortCutDTO activate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		ShortCut out = shortCutRepository.reactivate(id);
		return shortCutMapper.toDto(out);
	}

	public ShortCutDTO inactivate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		ShortCut out = shortCutRepository.deactivate(id);
		return shortCutMapper.toDto(out);

	}
	
	public ShortCutDTO shortcutId(Long id) throws Exception {
		logger.info("Request to get shortcutId : {}", id);
		Optional<ShortCut> out = shortCutRepository.findById(id);
		
		if(!out.isPresent()) {
			return null;
		}
		
		return shortCutMapper.toDto(out.get());
		
	}
}