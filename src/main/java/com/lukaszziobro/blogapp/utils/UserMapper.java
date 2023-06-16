package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.User;
import com.lukaszziobro.blogapp.payload.RegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User mapRegisterDtoToUser(RegisterDto registerDto);
//    User mapToUser(RegisterDto registerDto, @MappingTarget User user);
}
