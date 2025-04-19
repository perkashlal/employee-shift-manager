package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;

import com.perkash.employee_shift_manager.*;

import java.awt.*;

public class EmployeeShiftManagerUI {
    public static void main(String[] args) {
        // Create Shift Manager Object
        ShiftAssignmentManager manager = new ShiftAssignmentManager();

        // Create Main Frame
        JFrame frame = new JFrame("Employee Shift Manager");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create Tabs to add multiple panels
        JTabbedPane tabbedPane = new JTabbedPane();

        // Employee Form Panel (Add Employee)
        EmployeeFormPanel employeeFormPanel = new EmployeeFormPanel(manager);
        tabbedPane.addTab("Add Employee", employeeFormPanel);

        // Shift Form Panel (Assign Shift)
        ShiftFormPanel shiftFormPanel = new ShiftFormPanel(manager);
        tabbedPane.addTab("Assign Shift", shiftFormPanel);

        // Shift Summary Panel (View All)
        ShiftSummaryPanel shiftSummaryPanel = new ShiftSummaryPanel(manager);
        tabbedPane.addTab("View All Shifts", shiftSummaryPanel);

        // Add TabbedPane to Frame
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
