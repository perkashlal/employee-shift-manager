package com.perkash.employee_shift_manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.Shift;

import java.util.List;
import java.time.LocalDateTime;

public class EmployeeTest {

    @Test
    public void testCreateEmployee() {
        Employee emp = new Employee("John Doe", "E001", "Manager");

        assertEquals("John Doe", emp.getName());
        assertEquals("E001", emp.getEmployeeId());
        assertEquals("Manager", emp.getRole());
    }

    @Test
    public void testAddShift() {
        Employee emp = new Employee("John Doe", "E001", "Manager");

        LocalDateTime start = LocalDateTime.of(2025, 4, 15, 9, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 15, 17, 0);
        Shift shift = new Shift(start, end);

        emp.addShift(shift);

        List<Shift> shifts = emp.getShifts();
        assertEquals(1, shifts.size());
        assertTrue(shifts.contains(shift));
    }
}
