package com.example.views;

import com.example.controllers.AuthController;
import com.example.controllers.DashboardController;
import com.example.utils.SidebarUtil;
import com.example.utils.ThemeManager;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private HBox sidebarHeader;
    private HBox topBar;

    public void show(String theme) {
        // Register this view as the active listener
        ThemeManager.getInstance().setActiveListener(this);
    
        // FIXED: Use current theme from ThemeManager if no specific theme provided
        String currentTheme = (theme != null) ? theme : ThemeManager.getInstance().getCurrentTheme();
        
        currentScene = createScene(currentTheme);
        
        // Clear existing stylesheets and apply the new theme
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass().getResource("/themes/" + currentTheme + "Style.css").toExternalForm());
    
        new AuthController(this);
        ViewNavigator.switchViews(currentScene);
        
        // reset sidebar theme picker state when showing the view
        SidebarUtil.resetThemePickerState();
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
        
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH);
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox(0);
        contentArea.getStyleClass().add("content-area");
        
        // Top bar with dynamic title
        topBar = createTopBar();
        // TODO: add style to topBar

        // Main content with auth forms
        VBox mainContent = createMainContent();
        
        contentArea.getChildren().addAll(topBar, mainContent);
        VBox.setVgrow(mainContent, Priority.ALWAYS);
        
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
        cozyBadge.getStyleClass().add("cozy-mode-badge");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label hintLabel = new Label("Use the sidebar to customize themes");
        hintLabel.getStyleClass().add("hint-text");
        
        bar.getChildren().addAll(pageTitle, cozyBadge, spacer, hintLabel);
        return bar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(0, 40, 0, 40));
        
        // Two column layout
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(600);
        layout.setMinHeight(600);
        layout.setMaxHeight(600);
        layout.setPadding(new Insets(0, 60, 0, 0));
        
        // Branding column (left)
        brandingColumn = createBrandingColumn();
        
        // Auth column (right)
        VBox authColumn = createAuthColumn();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        layout.getChildren().addAll(brandingColumn, spacer, authColumn);
        mainContent.getChildren().add(layout);
        
        // Make columns wider
        brandingColumn.setMinWidth(380);
        brandingColumn.setMaxWidth(700);
        authColumn.setMinWidth(380);
        authColumn.setMaxWidth(520);

        // Make the main content area wider
        layout.setMinWidth(900);
        layout.setMaxWidth(1300);
        layout.setPrefWidth(1100);
        
        return mainContent;
    }

    private VBox createBrandingColumn() {
        VBox column = new VBox();
        column.getStyleClass().add("default-branding-column");
        column.setAlignment(Pos.TOP_LEFT);
        column.setPrefWidth(700);
        
        // Logo section
        HBox logoSection = new HBox(12);
        logoSection.setAlignment(Pos.CENTER_LEFT);
        
        Label logoIcon = new Label("☕");
        logoIcon.getStyleClass().add("main-icon");
        
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
        
        VBox card1 = createFeatureCard("📊", "Smart Analytics", "Track spending patterns with beautiful charts");
        VBox card2 = createFeatureCard("🎯", "Budget Goals", "Set and achieve your financial targets");
        VBox card3 = createFeatureCard("💳", "Category Tracking", "Track your spending by category");
        VBox card4 = createFeatureCard("💰", "Monthly Reports", "Get insights into your spending habits");
        
        featuresGrid.add(card1, 0, 0);
        featuresGrid.add(card2, 1, 0);
        featuresGrid.add(card3, 0, 1);
        featuresGrid.add(card4, 1, 1);
        
        return featuresGrid;
    }

    private VBox createFeatureCard(String emoji, String title, String description) {
        VBox card = new VBox(4);
        card.getStyleClass().add("default-feature-card");
        card.setAlignment(Pos.CENTER_LEFT   );
        card.setPadding(new Insets(15));
        card.setPrefWidth(320);
        card.setPrefHeight(120);
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.getStyleClass().add("default-feature-card-emoji");
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("default-feature-card-title");
        titleLabel.setWrapText(true);
        
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("default-feature-card-description");
        descLabel.setWrapText(true);
        
        card.getChildren().addAll(emojiLabel, titleLabel, descLabel);
        
        // Add hover animation
        addHoverAnimation(card);
        
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
        formHeader.setAlignment(Pos.CENTER);
        
        Label formIcon = new Label("💳");
        formIcon.getStyleClass().clear();
        formIcon.getStyleClass().add("form-icon");
        
        Label formTitle = new Label(isLoginMode ? "Welcome Back" : "Join CozyTracker");
        formTitle.getStyleClass().add("form-title");

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
        
        // Email field
        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("default-field-label");

        VBox emailGroup = new VBox(4, emailLabel, createIconField("📧", loginEmailField));
        emailGroup.setAlignment(Pos.TOP_LEFT);

        // Password field
        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("default-field-label");
        
        VBox passwordGroup = new VBox(4, passwordLabel, createIconField("🔒", loginPasswordField));
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

        // Name fields
        HBox nameRow = new HBox(12);
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.getStyleClass().add("default-field-label");
        VBox firstNameGroup = new VBox(4, firstNameLabel, createIconField("👤", firstNameField));
        firstNameGroup.setAlignment(Pos.TOP_LEFT);

        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.getStyleClass().add("default-field-label");
        VBox lastNameGroup = new VBox(4, lastNameLabel, createIconField("👤", lastNameField));
        lastNameGroup.setAlignment(Pos.TOP_LEFT);

        firstNameField.setPromptText("Gabi");
        lastNameField.setPromptText("Doodoo");
        nameRow.getChildren().addAll(firstNameGroup, lastNameGroup);

        // Email field
        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("default-field-label");
        VBox emailGroup = new VBox(4, emailLabel, createIconField("📧", signupEmailField));
        emailGroup.setAlignment(Pos.TOP_LEFT);
        
        // Password fields
        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("default-field-label");
        VBox passwordGroup = new VBox(4, passwordLabel, createIconField("🔒", signupPasswordField));
        passwordGroup.setAlignment(Pos.TOP_LEFT);

        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.getStyleClass().add("default-field-label");
        VBox confirmPasswordGroup = new VBox(4, confirmPasswordLabel, createIconField("🔒", confirmPasswordField));
        confirmPasswordGroup.setAlignment(Pos.TOP_LEFT);

        // Terms checkbox
        termsCheckBox.getStyleClass().add("default-checkbox");
        
        // Signup button
        signupButton.getStyleClass().add("default-primary-button");
        signupButton.setPrefWidth(380);
        signupButton.setPrefHeight(40);
        
        signupEmailField.setPromptText("Gabi@example.com");
        signupPasswordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");
        
        signupFields.getChildren().addAll(nameRow, emailGroup, passwordGroup, confirmPasswordGroup, termsCheckBox, signupButton);
        return signupFields;
    }

    private StackPane createIconField(String emoji, TextInputControl field) {
        // Emoji label
        Label icon = new Label(emoji);
        icon.getStyleClass().add("input-emoji-inside-left");
        icon.setMouseTransparent(true); // So it doesn't block input

        field.getStyleClass().add("default-text-field");

        StackPane stack = new StackPane(field, icon);
        stack.setAlignment(Pos.CENTER_LEFT);
        stack.getStyleClass().add("icon-field-stack");
        return stack;
    }

    private void addHoverAnimation(VBox card) {
        card.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1.02);
            scale.setToY(1.02);
            scale.play();
        });
        
        card.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });
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