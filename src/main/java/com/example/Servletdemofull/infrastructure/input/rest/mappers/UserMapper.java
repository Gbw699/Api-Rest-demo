package com.example.Servletdemofull.infrastructure.input.rest.mappers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.UserDto;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Named("fromUserDto")
    User fromUserDto(UserDto userDto);
}
