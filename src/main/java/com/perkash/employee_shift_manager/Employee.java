package com.perkash.employee_shift_manager;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String name;
    private String employeeId;
    private String role;
    private List<Shift> shifts;

    // Constructor
    public Employee() {
        this.shifts = new ArrayList<>(); // Initialize empty list
    }

    public Employee(String name, String employeeId, String role) {
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.shifts = new ArrayList<>(); // Initialize empty list
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getRole() {
        return role;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    // Add a single Shift to the Employee
    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    // Overriding toString for better print
    @Override
    public String toString() {
        return "Employee [name=" + name + ", employeeId=" + employeeId + ", role=" + role + ", shifts=" + shifts + "]";
    }
}
