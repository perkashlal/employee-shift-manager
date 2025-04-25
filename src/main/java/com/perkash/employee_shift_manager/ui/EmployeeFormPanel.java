package com.perkash.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;

import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, idField, roleField;
    private JButton saveButton;
    private JLabel statusLabel;
    private final EmployeeRepository repository;

    // ✅ This flag allows tests to disable popups
    private boolean showPopups = true;

    public EmployeeFormPanel(EmployeeRepository repository) {
        this.repository = repository;
        initializeUI();
    }

    // ✅ Setter to disable popups (used in tests)
    public void setShowPopups(boolean value) {
        this.showPopups = value;
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setName("nameField");
        add(nameField, gbc);

        // Employee ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Employee ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(20);
        idField.setName("idField");
        add(idField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleField = new JTextField(20);
        roleField.setName("roleField");
        add(roleField, gbc);

        // Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Add Employee");
        saveButton.setName("saveButton");
        saveButton.addActionListener(e -> saveEmployee());
        add(saveButton, gbc);

        // Status label
        gbc.gridy = 4;
        statusLabel = new JLabel(" ");
        statusLabel.setName("statusLabel");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, gbc);
    }

    private void saveEmployee() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String role = roleField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || role.isEmpty()) {
            if (showPopups) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            statusLabel.setText("Failed to add employee!");
            return;
        }

        Employee employee = new Employee(name, id, role);
        repository.save(employee);

        if (showPopups) {
            JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        statusLabel.setText("Employee added successfully!");

        nameField.setText("");
        idField.setText("");
        roleField.setText("");
        nameField.requestFocusInWindow();
    }
}
