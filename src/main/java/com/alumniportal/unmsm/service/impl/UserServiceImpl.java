package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.*;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.UserMapper;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.*;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.service.interfaces.IUserService;
import com.alumniportal.unmsm.util.ImageManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    private final ICertificationDAO certificationDAO;

    private final IEducationDAO educationDAO;

    private final IProjectDAO projectDAO;

    private final IWorkExperienceDAO workExperienceDAO;

    private final ISkillDAO skillDAO;

    private final UserMapper userMapper;

    private final ImageManagement imageManagement;

    private final IActivityService activityService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserResponseDTO> findAll() {
        List<User> users = userDAO.findAll();

        return userMapper.entityListToDTOList(users);
    }

    @Override
    public UserResponseDTO findById(Long id) {

        User user = userDAO.findById(id);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }
        return userMapper.entityToDTO(user);

    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }
        if (user.getPhotoUrl() != null) {
            imageManagement.deleteImageByUrl(user.getPhotoUrl());
        }

        deleteUserImage(user);
        deleteUserActivitiesImages(user);
        userDAO.deleteById(id);
    }

    private void deleteUserImage(User user) {
        if (user.getPhotoUrl() != null) {
            imageManagement.deleteImageByUrl(user.getPhotoUrl());
        }
    }

    private void deleteUserActivitiesImages(User user) {
        List<Activity> activities = user.getActivityList();
        if (!activities.isEmpty()) {
            activities.stream()
                    .filter(activity -> activity.getUrl() != null)
                    .forEach(activity -> {
                        try {
                            activityService.deleteActivityImage(activity.getId());
                        } catch (Exception e) {
                            throw new AppException("Error deleting activity image", "INTERNAL_SERVER_ERROR");
                        }
                    });
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }
        return userMapper.entityToDTO(user);
    }

    @Override
    public UserResponseDTO saveUser(User user) {
        boolean emailExists = userDAO.existsByEmail(user.getEmail());
        if (emailExists) {
            throw new AppException("Error: Email is already in use!", "CONFLICT");
        } else {
            user.setCreatedAt(LocalDate.now());
            userDAO.save(user);
        }
        return userMapper.entityToDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, Map<String, Object> fields) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(User.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        user.setUpdatedAt(LocalDate.now());
        userDAO.save(user);
        return userMapper.entityToDTO(user);
    }


    @Override
    public UserCVResponseDTO getUserCV(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("Error: User not found!", "NOT_FOUND");
        }

        UserCVResponseDTO cv = new UserCVResponseDTO();
        cv.setName(user.getName());
        cv.setPaternalSurname(user.getPaternalSurname());
        cv.setMaternalSurname(user.getMaternalSurname());
        cv.setEmail(user.getEmail());
        cv.setCareer(user.getCareer());
        cv.setContactNumber(user.getContactNumber());
        cv.setAbout(user.getAbout());

        cv.setCertifications(certificationDAO.findCertificationsByUserId(userId).stream().map(cert -> {
            CertificationResponseDTO certDto = new CertificationResponseDTO();
            certDto.setName(cert.getName());
            certDto.setIssuingOrganization(cert.getIssuingOrganization());
            certDto.setCredentialUrl(cert.getCredentialUrl());
            certDto.setIssueDate(cert.getIssueDate());
            return certDto;
        }).collect(Collectors.toList()));

        cv.setEducation(educationDAO.findEducationsByUserId(userId).stream().map(edu -> {
            EducationResponseDTO eduDto = new EducationResponseDTO();
            eduDto.setInstitution(edu.getInstitution());
            eduDto.setFieldOfStudy(edu.getFieldOfStudy());
            eduDto.setDegree(edu.getDegree());
            eduDto.setStartDate(edu.getStartDate());
            eduDto.setEndDate(edu.getEndDate());
            return eduDto;
        }).collect(Collectors.toList()));

        cv.setProjects(projectDAO.findProjectsByUserId(userId).stream().map(proj -> {
            ProjectResponseDTO projDto = new ProjectResponseDTO();
            projDto.setName(proj.getName());
            projDto.setDescription(proj.getDescription());
            projDto.setDate(proj.getDate());
            return projDto;
        }).collect(Collectors.toList()));

        cv.setSkills(skillDAO.findSkillsByUserId(userId).stream().map(skill -> {
            SkillResponseDTO skillResponseDto = new SkillResponseDTO();
            skillResponseDto.setName(skill.getName());
            skillResponseDto.setLevel(skill.getLevel());
            return skillResponseDto;
        }).collect(Collectors.toList()));

        cv.setWorkExperience(workExperienceDAO.findWorkExperiencesByUserId(userId).stream().map(work -> {
            WorkExperienceResponseDTO workDto = new WorkExperienceResponseDTO();
            workDto.setCompany(work.getCompany());
            workDto.setDescription(work.getDescription());
            workDto.setJobTitle(work.getJobTitle());
            workDto.setStartDate(work.getStartDate());
            workDto.setEndDate(work.getEndDate());
            return workDto;
        }).collect(Collectors.toList()));

        return cv;
    }

    @Override
    public void updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new AppException("Error: User not found!", "NOT_FOUND");
        }
        if (!user.getEmail().equals(passwordChangeRequestDTO.getEmail())) {
            throw new AppException("Error: Email does not match!", "BAD_REQUEST");
        }

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getPassword(), user.getPassword())) {
            throw new AppException("Error: Invalid password!", "BAD_REQUEST");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        user.setUpdatedAt(LocalDate.now());
        userDAO.save(user);
    }
}
