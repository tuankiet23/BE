package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity(name ="Company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Company {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Company_SEQ")
    @SequenceGenerator(name = "Company_SEQ", sequenceName = "Company_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "hot_line", nullable = false)
    String hot_line;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_incoporation", nullable = false)
    Date date_incoporation;
    @Column(name = "tax_code", nullable = false)
    String tax_code;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tax_date", nullable = false)
    Date tax_date;
    @Column(name = "head_office", nullable = false)
    String head_office;
    @Column(name = "number_staff ", nullable = false)
    String number_staff ;
    @Column(name = " link_web ", nullable = false)
    String  link_web ;
    @Column(name = " description ", nullable = false)
    String  description ;
    @Column(name = " avatar ", nullable = false)
    String  avatar ;
    @Column(name = " backdrop_img ", nullable = false)
    String  backdrop_img ;
    @Column(name = " is_delete ", nullable = false)
    Integer   is_delete ;


}
