package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeShiftManagerUI {
    public static void main(String[] args) {
        // Create the shared Employee Repository
        EmployeeRepository repository = new EmployeeRepository();

        // Setup the Main Frame
        JFrame frame = new JFrame("Employee Shift Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null); // Center the window on screen
        frame.setLayout(new BorderLayout());

        // Create Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create panels and add them to tabs
        tabbedPane.addTab("➕ Add Employee", new EmployeeFormPanel(repository));
        tabbedPane.addTab("🕑 Assign Shift", new ShiftFormPanel(repository));
        tabbedPane.addTab("📋 View/Delete Employees", new ShiftSummaryPanel(repository));

        // Add Tabbed Pane to Frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Make Frame Visible
        frame.setVisible(true);
    }
}
