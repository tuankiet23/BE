package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.config.AccountActivationConfig;
import com.itsol.recruit_managerment.constant.DateTimeConstant;
import com.itsol.recruit_managerment.dto.UserSignupDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.OTP;
import com.itsol.recruit_managerment.model.Role;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.RoleRepo;
import com.itsol.recruit_managerment.service.UserService;
import com.itsol.recruit_managerment.service.impl.UserServiceimpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    AccountActivationConfig accountActivationConfig;
    @Autowired
    UserServiceimpl userServiceimpl;
    @Autowired
    ServletContext application;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDTO userSignupDTO) {
        Role role = roleRepo.findByName("ROLE_USER");
        System.out.println("1111111111111111111111111111");
        System.out.println(userSignupDTO);
        User user = userServiceimpl.createUser(userSignupDTO);
        if (ObjectUtils.isEmpty(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstant.YYYYMMDD_FOMART); // truongbb - đưa những pattern này thành constant
        try {
            user.setBirthDay(sdf.parse(userSignupDTO.getBirthDay()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        user.setActive(false);
        user.setIsDelete(0); //  0 => deleted = false
        userService.saveUser(user);
        OTP otp = userService.generateOTP(user);
        String linkActive = accountActivationConfig.getActivateUrl() + user.getId();
        emailService.sendSimpleMessage(user.getEmail(),
                "Link active account",
                "<a href=\" " + linkActive + "\">Click vào đây để kích hoạt tài khoản</a>");
        return ResponseEntity.ok().body("check email for OTP");
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<String> activeAccount(@PathVariable Long id) {
        try {
            // truongbb - user lấy ra null thì sao?
            User user = userService.getUserById(id);
            if (user.isActive()) {
                return ResponseEntity.ok().body("Your account is already activated");
            }
            userService.activeAccount(user);
            return ResponseEntity.ok().body("Active successfull");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
