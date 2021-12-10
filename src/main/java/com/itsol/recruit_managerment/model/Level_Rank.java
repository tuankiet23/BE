package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "Level_Rank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Level_Rank {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEVEL_RANK_SEQ")
    @SequenceGenerator(name = "LEVEL_RANK_SEQ", sequenceName = "LEVEL_RANK_SEQ", allocationSize = 1, initialValue = 1)
    Long id;
    @Column(name = "name")
    String level_name;
    @Column(name = "description")
    String description;
    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_delete ;
}
