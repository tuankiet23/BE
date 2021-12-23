package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.UserSignupDTO;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.utils.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@Transactional
public class AdminService {
    @Autowired
    IUserRespository iUserRespository;


    public int update(UserSignupDTO userSignupDTO, Long id) {
        User newUser = iUserRespository.findById(id).get();
        try {
            newUser.setGender(userSignupDTO.getGender());
            newUser.setEmail(userSignupDTO.getEmail());
            newUser.setHomeTown(userSignupDTO.getHomeTown());
            newUser.setPhoneNumber(userSignupDTO.getPhoneNumber());
            newUser.setFullName(userSignupDTO.getFullName());
            newUser.setUserName(userSignupDTO.getUserName());
//            newUser.setPassword(userSignupDTO.getPassword());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                newUser.setBirthDay(sdf.parse(userSignupDTO.getBirthDay()));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            iUserRespository.save(newUser);
            return CommonConst.SUCCESS;
        } catch (Exception e) {
            return CommonConst.ERROR;
        }
    }

    public int delete(Long id) {

        try {

            User newUser = iUserRespository.getUserById(id);

            newUser.setActive(false);

            iUserRespository.save(newUser);
            return CommonConst.SUCCESS;
        } catch (Exception e) {
            return CommonConst.ERROR;
        }
    }

    public Optional<User> findById(Long id) {
        return iUserRespository.findById(id);
    }


}
