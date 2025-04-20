package com.perkash.employee_shift_manager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShiftAssignmentManagerTest {

    @Test
    public void testAddAndGetEmployee() {
        ShiftAssignmentManager manager = new ShiftAssignmentManager();

        Employee emp = new Employee("perkashlal", "E002", "Cashier");
        manager.addEmployee(emp);

        List<Employee> employees = manager.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("perkashlal", employees.get(0).getName());
    }

    @Test
    public void testRemoveEmployee() {
        ShiftAssignmentManager manager = new ShiftAssignmentManager();

        Employee emp = new Employee("Jane Doe", "E002", "Cashier");
        manager.addEmployee(emp);

        manager.removeEmployee("E002");

        List<Employee> employees = manager.getAllEmployees();
        assertEquals(0, employees.size());
    }
}
