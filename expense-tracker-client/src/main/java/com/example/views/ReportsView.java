package com.example.views;

import java.util.Calendar;

import com.example.controllers.DashboardController;
import com.example.controllers.ReportsController;
import com.example.utils.SidebarUtil;
import com.example.utils.ThemeManager;
import com.example.utils.ViewNavigator;
import com.example.utils.SidebarUtil.SidebarType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class ReportsView implements ThemeManager.ThemeChangeListener {

    private Scene currentScene;

    private BorderPane mainContainer;

    private VBox sidebar;

    public void show(String theme) {
        ThemeManager.getInstance().setActiveListener(this);

        String currentTheme = (theme != null) ? theme : ThemeManager.getInstance().getCurrentTheme();

        currentScene = createScene(currentTheme);

        // clear existing stylesheets and apply the new theme
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass().getResource("/themes/" + currentTheme + "Style.css").toExternalForm());

        // set up navigation callback for sidebar
        SidebarUtil.setNavigationCallback(new SidebarUtil.NavigationCallback() {
            @Override
            public void onNavigate(String destination) {
                // handle navigation within dashboard
                switch (destination) {
                    case "dashboard":
                        // switch to dashboard view
                        DashboardView dashboardView = new DashboardView();
                        dashboardView.show();
                        break;
                    case "reports":
                        System.out.println("Already in reports");
                        break;
                    case "goals":
                        // Switch to goals content
                        System.out.println("Goals - work in progress still");
                        break;
                    case "logout":
                        // handle logout - switch back to AuthView
                        AuthView authView = new AuthView();
                        authView.show(); // this will use current theme
                        break;
                }
            }
        });

        new ReportsController(this);
        ViewNavigator.switchViews(currentScene);
        
        // reset sidebar theme picker state when showing the view
        SidebarUtil.resetThemePickerState();
    }

    
    /* ------------------ MAIN SCENE CREATION ----------------------- */
    private Scene createScene(String currentTheme) {
        mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");

        // sidebar (left side)
        sidebar = SidebarUtil.createSidebar(SidebarType.REPORTS_VIEW);
        mainContainer.setLeft(sidebar);

        // main content (center)
        VBox contentArea = createContentArea();
        mainContainer.setCenter(contentArea);

        return new Scene(mainContainer);
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox();
        contentArea.getStyleClass().add("content-area");

        // top bar
        HBox topBar = createTopBar();

        // main reports view
        ScrollPane mainContent = createMainContent();

        contentArea.getChildren().addAll(topBar, mainContent);
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        
        return contentArea;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.getStyleClass().add("default-top-bar");
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(16, 24, 16, 24));
        topBar.setPrefHeight(64);
        topBar.setMinHeight(64);
        topBar.setMaxHeight(64);

        // Title section
        HBox titleSection = new HBox(16);
        titleSection.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label("Reports");
        titleLabel.getStyleClass().add("form-title");
        
        Label cozyBadge = new Label("Cozy Mode");
        cozyBadge.getStyleClass().add("cozy-mode-badge");
        
        titleSection.getChildren().addAll(titleLabel, cozyBadge);
        
        // Right side controls
        HBox controls = new HBox(12);
        controls.setAlignment(Pos.CENTER_RIGHT);
        
        Label hintLabel = new Label("Use the sidebar to customize themes");
        controls.getChildren().add(hintLabel);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        topBar.getChildren().addAll(titleSection, spacer, controls);
        return topBar;
    }

    private ScrollPane createMainContent() {
        ScrollPane mainContent = new ScrollPane();
        mainContent.getStyleClass().add("dashboard-content-scroll");
        mainContent.setFitToWidth(true);
        mainContent.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainContent.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(24);
        content.getStyleClass().add("content-container-vbox");
        content.setPadding(new Insets(24));
        content.setMaxWidth(Double.MAX_VALUE);

        HBox reportPeriod = createReportPeriod();

        GridPane quickLabels = createQuickLabelGrid();

        GridPane ReportsWidgets = createReportWidgets();

        content.getChildren().addAll(reportPeriod, quickLabels, ReportsWidgets);
        mainContent.setContent(content);

        return mainContent;
    }


    private HBox createReportPeriod() {
        HBox reportPeriod = new HBox();
        reportPeriod.setPadding(new Insets(0, 5, 0, 5));
        reportPeriod.getStyleClass().add("p-debug-border");
        // reportPeriod.getStyleClass().add("report-period");
        
        HBox heading = new HBox(12);
        heading.setAlignment(Pos.CENTER_LEFT);
        heading.getStyleClass().add("debug-border");

        SVGPath calendarIcon = new SVGPath();
        calendarIcon.setContent("M19 3H18V1H16V3H8V1H6V3H5C3.89 3 3 3.9 3 5V19C3 20.11 3.9 21 5 21H19C20.11 21 21 20.11 21 19V5C21 3.9 20.11 3 19 3M19 19H5V9H19V19M19 7H5V5H19V7Z");
        calendarIcon.setFill(Color.valueOf("#0000009f"));
        calendarIcon.setScaleX(1.4);
        calendarIcon.setScaleY(1.4);

        Label headingLabel = new Label("Reporting Period");
        headingLabel.getStyleClass().add("report-heading-label");

        heading.getChildren().addAll(calendarIcon, headingLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox toggleContainer = new HBox(2);
        toggleContainer.getStyleClass().add("debug-border");
        //toggleContainer.getStyleClass().add("period-toggle-container");

        Button week = new Button("Week");
        Button month = new Button("Month");
        Button quarter = new Button("Quarter");
        Button year = new Button("Year");

        toggleContainer.getChildren().addAll(week, month, quarter, year);

        reportPeriod.getChildren().addAll(heading, spacer, toggleContainer);
        return reportPeriod;
    }


    private GridPane createQuickLabelGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("o-debug-border");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20); 
        grid.setVgap(20);
        grid.setMaxWidth(1100);

        // load icons
        SVGPath totalExpensesIcon = new SVGPath();
        totalExpensesIcon.setContent("M7,15H9C9,16.08 10.37,17 12,17C13.63,17 15,16.08 15,15C15,13.9 13.96,13.5 11.76,12.97C9.64,12.44 7,11.78 7,9C7,7.21 8.47,5.69 10.5,5.18V3H13.5V5.18C15.53,5.69 17,7.21 17,9H15C15,7.92 13.63,7 12,7C10.37,7 9,7.92 9,9C9,10.1 10.04,10.5 12.24,11.03C14.36,11.56 17,12.22 17,15C17,16.79 15.53,18.31 13.5,18.82V21H10.5V18.82C8.47,18.31 7,16.79 7,15Z");

        SVGPath monthlySavingsIcon = new SVGPath();
        monthlySavingsIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,10.84 21.79,9.69 21.39,8.61L19.79,10.21C19.93,10.8 20,11.4 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4C12.6,4 13.2,4.07 13.79,4.21L15.4,2.6C14.31,2.21 13.16,2 12,2M19,2L15,6V7.5L12.45,10.05C12.3,10 12.15,10 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12C14,11.85 14,11.7 13.95,11.55L16.5,9H18L22,5H19V2M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12H16A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8V6Z");

        SVGPath savingsRateIcon = new SVGPath();
        savingsRateIcon.setContent("M12,24C5.9,24,1,19.1,1,13C1,7.3,5.4,2.6,11,2V0h1c4.1,0,7.9,1.9,10.4,5.2L23,6l-1.6,1.2C22.4,9,23,10.9,23,13 C23,19.1,18.1,24,12,24z M11,4.1C6.5,4.6,3,8.4,3,13c0,5,4,9,9,9s9-4,9-9c0-1.6-0.4-3.2-1.2-4.6L11,15V4.1z M13,2v9l7.2-5.4 C18.3,3.6,15.7,2.3,13,2z");

        SVGPath transactionsIcon = new SVGPath();
        transactionsIcon.setContent("M2 14H8V20H2M16 8H10V10H16M2 10H8V4H2M10 4V6H22V4M10 20H16V18H10M10 16H22V14H10");

        // create tracking boxes
        VBox totalExpenseBox = createTrackingBox(totalExpensesIcon, "+23.5%", "$2890.49", "Total Expenses");
        VBox monthlySavingsBox = createTrackingBox(monthlySavingsIcon, "+15.2%", "$610", "Monthly Savings");
        VBox savingsRateBox = createTrackingBox(savingsRateIcon, "+2.1%", "%17.4", "Savings Rate");
        VBox transactionsBox = createTrackingBox(transactionsIcon, "8 This week", "127", "Transactions");

        // put the boxes in the grid
        grid.add(totalExpenseBox, 0, 0);
        grid.add(monthlySavingsBox, 1, 0);
        grid.add(savingsRateBox, 2, 0);
        grid.add(transactionsBox, 3, 0);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(col1, col2, col3, col4);

        return grid;
    }

    private static VBox createTrackingBox(SVGPath icon, String percentage, String value, String label) {
        VBox box = new VBox(8);
        box.getStyleClass().add("b-debug-border");
        //box.getStyleClass().add("reports-tracking-box");
        box.setPadding(new Insets(16));
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER);

        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#000000b6"));
        clonIcon.setScaleX(1.1);
        clonIcon.setScaleY(1.1);

        topRow.getChildren().add(clonIcon);

        // middle row: main value
        HBox valueRow = new HBox();
        valueRow.setAlignment(Pos.CENTER);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("tracking-value");

        valueRow.getChildren().add(valueLabel);

        // bottom row: description label
        HBox labelRow = new HBox();
        labelRow.setAlignment(Pos.CENTER);

        Label descLabel = new Label(label);
        descLabel.getStyleClass().add("tracking-label");

        labelRow.getChildren().add(descLabel);

        HBox percentageRow = new HBox();
        percentageRow.setAlignment(Pos.CENTER);

        Label percentageLabel = new Label(percentage);
        percentageLabel.getStyleClass().add("tracking-percentage");

         // add specific styling based on positive/negative
        if (percentage != null) {
            if (percentage.startsWith("+")) {
                percentageLabel.getStyleClass().add("tracking-percentage-positive");
            } else if (percentage.startsWith("-")) {
                percentageLabel.getStyleClass().add("tracking-percentage-negative");
            } else {
                percentageLabel.getStyleClass().add("tracking-percentage-neutral");
            }
        }

        percentageRow.getChildren().add(percentageLabel);

        box.getChildren().addAll(topRow, valueRow, labelRow, percentageRow);
        return box;
    }


    private GridPane createReportWidgets() {
        GridPane grid = new GridPane();
        grid.setMaxWidth(1100);
        grid.setHgap(24);
        grid.setVgap(24);
        
        // create widgets
        VBox expenseCategoriesBox = createExpenseCategoryWidget();
        VBox periodTrend  = createPeriodTrendWidget();
        
        grid.add(expenseCategoriesBox, 0, 0);
        grid.add(periodTrend, 1, 0);
        
        // column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);
        
        return grid;
    }


    private VBox createExpenseCategoryWidget() {
        VBox box = new VBox(10);
        box.getStyleClass().add("p-debug-border");
        //box.getStyleClass().add("expense-category-widget");
        
        ScrollPane scrollPane = new ScrollPane();
        
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox categoriesContainer = new VBox(10);

        // TODO: replace with real categories
        String[] categories = {"Food & Dining", "Transportation", "Shopping", "Entertainment", "Utilities"};
        double[] amountsSpent = {200, 150, 100, 80, 120}; 
        double[] totalAmounts = {500, 500, 300, 200, 400};

        for (int i = 0; i < categories.length; i++) {
            categoriesContainer.getChildren().add(createCategoryRow(categories[i], amountsSpent[i], totalAmounts[i]));
        }

        scrollPane.setContent(categoriesContainer);
        box.getChildren().add(scrollPane);

        return box;
    }

    private HBox createCategoryRow(String categoryName, double amountSpent, double totalAmount) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("b-debug-border");

        Label nameLabel = new Label(categoryName);
        Label amountLabel = new Label("$" + amountSpent);

        // calculate percentage
        double percentage = (amountSpent / totalAmount) * 100;
        ProgressBar progressBar = new ProgressBar(percentage / 100);
        Label percentageLabel = new Label(String.format("%.1f%%", percentage));

        // add components to row
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        row.getChildren().addAll(nameLabel, new Region(), amountLabel, progressBar, percentageLabel);
        
        return row;
    }


    private VBox createPeriodTrendWidget() {
        // TODO: add styling
        VBox box = new VBox(10);
        box.getStyleClass().add("period-trend-widget");
        box.getStyleClass().add("debug-border");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox contentContainer = new VBox(10);

        // header Row
        HBox headerRow = new HBox(20);
        headerRow.getStyleClass().add("header-row");
        
        Label monthLabel = new Label("Month");
        Label incomeLabel = new Label("Income");
        Label expensesLabel = new Label("Expenses");
        Label savingsLabel = new Label("Savings");

        headerRow.getChildren().addAll(monthLabel, incomeLabel, expensesLabel, savingsLabel);
        contentContainer.getChildren().add(headerRow);

        // TODO: replace with real data
        String[] months = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        double[] incomes = {
            2000, 2200, 2100, 2300, 2400, 2500,
            2600, 2700, 2800, 2900, 3000, 3100
        };

        double[] expenses = {
            1500, 1800, 1600, 1700, 1900, 2000,
            2100, 2200, 2300, 2400, 2500, 2600
        };

        double[] savings = {
            500, 400, 500, 600, 500, 500,
            500, 500, 500, 500, 500, 500
        };

        for (int i = 0; i < months.length; i++) {
            contentContainer.getChildren().add(createTrendRow(months[i], incomes[i], expenses[i], savings[i]));
        }

        scrollPane.setContent(contentContainer);
        box.getChildren().add(scrollPane);

        return box;
    }

    private HBox createTrendRow(String month, double income, double expenses, double savings) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        
        Label monthLabel = new Label(month);
        Label incomeLabel = new Label("$" + income);
        Label expensesLabel = new Label("$" + expenses);
        Label savingsLabel = new Label("$" + savings);

        row.getChildren().addAll(monthLabel, incomeLabel, expensesLabel, savingsLabel);
        
        return row;
    }


    public void show(){
        show(null);
    }

    @Override
    public void onThemeChanged(String newTheme) {
        if (currentScene != null) {
            currentScene.getStylesheets().clear();
            currentScene.getStylesheets().add(getClass().getResource("/themes/" + newTheme + "Style.css").toExternalForm());
            System.out.println("Current stylesheet: " + newTheme + "Style.css loaded.");
        }
    }
}
