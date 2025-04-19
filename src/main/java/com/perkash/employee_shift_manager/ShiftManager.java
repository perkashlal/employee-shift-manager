package com.perkash.employee_shift_manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftManager {
    private Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
    }

    public Employee getEmployee(String employeeId) {
        return employees.get(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
}
