package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.AcademiclevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Academiclevelimpl implements Task<Academic_Level> {
    @Autowired
    AcademiclevelRepo academiclevelRepo;
    @Override
    public List<Academic_Level> getAll() {
        return academiclevelRepo.findAll();
    }

    @Override
    public Academic_Level save(Academic_Level academic_level) {
            return academiclevelRepo.save(academic_level);
        }

    @Override
    public Academic_Level getById(Long id) {
        return academiclevelRepo.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        academiclevelRepo.deleteAc(id);
    }


}
