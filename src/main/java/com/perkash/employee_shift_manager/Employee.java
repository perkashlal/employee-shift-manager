package com.perkash.employee_shift_manager;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String name;
    private String employeeId;
    private String role;

    // New: List to store assigned shifts
    private List<Shift> shifts;

    // Constructor
    public Employee(String name, String employeeId, String role) {
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.shifts = new ArrayList<>();  // Initialize empty list
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

    // Add Shift to Employee
    public void addShift(Shift shift) {
        shifts.add(shift);
    }
   
    

    // Get all assigned Shifts
    public List<Shift> getShifts() {
        return shifts;
    }
}
