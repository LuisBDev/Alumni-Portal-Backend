package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.EnrollmentDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.persistence.IEnrollmentDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IEnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    @Autowired
    private IEnrollmentDAO enrollmentDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IActivityDAO activityDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EnrollmentDTO> findAll() {
        return enrollmentDAO.findAll()
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    @Override
    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentDAO.findById(id);
        if (enrollment == null) {
            return null;
        }
        return modelMapper.map(enrollment, EnrollmentDTO.class);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentDAO.deleteById(id);
    }

    @Override
    public List<EnrollmentDTO> findEnrollmentsByUser_Id(Long userId) {
        return enrollmentDAO.findEnrollmentsByUser_Id(userId)
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findEnrollmentsByActivity_Id(Long activityId) {
        return enrollmentDAO.findEnrollmentsByActivity_Id(activityId)
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    public void saveEnrollment(Enrollment enrollment) {
        User user = userDAO.findById(enrollment.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }
        Activity activity = activityDAO.findById(enrollment.getActivity().getId());
        if (activity == null) {
            throw new IllegalArgumentException("Error: Activity not found!");
        }

//        Persistir enrollment
        enrollment.setUser(user);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        enrollmentDAO.save(enrollment);

//        Actualizar user
        user.getEnrollmentList().add(enrollment);
        userDAO.save(user);

//        Actualizar activity
        activity.getEnrollmentList().add(enrollment);
        activityDAO.save(activity);

    }


}
