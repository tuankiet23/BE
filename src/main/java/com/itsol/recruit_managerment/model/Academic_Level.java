package com.itsol.recruit_managerment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "Academic_Level")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Academic_Level {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AL_SEQ")
    @SequenceGenerator(name = "AL_SEQ", sequenceName = "AL_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "academic_name", nullable = false)
    String academic_name;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "is_delete ")
    @JsonIgnore
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_delete ;

}
