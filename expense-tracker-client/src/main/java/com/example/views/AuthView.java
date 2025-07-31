package com.example.views;

import com.example.controllers.AuthController;
import com.example.utils.SidebarUtil;
import com.example.utils.ThemeManager;
import com.example.utils.ViewNavigator;

import javafx.animation.FadeTransition;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class AuthView implements ThemeManager.ThemeChangeListener {

    private Scene currentScene;

    // Current mode
    private boolean isLoginMode = true;
    
    // Login form elements
    private TextField loginEmailField = new TextField();
    private PasswordField loginPasswordField = new PasswordField();
    private CheckBox rememberMeCheckBox = new CheckBox("Remember me");
    private Button loginButton = new Button("Sign In");
    
    // Signup form elements
    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField signupEmailField = new TextField();
    private PasswordField signupPasswordField = new PasswordField();
    private PasswordField confirmPasswordField = new PasswordField();
    private CheckBox termsCheckBox = new CheckBox("I agree to the Terms of Service");
    private Button signupButton = new Button("Create Account");
    
    // Layout containers (store references for easy access)
    private BorderPane mainContainer;
    private VBox authFormContainer;
    private HBox modeToggleContainer;
    private Button loginToggleButton;
    private Button signupToggleButton;
    private Label pageTitle;
    private VBox brandingColumn;
    private VBox authCard;
    private VBox sidebar;
    private HBox topBar;

    public void show(String theme) {
        // register this view as the active listener
        ThemeManager.getInstance().setActiveListener(this);
    
        // use current theme from ThemeManager if no specific theme provided
        String currentTheme = (theme != null) ? theme : ThemeManager.getInstance().getCurrentTheme();
        
        currentScene = createScene(currentTheme);
        
        // Clear existing stylesheets and apply the new theme
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass().getResource("/themes/" + currentTheme + "Style.css").toExternalForm());
    
        new AuthController(this);
        ViewNavigator.switchViews(currentScene);
        
        // reset sidebar theme picker state when showing the view
        SidebarUtil.resetThemePickerState();

        // login button is always active for default
        switchToLoginMode();
    }

    // overloaded method to show with current theme:
    public void show() {
        show(null); // will use current theme from ThemeManager
    }

    @Override
    public void onThemeChanged(String newTheme) {
        // just refresh with the new theme, don't call show(newTheme) to avoid recursion issues
        if (currentScene != null) {
            currentScene.getStylesheets().clear();
            currentScene.getStylesheets().add(getClass().getResource("/themes/" + newTheme + "Style.css").toExternalForm());
        }
    }

    private Scene createScene(String theme){
        // main container with sidebar layout
        mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        // create sidebar (left side)
        sidebar = SidebarUtil.createSidebar(SidebarUtil.SidebarType.AUTH_VIEW);
        mainContainer.setLeft(sidebar);
        
        // create main content area
        VBox contentArea = createContentArea();
        mainContainer.setCenter(contentArea);        
        
        return new Scene(mainContainer);
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox(0);
        contentArea.getStyleClass().add("content-area");
        
        // Top bar with dynamic title
        topBar = createTopBar();

        // Main content with auth forms
        ScrollPane mainContent = createMainContent();
        
        contentArea.getChildren().addAll(topBar, mainContent);
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        
        return contentArea;
    }

    private HBox createTopBar() {
        HBox bar = new HBox(16);
        bar.getStyleClass().add("default-top-bar");
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(16, 24, 16, 24));
        bar.setPrefHeight(64);
        bar.setMinHeight(64);
        bar.setMaxHeight(64);
        
        // Title section
        pageTitle = new Label(isLoginMode ? "Welcome Back" : "Get Started");
        pageTitle.getStyleClass().add("form-title");
        
        Label cozyBadge = new Label("Cozy Mode");
        cozyBadge.getStyleClass().clear();
        cozyBadge.getStyleClass().add("cozy-mode-badge");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label hintLabel = new Label("Use the sidebar to customize themes");
        hintLabel.getStyleClass().add("hint-text");
        
        bar.getChildren().addAll(pageTitle, cozyBadge, spacer, hintLabel);
        return bar;
    }

    private ScrollPane createMainContent() {
        ScrollPane mainContent = new ScrollPane();
        mainContent.setFitToWidth(true);
        mainContent.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        mainContent.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainContent.setPadding(new Insets(30, 10, 0, 30));
        mainContent.getStyleClass().add("auth-content-scroll");

        VBox content = new VBox();
        content.setMaxWidth(Double.MAX_VALUE);


        // Two column layout
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(600);
        layout.setMinHeight(600);
        layout.setMaxHeight(600);
        layout.setPadding(new Insets(0, 30, 0, 0));
        
        // Branding column (left)
        brandingColumn = createBrandingColumn();
        
        // Auth column (right)
        VBox authColumn = createAuthColumn();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        layout.getChildren().addAll(brandingColumn, spacer, authColumn);
        content.getChildren().add(layout);
        
        // Make columns wider
        brandingColumn.setMinWidth(380);
        brandingColumn.setMaxWidth(700);
        authColumn.setMinWidth(380);
        authColumn.setMaxWidth(520);

        // Make the main content area wider
        layout.setMinWidth(900);
        layout.setMaxWidth(1300);
        layout.setPrefWidth(1100);
        
        mainContent.setContent(content);

        return mainContent;
    }

    private VBox createBrandingColumn() {
        VBox column = new VBox();
        column.getStyleClass().add("default-branding-column");
        column.setAlignment(Pos.TOP_LEFT);
        column.setPadding(new Insets(0, 10, 0,0));
        column.setPrefWidth(700);
        
        // Logo section
        HBox logoSection = new HBox(12);
        logoSection.setPadding(new Insets(0,0,0,10));
        logoSection.setAlignment(Pos.CENTER_LEFT);
        
        SVGPath logoIcon = new SVGPath();
        logoIcon.setContent("M15 10C15 9.45 15.45 9 16 9C16.55 9 17 9.45 17 10S16.55 11 16 11 15 10.55 15 10M22 7.5V14.47L19.18 15.41L17.5 21H12V19H10V21H4.5C4.5 21 2 12.54 2 9.5S4.46 4 7.5 4H12.5C13.41 2.79 14.86 2 16.5 2C17.33 2 18 2.67 18 3.5C18 3.71 17.96 3.9 17.88 4.08C17.74 4.42 17.62 4.81 17.56 5.23L19.83 7.5H22M20 9.5H19L15.5 6C15.5 5.35 15.59 4.71 15.76 4.09C14.79 4.34 14 5.06 13.67 6H7.5C5.57 6 4 7.57 4 9.5C4 11.38 5.22 16.15 6 19H8V17H14V19H16L17.56 13.85L20 13.03V9.5Z");
        logoIcon.setFill(Color.valueOf("#000000"));
        logoIcon.setScaleX(1.8);
        logoIcon.setScaleY(1.8);
        
        Label appName = new Label("CozyTracker");
        appName.getStyleClass().add("logo-text");
        
        logoSection.getChildren().addAll(logoIcon, appName);
        
        // Headings
        Label mainHeading = new Label(isLoginMode ? "Your Personal" : "Start Your");
        mainHeading.getStyleClass().add("main-heading");
        
        Label accentHeading = new Label(isLoginMode ? "Expense Companion" : "Financial Journey");
        accentHeading.getStyleClass().add("default-accent-heading");
        
        // Description
        Label description = new Label(
            isLoginMode ? 
            "Track expenses, set budgets, and achieve your financial goals in a beautiful, cozy interface designed for daily use." :
            "Create your account and take control of your financial future with our beautiful, cozy expense tracking platform."
        );
        description.getStyleClass().add("description-text");
        description.setWrapText(true);
        description.setMaxWidth(650);
        
        // Features grid
        GridPane featuresGrid = createFeaturesGrid();
        
        column.getChildren().addAll(logoSection, mainHeading, accentHeading, description, featuresGrid);
        return column;
    }

    private GridPane createFeaturesGrid() {
        GridPane featuresGrid = new GridPane();

        featuresGrid.setHgap(16);
        featuresGrid.setVgap(16);
        featuresGrid.setAlignment(Pos.CENTER_LEFT);
        featuresGrid.setPadding(new Insets(40, 0, 0, 0));
        
        SVGPath analyticsIcon = new SVGPath();
        analyticsIcon.setContent("M9 17H7V10H9V17M13 17H11V7H13V17M17 17H15V13H17V17M19 19H5V5H19V19.1M19 3H5C3.9 3 3 3.9 3 5V19C3 20.1 3.9 21 5 21H19C20.1 21 21 20.1 21 19V5C21 3.9 20.1 3 19 3Z");

        SVGPath targetIcon = new SVGPath();
        targetIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,10.84 21.79,9.69 21.39,8.61L19.79,10.21C19.93,10.8 20,11.4 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4C12.6,4 13.2,4.07 13.79,4.21L15.4,2.6C14.31,2.21 13.16,2 12,2M19,2L15,6V7.5L12.45,10.05C12.3,10 12.15,10 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12C14,11.85 14,11.7 13.95,11.55L16.5,9H18L22,5H19V2M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12H16A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8V6Z");

        SVGPath categoryIcon = new SVGPath();
        categoryIcon.setContent("M14,6H22V22H14V6M2,4H22V2H2V4M2,8H12V6H2V8M9,22H12V10H9V22M2,22H7V10H2V22Z");

        SVGPath reportIcon = new SVGPath();
        reportIcon.setContent("M12.04,2.5L9.53,5H14.53L12.04,2.5M4,7V20H20V7H4M12,0L17,5V5H20A2,2 0 0,1 22,7V20A2,2 0 0,1 20,22H4A2,2 0 0,1 2,20V7A2,2 0 0,1 4,5H7V5L12,0M7,18V14H12V18H7M14,17V10H18V17H14M6,12V9H11V12H6Z");

        VBox card1 = createFeatureCard(analyticsIcon, "Smart Analytics", "Track spending patterns with beautiful charts");
        VBox card2 = createFeatureCard(targetIcon, "Budget Goals", "Set and achieve your financial targets");
        VBox card3 = createFeatureCard(categoryIcon, "Category Tracking", "Track your spending by category");
        VBox card4 = createFeatureCard(reportIcon, "Monthly Reports", "Get insights into your spending habits");
        
        featuresGrid.add(card1, 0, 0);
        featuresGrid.add(card2, 1, 0);
        featuresGrid.add(card3, 0, 1);
        featuresGrid.add(card4, 1, 1);
        
        return featuresGrid;
    }

    private VBox createFeatureCard(SVGPath icon, String title, String description) {
        VBox card = new VBox(4);
        card.getStyleClass().add("default-feature-card");
        card.setAlignment(Pos.CENTER_LEFT   );
        card.setPadding(new Insets(15));
        card.setPrefWidth(320);
        card.setPrefHeight(120);
        
        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#0000009f"));
        clonIcon.setScaleX(1.1);
        clonIcon.setScaleY(1.1);
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("default-feature-card-title");
        titleLabel.setWrapText(true);
        
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("default-feature-card-description");
        descLabel.setWrapText(true);
        
        card.getChildren().addAll(clonIcon, titleLabel, descLabel);
        
        return card;
    }

    private VBox createAuthColumn() {
        VBox authColumn = new VBox();
        authColumn.setAlignment(Pos.CENTER);
        
        authCard = new VBox(10);
        authCard.getStyleClass().add("default-auth-card");
        authCard.setAlignment(Pos.TOP_CENTER);
        authCard.setPadding(new Insets(10));
        authCard.setPrefWidth(400);
        
        // Form header
        VBox formHeader = createFormHeader();
        
        // Mode toggle
        modeToggleContainer = createModeToggle();
        
        // Form container
        authFormContainer = createFormContainer();
        
        authCard.getChildren().addAll(formHeader, modeToggleContainer, authFormContainer);
        authColumn.getChildren().add(authCard);
        
        return authColumn;
    }

    private VBox createFormHeader() {
        VBox formHeader = new VBox();
        formHeader.setPadding(new Insets(10,0,0,0));
        formHeader.setAlignment(Pos.CENTER);
        
        SVGPath formIcon = new SVGPath();
        formIcon.setContent("M15 10C15 9.45 15.45 9 16 9C16.55 9 17 9.45 17 10S16.55 11 16 11 15 10.55 15 10M22 7.5V14.47L19.18 15.41L17.5 21H12V19H10V21H4.5C4.5 21 2 12.54 2 9.5S4.46 4 7.5 4H12.5C13.41 2.79 14.86 2 16.5 2C17.33 2 18 2.67 18 3.5C18 3.71 17.96 3.9 17.88 4.08C17.74 4.42 17.62 4.81 17.56 5.23L19.83 7.5H22M20 9.5H19L15.5 6C15.5 5.35 15.59 4.71 15.76 4.09C14.79 4.34 14 5.06 13.67 6H7.5C5.57 6 4 7.57 4 9.5C4 11.38 5.22 16.15 6 19H8V17H14V19H16L17.56 13.85L20 13.03V9.5Z");
        formIcon.setFill(Color.valueOf("#000000"));
        formIcon.setScaleX(1.5);
        formIcon.setScaleY(1.5);

        Label formTitle = new Label(isLoginMode ? "Welcome Back" : "Join CozyTracker");
        formTitle.getStyleClass().add("form-title");
        formTitle.setPadding(new Insets(5,0,0,0));

        Label formSubtitle = new Label(isLoginMode ? "Sign in to your account" : "Create your account");
        formSubtitle.getStyleClass().add("form-subtitle");

        formHeader.getChildren().addAll(formIcon, formTitle, formSubtitle);
        return formHeader;
    }

    private HBox createModeToggle() {
        HBox toggleContainer = new HBox(20);
        toggleContainer.getStyleClass().add("mode-toggle");
        toggleContainer.setAlignment(Pos.CENTER);
        toggleContainer.setPadding(new Insets(4));
        
        loginToggleButton = new Button("Login");
        signupToggleButton = new Button("Sign Up");
        
        loginToggleButton.getStyleClass().add("mode-toggle-button");
        signupToggleButton.getStyleClass().add("mode-toggle-button");
        
        loginToggleButton.setPrefWidth(160);
        signupToggleButton.setPrefWidth(160);
        
        toggleContainer.getChildren().addAll(loginToggleButton, signupToggleButton);
        return toggleContainer;
    }

    private VBox createFormContainer() {
        VBox formContainer = new VBox();
        formContainer.setAlignment(Pos.TOP_CENTER);
        
        if (isLoginMode) {
            formContainer.getChildren().addAll(createLoginFields());
        } else {
            formContainer.getChildren().addAll(createSignupFields());
        }
        
        return formContainer;
    }

    private VBox createLoginFields() {
        VBox loginFields = new VBox(10);
        loginFields.setAlignment(Pos.TOP_CENTER);

        SVGPath loginEmailIcon = new SVGPath();
        loginEmailIcon.setContent("M22 6C22 4.9 21.1 4 20 4H4C2.9 4 2 4.9 2 6V18C2 19.1 2.9 20 4 20H20C21.1 20 22 19.1 22 18V6M20 6L12 11L4 6H20M20 18H4V8L12 13L20 8V18Z");
        loginEmailIcon.setFill(Color.valueOf("#000000b6"));
        
        SVGPath loginPasswordIcon = new SVGPath();
        loginPasswordIcon.setContent("M12,17C10.89,17 10,16.1 10,15C10,13.89 10.89,13 12,13A2,2 0 0,1 14,15A2,2 0 0,1 12,17M18,20V10H6V20H18M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6C4.89,22 4,21.1 4,20V10C4,8.89 4.89,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z");
        loginPasswordIcon.setFill(Color.valueOf("#000000b6"));

        // Email field
        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("default-field-label");

        VBox emailGroup = new VBox(4, emailLabel, createIconField(loginEmailIcon, loginEmailField));
        emailGroup.setAlignment(Pos.TOP_LEFT);

        // Password field
        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("default-field-label");
        
        VBox passwordGroup = new VBox(4, passwordLabel, createIconField(loginPasswordIcon, loginPasswordField));
        passwordGroup.setAlignment(Pos.TOP_LEFT);

        // Remember me checkbox
        rememberMeCheckBox.getStyleClass().add("default-checkbox");

        // Login button
        loginFields.getStyleClass().clear();
        loginButton.getStyleClass().add("default-primary-button");
        loginButton.setPrefWidth(380);
        loginButton.setPrefHeight(40);
        
        loginEmailField.setPromptText("Ron@example.com");
        loginPasswordField.setPromptText("Password");
        
        loginFields.getChildren().addAll(emailGroup, passwordGroup, rememberMeCheckBox, loginButton);
        return loginFields;
    }

    private VBox createSignupFields() {
        VBox signupFields = new VBox(10);
        signupFields.setAlignment(Pos.TOP_CENTER);

        // load the svg icon
        SVGPath firstNameIcon = new SVGPath();
        firstNameIcon.setContent("M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z");
        firstNameIcon.setFill(Color.valueOf("#000000b6"));

        SVGPath lastNameIcon = new SVGPath();
        lastNameIcon.setContent("M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z");
        lastNameIcon.setFill(Color.valueOf("#000000b6"));

        SVGPath signupEmailIcon = new SVGPath();
        signupEmailIcon.setContent("M22 6C22 4.9 21.1 4 20 4H4C2.9 4 2 4.9 2 6V18C2 19.1 2.9 20 4 20H20C21.1 20 22 19.1 22 18V6M20 6L12 11L4 6H20M20 18H4V8L12 13L20 8V18Z");
        signupEmailIcon.setFill(Color.valueOf("#000000b6"));

        SVGPath signupPasswordIcon = new SVGPath();
        signupPasswordIcon.setContent("M12,17C10.89,17 10,16.1 10,15C10,13.89 10.89,13 12,13A2,2 0 0,1 14,15A2,2 0 0,1 12,17M18,20V10H6V20H18M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6C4.89,22 4,21.1 4,20V10C4,8.89 4.89,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z");
        signupPasswordIcon.setFill(Color.valueOf("#000000b6"));

        SVGPath confirmPasswordIcon = new SVGPath();
        confirmPasswordIcon.setContent("M12,17C10.89,17 10,16.1 10,15C10,13.89 10.89,13 12,13A2,2 0 0,1 14,15A2,2 0 0,1 12,17M18,20V10H6V20H18M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6C4.89,22 4,21.1 4,20V10C4,8.89 4.89,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z");
        confirmPasswordIcon.setFill(Color.valueOf("#000000b6"));

        // Name fields
        HBox nameRow = new HBox(12);

        Label firstNameLabel = new Label("First Name");
        firstNameLabel.getStyleClass().add("default-field-label");

        VBox firstNameGroup = new VBox(4, firstNameLabel, createIconField(firstNameIcon, firstNameField));
        firstNameGroup.setAlignment(Pos.TOP_LEFT);

        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.getStyleClass().add("default-field-label");
        VBox lastNameGroup = new VBox(4, lastNameLabel, createIconField(lastNameIcon, lastNameField));
        lastNameGroup.setAlignment(Pos.TOP_LEFT);

        firstNameField.setPromptText("Gabi");
        lastNameField.setPromptText("Doodoo");
        nameRow.getChildren().addAll(firstNameGroup, lastNameGroup);

        // Email field
        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("default-field-label");
        VBox emailGroup = new VBox(4, emailLabel, createIconField(signupEmailIcon, signupEmailField));
        emailGroup.setAlignment(Pos.TOP_LEFT);
        
        // Password fields
        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("default-field-label");
        VBox passwordGroup = new VBox(4, passwordLabel, createIconField(signupPasswordIcon, signupPasswordField));
        passwordGroup.setAlignment(Pos.TOP_LEFT);

        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.getStyleClass().add("default-field-label");
        VBox confirmPasswordGroup = new VBox(4, confirmPasswordLabel, createIconField(confirmPasswordIcon, confirmPasswordField));
        confirmPasswordGroup.setAlignment(Pos.TOP_LEFT);

        // Terms checkbox
        termsCheckBox.getStyleClass().add("default-checkbox");
        
        // Signup button
        signupButton.getStyleClass().add("default-primary-button");
        signupButton.setPrefWidth(380);
        signupButton.setPrefHeight(40);
        
        signupEmailField.setPromptText("Ronal@example.com");
        signupPasswordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");
        
        signupFields.getChildren().addAll(nameRow, emailGroup, passwordGroup, confirmPasswordGroup, termsCheckBox, signupButton);
        return signupFields;
    }

    private StackPane createIconField(SVGPath svgIcon, TextInputControl field) {
        field.getStyleClass().add("default-text-field");

        StackPane stack = new StackPane(field, svgIcon);
        stack.setAlignment(Pos.CENTER_LEFT);
        stack.getStyleClass().add("icon-field-stack");

        svgIcon.setTranslateX(10);
        return stack;
    }

    public void switchToLoginMode() {
        isLoginMode = true;
        updateUI();
        animateFormSwitch();
    }

    public void switchToSignupMode() {
        isLoginMode = false;
        updateUI();
        animateFormSwitch();
    }

    private void updateUI() {
        // update page title
        pageTitle.setText(isLoginMode ? "Welcome Back" : "Get Started");
        
        // update form header
        VBox formHeader = (VBox) authCard.getChildren().get(0);
        Label formTitle = (Label) formHeader.getChildren().get(1);
        Label formSubtitle = (Label) formHeader.getChildren().get(2);
        formTitle.setText(isLoginMode ? "Welcome Back" : "Join CozyTracker");
        formSubtitle.setText(isLoginMode ? "Sign in to your account" : "Create your account");
        
        // update branding content
        Label mainHeading = (Label) brandingColumn.getChildren().get(1);
        Label accentHeading = (Label) brandingColumn.getChildren().get(2);
        Label description = (Label) brandingColumn.getChildren().get(3);
        mainHeading.setText(isLoginMode ? "Your Personal" : "Start Your");
        accentHeading.setText(isLoginMode ? "Expense Companion" : "Financial Journey");
        description.setText(
            isLoginMode ? 
            "Track expenses, set budgets, and achieve your financial goals in a beautiful, cozy interface designed for daily use." :
            "Create your account and take control of your financial future with our beautiful, cozy expense tracking platform."
        );
        // update mode toggle styles
        updateModeToggleStyles();
    }

    private void updateModeToggleStyles() {
        if (isLoginMode) {
        loginToggleButton.getStyleClass().add("selected");
        signupToggleButton.getStyleClass().remove("selected");
        } else {
            signupToggleButton.getStyleClass().add("selected");
            loginToggleButton.getStyleClass().remove("selected");
        }
    }

    private void animateFormSwitch() {
        // fade out current form
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), authFormContainer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        
        fadeOut.setOnFinished(e -> {
            // update form content
            authFormContainer.getChildren().clear();
            if (isLoginMode) {
                authFormContainer.getChildren().addAll(createLoginFields());
            } else {
                authFormContainer.getChildren().addAll(createSignupFields());
            }
            
            // fade in new form
            FadeTransition fadeIn = new FadeTransition(Duration.millis(150), authFormContainer);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        
        fadeOut.play();
    }

    // getters for controller
    public TextField getLoginEmailField() { return loginEmailField; }
    public PasswordField getLoginPasswordField() { return loginPasswordField; }
    public CheckBox getRememberMeCheckBox() { return rememberMeCheckBox; }
    public Button getLoginButton() { return loginButton; }
    
    public TextField getFirstNameField() { return firstNameField; }
    public TextField getLastNameField() { return lastNameField; }
    public TextField getSignupEmailField() { return signupEmailField; }
    public PasswordField getSignupPasswordField() { return signupPasswordField; }
    public PasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public CheckBox getTermsCheckBox() { return termsCheckBox; }
    public Button getSignupButton() { return signupButton; }
    
    public Button getLoginToggleButton() { return loginToggleButton; }
    public Button getSignupToggleButton() { return signupToggleButton; }
    public boolean isLoginMode() { return isLoginMode; }
}