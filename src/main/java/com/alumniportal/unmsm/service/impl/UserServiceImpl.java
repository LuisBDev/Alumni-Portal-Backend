package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.*;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.*;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.service.interfaces.IUserService;
import com.alumniportal.unmsm.util.ImageManagement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    private final ImageManagement imageManagement;

    private final IActivityService activityService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDTO> findAll() {
        List<User> users = userDAO.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO findById(Long id) {

        User user = userDAO.findById(id);
        return modelMapper.map(user, UserDTO.class);

    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("Error: User not found!");
        }
        if (user.getPhotoUrl() != null) {
            imageManagement.deleteImageByUrl(user.getPhotoUrl());
        }
//TODO: Hacer mas eficiente la eliminación de las imagenes de las actividades a traves de url
        if (!user.getActivityList().isEmpty()) {
            user.getActivityList().stream()
                    .filter(activity -> activity.getUrl() != null)
                    .forEach(activity -> {
                        try {
                            activityService.deleteActivityImage(activity.getId());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        userDAO.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void saveUser(User user) {
        boolean emailExists = userDAO.existsByEmail(user.getEmail());
        if (emailExists) {
            throw new RuntimeException("Error: Email already exists!");
        } else {
            user.setCreatedAt(LocalDate.now());
            userDAO.save(user);
        }
    }

    @Override
    public void updateUser(Long id, Map<String, Object> fields) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("Error: User not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(User.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        user.setUpdatedAt(LocalDate.now());
        userDAO.save(user);

    }

    @Override
    public UserDTO validateLogin(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Error: User not found!");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Error: Invalid password!");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserCVDTO getUserCV(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        UserCVDTO cv = new UserCVDTO();
        cv.setName(user.getName());
        cv.setPaternalSurname(user.getPaternalSurname());
        cv.setMaternalSurname(user.getMaternalSurname());
        cv.setEmail(user.getEmail());
        cv.setCareer(user.getCareer());
        cv.setContactNumber(user.getContactNumber());
        cv.setAbout(user.getAbout());

        cv.setCertifications(certificationDAO.findCertificationsByUserId(userId).stream().map(cert -> {
            CertificationDTO certDto = new CertificationDTO();
            certDto.setName(cert.getName());
            certDto.setIssuingOrganization(cert.getIssuingOrganization());
            certDto.setCredentialUrl(cert.getCredentialUrl());
            certDto.setIssueDate(cert.getIssueDate());
            return certDto;
        }).collect(Collectors.toList()));

        cv.setEducation(educationDAO.findEducationsByUserId(userId).stream().map(edu -> {
            EducationDTO eduDto = new EducationDTO();
            eduDto.setInstitution(edu.getInstitution());
            eduDto.setFieldOfStudy(edu.getFieldOfStudy());
            eduDto.setDegree(edu.getDegree());
            eduDto.setStartDate(edu.getStartDate());
            eduDto.setEndDate(edu.getEndDate());
            return eduDto;
        }).collect(Collectors.toList()));

        cv.setProjects(projectDAO.findProjectsByUserId(userId).stream().map(proj -> {
            ProjectDTO projDto = new ProjectDTO();
            projDto.setName(proj.getName());
            projDto.setDescription(proj.getDescription());
            projDto.setDate(proj.getDate());
            return projDto;
        }).collect(Collectors.toList()));

        cv.setSkills(skillDAO.findSkillsByUserId(userId).stream().map(skill -> {
            SkillDTO skillDto = new SkillDTO();
            skillDto.setName(skill.getName());
            skillDto.setLevel(skill.getLevel());
            return skillDto;
        }).collect(Collectors.toList()));

        cv.setWorkExperience(workExperienceDAO.findWorkExperiencesByUserId(userId).stream().map(work -> {
            WorkExperienceDTO workDto = new WorkExperienceDTO();
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
    public void updatePassword(Long id, PasswordChangeDTO passwordChangeDTO) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("Error: User not found!");
        }
        if (!user.getEmail().equals(passwordChangeDTO.getEmail())) {
            throw new RuntimeException("Error: Invalid email!");
        }

        if (!passwordEncoder.matches(passwordChangeDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Error: Old password does not match!");
        }


        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        user.setUpdatedAt(LocalDate.now());
        userDAO.save(user);
    }
}
