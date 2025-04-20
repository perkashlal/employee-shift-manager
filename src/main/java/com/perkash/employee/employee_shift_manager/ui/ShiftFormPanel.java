package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;
import com.perkash.employee_shift_manager.Shift;

public class ShiftFormPanel extends JPanel {
    private JTextField employeeIdField;
    private JTextField startField;
    private JTextField endField;
    private JButton assignShiftButton;
    private EmployeeRepository repository;

    public ShiftFormPanel(EmployeeRepository repository) {
        this.repository = repository;
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField();
        add(employeeIdField);

        add(new JLabel("Shift Start (yyyy-MM-dd HH:mm):"));
        startField = new JTextField();
        add(startField);

        add(new JLabel("Shift End (yyyy-MM-dd HH:mm):"));
        endField = new JTextField();
        add(endField);

        assignShiftButton = new JButton("Assign Shift");
        add(assignShiftButton);

        add(new JLabel(""));

        assignShiftButton.addActionListener(e -> assignShift());
    }

    private void assignShift() {
        try {
            String employeeId = employeeIdField.getText().trim();
            String startStr = startField.getText().trim();
            String endStr = endField.getText().trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endStr, formatter);

            List<Employee> employees = repository.findAll();
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

            Shift shift = new Shift(startDateTime, endDateTime);
            found.addShift(shift);

            repository.deleteEmployeeById(found.getEmployeeId());
            repository.save(found);

            JOptionPane.showMessageDialog(this, "Shift assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            employeeIdField.setText("");
            startField.setText("");
            endField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
