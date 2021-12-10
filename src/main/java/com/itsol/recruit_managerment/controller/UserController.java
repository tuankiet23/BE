package com.itsol.recruit_managerment.controller;


import com.itsol.recruit_managerment.dto.PasswordDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.OTP;
import com.itsol.recruit_managerment.model.TokenAuthen;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.service.ProfilesServiceimpl;
import com.itsol.recruit_managerment.service.UserServiceimpl;
import com.itsol.recruit_managerment.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceimpl userService;
    @Autowired
    private  EmailServiceImpl emailService;
    @Autowired
    private ProfilesServiceimpl  profilesServiceimpl;



    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getData();
    }

    @PostMapping()
    public ResponseEntity<Object> add(@Valid @RequestBody UserVM userVM, BindingResult result) {
        if(result.hasErrors()){
            return  ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            userService.validateUser(userVM);
            userService.add(userVM);

            return  ResponseEntity.ok().body(userVM);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to create user");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id , @RequestBody UserVM userVM) {
        try {
            userService.validateUser(userVM);
            userService.update(userVM,id);
            return  ResponseEntity.ok().body(userVM);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to update user");
        }

    }
    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id ) {
        return userService.deleteById(id);
    }
    @GetMapping("/search/{fullName}")
    public  List<User> search(@PathVariable String fullName){
        return userService.searchName(fullName);
    }


    @GetMapping("/users/info/get-otp")
    public ResponseEntity<String> getOTP(){
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.retrieveNewOTP(user);
            emailService.sendSimpleMessage(user.getUserName(), "OTP", "OTP: " + otp.getCode());
            return ResponseEntity.ok().body("check email for OTP");
        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/users/info/change-password")
    public ResponseEntity<String> changePasswordRequest(@RequestBody PasswordDTO passwordDTO){
        User user = userService.loadUserFromContext();
        try {
            if(userService.verifyPassword(user, passwordDTO)){
                OTP otp = userService.retrieveNewOTP(user);
                emailService.sendSimpleMessage(user.getUserName(),
                        "Change password",
                        "OTP: " + otp.getCode() + "\nNew password: " + passwordDTO.getNewPassword());
                return ResponseEntity.ok().body("Check mail for OTP to commit changing");
            }else{
                return ResponseEntity.badRequest().body("Failed changing password");
            }
        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/users/info/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String otpCode, @RequestParam String password){
        try {
            User user = userService.loadUserFromContext();
            OTP otp = userService.getOTPByUser(user);
            userService.verifyOTP(otp, otpCode);
            userService.changePassword(password, user);
            return ResponseEntity.ok().body("Password change successfull");
            }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
//        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
//        }
//        TokenAuthen token = new TokenAuthen();
//        token.setToken(jwtUtil.generateToken(userPrincipal));
//        token.setTokenExpDate(jwtUtil.generateExpirationDate());
//        token.setCreatedBy(userPrincipal.getUserId());
//        tokenService.createToken(token);
//        return ResponseEntity.ok(token.getToken());
//    }

}
