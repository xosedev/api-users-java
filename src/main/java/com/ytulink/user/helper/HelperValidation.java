package com.ytulink.user.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Component
public class HelperValidation {

	private static final Logger LOG = LogManager.getLogger(HelperValidation.class);

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();

	public <T> List<String> validarObjeto(T o) {

		Set<ConstraintViolation<T>> errors = validator.validate(o);
		List<String> errorMsgs = new ArrayList<>();
		if (!errors.isEmpty()) {
			LOG.debug("Validation failed.");
			for (ConstraintViolation<?> e : errors) {
				errorMsgs.add("Error de entrada: " + e.getPropertyPath() + " " + e.getMessage() + ".");
			}
		}
		return errorMsgs;
	}
}
