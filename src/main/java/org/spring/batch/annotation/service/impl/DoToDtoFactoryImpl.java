package org.spring.batch.annotation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.spring.batch.annotation.service.DoToDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Convert model DB to DTO.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 * @param <DO>
 *            model entity.
 * @param <DTO>
 *            the DTO.
 */
@Service
public class DoToDtoFactoryImpl<DO, DTO> implements DoToDtoFactory<DO, DTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DTO convertDoToDto(DO domainObject, Class<DTO> typeParameterClass) {
		if (domainObject == null) {
			return null;
		}

		return modelMapper.map(domainObject, typeParameterClass);
	}

	@Override
	public List<DTO> listDoToListDto(List<DO> listDo, Class<DTO> typeParameterClass) {
		final List<DTO> listDto = new ArrayList<DTO>();
		if (listDo == null || listDo.size() == 0) {
			return listDto;
		}

		for (DO domainObject : listDo) {
			listDto.add(convertDoToDto(domainObject, typeParameterClass));
		}
		return listDto;
	}

	@Override
	public DO convertDtoToDo(DTO dto, Class<DO> typeParameterClass) {
		if (dto == null) {
			return null;
		}

		return modelMapper.map(dto, typeParameterClass);
	}

}
