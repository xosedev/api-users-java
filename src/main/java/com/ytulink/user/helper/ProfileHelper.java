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

import com.ytulink.user.dto.ProfileDTO;
import com.ytulink.user.entity.Profile;
import com.ytulink.user.entity.User;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.mapper.ProfileMapper;
import com.ytulink.user.repository.IUserRepository;
import com.ytulink.user.repository.ProfileRepository;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@Component
public class ProfileHelper {
	static Logger logger = LogManager.getLogger(ProfileHelper.class);

	@Autowired
	ProfileMapper profileMapper;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	IUserRepository userRepository;

	@Transactional(rollbackOn = Exception.class)
	public ProfileDTO create(ProfileDTO profileDTO) throws Exception {
		Profile profile= null;
		Optional<User> user = userRepository.findById(profileDTO.getUserId());
		
		if (!user.isPresent()) {
			throw new YtulinkException("El user id no existe.");
		}
		
		
		
		if(profileDTO.getId() != null) {
			Optional<Profile> prof = profileRepository.findById(profileDTO.getId());
			if(!prof.isPresent()) {
				throw new YtulinkException("El profile id no existe.");
			}
			profile = prof.get();
			profile.setUpdateDate(new Date());
			
			profile.setShortcutsPosition(profileDTO.getShortcutsPosition());
			profile.setUsernameColor(profileDTO.getUsernameColor());
			profile.setShortcutColorBackground(profileDTO.getShortcutColorBackground());
			profile.setShortcutColorLabel(profileDTO.getShortcutColorLabel());
			profile.setLinkColorLabel(profileDTO.getLinkColorLabel());
			profile.setLinkColorBackground(profileDTO.getLinkColorBackground());
			profile.setLinkColorBorder(profileDTO.getLinkColorBorder());
			profile.setLinkAnimation(profileDTO.getLinkAnimation());
			profile.setLinkBorderHeight(profileDTO.getLinkBorderHeight());
			profile.setPortfolioBackground(profileDTO.getPortfolioBackground());
			
			
		}else {
			
			Optional<Profile> profileAux = profileRepository.findByUserId(profileDTO.getUserId());
			if(profileAux.isPresent()) {
				throw new YtulinkException("El Usuario ya posee un perfil activo con id :"+profileAux.get().getId());
			}
			
			
			profile = profileMapper.toEntity(profileDTO);
		}
	
		
		profile.setUser(user.get());
		Profile out = profileRepository.save(profile);
		return profileMapper.toDto(out);


	}

	public Page<ProfileDTO> findAllByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
		logger.info("Request to get findAll");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<ProfileDTO> page = profileRepository.findAllByUserId(userId, pageable).map(profileMapper::toDto);
		return page;
	}

	public ProfileDTO activate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		Profile out = profileRepository.reactivate(id);
		return profileMapper.toDto(out);
	}

	public ProfileDTO inactivate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		Profile out = profileRepository.deactivate(id);
		return profileMapper.toDto(out);

	}
}