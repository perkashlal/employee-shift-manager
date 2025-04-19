package com.perkash.employee_shift_manager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        // Create some example Employees
        Employee employee1 = new Employee("John Doe", "E001", "Manager");
        Employee employee2 = new Employee("Jane Smith", "E002", "Cashier");

        // Add some shifts
        employee1.addShift(new Shift(LocalDateTime.now(), LocalDateTime.now().plusHours(8)));
        employee2.addShift(new Shift(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(8)));

        // Build a simple GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Employee Shift Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            // Display Employees and Shifts
            textArea.append("Employee: " + employee1.getName() + " (" + employee1.getRole() + ")\n");
            for (Shift shift : employee1.getShifts()) {
                textArea.append(shift.toString() + "\n");
            }

            textArea.append("\nEmployee: " + employee2.getName() + " (" + employee2.getRole() + ")\n");
            for (Shift shift : employee2.getShifts()) {
                textArea.append(shift.toString() + "\n");
            }

            frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
