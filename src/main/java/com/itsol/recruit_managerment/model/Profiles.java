package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Profiles")

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Profiles implements Serializable {

    @Id
    private Long id;



    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // truongbb - nullable là true thì không cần viết ra, đó là default ==> tự rà soát các class khác và bỏ đi
    @Column(name = "skill", nullable = false)
    private String Skill;

    @Column(name = "years_experience", nullable = false)
    private String yearsExperience;

    @OneToOne
    @JoinColumn(name ="academic_level_id")
    private AcademicLevel academicLevel;

    @Column(name = "desired_salary ", nullable = false)
    String desiredSalary;

    @Column(name = "desired_working_address  ", nullable = false)
    String desiredWorkingAddress;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSkill() {
        return Skill;
    }

    public void setSkill(String skill) {
        Skill = skill;
    }

    public String getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(String yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(String desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public String getDesiredWorkingAddress() {
        return desiredWorkingAddress;
    }

    public void setDesiredWorkingAddress(String desiredWorkingAddress) {
        this.desiredWorkingAddress = desiredWorkingAddress;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
