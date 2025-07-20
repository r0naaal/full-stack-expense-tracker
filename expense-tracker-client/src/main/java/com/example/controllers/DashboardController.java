package com.example.controllers;

import com.example.views.AuthView;
import com.example.views.DashboardView;

public class DashboardController {

    private DashboardView dashboardView;

    public DashboardController(DashboardView dashboardView){
        this.dashboardView = dashboardView;

        initialize();
    }

    private void initialize() {
        
        dashboardView.getReportsButton().setOnMouseClicked(e -> showReports());
        //dashboardView.getGoalsButton().setOnMouseClicked(e -> showGoals());
        dashboardView.getLoginViewButton().setOnMouseClicked(e -> showLoginView());
        
    }
    
    private void showReports() {
        System.out.println("Navigating to Categories");
        // TODO: add logic
    }

    private void showGoals() {
        System.out.println("Navigating to goals");
        // TODO: add logic
    }

    private void showLoginView() {
        System.out.println("Navigating to Login view");
        new AuthView().show("Cafe");
    }
}
