package com.example.views;

import com.example.controllers.LoginController;
import com.example.utils.AuthPanelUtil;
import com.example.utils.BrandingPanelUtil;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
        HBox mainContainer = new HBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainContainer.setMaxWidth(Double.MAX_VALUE);
        
        VBox leftPanel = BrandingPanelUtil.createBrandingPanel();
        StackPane rightPanel = AuthPanelUtil.createAuthPanel(createAuthCard());
        
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        leftPanel.setPrefWidth(Utilities.APP_WIDTH / 2.0);
        rightPanel.setPrefWidth(Utilities.APP_WIDTH / 2.0);
        
        mainContainer.getChildren().addAll(leftPanel, rightPanel);
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH); 
    }

    private VBox createAuthCard() {
        VBox authCard = AuthPanelUtil.createBaseAuthCard();
        
        // form header
        VBox formHeader = AuthPanelUtil.createFormHeader("👤", "Welcome back", "Sign in to continue managing your finances");
        
        // form fields
        VBox formFields = createFormFields();
        
        // social login section
        VBox socialSection = AuthPanelUtil.createSocialSection(googleButton, githubButton, "Or continue with");
        
        // switch to signup
        HBox switchSection = AuthPanelUtil.createSwitchSection(signupLabel, signupLink);
        
        authCard.getChildren().addAll(
            formHeader,
            formFields,
            socialSection,
            switchSection
        );

        return authCard;
    }

    private VBox createFormFields() {
        VBox formFields = new VBox(24);
        formFields.getStyleClass().add("form-container");

        // username field
        VBox usernameGroup = AuthPanelUtil.createInputGroup("Email", usernameField, "Enter your email");

        // password field
        VBox passwordGroup = AuthPanelUtil.createInputGroup("Password", passwordField, "Enter your password");

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