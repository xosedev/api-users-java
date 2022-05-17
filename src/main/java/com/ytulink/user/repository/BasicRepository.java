package com.ytulink.user.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ytulink.user.entity.AbstractEntity;

import javassist.NotFoundException;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@NoRepositoryBean
public interface BasicRepository<T extends AbstractEntity, ID extends Long> extends PagingAndSortingRepository<T, Long> {

	@Query(value = "select e from #{#entityName} e where e.deleted = false and e.id = :id ")
	Optional<T> findById(@Param("id") Long id);

	@Query("select e from #{#entityName} e where e.deleted = false")
	Page<T> findAll(Pageable pageable);

	@Query("select e from #{#entityName} e where e.deleted = false")
	List<T> findAll();

	@Query("select e from #{#entityName} e where e.id = :id and e.deleted = false")
	T getOne(@Param("id") ID id);

	@Query("select e from #{#entityName} e where e.deleted = true")
	List<T> findAllInactive();

	@Query("select count(e) from #{#entityName} e where e.deleted = false")
	long count();
	
	
	@Query(value = "select e from #{#entityName} e where e.deleted = true and e.id = :id ")
	Optional<T> findByIdInactive(@Param("id") Long id);

	default boolean existsById(ID id) {
		return getOne(id) != null;
	}

	default void deleteById(ID id) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void delete(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void deleteAll(Iterable<? extends T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void deleteAll() {
		throw new UnsupportedOperationException();
	}

	/**
	 * elimina la entidad en la base de datos. No aparecerá en el conjunto de
	 * resultados de consultas predeterminadas.
	 *
	 * @param id         de la entidad para la desactivación
	 * @param modifiedBy quién modificó esta entidad
	 * @return entidad desactivada con campos recuperados
	 * @throws IncorrectaConditionException cuando la entidad ya está desactivada.
	 * @throws NotFoundException            cuando la entidad no se encuentra en el
	 *                                      base de datos.
	 */

	@Transactional
	@Modifying
	default T deactivate(ID id) throws Exception {
		final T entity = findById(id).orElseThrow(() -> new NotFoundException(
				String.format("Entity with ID [%s] wasn't found in the database. " + "Nothing to deactivate.", id)));
		if (entity.isDeleted()) {
			throw new Exception(String.format("Entity with ID [%s] is already deactivated.", id));
		}
		entity.setDeleted(true);
		entity.setUpdateDate(new Date());
		return save(entity);
	}

	/**
	 * Activa la entidad eliminada temporalmente en la base de datos.
	 *
	 * @param id         de la entidad para la reactivación
	 * @return entidad actualizada con campos recuperados
	 * @throws IncorrectaConditionException cuando la entidad ya está activada.
	 * @throws NotFoundException            cuando la entidad no se encuentra en el
	 *                                      base de datos.
	 */

	@Transactional
	@Modifying
	default T reactivate(ID id) throws Exception {
		final T entity = findByIdInactive(id).orElseThrow(() -> new NotFoundException(
				String.format("Entity with ID [%s] wasn't found in the database. " + "Nothing to reactivate.", id)));
		if (!entity.isDeleted()) {
			throw new Exception(String.format("Entity with ID [%s] is already active.", id));
		}
		entity.setDeleted(false);
		entity.setUpdateDate(new Date());
		return save(entity);
	}
}