package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;

import com.perkash.employee_shift_manager.*;

import java.awt.*;

public class EmployeeFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, idField, roleField;
    private JButton addButton;
    private ShiftAssignmentManager manager;

    public EmployeeFormPanel(ShiftAssignmentManager manager) {
        this.manager = manager;
        setLayout(new GridLayout(4, 2));

        // Name Field
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // Employee ID Field
        add(new JLabel("Employee ID:"));
        idField = new JTextField();
        add(idField);

        // Role Field
        add(new JLabel("Role:"));
        roleField = new JTextField();
        add(roleField);

        // Add Button
        addButton = new JButton("Add Employee");
        addButton.addActionListener(e -> addEmployee());
        add(addButton);
    }

    private void addEmployee() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String role = roleField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee employee = new Employee(name, id, role);
        manager.addEmployee(employee);

        JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear fields
        nameField.setText("");
        idField.setText("");
        roleField.setText("");
    }
}
