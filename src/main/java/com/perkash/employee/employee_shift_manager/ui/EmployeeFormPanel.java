package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, idField, roleField;
    private JButton addButton;
    private EmployeeRepository repository;

    public EmployeeFormPanel(EmployeeRepository repository) {
        this.repository = repository;
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Employee ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Role:"));
        roleField = new JTextField();
        add(roleField);

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
        repository.save(employee);

        JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        nameField.setText("");
        idField.setText("");
        roleField.setText("");
    }
}
