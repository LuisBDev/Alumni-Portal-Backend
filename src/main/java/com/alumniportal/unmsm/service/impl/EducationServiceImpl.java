package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.EducationMapper;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IEducationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
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
public class EducationServiceImpl implements IEducationService {

    private final IEducationDAO educationDAO;

    private final IUserDAO userDAO;

    private final EducationMapper educationMapper;


    @Override
    public List<EducationResponseDTO> findAll() {
        List<Education> educationList = educationDAO.findAll();
        if (educationList.isEmpty()) {
            throw new AppException("No educations found", "NOT_FOUND");
        }
        return educationMapper.entityListToDTOList(educationList);
    }

    @Override
    public EducationResponseDTO findById(Long id) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            throw new AppException("Education not found", "NOT_FOUND");
        }
        return educationMapper.entityToDTO(education);
    }

    @Override
    public void save(Education education) {
        educationDAO.save(education);
    }

    @Override
    public void deleteById(Long id) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            throw new AppException("Education not found", "NOT_FOUND");
        }
        educationDAO.deleteById(id);
    }

    @Override
    public List<EducationResponseDTO> findEducationsByUserId(Long userId) {
        List<Education> educationsByUserId = educationDAO.findEducationsByUserId(userId);
        if (educationsByUserId.isEmpty()) {
            throw new AppException("No educations found for user", "NOT_FOUND");
        }
        return educationMapper.entityListToDTOList(educationsByUserId);
    }

    @Override
    public void saveEducation(EducationRequestDTO educationRequestDTO, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found", "NOT_FOUND");
        }

        Education education = educationMapper.requestDtoToEntity(educationRequestDTO);
        education.setUser(user);
        educationDAO.save(education);
    }

    @Override
    public void updateEducation(Long id, Map<String, Object> fields) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            throw new AppException("Education not found", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Education.class, k);
            field.setAccessible(true);
            if ("startDate".equals(k) || "endDate".equals(k)) {
                // Convertir el valor de String a LocalDate
                LocalDate date = LocalDate.parse(v.toString());
                ReflectionUtils.setField(field, education, date);
            } else {
                ReflectionUtils.setField(field, education, v);
            }
        });
        educationDAO.save(education);
    }
}
