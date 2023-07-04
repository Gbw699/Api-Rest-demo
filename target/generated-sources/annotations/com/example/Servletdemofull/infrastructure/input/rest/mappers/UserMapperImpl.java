package com.example.Servletdemofull.infrastructure.input.rest.mappers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.UserDto;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-04T09:55:37-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromUserDto(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setLastName( userDto.getLastName() );
        user.setEmail( userDto.getEmail() );
        user.setCellphoneNumber( userDto.getCellphoneNumber() );

        return user;
    }
}
