package com.sema.librarymanagment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDate borrowDate;

    LocalDate returnDate;

    @Column(nullable = false)
    LocalDate dueDate;

    @Column(nullable = false)
    boolean returned = false;
}
