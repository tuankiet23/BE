package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "Types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Types {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TYPE_SEQ")
    @SequenceGenerator(name = "TYPE_SEQ", sequenceName = "TYPE_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_delete ")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete ;
}
