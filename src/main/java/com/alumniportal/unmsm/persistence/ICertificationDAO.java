package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Certification;

import java.util.List;

public interface ICertificationDAO {

    List<Certification> findAll();

    Certification findById(Long id);

    void save(Certification certification);

    void deleteById(Long id);

    List<Certification> findCertificationsByUser_Id(Long userId);


}
