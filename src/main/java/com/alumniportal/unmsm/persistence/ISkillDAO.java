package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Skill;

import java.util.List;

public interface ISkillDAO {

    List<Skill> findAll();

    Skill findById(Long id);

    void save(Skill skill);

    void deleteById(Long id);

    //    Buscar todos los skills de un usuario
    List<Skill> findSkillsByUser_Id(Long userId);


}
