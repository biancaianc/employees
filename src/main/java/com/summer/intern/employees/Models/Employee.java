package com.summer.intern.employees.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee", schema = "employees_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    @Id
    private String id;
    private String name;
    private int age;
}
