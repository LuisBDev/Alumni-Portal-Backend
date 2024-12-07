package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.response.UserResponseDTO;
import com.alumniportal.unmsm.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponseDTO entityToDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> entityListToDTOList(List<User> users) {
        return users.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public User dtoToEntity(UserResponseDTO userResponseDTO) {
        return modelMapper.map(userResponseDTO, User.class);
    }

    public List<User> dtoListToEntityList(List<UserResponseDTO> userResponseDTOS) {
        return userResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public User requestDtoToEntity(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, User.class);
    }

    public AuthUserResponseDTO entityToAuthUserResponseDTO(User user) {
        return modelMapper.map(user, AuthUserResponseDTO.class);
    }

}
