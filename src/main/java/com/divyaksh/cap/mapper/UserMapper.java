package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.UserResponse;
import com.divyaksh.cap.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User toEntity(RegisterRequest request);

     UserResponse toResponse(User user);
}