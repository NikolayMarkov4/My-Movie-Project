package org.softuni.mymoviemaster.domain.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Transactional
@Entity
@Table(name = "directors")
public class Director extends Person {
    public Director() {
    }
}
