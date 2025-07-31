package com.example.views;

import com.example.controllers.ReportsController;
import com.example.utils.SidebarUtil;
import com.example.utils.ThemeManager;
import com.example.utils.ViewNavigator;
import com.example.utils.SidebarUtil.SidebarType;

import javafx.geometry.*;  
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
        reportPeriod.setMaxWidth(1100);
        reportPeriod.setMaxHeight(40);
        
        HBox heading = new HBox(12);
        heading.setAlignment(Pos.CENTER);

        SVGPath calendarIcon = new SVGPath();
        calendarIcon.setContent("M19 3H18V1H16V3H8V1H6V3H5C3.89 3 3 3.9 3 5V19C3 20.11 3.9 21 5 21H19C20.11 21 21 20.11 21 19V5C21 3.9 20.11 3 19 3M19 19H5V9H19V19M19 7H5V5H19V7Z");
        calendarIcon.setFill(Color.valueOf("#000000ff"));
        calendarIcon.setScaleX(1.4);
        calendarIcon.setScaleY(1.4);

        Label headingLabel = new Label("Reporting Period");
        headingLabel.getStyleClass().add("report-title");

        heading.getChildren().addAll(calendarIcon, headingLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox toggleContainer = new HBox(2);
        toggleContainer.setPadding(new Insets(2));
        toggleContainer.setAlignment(Pos.CENTER);
        toggleContainer.getStyleClass().add("period-mode-toggle");
        //toggleContainer.getStyleClass().add("debug-border");

        // TODO: add period logic
        Button week = new Button("Week");
        week.getStyleClass().add("period-mode-toggle-button");
        week.setFocusTraversable(false);
        
        Button month = new Button("Month");
        month.getStyleClass().add("period-mode-toggle-button");
        month.setFocusTraversable(false);
        
        Button quarter = new Button("Quarter");
        quarter.getStyleClass().add("period-mode-toggle-button");
        quarter.setFocusTraversable(false);
        
        Button year = new Button("Year");
        year.getStyleClass().add("period-mode-toggle-button");
        year.setFocusTraversable(false);

        toggleContainer.getChildren().addAll(week, month, quarter, year);

        reportPeriod.getChildren().addAll(heading, spacer, toggleContainer);
        return reportPeriod;
    }


    private GridPane createQuickLabelGrid() {
        GridPane grid = new GridPane();
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
        VBox savingsRateBox = createTrackingBox(savingsRateIcon, "+2.1%", "17.4%", "Savings Rate");
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
        box.getStyleClass().add("period-tracking-box");
        box.setPadding(new Insets(16));
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER);

        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#000000a7"));
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
                percentageLabel.getStyleClass().add("tracking-percentage-positive-nobg");
            } else if (percentage.startsWith("-")) {
                percentageLabel.getStyleClass().add("tracking-percentage-negative-nobg");
            } else {
                percentageLabel.setText("+" + percentageLabel.getText());
                percentageLabel.getStyleClass().add("tracking-percentage-neutral-nobg");
            }
        }

        percentageRow.getChildren().add(percentageLabel);

        box.getChildren().addAll(topRow, valueRow, labelRow, percentageRow);
        return box;
    }


    private GridPane createReportWidgets() {
        GridPane grid = new GridPane();
        grid.setMaxWidth(1100);
        grid.setMaxHeight(500);
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
        VBox box = new VBox(16);
        box.setPadding(new Insets(16));
        box.getStyleClass().add("left-widget-container");
        box.setPrefWidth(500);

        HBox titleBox = new HBox(12);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        SVGPath expenseCateGoriesIcon = new SVGPath();
        expenseCateGoriesIcon.setContent("M9 17H7V10H9V17M13 17H11V7H13V17M17 17H15V13H17V17M19 19H5V5H19V19.1M19 3H5C3.9 3 3 3.9 3 5V19C3 20.1 3.9 21 5 21H19C20.1 21 21 20.1 21 19V5C21 3.9 20.1 3 19 3Z");
        expenseCateGoriesIcon.setFill(Color.valueOf("#000000"));
        expenseCateGoriesIcon.setScaleX(1.1);
        expenseCateGoriesIcon.setScaleY(1.1);

        Label titleLabel = new Label("Expense Categories");
        titleLabel.getStyleClass().add("widget-title");

        titleBox.getChildren().addAll(expenseCateGoriesIcon, titleLabel);
        
        ScrollPane scrollPane = new ScrollPane();
         scrollPane.getStyleClass().add("recent-activity-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMaxHeight(500);

        VBox categoriesContainer = new VBox(20);
        categoriesContainer.setPadding(new Insets(10,0,5,0));

        // TODO: replace with real categories
        String[] categories = {"Food & Dining", "Transportation", "Shopping", "Entertainment", "Utilities"};
        double[] amountsSpent = {200, 150, 100, 80, 120}; 
        double[] totalAmounts = {500, 500, 300, 200, 400};

        for (int i = 0; i < categories.length; i++) {
            categoriesContainer.getChildren().add(createCategoryRow(categories[i], amountsSpent[i], totalAmounts[i]));
        }

        scrollPane.setContent(categoriesContainer);
        box.getChildren().addAll(titleBox, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return box;
    }

    private VBox createCategoryRow(String categoryName, double amountSpent, double totalAmount) {
        VBox row = new VBox();
        row.setAlignment(Pos.CENTER);

        HBox nameBox = new HBox();

        Label nameLabel = new Label(categoryName);
        nameLabel.getStyleClass().add("category-title");
        
        Label amountLabel = new Label("$" + amountSpent);
        amountLabel.getStyleClass().add("category-value");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        nameBox.getChildren().addAll(nameLabel, spacer, amountLabel);

        HBox progressBox = new HBox();

        // calculate percentage
        double percentage = (amountSpent / totalAmount) * 100;

        ProgressBar progressBar = new ProgressBar(percentage / 100);
        progressBar.setPrefWidth(300);
        progressBar.setMaxWidth(600);
        progressBar.setMaxHeight(20); 
        progressBar.getStyleClass().add("progress-bar");

        HBox.setHgrow(progressBar, Priority.ALWAYS);

        Label percentageLabel = new Label(String.format("%.1f%%", percentage));
        percentageLabel.getStyleClass().add("report-category-percentage");

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        progressBox.getChildren().addAll(progressBar, spacer2, percentageLabel);
        
        row.getChildren().addAll(nameBox, progressBox);
        return row;
    }

    private VBox createPeriodTrendWidget() {
        VBox box = new VBox(16);
        box.setPadding(new Insets(16));

        box.getStyleClass().add("left-widget-container");
        box.setMaxHeight(500);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        SVGPath expenseCateGoriesIcon = new SVGPath();
        expenseCateGoriesIcon.setContent("M3,14L3.5,14.07L8.07,9.5C7.89,8.85 8.06,8.11 8.59,7.59C9.37,6.8 10.63,6.8 11.41,7.59C11.94,8.11 12.11,8.85 11.93,9.5L14.5,12.07L15,12C15.18,12 15.35,12 15.5,12.07L19.07,8.5C19,8.35 19,8.18 19,8A2,2 0 0,1 21,6A2,2 0 0,1 23,8A2,2 0 0,1 21,10C20.82,10 20.65,10 20.5,9.93L16.93,13.5C17,13.65 17,13.82 17,14A2,2 0 0,1 15,16A2,2 0 0,1 13,14L13.07,13.5L10.5,10.93C10.18,11 9.82,11 9.5,10.93L4.93,15.5L5,16A2,2 0 0,1 3,18A2,2 0 0,1 1,16A2,2 0 0,1 3,14Z");
        expenseCateGoriesIcon.setFill(Color.valueOf("#000000"));
        expenseCateGoriesIcon.setScaleX(1.1);
        expenseCateGoriesIcon.setScaleY(1.1);

        Label titleLabel = new Label("Monthly Trend");
        titleLabel.getStyleClass().add("widget-title");

        header.getChildren().addAll(expenseCateGoriesIcon, titleLabel);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("recent-activity-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setMaxHeight(500);

        VBox contentContainer = new VBox(15);
        contentContainer.setPadding(new Insets(10,0,5,0));

        // Header Row
        GridPane headerRow = new GridPane();
        headerRow.getStyleClass().add("header-row");
        headerRow.setHgap(30);
        headerRow.setVgap(10); 
        headerRow.setAlignment(Pos.CENTER);

        // create labels for each column
        Label monthLabel = new Label("Month");
        monthLabel.getStyleClass().add("table-label");
        
        Label incomeLabel = new Label("Income");
        incomeLabel.getStyleClass().add("table-label");
        
        Label expensesLabel = new Label("Expenses");
        expensesLabel.getStyleClass().add("table-label");
        
        Label savingsLabel = new Label("Savings");
        savingsLabel.getStyleClass().add("table-label");

        // add labels to the grid
        headerRow.add(monthLabel, 0, 0);
        headerRow.add(incomeLabel, 1, 0); 
        headerRow.add(expensesLabel, 2, 0);
        headerRow.add(savingsLabel, 3, 0); 

        // set column constraints for even spacing
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        headerRow.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        headerRow.getColumnConstraints().add(col2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        headerRow.getColumnConstraints().add(col3);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        headerRow.getColumnConstraints().add(col4);

        // add the header row to the content container
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
        box.getChildren().addAll(header, scrollPane);

        return box;
    }

    private GridPane createTrendRow(String month, double income, double expenses, double savings) {
        GridPane row = new GridPane();
        row.getStyleClass().add("trend-row");
        row.setPadding(new Insets(10));
        row.setAlignment(Pos.CENTER);

        row.setHgap(30);
        row.setVgap(15);

        Label monthLabel = new Label(month);
        monthLabel.getStyleClass().add("month-label");
        row.add(monthLabel, 0, 0);

        Label incomeLabel = new Label("$" + income);
        incomeLabel.getStyleClass().add("income-label");
        row.add(incomeLabel, 1, 0);
        
        Label expensesLabel = new Label("$" + expenses);
        expensesLabel.getStyleClass().add("expenses-label");
        row.add(expensesLabel, 2, 0);
        
        Label savingsLabel = new Label("$" + savings);
        savingsLabel.getStyleClass().add("savings-label");
        row.add(savingsLabel, 3, 0);

        // Set constraints for consistent spacing
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            row.getColumnConstraints().add(col);
        }

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
