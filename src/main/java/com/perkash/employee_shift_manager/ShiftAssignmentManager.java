package com.perkash.employee_shift_manager;

import java.util.ArrayList;
import java.util.List;

public class ShiftAssignmentManager {
    
    private List<Employee> employees;

    public ShiftAssignmentManager() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(String employeeId) {
        employees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
