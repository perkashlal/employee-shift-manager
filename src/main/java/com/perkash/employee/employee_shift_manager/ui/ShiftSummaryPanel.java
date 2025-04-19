package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;

import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.Shift;
import com.perkash.employee_shift_manager.ShiftAssignmentManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftSummaryPanel extends JPanel {
    
    @SuppressWarnings("unused")
    private ShiftAssignmentManager manager;
    private JTextArea displayArea;

    public ShiftSummaryPanel(ShiftAssignmentManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton showButton = new JButton("Show All Employees with Shifts");
        add(showButton, BorderLayout.SOUTH);

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayArea.setText(""); // Clear previous data

                for (Employee emp : manager.getAllEmployees()) {
                    displayArea.append("Employee: " + emp.getName() + " (" + emp.getRole() + ")\n");
                    displayArea.append("Assigned Shifts:\n");

                    for (Shift shift : emp.getShifts()) {
                        displayArea.append(shift.toString() + "\n");
                    }

                    displayArea.append("------------------------------------------\n");
                }
            }
        });
    }
}
