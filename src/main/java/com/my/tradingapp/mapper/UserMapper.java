package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userName", target = "username")
    UserResponse toResponse(User user);
}
