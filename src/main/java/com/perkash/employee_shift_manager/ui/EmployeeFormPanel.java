package com.perkash.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, idField, roleField;
    private JButton saveButton, deleteButton;
    private JLabel statusLabel;
    private final EmployeeRepository repository;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

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

        // Button for adding employee
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Add Employee");
        saveButton.setName("saveButton");
        saveButton.addActionListener(e -> saveEmployee());
        add(saveButton, gbc);

        // Button for deleting employee
        deleteButton = new JButton("Delete Employee");
        deleteButton.setName("deleteButton");
        deleteButton.addActionListener(e -> deleteEmployee());
        add(deleteButton, gbc);

        // Status label
        gbc.gridy = 4;
        statusLabel = new JLabel(" ");
        statusLabel.setName("statusLabel");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, gbc);

        // Employee Table to display added employees
        String[] columns = {"Name", "Employee ID", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        gbc.gridy = 5;
        gbc.gridheight = 2;
        add(scrollPane, gbc);
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

        // Add the employee to the table
        tableModel.addRow(new Object[]{name, id, role});

        if (showPopups) {
            JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        statusLabel.setText("Employee added successfully!");

        // Clear the input fields
        nameField.setText("");
        idField.setText("");
        roleField.setText("");
        nameField.requestFocusInWindow();
    }

    private void deleteEmployee() {
        // Get selected row from the table
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            // Retrieve the Employee ID from the selected row (assume it’s in column 1)
            String employeeId = (String) tableModel.getValueAt(selectedRow, 1);

            // Delete the employee from the repository using the ID
            boolean isDeleted = repository.deleteEmployeeById(employeeId);

            if (isDeleted) {
                // Remove the row from the table if deletion is successful
                tableModel.removeRow(selectedRow);
                if (showPopups) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                statusLabel.setText("Employee deleted successfully!");
            } else {
                if (showPopups) {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                statusLabel.setText("Failed to delete employee!");
            }
        } else {
            // No employee selected
            if (showPopups) {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            statusLabel.setText("No employee selected!");
        }
    }
}
