package org.spring.batch.annotation.service;

import java.util.List;

/**
 * Converter class. Domain object (entity) to domain transfer object.
 *
 * @param <DO>
 *            the domain object.
 * @param <DTO>
 *            the domain transfer object.
 * 
 * @author Rija RAMAMPIANDRA.
 */
public interface DoToDtoFactory<DO, DTO> {

	/**
	 * Convertert object from DAO (domain object) to DTO (data transfer object).
	 *
	 * @param domainObject
	 *            object from DAO.
	 * @param typeParameterClass
	 *            type of class.
	 * @return the DTO.
	 */
	DTO convertDoToDto(DO domainObject, Class<DTO> typeParameterClass);

	/**
	 * Convert list domain object to list DTO.
	 *
	 * @param listDo
	 *            the listDto.
	 * @param typeParameterClass
	 *            the parameterClass.
	 * @return the dto.
	 */
	List<DTO> listDoToListDto(List<DO> listDo, Class<DTO> typeParameterClass);

	/**
	 * Convert DTO (Json or other object) to model object.
	 * 
	 * @param dto
	 *            the dto.
	 * @param typeParameterClass
	 *            the model class type.
	 * @return the model.
	 */
	DO convertDtoToDo(DTO dto, Class<DO> typeParameterClass);
}
