package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.model.Activity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActivityMapper {

    private final ModelMapper modelMapper;


    public ActivityResponseDTO entityToDTO(Activity activity) {
        return modelMapper.map(activity, ActivityResponseDTO.class);
    }

    public List<ActivityResponseDTO> entityListToDTOList(List<Activity> activities) {
        return activities.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Activity dtoToEntity(ActivityResponseDTO activityResponseDTO) {
        return modelMapper.map(activityResponseDTO, Activity.class);
    }

    public List<Activity> dtoListToEntityList(List<ActivityResponseDTO> activityResponseDTOS) {
        return activityResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Activity requestDtoToEntity(ActivityRequestDTO activityRequestDTO) {
        return modelMapper.map(activityRequestDTO, Activity.class);
    }

}

