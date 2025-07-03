package com.example.views;

import com.example.controllers.SignUpController;
import com.example.utils.AuthPanelUtil;
import com.example.utils.BrandingPanelUtil;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SignUpView {
    private Label titleLabel = new Label("Start Your Journey");
    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField confirmPasswordField = new PasswordField();
    private CheckBox termsCheckBox = new CheckBox();
    private Button signupButton = new Button("Create My Account");
    private Label loginLabel = new Label("Already have an account?");
    private Hyperlink loginLink = new Hyperlink("Sign in");

    private Button googleButton = new Button("Google");
    private Button githubButton = new Button("Github");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/signupStyle.css").toExternalForm());

        new SignUpController(this); // pass current view
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        HBox mainContainer = new HBox();
        mainContainer.getStyleClass().add("main-container");
        VBox leftPanel = BrandingPanelUtil.createBrandingPanel();
        StackPane rightPanel = AuthPanelUtil.createAuthPanel(createAuthCard());
        leftPanel.prefWidthProperty().bind(mainContainer.widthProperty().multiply(0.5));
        rightPanel.prefWidthProperty().bind(mainContainer.widthProperty().multiply(0.5));
        leftPanel.setMinWidth(0);
        leftPanel.setMaxWidth(Double.MAX_VALUE);
        rightPanel.setMinWidth(0);
        rightPanel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        mainContainer.getChildren().addAll(leftPanel, rightPanel);
        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGTH);
    }

    private VBox createAuthCard() {
        VBox authCard = AuthPanelUtil.createBaseAuthCard();
        
        // form header
        VBox formHeader = AuthPanelUtil.createFormHeader("✨", "Start Your Journey", "Create your account and take control of your financial future");
        
        // form fields
        VBox formFields = createFormFields();
        
        // social signup section
        VBox socialSection = AuthPanelUtil.createSocialSection(googleButton, githubButton, "Or sign up with");
        
        // switch to sign in
        HBox switchSection = AuthPanelUtil.createSwitchSection(loginLabel, loginLink);
        
        authCard.getChildren().addAll(
            formHeader,
            formFields,
            socialSection,
            switchSection
        );

        return authCard;
    }
    
    private VBox createFormFields() {
        VBox formFields = new VBox(12);
        formFields.getStyleClass().add("form-container");

        // name fields (side by side)
        HBox nameRow = new HBox(16);
        VBox firstNameGroup = AuthPanelUtil.createInputGroup("First Name", firstNameField, "Gabi");
        VBox lastNameGroup = AuthPanelUtil.createInputGroup("Last Name", lastNameField, "Doodo");

        HBox.setHgrow(firstNameGroup, Priority.ALWAYS);
        HBox.setHgrow(lastNameGroup, Priority.ALWAYS);
        nameRow.getChildren().addAll(firstNameGroup, lastNameGroup);

        // other fields
        VBox emailGroup = AuthPanelUtil.createInputGroup("Email", emailField, "gabi74@example.com");
        VBox passwordGroup = AuthPanelUtil.createInputGroup("Password", passwordField, "Create a strong password");
        VBox confirmPasswordGroup = AuthPanelUtil.createInputGroup("Confirm Password", confirmPasswordField, "Confirm your password");

        // terms checkbox
        HBox termsRow = new HBox(6);
        termsRow.setAlignment(Pos.CENTER_LEFT);

        termsCheckBox.getStyleClass().add("checkbox-container");
        Label termsText = new Label("I agree to the ");
        termsText.getStyleClass().add("switch-form-text");
        Hyperlink termsLink = new Hyperlink("Terms of Service");
        termsLink.getStyleClass().add("link-text");
        Label andText = new Label(" and ");
        andText.getStyleClass().add("switch-form-text");
        Hyperlink privacyLink = new Hyperlink("Privacy Policy");
        privacyLink.getStyleClass().add("link-text");

        termsRow.getChildren().addAll(termsCheckBox, termsText, termsLink, andText, privacyLink);

        // signup button
        signupButton.getStyleClass().clear();
        signupButton.getStyleClass().add("primary-button");
        signupButton.setMaxWidth(Double.MAX_VALUE);

        formFields.getChildren().addAll(
            nameRow,
            emailGroup, 
            passwordGroup, 
            confirmPasswordGroup, 
            termsRow, 
            signupButton
        );

        return formFields;
    }

    // Getters
    public TextField getFirstNameField() { return firstNameField; }
    public TextField getLastNameField() { return lastNameField; }
    public TextField getEmailField() { return emailField; }
    public PasswordField getPasswordField() { return passwordField; }
    public PasswordField getConfirmPasswordField() { return confirmPasswordField; }

    public Label getTitleLabel() { return titleLabel; }
    public CheckBox getTermsCheckbox() { return termsCheckBox; }
    public Button getSignupButton() { return signupButton; }
    public Label getLoginLabel() { return loginLabel; }
    public Hyperlink getLoginLink() { return loginLink; }
    
    public Button getGoogleButton() { return googleButton; }
    public Button getGithubButton() { return githubButton; }
}
