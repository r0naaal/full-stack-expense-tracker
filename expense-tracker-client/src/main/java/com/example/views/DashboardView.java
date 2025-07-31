package com.example.views;

import com.example.controllers.DashboardController;
import com.example.utils.SidebarUtil;
import com.example.utils.ThemeManager;
import com.example.utils.ViewNavigator;
import com.example.utils.SidebarUtil.SidebarType;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class DashboardView implements ThemeManager.ThemeChangeListener {
    
    private Scene currentScene; 

    /* ----------------------- LAYOUT CONTAINERS ----------------------- */
    
    private BorderPane mainContainer;
    private VBox currentActiveSection;
    private String activeTab = "Dashboard";
    
    /* ----------------------- SIDEBAR ----------------------- */

    private VBox sidebar;
    
    /* ----------------------- DASHBOARD WIDGETS ----------------------- */

    private VBox recentActivityWidget;
    private VBox monthlyOverviewWidget;
    private VBox quickActionsWidget;
    private VBox goalsWidget;
    
    /* ----------------------- NAVIGATION ----------------------- */
    private Button dashboardBtn;
    private Button reportsBtn;
    private Button goalsBtn;
    private Button loginViewBtn;

    /* ----------------------- TRACKING DATA FIELDS ----------------------- */
    // Total Balance tracking
    private double totalBalance = 12480.50;
    private double totalBalancePercentage = 12.5;
    private boolean totalBalanceIsPositive = true;
    
    // Monthly Spending tracking
    private double monthlySpending = 2847.30;
    private double monthlySpendingPercentage = -8.2;
    private boolean monthlySpendingIsPositive = false;
    
    // Savings Goal tracking
    private double savingsGoalPercentage = 78.0;
    private double savingsGoalChange = 5.1;
    private boolean savingsGoalChangeIsPositive = true;
    private double savingsGoalTarget = 10000.0; // Target amount for savings
    private double currentSavings = 7800.0; // Current savings amount
    
    // Active Categories tracking
    private int activeCategoriesCount = 12;
    private int activeCategoriesChange = 2;
    private boolean activeCategoriesChangeIsPositive = true;
    
    // Additional financial data
    private double budgetRemaining = 752.50;
    private double monthlyBudget = 2600.00;
    private String encouragementMessage = "Great job staying on budget!";
    
    /* ----------------------- TRACKING DATA LABELS (UI REFERENCES) ----------------------- */
    private Label totalBalanceValueLabel;
    private Label totalBalancePercentageLabel;
    private Label monthlySpendingValueLabel;
    private Label monthlySpendingPercentageLabel;
    private Label savingsGoalValueLabel;
    private Label savingsGoalPercentageLabel;
    private Label activeCategoriesValueLabel;
    private Label activeCategoriesChangeLabel;
    private ProgressBar savingsProgressBar;
    private Label budgetRemainingLabel;
    private Label encouragementLabel;

    
    public void show(String theme) {
        // register this view as the active listener
        ThemeManager.getInstance().setActiveListener(this);

        // use current theme from ThemeManager if no specific theme provided
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
                        System.out.println("Already on dashboard");
                        break;
                    case "reports":
                        // switch to reports view
                        ReportsView reportsView = new ReportsView();
                        reportsView.show();
                        break;
                    case "goals":
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

        new DashboardController(this);
        ViewNavigator.switchViews(currentScene);
        
        // reset sidebar theme picker state when showing the view
        SidebarUtil.resetThemePickerState();
    }
    
    /* ----------------------- MAIN SCENE CREATION ----------------------- */
    private Scene createScene(String theme) {
        // main container with sidebar layout
        mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        // create sidebar (left side)
        sidebar = SidebarUtil.createSidebar(SidebarType.DASHBOARD_VIEW);
        mainContainer.setLeft(sidebar);
        
        // Create main content area
        VBox contentArea = createContentArea();
        mainContainer.setCenter(contentArea);
        
        return new Scene(mainContainer);
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
    
     /* ----------------------- MAIN DASHBOARD CONTENT ----------------------- */
    private ScrollPane createMainContent() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("dashboard-content-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(0);

        VBox content = new VBox(24);
        content.getStyleClass().add("content-container-vbox");
        content.setPadding(new Insets(24));
        content.setMaxWidth(Double.MAX_VALUE);
        
        // welcome section
        VBox welcomeSection = createWelcomeSection();
        
        // tracking labels grid
        GridPane trackingLabels = createTrackingLabelsGrid();

        // main dashboard grid
        GridPane dashboardGrid = createDashboardGrid();
        
        content.getChildren().addAll(welcomeSection, trackingLabels, dashboardGrid);
        scrollPane.setContent(content);
        
        return scrollPane;
    }
    
    /* ----------------------- WELCOME SECTION ----------------------- */
    private VBox createWelcomeSection() {
        VBox welcomeSection = new VBox(20);
        welcomeSection.getStyleClass().add("dashboard-welcome-section");
        welcomeSection.setMaxWidth(1100);
        welcomeSection.setPadding(new Insets(24));
        
        // Header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        SVGPath logoIcon = new SVGPath();
        logoIcon.setContent("M15 10C15 9.45 15.45 9 16 9C16.55 9 17 9.45 17 10S16.55 11 16 11 15 10.55 15 10M22 7.5V14.47L19.18 15.41L17.5 21H12V19H10V21H4.5C4.5 21 2 12.54 2 9.5S4.46 4 7.5 4H12.5C13.41 2.79 14.86 2 16.5 2C17.33 2 18 2.67 18 3.5C18 3.71 17.96 3.9 17.88 4.08C17.74 4.42 17.62 4.81 17.56 5.23L19.83 7.5H22M20 9.5H19L15.5 6C15.5 5.35 15.59 4.71 15.76 4.09C14.79 4.34 14 5.06 13.67 6H7.5C5.57 6 4 7.57 4 9.5C4 11.38 5.22 16.15 6 19H8V17H14V19H16L17.56 13.85L20 13.03V9.5Z");
        logoIcon.setFill(Color.valueOf("#000000"));
        logoIcon.setScaleX(1.7);
        logoIcon.setScaleY(1.7);

        Label title = new Label("Welcome to Your Dashboard");
        title.setPadding(new Insets(0, 0, 0, 8));
        title.getStyleClass().add("dashboard-welcome-title");
        
        header.getChildren().addAll(logoIcon, title);
        
        // separator line
        Region separator = new Region();
        separator.getStyleClass().add("welcome-separator");
        separator.setPrefHeight(2);

        // Description
        Label description = new Label("Here you can track your expenses, set budgets, and monitor your financial goals. Use the quick actions below to get started.");
        description.getStyleClass().add("welcome-description");
        description.setWrapText(true);
        
        // Quick actions grid
        GridPane quickActions = createQuickActionsGrid();
        quickActions.setAlignment(Pos.CENTER);

        welcomeSection.getChildren().addAll(header,separator, description, quickActions);
        return welcomeSection;
    }

    /* ----------------------- TRACKING LABELS GRID ----------------------- */
    private static GridPane createTrackingLabelsGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20); 
        grid.setVgap(20);
        grid.setMaxWidth(1100);

        SVGPath totalBalanceIcon = new SVGPath();
        totalBalanceIcon.setContent("M7,15H9C9,16.08 10.37,17 12,17C13.63,17 15,16.08 15,15C15,13.9 13.96,13.5 11.76,12.97C9.64,12.44 7,11.78 7,9C7,7.21 8.47,5.69 10.5,5.18V3H13.5V5.18C15.53,5.69 17,7.21 17,9H15C15,7.92 13.63,7 12,7C10.37,7 9,7.92 9,9C9,10.1 10.04,10.5 12.24,11.03C14.36,11.56 17,12.22 17,15C17,16.79 15.53,18.31 13.5,18.82V21H10.5V18.82C8.47,18.31 7,16.79 7,15Z");

        SVGPath monthlySpendingIcon = new SVGPath();
        monthlySpendingIcon.setContent("M5,3C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V16.72C21.59,16.37 22,15.74 22,15V9C22,8.26 21.59,7.63 21,7.28V5A2,2 0 0,0 19,3H5M5,5H19V7H13A2,2 0 0,0 11,9V15A2,2 0 0,0 13,17H19V19H5V5M13,9H20V15H13V9M16,10.5A1.5,1.5 0 0,0 14.5,12A1.5,1.5 0 0,0 16,13.5A1.5,1.5 0 0,0 17.5,12A1.5,1.5 0 0,0 16,10.5Z");

        SVGPath savingsGoalIcon = new SVGPath();
        savingsGoalIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,10.84 21.79,9.69 21.39,8.61L19.79,10.21C19.93,10.8 20,11.4 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4C12.6,4 13.2,4.07 13.79,4.21L15.4,2.6C14.31,2.21 13.16,2 12,2M19,2L15,6V7.5L12.45,10.05C12.3,10 12.15,10 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12C14,11.85 14,11.7 13.95,11.55L16.5,9H18L22,5H19V2M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12H16A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8V6Z");

        SVGPath activeCategoriesIcon = new SVGPath();
        activeCategoriesIcon.setContent("M6.5 10C7.3 10 8 9.3 8 8.5S7.3 7 6.5 7 5 7.7 5 8.5 5.7 10 6.5 10M9 6L16 13L11 18L4 11V6H9M9 4H4C2.9 4 2 4.9 2 6V11C2 11.6 2.2 12.1 2.6 12.4L9.6 19.4C9.9 19.8 10.4 20 11 20S12.1 19.8 12.4 19.4L17.4 14.4C17.8 14 18 13.5 18 13C18 12.4 17.8 11.9 17.4 11.6L10.4 4.6C10.1 4.2 9.6 4 9 4M13.5 5.7L14.5 4.7L21.4 11.6C21.8 12 22 12.5 22 13S21.8 14.1 21.4 14.4L16 19.8L15 18.8L20.7 13L13.5 5.7Z");


        // create the four tracking boxes
        VBox totalBalanceBox = createTrackingBox(totalBalanceIcon, "+12.5%", "$12,480.50", "Total Balance");
        VBox monthlySpendingBox = createTrackingBox(monthlySpendingIcon, "-8.2%", "$2,847.30", "Monthly Spending");
        VBox savingsGoalBox = createTrackingBox(savingsGoalIcon, "+5.1%", "78%", "Savings Goal");
        VBox activeCategoriesBox = createTrackingBox(activeCategoriesIcon, "+2", "12", "Active Categories");

        // add boxes to grid
        grid.add(totalBalanceBox, 0, 0);
        grid.add(monthlySpendingBox, 1, 0);
        grid.add(savingsGoalBox, 2, 0);
        grid.add(activeCategoriesBox, 3, 0);

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
        box.getStyleClass().add("tracking-box");
        box.setPadding(new Insets(16));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#000000b6"));
        clonIcon.setScaleX(0.9);
        clonIcon.setScaleY(0.9);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label percentageLabel = new Label(percentage);
        percentageLabel.getStyleClass().add("tracking-percentage");

        // add specific styling based on positive/negative
        if (percentage.startsWith("+")) {
            percentageLabel.getStyleClass().add("tracking-percentage-positive");
        } else if (percentage.startsWith("-")) {
            percentageLabel.getStyleClass().add("tracking-percentage-negative");
        } else {
            percentageLabel.getStyleClass().add("tracking-percentage-neutral");
        }

        topRow.getChildren().addAll(clonIcon, spacer, percentageLabel);

        // middle row: main value
        HBox valueRow = new HBox();
        valueRow.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("tracking-value");

        valueRow.getChildren().add(valueLabel);

        // bottom row: description label
        HBox labelRow = new HBox();
        labelRow.setAlignment(Pos.CENTER_LEFT);

        Label descLabel = new Label(label);
        descLabel.getStyleClass().add("tracking-label");

        labelRow.getChildren().add(descLabel);

        box.getChildren().addAll(topRow, valueRow, labelRow);
        return box;
    }
    
    /* ----------------------- QUICK ACTIONS GRID ----------------------- */
    private GridPane createQuickActionsGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);

        SVGPath addExpenseIcon = new SVGPath();
        addExpenseIcon.setContent("M15 15V17H18V20H20V17H23V15H20V12H18V15M14.97 11.61C14.85 10.28 13.59 8.97 12 9C10.3 9.03 9 10.3 9 12C9 13.7 10.3 14.94 12 15C12.38 15 12.77 14.92 13.14 14.77C13.41 13.67 13.86 12.63 14.97 11.61M13 16H7C7 14.9 6.11 14 5 14V10C6.11 10 7 9.11 7 8H17C17 9.11 17.9 10 19 10V10.06C19.67 10.06 20.34 10.18 21 10.4V6H3V18H13.32C13.1 17.33 13 16.66 13 16Z");

        SVGPath setBudgetIcon = new SVGPath();
        setBudgetIcon.setContent("M3 0V3H0V5H3V8H5V5H8V3H5V0H3M10 3V5H19V7H13C11.9 7 11 7.9 11 9V15C11 16.1 11.9 17 13 17H19V19H5V10H3V19C3 20.1 3.89 21 5 21H19C20.1 21 21 20.1 21 19V16.72C21.59 16.37 22 15.74 22 15V9C22 8.26 21.59 7.63 21 7.28V5C21 3.9 20.1 3 19 3H10M13 9H20V15H13V9M16 10.5A1.5 1.5 0 0 0 14.5 12A1.5 1.5 0 0 0 16 13.5A1.5 1.5 0 0 0 17.5 12A1.5 1.5 0 0 0 16 10.5Z");

        SVGPath viewReportsIcon = new SVGPath();
        viewReportsIcon.setContent("M16,11.78L20.24,4.45L21.97,5.45L16.74,14.5L10.23,10.75L5.46,19H22V21H2V3H4V17.54L9.5,8L16,11.78Z");

        SVGPath goalsIcon = new SVGPath();
        goalsIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,4A8,8 0 0,1 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12A6,6 0 0,0 12,6M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10Z");

        SVGPath activeCategoriesIcon = new SVGPath();
        activeCategoriesIcon.setContent("M6.5 10C7.3 10 8 9.3 8 8.5S7.3 7 6.5 7 5 7.7 5 8.5 5.7 10 6.5 10M9 6L16 13L11 18L4 11V6H9M9 4H4C2.9 4 2 4.9 2 6V11C2 11.6 2.2 12.1 2.6 12.4L9.6 19.4C9.9 19.8 10.4 20 11 20S12.1 19.8 12.4 19.4L17.4 14.4C17.8 14 18 13.5 18 13C18 12.4 17.8 11.9 17.4 11.6L10.4 4.6C10.1 4.2 9.6 4 9 4M13.5 5.7L14.5 4.7L21.4 11.6C21.8 12 22 12.5 22 13S21.8 14.1 21.4 14.4L16 19.8L15 18.8L20.7 13L13.5 5.7Z");

        Button addExpenseBtn = createQuickActionButton(addExpenseIcon, "Add Expense");
        addExpenseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Add Expense button clicked");
                // TODO: Implement add expense logic
            }
        });

        Button setBudgetBtn = createQuickActionButton(setBudgetIcon, "Set Budget");
        setBudgetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Set Budget button clicked");
                // TODO: Implement set budget logic
            }
        });

        Button viewReportsBtn = createQuickActionButton(viewReportsIcon, "View Reports");
        viewReportsBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("View Reports button clicked");
                // TODO: Implement view reports logic
            }
        });

        Button goalsBtn = createQuickActionButton(goalsIcon, "Goals");
        goalsBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Goals button clicked");
                // TODO: Implement goals logic
            }
        });

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
        col4.setPrefWidth(0);
        col4.setHgrow(Priority.ALWAYS); 
        
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        return grid;
    }

    /* ----------------------- QUICK ACTION BUTTON ----------------------- */
    private Button createQuickActionButton(SVGPath icon, String text) {
        Button button = new Button();
        button.setFocusTraversable(false);
        button.getStyleClass().add("quick-action-button");
        //System.out.println("Styles for quickactionbutton: " + button.getStyle());
        button.setPrefHeight(80);
        button.setPrefWidth(250);

        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER);

        SVGPath iconClone = new SVGPath();
        iconClone.setContent(icon.getContent());
        iconClone.setFill(Color.valueOf("#000000b6"));
        iconClone.setScaleX(1.1);
        iconClone.setScaleY(1.1);

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("quick-action-button-text");

        content.getChildren().addAll(iconClone, textLabel);
        button.setGraphic(content);

        return button;
    }
    
    /* ----------------------- DASHBOARD GRID ----------------------- */
    private GridPane createDashboardGrid() {
        GridPane grid = new GridPane();
        grid.setMaxWidth(1100);
        grid.setHgap(24);
        grid.setVgap(24);
        
        // create widgets
        recentActivityWidget = createRecentActivityWidget();
        monthlyOverviewWidget = createMonthlyOverviewWidget();
        
        grid.add(recentActivityWidget, 0, 0);
        grid.add(monthlyOverviewWidget, 1, 0);
        
        // column constraints
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
        widget.getStyleClass().add("left-widget-container");

        widget.setPadding(new Insets(16));
        widget.setPrefWidth(500);
        
        // Widget header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        
        SVGPath icon = new SVGPath();
        icon.setContent("M3,14L3.5,14.07L8.07,9.5C7.89,8.85 8.06,8.11 8.59,7.59C9.37,6.8 10.63,6.8 11.41,7.59C11.94,8.11 12.11,8.85 11.93,9.5L14.5,12.07L15,12C15.18,12 15.35,12 15.5,12.07L19.07,8.5C19,8.35 19,8.18 19,8A2,2 0 0,1 21,6A2,2 0 0,1 23,8A2,2 0 0,1 21,10C20.82,10 20.65,10 20.5,9.93L16.93,13.5C17,13.65 17,13.82 17,14A2,2 0 0,1 15,16A2,2 0 0,1 13,14L13.07,13.5L10.5,10.93C10.18,11 9.82,11 9.5,10.93L4.93,15.5L5,16A2,2 0 0,1 3,18A2,2 0 0,1 1,16A2,2 0 0,1 3,14Z");
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);
        icon.setFill(Color.valueOf("#000000"));

        Label title = new Label("Recent Activity");
        title.getStyleClass().add("widget-title");
        
        header.getChildren().addAll(icon, title);
        
        // create scrollable content area
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("recent-activity-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefHeight(280);
        scrollPane.setMaxHeight(300);

        // transaction container
        VBox transactionContainer = new VBox(8);
        transactionContainer.setPadding(new Insets(5,0,5,0));
        
        // TODO: replace with real data
        Object[][] transactions = {
            {"M2,21V19H20V21H2M20,8V5H18V8H20M20,3A2,2 0 0,1 22,5V8A2,2 0 0,1 20,10H18V13A4,4 0 0,1 14,17H8A4,4 0 0,1 4,13V3H20M16,5H6V13A2,2 0 0,0 8,15H14A2,2 0 0,0 16,13V5Z", "Coffee Shop", "Food", "Today", -5.40},
            {"M18.36 9L18.96 12H5.04L5.64 9H18.36M20 4H4V6H20V4M20 7H4L3 12V14H4V20H14V14H18V20H20V14H21V12L20 7M6 18V14H12V18H6Z", "Grocery Store", "Food", "Yesterday", -45.20},
            {"M5,6H23V18H5V6M14,9A3,3 0 0,1 17,12A3,3 0 0,1 14,15A3,3 0 0,1 11,12A3,3 0 0,1 14,9M9,8A2,2 0 0,1 7,10V14A2,2 0 0,1 9,16H19A2,2 0 0,1 21,14V10A2,2 0 0,1 19,8H9M1,10H3V20H19V22H1V10Z", "Salary Deposit", "Income", "2 days ago", 2500.00},
            {"M18,10A1,1 0 0,1 17,9A1,1 0 0,1 18,8A1,1 0 0,1 19,9A1,1 0 0,1 18,10M12,10H6V5H12M19.77,7.23L19.78,7.22L16.06,3.5L15,4.56L17.11,6.67C16.17,7 15.5,7.93 15.5,9A2.5,2.5 0 0,0 18,11.5C18.36,11.5 18.69,11.42 19,11.29V18.5A1,1 0 0,1 18,19.5A1,1 0 0,1 17,18.5V14C17,12.89 16.1,12 15,12H14V5C14,3.89 13.1,3 12,3H6C4.89,3 4,3.89 4,5V21H14V13.5H15.5V18.5A2.5,2.5 0 0,0 18,21A2.5,2.5 0 0,0 20.5,18.5V9C20.5,8.31 20.22,7.68 19.77,7.23Z", "Gas Station", "Transport", "3 days ago", -35.80},
            {"M20.84 2.18L16.91 2.96L19.65 6.5L21.62 6.1L20.84 2.18M13.97 3.54L12 3.93L14.75 7.46L16.71 7.07L13.97 3.54M9.07 4.5L7.1 4.91L9.85 8.44L11.81 8.05L9.07 4.5M4.16 5.5L3.18 5.69A2 2 0 0 0 1.61 8.04L2 10L6.9 9.03L4.16 5.5M2 10V20C2 21.11 2.9 22 4 22H20C21.11 22 22 21.11 22 20V10H2Z", "Movie Theater", "Entertainment", "4 days ago", -12.50},
            {"M1 22C1 22.54 1.45 23 2 23H15C15.56 23 16 22.54 16 22V21H1V22M8.5 9C4.75 9 1 11 1 15H16C16 11 12.25 9 8.5 9M3.62 13C4.73 11.45 7.09 11 8.5 11S12.27 11.45 13.38 13H3.62M1 17H16V19H1V17M18 5V1H16V5H11L11.23 7H20.79L19.39 21H18V23H19.72C20.56 23 21.25 22.35 21.35 21.53L23 5H18Z", "Pizza Place", "Food", "5 days ago", -18.90},
            {"M19.5 3.5L18 2L16.5 3.5L15 2L13.5 3.5L12 2L10.5 3.5L9 2L7.5 3.5L6 2L4.5 3.5L3 2V22L4.5 20.5L6 22L7.5 20.5L9 22L10.5 20.5L12 22L13.5 20.5L15 22L16.5 20.5L18 22L19.5 20.5L21 22V2L19.5 3.5M19 19H5V5H19V19M6 15H18V17H6M6 11H18V13H6M6 7H18V9H6V7Z", "Phone Bill", "Utilities", "1 week ago", -89.99}
        };

        // create transaction items
        for (Object[] transaction : transactions) {

            // create svgpath before passing the path object
            SVGPath categoryIcon = new SVGPath();
            categoryIcon.setContent((String) transaction[0]);

            Button transactionItem = createTransactionItem(
                categoryIcon,
                (String) transaction[1], // title
                (String) transaction[2], // category
                (String) transaction[3], // date
                (double) transaction[4] // amount
            );
            transactionContainer.getChildren().add(transactionItem);
        }
        
        scrollPane.setContent(transactionContainer);

        // see more button
        Button seeMoreButton = new Button("See More Transactions");
        seeMoreButton.getStyleClass().add("see-more-button");
        seeMoreButton.setPrefWidth(Double.MAX_VALUE);
        seeMoreButton.setPadding(new Insets(12));
        seeMoreButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: implement see more logic
                System.out.println("See More Transactions clicked");
            }
        });

        widget.getChildren().addAll(header, scrollPane, seeMoreButton);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        return widget;
    }
    
    /* ----------------------- TRANSACTION ITEM COMPONENT ----------------------- */
    private Button createTransactionItem(SVGPath icon, String title, String category, String date, double amount) {
        Button transactionBtn = new Button();
        transactionBtn.setFocusTraversable(false);
        transactionBtn.setPadding(new Insets(12, 16, 12, 16));
        transactionBtn.setPrefWidth(Double.MAX_VALUE);
        transactionBtn.setMaxWidth(Double.MAX_VALUE);
        transactionBtn.getStyleClass().add("transaction-item-button");

        // main container
        HBox mainContainer = new HBox(12);
        mainContainer.setAlignment(Pos.CENTER_LEFT);
        mainContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainContainer.setMaxWidth(Double.MAX_VALUE);

        // left side - category Icon
        VBox iconContainer = new VBox();
        iconContainer.setAlignment(Pos.CENTER);
        iconContainer.setPrefWidth(40);
        iconContainer.setMinWidth(40);
        iconContainer.setMaxWidth(40);

        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#000000b6"));
        clonIcon.setScaleX(1.5);
        clonIcon.setScaleY(1.5);

        iconContainer.getChildren().add(clonIcon);

        // ,iddle section - title and category/date
        VBox detailsContainer = new VBox(2);
        detailsContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(detailsContainer, Priority.ALWAYS);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("transaction-title");

        Label categoryDateLabel = new Label(category + " • " + date);
        categoryDateLabel.getStyleClass().add("transaction-category-date");

        detailsContainer.getChildren().addAll(titleLabel, categoryDateLabel);

        // right side - amount and action Buttons
        HBox rightContainer = new HBox(8);
        rightContainer.setAlignment(Pos.CENTER_RIGHT);

        // amount label
        Label amountLabel = new Label(String.format("%.2f", Math.abs(amount)));
        if (amount < 0) {
            amountLabel.setText("-$" + String.format("%.2f", Math.abs(amount)));
            amountLabel.getStyleClass().addAll("transaction-amount", "transaction-amount-negative");
            amountLabel.setStyle("-fx-text-fill: #d73a49; -fx-font-weight: bold;");
        } else {
            amountLabel.setText("+$" + String.format("%.2f", amount));
            amountLabel.getStyleClass().addAll("transaction-amount", "transaction-amount-positive");
            amountLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;"); 
        }

        // action buttons container (initially hidden)
        HBox actionButtons = new HBox(4);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);
        actionButtons.setVisible(false);
        actionButtons.setManaged(false);

        // eedit button
        Button editBtn = new Button();
        editBtn.getStyleClass().add("transaction-action-button");
        editBtn.setPrefSize(24, 24);
        editBtn.setMinSize(24, 24);
        editBtn.setMaxSize(24, 24);

        SVGPath editIcon = new SVGPath();
        editIcon.setContent("M18.13 12L19.39 10.74C19.83 10.3 20.39 10.06 21 10V9L15 3H5C3.89 3 3 3.89 3 5V19C3 20.1 3.89 21 5 21H11V19.13L11.13 19H5V5H12V12H18.13M14 4.5L19.5 10H14V4.5M19.13 13.83L21.17 15.87L15.04 22H13V19.96L19.13 13.83M22.85 14.19L21.87 15.17L19.83 13.13L20.81 12.15C21 11.95 21.33 11.95 21.53 12.15L22.85 13.47C23.05 13.67 23.05 14 22.85 14.19Z");
        editIcon.setScaleX(0.7);
        editIcon.setScaleY(0.7);
        editIcon.setFill(Color.valueOf("#666666"));

        editBtn.setGraphic(editIcon);
        editBtn.setOnAction(e -> {
            System.out.println("Edit transaction: " + title);
            // TODO: implement edit transaction logic
        });

        // Delete button
        Button deleteBtn = new Button();
        deleteBtn.getStyleClass().add("transaction-action-button");
        deleteBtn.setPrefSize(24, 24);
        deleteBtn.setMinSize(24, 24);
        deleteBtn.setMaxSize(24, 24);

        SVGPath deleteIcon = new SVGPath();
        deleteIcon.setContent("M9,3V4H4V6H5V19A2,2 0 0,0 7,21H17A2,2 0 0,0 19,19V6H20V4H15V3H9M7,6H17V19H7V6M9,8V17H11V8H9M13,8V17H15V8H13Z");
        deleteIcon.setScaleX(0.7);
        deleteIcon.setScaleY(0.7);
        deleteIcon.setFill(Color.valueOf("#dc3546d8"));

        deleteBtn.setGraphic(deleteIcon);
        deleteBtn.setOnAction(e -> {
            System.out.println("Delete transaction: " + title);
            // TODO: implement delete transaction logic
        });

        actionButtons.getChildren().addAll(editBtn, deleteBtn);
        rightContainer.getChildren().addAll(amountLabel, actionButtons);

        // combine cmoponents
        mainContainer.getChildren().addAll(iconContainer, detailsContainer, rightContainer);
        transactionBtn.setGraphic(mainContainer);

        // hover effects
        transactionBtn.setOnMouseEntered(e -> {
            transactionBtn.getStyleClass().add("transaction-item-hover");
            actionButtons.setVisible(true);
            actionButtons.setManaged(true);

            // animate the action buttons appearing
            actionButtons.setOpacity(0);
            Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.millis(200), 
                    new KeyValue(actionButtons.opacityProperty(), 1.0))
            );
            fadeIn.play();
        });

        transactionBtn.setOnMouseExited(e -> {
            transactionBtn.getStyleClass().remove("transaction-item-hover");

            // animate the action buttons disappearing
            Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.millis(150), 
                    new KeyValue(actionButtons.opacityProperty(), 0.0))
            );
            fadeOut.setOnFinished(event -> {
                actionButtons.setVisible(false);
                actionButtons.setManaged(false);
            });
            fadeOut.play();
        });

        return transactionBtn;
    }

    /* ----------------------- MONTHLY OVERVIEW WIDGET ----------------------- */
    private VBox createMonthlyOverviewWidget() {
        VBox widget = new VBox(16);
        widget.getStyleClass().add("monthlyoverview-widget-container");
        widget.setPadding(new Insets(24));
        
        // Widget header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        
        SVGPath icon = new SVGPath();
        icon.setContent("M16,11.78L20.24,4.45L21.97,5.45L16.74,14.5L10.23,10.75L5.46,19H22V21H2V3H4V17.54L9.5,8L16,11.78Z");
        icon.setFill(Color.BLACK);
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);

        Label title = new Label("Monthly Overview");
        title.getStyleClass().add("widget-title");
        
        header.getChildren().addAll(icon, title);
        
        // Overview items
        VBox overview = new VBox(16);
        
        // Spent this month
        HBox spentRow = createOverviewRow("Spent This Month", "$1,247.50");
        HBox budgetRow = createOverviewRow("Budget Remaining", "$752.50");
        
        // Savings goal with progress
        VBox goalSection = new VBox(8);
        HBox goalHeader = createOverviewRow("Savings Goal", "78%");
        
        // Progress bar
        // TODO: add logic to increase and decrease bar depending on real values

        ProgressBar progressBar = new ProgressBar((78/100));
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefHeight(8);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        
        goalSection.getChildren().addAll(goalHeader, progressBar);
        
        // Encouragement message
        HBox encouragement = new HBox(8);
        encouragement.setAlignment(Pos.CENTER_LEFT);
        
        Label message = new Label("Great job staying on budget!");
        message.getStyleClass().add("encouragement-text");
        
        SVGPath encourangeIcon = new SVGPath();
        encourangeIcon.setContent("M21.37 36c1.45-5.25 6.52-9 12.36-8.38c5.56.59 9.98 5.28 10.26 10.86c.07 1.47-.13 2.88-.56 4.19c-.26.8-1.04 1.33-1.89 1.33H11.758c-5.048 0-8.834-4.619-7.844-9.569L10 4h12l4 7l-8.57 6.13L15 14m2.44 3.13L22 34");
        encourangeIcon.setFill(Color.valueOf("#0000009e"));
        encourangeIcon.setScaleX(0.8);
        encourangeIcon.setScaleY(0.8);

        encouragement.getChildren().addAll(message, encourangeIcon);
        
        overview.getChildren().addAll(spentRow, budgetRow, goalSection, encouragement);
        
        widget.getChildren().addAll(header, overview);
        return widget;
    }
    
    /* ----------------------- OVERVIEW ROW ----------------------- */
    private HBox createOverviewRow(String label, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        
        Label labelText = new Label(label);
        labelText.getStyleClass().add("overview-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label valueText = new Label(value);
        valueText.getStyleClass().add("overview-value");
        
        row.getChildren().addAll(labelText, spacer, valueText);
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
