package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.WorkExperienceMapper;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.persistence.interfaces.IWorkExperienceDAO;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements IWorkExperienceService {

    private final IWorkExperienceDAO workExperienceDAO;

    private final IUserDAO userDAO;

    private final WorkExperienceMapper workExperienceMapper;


    @Override
    public List<WorkExperienceResponseDTO> findAll() {
        List<WorkExperience> workExperienceList = workExperienceDAO.findAll();

        return workExperienceMapper.entityListToDTOList(workExperienceList);
    }


    @Override
    public WorkExperienceResponseDTO findById(Long id) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            throw new AppException("Work experience not found", "NOT_FOUND");
        }
        return workExperienceMapper.entityToDTO(workExperience);
    }

    @Override
    public void save(WorkExperience workExperience) {
        workExperienceDAO.save(workExperience);
    }

    @Override
    public void deleteById(Long id) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            throw new AppException("Work experience not found", "NOT_FOUND");
        }
        workExperienceDAO.deleteById(id);
    }

    @Override
    public List<WorkExperienceResponseDTO> findWorkExperiencesByUserId(Long userId) {
        List<WorkExperience> workExperiencesByUserId = workExperienceDAO.findWorkExperiencesByUserId(userId);
        if (workExperiencesByUserId.isEmpty()) {
            throw new AppException("No work experiences found for user", "NOT_FOUND");
        }
        return workExperienceMapper.entityListToDTOList(workExperiencesByUserId);
    }

    @Override
    public WorkExperienceResponseDTO saveWorkExperience(WorkExperienceRequestDTO workExperienceRequestDTO, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found", "NOT_FOUND");
        }

        WorkExperience workExperience = workExperienceMapper.requestDtoToEntity(workExperienceRequestDTO);

        workExperience.setUser(user);
        workExperienceDAO.save(workExperience);
        return workExperienceMapper.entityToDTO(workExperience);

    }

    @Override
    public WorkExperienceResponseDTO updateWorkExperience(Long id, Map<String, Object> fields) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            throw new AppException("Work experience not found", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(WorkExperience.class, k);
            field.setAccessible(true);
            if ("startDate".equals(k) || "endDate".equals(k)) {
                // Convertir el valor de String a LocalDate
                LocalDate date = LocalDate.parse(v.toString());
                ReflectionUtils.setField(field, workExperience, date);
            } else {
                ReflectionUtils.setField(field, workExperience, v);
            }
        });
        workExperienceDAO.save(workExperience);
        return workExperienceMapper.entityToDTO(workExperience);

    }
}
