package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    @Column(name = "skill", nullable = true)
    private String Skill;

    @Column(name = "years_experience", nullable = true)
    private String years_experience;

    @OneToOne
    private Academic_Level academic_level ;

    @Column(name = "desired_salary ", nullable = true)
    String desired_salary ;

    @Column(name = "desired_working_address  ", nullable = true)
    String desired_working_address  ;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_delete ;

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

    public String getYears_experience() {
        return years_experience;
    }

    public void setYears_experience(String years_experience) {
        this.years_experience = years_experience;
    }

    public Academic_Level getAcademic_level() {
        return academic_level;
    }

    public void setAcademic_level(Academic_Level academic_level) {
        this.academic_level = academic_level;
    }

    public String getDesired_salary() {
        return desired_salary;
    }

    public void setDesired_salary(String desired_salary) {
        this.desired_salary = desired_salary;
    }

    public String getDesired_working_address() {
        return desired_working_address;
    }

    public void setDesired_working_address(String desired_working_address) {
        this.desired_working_address = desired_working_address;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
