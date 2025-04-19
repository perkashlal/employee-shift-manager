package com.perkash.employee_shift_manager;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String name;
    private String employeeId;
    private String role;
    private List<Shift> shifts;

    public Employee() {
        // Default constructor (required for MongoDB deserialization)
    }

    public Employee(String name, String employeeId, String role) {
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.shifts = new ArrayList<>();
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

    // Important for tests: alias for 'position' field
    public void setPosition(String position) {
        this.role = position;
    }

    public String getPosition() {
        return role;
    }
}
