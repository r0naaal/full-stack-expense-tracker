package com.example.views;

import com.example.controllers.LoginController;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class LoginView {
    // create elements
    private Label welcomebackLabel = new Label("Welcome back"); 
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Access Your Dashboard");
    private Label signupLabel = new Label("Don't have an account?");
    private Hyperlink signupLink = new Hyperlink("Sign up");

    // social login buttons
    private Button googleButton = new Button("Google");
    private Button githubButton = new Button("Github");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

        new LoginController(this); // passing instance of this view
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        // use BorderPane for reliable layout
        BorderPane mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        VBox leftPanel = createBrandingPanel();
        StackPane rightPanel = createAuthPanel();
        
        // set as left and right regions
        mainContainer.setLeft(leftPanel);
        mainContainer.setRight(rightPanel);
        
        // ensure equal width distribution
        leftPanel.prefWidthProperty().bind(mainContainer.widthProperty().divide(2));
        rightPanel.prefWidthProperty().bind(mainContainer.widthProperty().divide(2));
        
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH); 
    }

    private VBox createBrandingPanel() {
        VBox brandingPanel = new VBox();
        brandingPanel.getStyleClass().add("branding-panel");
        
        // content container
        VBox contentContainer = new VBox(20);
        contentContainer.setAlignment(Pos.CENTER_LEFT);
        contentContainer.setPadding(new Insets(64));

        // logo section
        HBox logoSection = createLogoSection();

        // main heading
        VBox headingSection = createHeadingSection();

        // description
        Label description = new Label("Track expenses, manage budgets, and achieve your financial goals with our comprehensive expense tracking platform.");
        description.getStyleClass().add("main-description");
        description.setWrapText(true);
        description.setMaxWidth(450);

        // feature highlights
        VBox featuresSection = createFeaturesSection();

        // statistics
        HBox statsSection = createStatsSection();

        contentContainer.getChildren().addAll(
            logoSection,
            headingSection,
            description,
            featuresSection,
            statsSection
        );
        
        // create the final layered panel with decorations
        StackPane layeredPanel = new StackPane();
        
        // add the main branding panel as background
        layeredPanel.getChildren().add(brandingPanel);
        
        // create blur decorations with proper styling
        Circle blurDecoration1 = createBlurDecoration(128, 128, "#24c25e", 0.05);
        Circle blurDecoration2 = createBlurDecoration(96, 96, "#24c25e", 0.1);
        
        // position the decorations
        StackPane.setAlignment(blurDecoration1, Pos.TOP_RIGHT);
        StackPane.setMargin(blurDecoration1, new Insets(80, 80, 0, 0));
        
        StackPane.setAlignment(blurDecoration2, Pos.BOTTOM_LEFT);
        StackPane.setMargin(blurDecoration2, new Insets(0, 0, 128, 64));
        
        // add content on top
        layeredPanel.getChildren().addAll(blurDecoration1, blurDecoration2, contentContainer);
        
        VBox finalPanel = new VBox();
        finalPanel.getChildren().add(layeredPanel);
        finalPanel.getStyleClass().add("branding-panel");
        
        return finalPanel;
    }

    // helper method to create proper blur decorations
    private Circle createBlurDecoration(double width, double height, String color, double opacity) {
        Circle circle = new Circle(width / 2.0);
        // set the fill color with opacity
        circle.setFill(Color.web(color, opacity));
        circle.setStroke(null); // Remove any stroke
        // apply blur effect
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(32);
        circle.setEffect(blur);
        return circle;
    }

    private StackPane createAuthPanel() {
        StackPane authPanel = new StackPane();
        authPanel.getStyleClass().add("auth-panel");
        
        VBox authCard = createAuthCard();
        authPanel.getChildren().add(authCard);

        return authPanel;
    }

    private VBox createAuthCard() {
        VBox authCard = new VBox(32);
        authCard.getStyleClass().add("auth-card");
        authCard.setMaxWidth(480);
        authCard.setAlignment(Pos.CENTER);
        
        // form header
        VBox formHeader = createFormHeader();
        
        // form fields
        VBox formFields = createFormFields();
        
        // social login section
        VBox socialSection = createSocialSection();
        
        // switch to signup
        HBox switchSection = createSwitchSection();
        
        authCard.getChildren().addAll(formHeader, formFields, socialSection, switchSection);
        return authCard;

    }

    private VBox createFormHeader() {
        VBox formHeader = new VBox(12);
        formHeader.getStyleClass().add("form-header");
        formHeader.setAlignment(Pos.CENTER);
        
        // form icon
        StackPane formIcon = new StackPane();
        formIcon.getStyleClass().add("form-icon");
        formIcon.setPrefSize(64, 64);
        
        Label userIcon = new Label("👤");
        userIcon.setStyle("-fx-font-size: 32px;");
        formIcon.getChildren().add(userIcon);
        
        welcomebackLabel.getStyleClass().add("form-title");
        
        Label subtitle = new Label("Sign in to continue managing your finances");
        subtitle.getStyleClass().add("form-subtitle");
        
        formHeader.getChildren().addAll(formIcon, welcomebackLabel, subtitle);
        return formHeader;
    }

    private VBox createFormFields() {
        VBox formFields = new VBox(24);
        formFields.getStyleClass().add("form-container");

        // username field
        VBox usernameGroup = new VBox(8);
        usernameGroup.getStyleClass().add("input-group");

        Label usernameLabel = new Label("Email");
        usernameLabel.getStyleClass().add("field-label");

        usernameField.setPromptText("Enter your email");
        usernameField.getStyleClass().clear();
        usernameField.getStyleClass().add("form-input");

        usernameGroup.getChildren().addAll(usernameLabel, usernameField);

        // password field
        VBox passwordGroup = new VBox(8);
        passwordGroup.getStyleClass().add("input-group");
        
        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("field-label");
        
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().clear();
        passwordField.getStyleClass().add("form-input");
        
        passwordGroup.getChildren().addAll(passwordLabel, passwordField);

        // remember me and forgot password
        HBox optionsRow = new HBox();
        optionsRow.setAlignment(Pos.CENTER_LEFT);
        optionsRow.setSpacing(8);

        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.getStyleClass().add("checkbox-container");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Hyperlink forgotPassword = new Hyperlink("Forgot password?");
        forgotPassword.getStyleClass().add("link-text");

        optionsRow.getChildren().addAll(rememberMe, spacer, forgotPassword);

        // Login button
        loginButton.getStyleClass().clear();
        loginButton.getStyleClass().add("primary-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        formFields.getChildren().addAll(usernameGroup, passwordGroup, optionsRow, loginButton);
        return formFields;
        
    }

    private VBox createSocialSection() {
        VBox socialSection = new VBox(16);
        
        // divider
        HBox dividerSection = new HBox(16);
        dividerSection.setAlignment(Pos.CENTER);
        dividerSection.getStyleClass().add("divider-container");
        
        Line leftLine = new Line();
        leftLine.getStyleClass().add("divider-line");
        leftLine.setEndX(100);
        
        Label dividerText = new Label("Or continue with");
        dividerText.getStyleClass().add("divider-text");
        
        Line rightLine = new Line();
        rightLine.getStyleClass().add("divider-line");
        rightLine.setEndX(100);
        
        dividerSection.getChildren().addAll(leftLine, dividerText, rightLine);
        
        // social buttons
        HBox socialButtons = new HBox(16);
        socialButtons.getStyleClass().add("social-buttons");
        
        googleButton.getStyleClass().add("social-button");
        googleButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(googleButton, Priority.ALWAYS);
        
        githubButton.getStyleClass().add("social-button");
        githubButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(githubButton, Priority.ALWAYS);
        
        socialButtons.getChildren().addAll(googleButton, githubButton);
        
        socialSection.getChildren().addAll(dividerSection, socialButtons);
        return socialSection;
    }

    private HBox createSwitchSection() {
        HBox switchSection = new HBox(8);
        switchSection.setAlignment(Pos.CENTER);

        signupLabel.getStyleClass().clear();
        signupLabel.getStyleClass().add("switch-form-text");

        signupLink.getStyleClass().add("link-text");

        switchSection.getChildren().addAll(signupLabel, signupLink);
        return switchSection;
    }

    private HBox createStatsSection() {
        HBox statsSection = new HBox(32);
        statsSection.getStyleClass().add("stats-container");
        statsSection.setAlignment(Pos.CENTER);

        VBox stat1 = createStatItem("$2.5M+", "Money Tracked");
        VBox stat2 = createStatItem("50K+", "Active Users");
        VBox stat3 = createStatItem("99.9%", "Uptime");

        statsSection.getChildren().addAll(stat1, stat2, stat3);
        return statsSection;
    }

    private VBox createStatItem(String number, String label) {
        VBox statItem = new VBox(4);
        statItem.getStyleClass().add("stat-item");
        statItem.setAlignment(Pos.CENTER);

        Label statNumber = new Label(number);
        statNumber.getStyleClass().add("stat-number");

        Label statLabel = new Label(label);
        statLabel.getStyleClass().add("stat-label");

        statItem.getChildren().addAll(statNumber, statLabel);
        return statItem;

    }

    private VBox createFeaturesSection() {
        VBox featuresSection = new VBox(24);
        featuresSection.getStyleClass().add("feature-container");

        // smart analytics feature
        HBox feature1 = createFeatureItem("📊", "Smart Analytics", "AI-powered insights into your spending patterns");
        
        // budget Management feature
        HBox feature2 = createFeatureItem("💼", "Budget Management", "Set limits and get alerts when you're overspending");
        
        // security feature
        HBox feature3 = createFeatureItem("🔒", "Bank-Grade Security", "Your financial data is protected with enterprise-level encryption");
        
        featuresSection.getChildren().addAll(feature1, feature2, feature3);
        return featuresSection;
    }

    private HBox createFeatureItem(String icon, String title, String description){
        HBox featureItem = new HBox(16);
        featureItem.getStyleClass().add("feature-item");
        featureItem.setAlignment(Pos.CENTER_LEFT);

        // feature icon
        StackPane iconContainer = new StackPane();
        iconContainer.getStyleClass().add("-fx-font-size: 24px;");
        iconContainer.setPrefSize(48, 48);
        
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");
        iconContainer.getChildren().add(iconLabel);
        
        // feature text
        VBox textContainer = new VBox(4);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("feature-title");

        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("feature-description");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(300);

        textContainer.getChildren().addAll(titleLabel, descLabel);
        featureItem.getChildren().addAll(iconContainer, textContainer);

        return featureItem;
    }

    private VBox createHeadingSection() {
        VBox headingSection = new VBox(8);

        Label mainHeading = new Label("Take Control of Your");
        mainHeading.getStyleClass().add("main-heading");

        Label accentHeading = new Label("Finances");
        accentHeading.getStyleClass().add("main-heading-accent");

        headingSection.getChildren().addAll(mainHeading, accentHeading);
        return headingSection;
    }

    private HBox createLogoSection() {

        HBox logoSection = new HBox(16);
        logoSection.getStyleClass().add("logo-container");
        logoSection.setAlignment(Pos.CENTER_LEFT);

        // logo icon with financial symbol
        StackPane logoIcon = new StackPane();
        logoIcon.getStyleClass().add("logo-icon");
        logoIcon.setPrefSize(64, 64);

        // add dollar sign
        Label dollarSign = new Label("$");
        dollarSign.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoIcon.getChildren().add(dollarSign);

        // brand text
        VBox brandText = new VBox(4);
        Label brandTitle = new Label("ExpenseTracker");
        brandTitle.getStyleClass().add("brand-title");

        Label brandSubtitle = new Label("Smart Financial Management");
        brandSubtitle.getStyleClass().add("brand-subtitle");

        brandText.getChildren().addAll(brandTitle, brandSubtitle);
        
        logoSection.getChildren().addAll(logoIcon, brandText);
        return logoSection;
    }

    public Label getWelcomebackLabel() { return welcomebackLabel; }
    public void setWelcomebackLabel(Label welcomebackLabel) { this.welcomebackLabel = welcomebackLabel; }

    public TextField getUsernameField() { return usernameField; }
    public void setUsernameField(TextField usernameField) { this.usernameField = usernameField; }

    public PasswordField getPasswordField() { return passwordField; }
    public void setPasswordField(PasswordField passwordField) { this.passwordField = passwordField; }

    public Button getLoginButton() { return loginButton; }
    public void setLoginButton(Button loginButton) { this.loginButton = loginButton; }

    public Label getSignupLabel() { return signupLabel; }
    public void setSignupLabel(Label signupLabel) { this.signupLabel = signupLabel; }
    
    public Hyperlink getSignupLink() { return signupLink; }
    public Button getGoogleButton() { return googleButton; }
    public Button getGithubButton() { return githubButton; }
}