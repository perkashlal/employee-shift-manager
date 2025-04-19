package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;

import com.perkash.employee_shift_manager.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShiftFormPanel extends JPanel {
    private JTextField employeeIdField;
    private JTextField startField;
    private JTextField endField;
    private JButton assignShiftButton;
    private ShiftAssignmentManager manager;

    public ShiftFormPanel(ShiftAssignmentManager manager) {
        this.manager = manager;
        setLayout(new GridLayout(4, 2));

        // Employee ID field
        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField();
        add(employeeIdField);

        // Shift Start field
        add(new JLabel("Shift Start (yyyy-MM-dd HH:mm):"));
        startField = new JTextField();
        add(startField);

        // Shift End field
        add(new JLabel("Shift End (yyyy-MM-dd HH:mm):"));
        endField = new JTextField();
        add(endField);

        // Assign Shift Button
        assignShiftButton = new JButton("Assign Shift");
        add(assignShiftButton);

        // Empty label to fill grid
        add(new JLabel(""));

        // ✅ Button Action
        assignShiftButton.addActionListener(e -> assignShift());
    }

    private void assignShift() {
        try {
            String employeeId = employeeIdField.getText().trim();
            String startStr = startField.getText().trim();
            String endStr = endField.getText().trim();

            // Parse dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endStr, formatter);

            // Find employee
            List<Employee> employees = manager.getAllEmployees();
            Employee found = null;
            for (Employee emp : employees) {
                if (emp.getEmployeeId().equals(employeeId)) {
                    found = emp;
                    break;
                }
            }

            if (found == null) {
                JOptionPane.showMessageDialog(this, "Employee ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create shift
            Shift shift = new Shift(startDateTime, endDateTime);
            found.addShift(shift);

            JOptionPane.showMessageDialog(this, "Shift assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after success
            employeeIdField.setText("");
            startField.setText("");
            endField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
