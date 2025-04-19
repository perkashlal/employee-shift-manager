package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;
import com.perkash.employee_shift_manager.Shift;

public class ShiftSummaryPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextArea textArea;
    private JButton refreshButton, deleteButton;
    private JTextField deleteField;
    private EmployeeRepository repository;

    public ShiftSummaryPanel(EmployeeRepository repository) {
        this.repository = repository;
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        refreshButton = new JButton("Refresh Employees");
        refreshButton.addActionListener(e -> loadEmployees());
        bottomPanel.add(refreshButton);

        bottomPanel.add(new JLabel("Employee ID:"));
        deleteField = new JTextField(10);
        bottomPanel.add(deleteField);

        deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(e -> deleteEmployee());
        bottomPanel.add(deleteButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load employees initially
        loadEmployees();
    }

    private void loadEmployees() {
        List<Employee> employees = repository.findAll();
        textArea.setText("");

        for (Employee emp : employees) {
            textArea.append("Name: " + emp.getName() + ", ID: " + emp.getEmployeeId() + ", Role: " + emp.getRole() + "\n");

            List<Shift> shifts = emp.getShifts();
            if (shifts == null || shifts.isEmpty()) {
                textArea.append("    ➔ No shifts assigned.\n");
            } else {
                for (Shift shift : shifts) {
                    textArea.append("    ➔ Shift: " + shift.toString() + "\n");
                }
            }
            textArea.append("\n");
        }
    }

    private void deleteEmployee() {
        String employeeId = deleteField.getText().trim();
        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Employee ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean deleted = repository.deleteEmployeeById(employeeId);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Employee ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        deleteField.setText("");
    }
}
