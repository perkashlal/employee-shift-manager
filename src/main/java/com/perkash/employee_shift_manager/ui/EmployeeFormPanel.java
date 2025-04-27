package com.perkash.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JTextField nameField;
    private final JTextField idField;
    private final JTextField roleField;
    private final JButton saveButton;
    private final JButton deleteButton;
    private final JLabel statusLabel;
    private final EmployeeRepository repository;
    private final JTable employeeTable;
    private final DefaultTableModel tableModel;
    private boolean showPopups = true;

    public EmployeeFormPanel(EmployeeRepository repository) {
        this.repository = repository;

        nameField   = new JTextField(20);
        idField     = new JTextField(20);
        roleField   = new JTextField(20);
        saveButton  = new JButton("Add Employee");
        deleteButton= new JButton("Delete Selected");
        statusLabel = new JLabel(" ");

        tableModel  = new DefaultTableModel(new String[]{"Name", "Employee ID", "Role"}, 0);
        employeeTable = new JTable(tableModel);

        initializeUI();
    }

    public void setShowPopups(boolean value) {
        this.showPopups = value;
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(10, 10, 10, 10);
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        // Name label & field
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField.setName("nameField");
        add(nameField, gbc);

        // Employee ID label & field
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        idField.setName("idField");
        add(idField, gbc);

        // Role label & field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleField.setName("roleField");
        add(roleField, gbc);

        // Save button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        saveButton.setName("saveButton");
        saveButton.addActionListener(e -> saveEmployee());
        add(saveButton, gbc);

        // Delete button
        gbc.gridx = 1;
        deleteButton.setName("deleteButton");
        deleteButton.addActionListener(e -> deleteSelectedEmployee());
        add(deleteButton, gbc);

        // Status label
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        statusLabel.setName("statusLabel");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, gbc);

        // Table
        employeeTable.setName("employeeTable");
        JScrollPane scroll = new JScrollPane(employeeTable);
        gbc.gridy = 5; gbc.gridheight = 2;
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        add(scroll, gbc);
    }

    private void saveEmployee() {
        String name = nameField.getText().trim();
        String id   = idField.getText().trim();
        String role = roleField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || role.isEmpty()) {
            if (showPopups) {
                JOptionPane.showMessageDialog(
                    this,
                    "All fields must be filled!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            statusLabel.setText("Failed to add employee!");
            return;
        }

        Employee emp = new Employee(name, id, role);
        repository.save(emp);

        tableModel.addRow(new Object[]{name, id, role});

        if (showPopups) {
            JOptionPane.showMessageDialog(
                this,
                "Employee added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        statusLabel.setText("Employee added successfully!");

        nameField.setText("");
        idField.setText("");
        roleField.setText("");
        nameField.requestFocusInWindow();
    }

    private void deleteSelectedEmployee() {
        int row = employeeTable.getSelectedRow();

        // original behavior: show popup & status if no row selected
        if (row < 0) {
            if (showPopups) {
                JOptionPane.showMessageDialog(
                    this,
                    "Select a row to delete!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            statusLabel.setText("No employee selected!");
            return;
        }

        String empId = (String) tableModel.getValueAt(row, 1);
        boolean ok = repository.deleteEmployeeById(empId);

        if (ok) {
            tableModel.removeRow(row);
            if (showPopups) {
                JOptionPane.showMessageDialog(
                    this,
                    "Employee deleted!",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            statusLabel.setText("Employee deleted successfully!");
        } else {
            if (showPopups) {
                JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            statusLabel.setText("Failed to delete employee!");
        }
    }
}
