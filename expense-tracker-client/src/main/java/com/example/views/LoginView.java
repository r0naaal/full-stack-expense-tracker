package com.example.views;

import com.example.controllers.LoginController;
import com.example.utils.BrandingPanelUtil;
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
        
        VBox leftPanel = BrandingPanelUtil.createBrandingPanel();
        StackPane rightPanel = createAuthPanel();
        
        // set as left and right regions
        mainContainer.setLeft(leftPanel);
        mainContainer.setRight(rightPanel);
        
        // ensure equal width distribution
        leftPanel.prefWidthProperty().bind(mainContainer.widthProperty().divide(2));
        rightPanel.prefWidthProperty().bind(mainContainer.widthProperty().divide(2));
        
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH); 
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