package com.itsol.recruit_managerment.controller;


import com.itsol.recruit_managerment.config.AccountActivationConfig;
import com.itsol.recruit_managerment.constant.Constants;
import com.itsol.recruit_managerment.constant.DateTimeConstant;
import com.itsol.recruit_managerment.dto.PasswordDTO;
import com.itsol.recruit_managerment.dto.UserSignupDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.OTP;
import com.itsol.recruit_managerment.model.Role;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.RoleRepo;
import com.itsol.recruit_managerment.security.AuthenFilter;
import com.itsol.recruit_managerment.service.impl.ProfilesServiceimpl;
import com.itsol.recruit_managerment.service.impl.UserServiceimpl;
import com.itsol.recruit_managerment.vm.FogotPasswordVM;
import com.itsol.recruit_managerment.vm.UserVM;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.naming.AuthenticationException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    AccountActivationConfig accountActivationConfig;
    @Autowired
    UserServiceimpl userServiceimpl;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private UserServiceimpl userService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private ProfilesServiceimpl profilesServiceimpl;
    private AuthenticationManager authenticationManager;
    private AuthenFilter authenFilter;
    @Autowired
    private UserDetailsService userDetailsService;

    private Response response;
//    @GetMapping("/all")
//    public List<User> getAll(){
//        return userService.getData();
//    }

    @PostMapping("/adduser")

    public ResponseEntity<Object> singupuser(@RequestBody UserSignupDTO userSignupDTO) {
        Role role = roleRepo.findByName("ROLE_USER");
        User user = userServiceimpl.createUser(userSignupDTO);
//        if (ObjectUtils.isEmpty(user)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstant.YYYYMMDD_FOMART);
        try {
            user.setBirthDay(sdf.parse(userSignupDTO.getBirthDay()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        user.setActive(false);
        user.setIsDelete(0);
        userService.saveUser(user);
        OTP otp = userService.generateOTP(user);
        String linkActive = accountActivationConfig.getActivateUrl() + user.getId();
        emailService.sendSimpleMessage(user.getEmail(), "Link active account", "<a href=\" " + linkActive + "\">Click vào đây để kích hoạt tài khoản</a>");
        return ResponseEntity.ok().body("check email for OTP");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("xóa thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }
    }

    @GetMapping("/search/{fullName}")
    public List<User> search(@PathVariable String fullName) {
        return userService.searchName(fullName);
    }


    @GetMapping("/users/info/get-otp")
    public ResponseEntity<String> getOTP() {
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.retrieveNewOTP(user);
            emailService.sendSimpleMessage(user.getUserName(), "OTP", "OTP: " + otp.getCode());
            return ResponseEntity.ok().body("check email for OTP");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/info/change-password")
    public ResponseEntity<String> changePasswordRequest(@RequestBody PasswordDTO passwordDTO) {
        User user = userService.loadUserFromContext();
        try {
            if (userService.verifyPassword(user, passwordDTO)) {
                user.setNewPassword(passwordDTO.getNewPassword());
                OTP otp = userService.retrieveNewOTP(user);
                emailService.sendSimpleMessage(user.getEmail(),
                        "Change password",
                        "OTP: " + otp.getCode());
                return ResponseEntity.ok().body("Check mail for OTP to commit changing");
            } else {
                return ResponseEntity.badRequest().body("Failed changing password");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/users/info/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String otpCode) {
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.getOTPByUser(user);
            userService.verifyOTP(otp, otpCode);
            userService.changePassword(user);
            return ResponseEntity.ok().body("Password change successfull");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/fogotpass")
    public ResponseEntity<String> fogotPassword(@RequestBody FogotPasswordVM fogotPasswordVM) {
        return (ResponseEntity<String>) userService.sendFogotPasswordMail(fogotPasswordVM.getEmail());
    }

    @GetMapping("/getProfile")
    public ResponseEntity getProfile(HttpServletRequest request) {
        return ResponseEntity.ok().body(userService.getProfileUser(request));

    }


//    @PostMapping("/authenticate")
//    public @ResponseBody
//    ResponseEntity<String>  createAuthenticationTokenAndRole(@RequestBody UserSignupDTO form) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(form.getUserName(), form.getPassword())
//            );
//        } catch (RuntimeException e) {
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(form.getUserName());
//        final String jwt = authenFilter.generateToken(userDetails);
//        final List<String> roles = new ArrayList<>();
//        userDetails.getAuthorities().stream().forEach(grantedAuthority -> {
//            roles.add(grantedAuthority.getAuthority());
//        });
//        Map<String, Object> data = new HashMap<>();
//        data.put("jwt", jwt);
//        data.put("roles", roles);
//         return ResponseEntity.ok().body("Login thanh cong");
//    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file,
                                             @PathVariable("id") Long id) {
        try {
            User user = userService.store(file, id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                            user.getAvatarName() + "\"")
                    .body(user.getAvatar());
        } catch (Exception e) {
            return null;
        }
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        User user = userService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        user.getAvatarName() + "\"")
                .body(user.getAvatar());
    }
}
