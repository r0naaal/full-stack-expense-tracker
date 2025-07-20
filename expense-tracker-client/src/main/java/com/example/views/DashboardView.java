package com.example.views;

import com.example.controllers.DashboardController;
import com.example.utils.SidebarUtil;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class DashboardView {
    
    private Scene currentScene; 

    /* ----------------------- LAYOUT CONTAINERS ----------------------- */
    private BorderPane mainContainer;
    private VBox currentActiveSection;
    private String activeTab = "Dashboard";
    
    /* ----------------------- SIDEBAR AND TOP BAR ----------------------- */
    private VBox sidebar;
    private HBox topBar;

    /* ----------------------- DASHBOARD WIDGETS ----------------------- */
    private VBox recentActivityWidget;
    private VBox monthlyOverviewWidget;
    private VBox quickActionsWidget;
    private VBox goalsWidget;
    
    private boolean sidebarExpanded = false;
    private VBox currentSidebar;
    private HBox logoSection;
    private Label toggleIcon;
    private VBox currentSidebarNavigation;
    private VBox currentFooter;
    
    private Button themePickerBtn;

    private Button dashboardBtn;

    private Button categoriesBtn;

    private Button reportsBtn;

    private Button goalsBtn;

    private Button loginViewBtn;

    
    public void show(String theme) {
        currentScene = createScene(theme);
        
        // clear existing stylesheets
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass().getResource("/themes/" + theme + "Style.css").toExternalForm());

        new DashboardController(this);
        ViewNavigator.switchViews(currentScene);
    }

    public void switchTheme(String themeName) {
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(
            getClass().getResource("/styles/" + themeName + ".css").toExternalForm()
        );
    }
    
    /* ----------------------- MAIN SCENE CREATION ----------------------- */
    private Scene createScene(String theme) {
        // main container with sidebar layout
        mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        // create sidebar (left side)
        sidebar = createDashboardSidebar();
        sidebar.getStyleClass().add("default-sidebar");

        mainContainer.setLeft(sidebar);
        
        // Create main content area
        VBox contentArea = createContentArea();
        mainContainer.setCenter(contentArea);
        
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH);
    }
    
    /* ----------------------- DASHBOARD CONTENT AREA ----------------------- */
    private VBox createContentArea() {
        VBox contentArea = new VBox();
        contentArea.getStyleClass().add("content-area");
                
        // Top bar
        HBox topBar = createDashboardTopBar();
        
        // Main dashboard content
        ScrollPane mainContent = createMainContent();
        
        contentArea.getChildren().addAll(topBar, mainContent);
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        
        return contentArea;
    }
    
    /* ----------------------- DASHBOARD TOP BAR ----------------------- */
    private HBox createDashboardTopBar() {
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
        
        Label titleLabel = new Label("Dashboard");
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
    
    /* ----------------------- DASHBOARD SIDEBAR ----------------------- */
    private VBox createDashboardSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(sidebarExpanded ? 320 : 64);
        sidebar.setMinWidth(64);
        sidebar.setMaxWidth(320);
        currentSidebar = sidebar;
        
        HBox header = createSidebarHeader();
        VBox navigation = createSidebarNavigation();
        
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        VBox footer = createSidebarFooter();

        sidebar.getChildren().addAll(header, navigation, spacer, footer);
        return sidebar;
    }

     /* ----------------------- CREATE SIDEBAR HEADER ----------------------- */
    private HBox createSidebarHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setMinHeight(64);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("debug-border");
        
        logoSection = new HBox(12);
        logoSection.setAlignment(Pos.CENTER_LEFT);

        Label logoText = new Label("📜");
        logoText.getStyleClass().add("sidebar-logo-icon");

        Label appTitle = new Label("CozyTracker");
        appTitle.getStyleClass().add("sidebar-title");
        
        logoSection.getChildren().addAll(logoText, appTitle);
        logoSection.setVisible(sidebarExpanded);
        logoSection.setManaged(sidebarExpanded);
        
        Button toggleBtn = new Button();
        toggleBtn.setFocusTraversable(false);
        toggleBtn.getStyleClass().add("sidebar-toggle-button");

        toggleIcon = new Label(sidebarExpanded ? "<" : ">");
        toggleIcon.getStyleClass().add("sidebar-toggle-icon");
        toggleBtn.setGraphic(toggleIcon);
        
        toggleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                toggleSidebar();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(logoSection, spacer, toggleBtn);
        
        return header;
    }
    
    private VBox createSidebarNavigation() {
        VBox navigation = new VBox(8);
        navigation.getStyleClass().add("sidebar-navigation");
        navigation.setPadding(new Insets(10));
        navigation.setMinHeight(64);
        navigation.setAlignment(Pos.CENTER);

        // hide while the sidebar is not toggled
        navigation.setVisible(sidebarExpanded);
        navigation.setManaged(sidebarExpanded);
        currentSidebarNavigation = navigation;

        // Create buttons and assign them to instance variables
        dashboardBtn = createNavButtonWithSeparator("📊", "Dashboard");
        reportsBtn = createNavButtonWithSeparator("📈", "Reports");
        themePickerBtn = createNavButtonWithSeparator("🎨", "Theme Picker");
        themePickerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                toggleThemePicker();
            }
            
        });

        loginViewBtn = createNavButtonWithSeparator("@", "Login View");

        // Add buttons to navigation
        navigation.getChildren().addAll(
            dashboardBtn,
            reportsBtn,
            themePickerBtn,
            loginViewBtn
        );
        
        return navigation;
    }
    
    private void toggleThemePicker() {

    }

    private VBox createSidebarFooter() {
        VBox footer = new VBox();
        footer.getStyleClass().add("sidebar-footer");
        footer.setPadding(new Insets(16));
        footer.setAlignment(Pos.CENTER);
        currentFooter = footer;

        Label icon = new Label("⭐");
        icon.getStyleClass().add("footer-icon");
        footer.getChildren().add(icon);

        return footer;
    }
    
    private void toggleSidebar() {
        sidebarExpanded = !sidebarExpanded;
        double endWidth = sidebarExpanded ? 320 : 64;

        // animate the width of the sidebar
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(currentSidebar.prefWidthProperty(), endWidth);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(130), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        
        timeline.setOnFinished(e -> {
            toggleIcon.setText(sidebarExpanded ? "<" : ">");
            logoSection.setVisible(sidebarExpanded);
            logoSection.setManaged(sidebarExpanded);

            currentSidebarNavigation.setVisible(sidebarExpanded);
            currentSidebarNavigation.setManaged(sidebarExpanded);
        });

        // start animation
        timeline.play();
    }

    /* ----------------------- NAVIGATION BUTTON CREATION ----------------------- */
        private Button createNavButtonWithSeparator(String icon, String text) {
        VBox buttonContainer = new VBox();
        buttonContainer.getStyleClass().add("p-debug-border");
        buttonContainer.setPadding(new Insets(4));
        
        // create the button
        Button button = createNavButton(icon, text);
        
        // add button to the container
        buttonContainer.getChildren().add(button);        
        return button;
    }


    private Button createNavButton(String icon, String text) {
        Button button = new Button();
        button.setFocusTraversable(false);
        
        button.getStyleClass().add("sidebar-nav-button");
        
        HBox content = new HBox(8);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(4,4,4,4));
        
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("sidebar-nav-button-text");
        
        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("sidebar-nav-button-text");
        
        content.getChildren().addAll(iconLabel, textLabel);
        button.setGraphic(content);
        button.setMaxWidth(Double.MAX_VALUE);
        
        return button;
    }
    
    /* ----------------------- MAIN DASHBOARD CONTENT ----------------------- */
    private ScrollPane createMainContent() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("dashboard-content-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        VBox content = new VBox(24);
        content.getStyleClass().add("b-debug-border");
        content.setPadding(new Insets(24));
        content.setMaxWidth(Double.MAX_VALUE);
        
        // Welcome section
        VBox welcomeSection = createWelcomeSection();
        
        // Main dashboard grid
        GridPane dashboardGrid = createDashboardGrid();
        
        content.getChildren().addAll(welcomeSection, dashboardGrid);
        scrollPane.setContent(content);
        
        return scrollPane;
    }
    
    /* ----------------------- WELCOME SECTION ----------------------- */
    private VBox createWelcomeSection() {
        VBox welcomeSection = new VBox(20);
        welcomeSection.getStyleClass().add("p-debug-border");
        // TODO: add cozy welcome section style
        welcomeSection.setPadding(new Insets(24));
        
        // Header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        // TODO: add styling for header

        Label sparkle = new Label("✨");
        sparkle.getStyleClass().add("welcome-icon");
        
        Label title = new Label("Welcome to CozyTracker");
        title.getStyleClass().add("welcome-title");
        
        header.getChildren().addAll(sparkle, title);
        
        // Description
        Label description = new Label(
            "Your personal expense tracking companion. Manage your budget, track spending, and achieve your financial goals in style."
        );
        description.getStyleClass().add("welcome-description");
        description.setWrapText(true);
        
        // Quick actions grid
        GridPane quickActions = createQuickActionsGrid();

        // TODO: create tracking labels

        welcomeSection.getChildren().addAll(header, description, quickActions);
        return welcomeSection;
    }
    
    /* ----------------------- QUICK ACTIONS GRID ----------------------- */
    private GridPane createQuickActionsGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("b-debug-border");
        // TODO: add cozy quick actions style
        grid.setHgap(16);
        grid.setVgap(16);
        
        Button addExpenseBtn = createQuickActionButton("💵", "Add Expense");
        Button setBudgetBtn = createQuickActionButton("🎯", "Set Budget");
        Button viewReportsBtn = createQuickActionButton("📊", "View Reports");
        Button goalsBtn = createQuickActionButton("🏆", "Goals");
        
        grid.add(addExpenseBtn, 0, 0);
        grid.add(setBudgetBtn, 1, 0);
        grid.add(viewReportsBtn, 2, 0);
        grid.add(goalsBtn, 3, 0);
        
        // Make columns equal width - MANUAL VERSION
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        return grid;
    }

    /* ----------------------- QUICK ACTION BUTTON ----------------------- */
    private Button createQuickActionButton(String icon, String text) {
        Button button = new Button();
        button.getStyleClass().add("g-debug-border");
        // TODO: add cozy quick action button style
        button.setPrefHeight(80);
        button.setMaxWidth(Double.MAX_VALUE);

        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("quick-action-icon");

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("quick-action-text");

        content.getChildren().addAll(iconLabel, textLabel);
        button.setGraphic(content);

        return button;
    }
    
    /* ----------------------- DASHBOARD GRID ----------------------- */
    private GridPane createDashboardGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("o-debug-border");
        // TODO: add cozy dashboard grid style
        grid.setHgap(24);
        grid.setVgap(24);
        
        // Create widgets
        recentActivityWidget = createRecentActivityWidget();
        monthlyOverviewWidget = createMonthlyOverviewWidget();
        
        grid.add(recentActivityWidget, 0, 0);
        grid.add(monthlyOverviewWidget, 1, 0);
        
        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);
        
        return grid;
    }
    
    /* ----------------------- RECENT ACTIVITY WIDGET ----------------------- */
    private VBox createRecentActivityWidget() {
        VBox widget = new VBox(16);
        widget.getStyleClass().add("debug-border");
        widget.setPadding(new Insets(24));
        
        // Widget header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label icon = new Label("📊");
        icon.getStyleClass().add("widget-icon");
        
        Label title = new Label("Recent Activity");
        title.getStyleClass().add("widget-title");
        
        header.getChildren().addAll(icon, title);
        
        // Activity items
        VBox activities = new VBox(12);
        
        String[][] activityData = {
            {"Current Budget", "active", "🟢"},
            {"Monthly Report", "running", "🔵"},
            {"Categories", "idle", "⚪"},
            {"Recurring Bills", "idle", "⚪"}
        };
        
        for (String[] activity : activityData) {
            HBox item = createActivityItem(activity[0], activity[1], activity[2]);
            activities.getChildren().add(item);
        }
        
        widget.getChildren().addAll(header, activities);
        return widget;
    }
    
    /* ----------------------- ACTIVITY ITEM ----------------------- */
    private HBox createActivityItem(String name, String status, String indicator) {
        HBox item = new HBox(12);
        item.getStyleClass().add("activity-item");
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(12));
        
        HBox nameSection = new HBox(8);
        nameSection.setAlignment(Pos.CENTER_LEFT);
        
        Label indicatorLabel = new Label(indicator);
        indicatorLabel.getStyleClass().add("activity-indicator");
        
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("activity-name");
        
        nameSection.getChildren().addAll(indicatorLabel, nameLabel);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label statusLabel = new Label(status);
        statusLabel.getStyleClass().add("activity-status");
        statusLabel.getStyleClass().add("status-" + status);
        
        item.getChildren().addAll(nameSection, spacer, statusLabel);
        
        // Hover effect
        item.setOnMouseEntered(e -> item.getStyleClass().add("activity-item-hover"));
        item.setOnMouseExited(e -> item.getStyleClass().remove("activity-item-hover"));
        
        return item;
    }
    
    /* ----------------------- MONTHLY OVERVIEW WIDGET ----------------------- */
    private VBox createMonthlyOverviewWidget() {
        VBox widget = new VBox(16);
        widget.getStyleClass().add("debug-border");
        widget.setPadding(new Insets(24));
        
        // Widget header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label icon = new Label("📈");
        icon.getStyleClass().add("widget-icon");
        
        Label title = new Label("Monthly Overview");
        title.getStyleClass().add("widget-title");
        
        header.getChildren().addAll(icon, title);
        
        // Overview items
        VBox overview = new VBox(16);
        
        // Spent this month
        HBox spentRow = createOverviewRow("Spent This Month", "$1,247.50", false);
        HBox budgetRow = createOverviewRow("Budget Remaining", "$752.50", false);
        
        // Savings goal with progress
        VBox goalSection = new VBox(8);
        HBox goalHeader = createOverviewRow("Savings Goal", "78% 🔥", false);
        
        // Progress bar
        ProgressBar progressBar = new ProgressBar(0.78);
        progressBar.getStyleClass().add("savings-progress-bar");
        progressBar.setPrefHeight(8);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        
        goalSection.getChildren().addAll(goalHeader, progressBar);
        
        // Encouragement message
        HBox encouragement = new HBox(8);
        encouragement.setAlignment(Pos.CENTER_LEFT);
        
        Label message = new Label("Great job staying on budget!");
        message.getStyleClass().add("encouragement-text");
        
        Label emoji = new Label("💪");
        emoji.getStyleClass().add("encouragement-emoji");
        
        encouragement.getChildren().addAll(message, emoji);
        
        overview.getChildren().addAll(spentRow, budgetRow, goalSection, encouragement);
        
        widget.getChildren().addAll(header, overview);
        return widget;
    }
    
    /* ----------------------- OVERVIEW ROW ----------------------- */
    private HBox createOverviewRow(String label, String value, boolean highlight) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        
        Label labelText = new Label(label);
        labelText.getStyleClass().add("overview-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label valueText = new Label(value);
        valueText.getStyleClass().add("overview-value");
        if (highlight) valueText.getStyleClass().add("overview-value-highlight");
        
        row.getChildren().addAll(labelText, spacer, valueText);
        return row;
    }
    
    /* ----------------------- TAB SWITCHING ----------------------- */
    private void switchDashboardTab(String tabName) {
        activeTab = tabName;
        // Here you would implement tab switching logic
        System.out.println("Switched to tab: " + tabName);
    }
    
    /* ----------------------- GETTERS FOR CONTROLLER ----------------------- */
    public String getActiveTab() { return activeTab; }
    public VBox getRecentActivityWidget() { return recentActivityWidget; }
    public VBox getMonthlyOverviewWidget() { return monthlyOverviewWidget; }

    public Button getDashboardButton() { return dashboardBtn; }
    public Button getReportsButton() { return reportsBtn; }
    public Button getGoalsButton() { return goalsBtn; }
    public Button getLoginViewButton() { return loginViewBtn; }
}
