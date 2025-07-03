package com.example.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class AuthPanelUtil {
    
    public static StackPane createAuthPanel(VBox authCard) {
        StackPane authPanel = new StackPane();
        authPanel.getStyleClass().add("auth-panel");
        authPanel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        authPanel.setMaxWidth(Double.MAX_VALUE);
        authPanel.setAlignment(Pos.CENTER);
        
        authPanel.getChildren().add(authCard);
        return authPanel;
    }

    public static VBox createBaseAuthCard(){
        VBox authCard = new VBox(32);
        authCard.getStyleClass().add("auth-card");
        authCard.setMaxWidth(480);
        authCard.setAlignment(Pos.CENTER);
        return authCard;
    }

    public static VBox createFormHeader(String icon, String title, String subtitle) {
        VBox formHeader = new VBox(12);
        formHeader.getStyleClass().add("form-header");
        formHeader.setAlignment(Pos.CENTER);
        
        // form icon
        StackPane formIcon = new StackPane();
        formIcon.getStyleClass().add("form-icon");
        formIcon.setPrefSize(64, 64);
        
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 32px;");
        formIcon.getChildren().add(iconLabel);
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("form-title");
        
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("form-subtitle");
        
        formHeader.getChildren().addAll(formIcon, titleLabel, subtitleLabel);
        return formHeader;
    }

    public static VBox createInputGroup(String labelText, TextField field, String promptText) {
        VBox inputGroup = new VBox(8);
        inputGroup.getStyleClass().add("input-group");

        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");
        
        field.setPromptText(promptText);
        field.getStyleClass().clear();
        field.getStyleClass().add("form-input");

        inputGroup.getChildren().addAll(label, field);
        return inputGroup;
    }

    public static VBox createSocialSection(Button googleButton, Button githubButton, String dividerText) {
        VBox socialSection = new VBox(16);

        // divider
        HBox dividerSection = new HBox(16);
        dividerSection.setAlignment(Pos.CENTER);
        dividerSection.getStyleClass().add("divider-container");

        Line leftLine = new Line();
        leftLine.getStyleClass().add("divider-line");
        leftLine.setEndX(100);

        Label dividerLabel = new Label(dividerText);
        dividerLabel.getStyleClass().add("divider-text");

        Line rightLine = new Line();
        rightLine.getStyleClass().add("divider-line");
        rightLine.setEndX(100);
        
        dividerSection.getChildren().addAll(leftLine, dividerLabel, rightLine);

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

    public static HBox createSwitchSection(Label switchLabel, Hyperlink switchLink) {
        HBox switchSection = new HBox(8);
        switchSection.setAlignment(Pos.CENTER);

        switchLabel.getStyleClass().add("switch-form-text");

        switchLink.getStyleClass().add("link-text");

        switchSection.getChildren().addAll(switchLabel, switchLink);
        return switchSection;
    }
}
