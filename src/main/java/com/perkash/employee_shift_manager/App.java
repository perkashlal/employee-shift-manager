package com.perkash.employee_shift_manager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Employee Shift Manager!");

        // Create Employee 1
        Employee employee1 = new Employee("Perkashlal", "E001", "Manager");

        // Create Shifts for Employee 1
        Shift shift1 = new Shift(LocalDateTime.of(2025, 4, 16, 9, 0),
                                 LocalDateTime.of(2025, 4, 16, 17, 0));

        Shift shift2 = new Shift(LocalDateTime.of(2025, 4, 17, 10, 0),
                                 LocalDateTime.of(2025, 4, 17, 18, 0));

        employee1.addShift(shift1);
        employee1.addShift(shift2);

        // Create Employee 2
        Employee employee2 = new Employee("Alice", "E002", "Cashier");

        // Create Shift for Employee 2
        Shift shift3 = new Shift(LocalDateTime.of(2025, 4, 18, 8, 0),
                                 LocalDateTime.of(2025, 4, 18, 16, 0));

        employee2.addShift(shift3);

        // Manage all Employees
        ShiftAssignmentManager manager = new ShiftAssignmentManager();
        manager.addEmployee(employee1);
        manager.addEmployee(employee2);

        // Display All Employees with Shifts
        for (Employee emp : manager.getAllEmployees()) {
            System.out.println("\nEmployee: " + emp.getName() + " (" + emp.getRole() + ")");
            System.out.println("Assigned Shifts:");
            for (Shift shift : emp.getShifts()) {
                System.out.println(shift);
            }
        }
    }
}
