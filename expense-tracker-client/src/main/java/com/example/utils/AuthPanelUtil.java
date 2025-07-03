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

    public static VBox createBaseAuthCard() {
        VBox authCard = new VBox(16);
        authCard.getStyleClass().add("auth-card");
        authCard.setMaxWidth(480);
        authCard.setAlignment(Pos.TOP_CENTER);
        authCard.setPrefHeight(Region.USE_COMPUTED_SIZE);
        authCard.setMaxHeight(Region.USE_COMPUTED_SIZE);
        return authCard;
    }

    public static VBox createFormHeader(String icon, String title, String subtitle) {
        VBox formHeader = new VBox(8);
        formHeader.getStyleClass().add("form-header");
        formHeader.setAlignment(Pos.CENTER);
        
        // form icon
        StackPane formIcon = new StackPane();
        formIcon.getStyleClass().add("form-icon");
        formIcon.setPrefSize(48, 48);
        
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");
        formIcon.getChildren().add(iconLabel);
        
        // Title and subtitle
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
        VBox socialSection = new VBox(12);

        // divider
        HBox dividerSection = new HBox(12);
        dividerSection.setAlignment(Pos.CENTER);
        dividerSection.getStyleClass().add("divider-container");
        dividerSection.setMaxWidth(Double.MAX_VALUE);


        Region leftLine = new Region();
        leftLine.getStyleClass().add("divider-line");
        leftLine.setPrefHeight(1);
        HBox.setHgrow(leftLine, Priority.ALWAYS);

        Label dividerLabel = new Label(dividerText);
        dividerLabel.getStyleClass().add("divider-text");

        Region rightLine = new Region();
        rightLine.getStyleClass().add("divider-line");
        rightLine.setPrefHeight(1);
        HBox.setHgrow(rightLine, Priority.ALWAYS);

        dividerSection.getChildren().addAll(leftLine, dividerLabel, rightLine);

        // social buttons 
        HBox socialButtons = new HBox(12);
        socialButtons.getStyleClass().add("social-buttons");
        socialButtons.setAlignment(Pos.CENTER);
        socialButtons.setMaxWidth(Double.MAX_VALUE);

        googleButton.getStyleClass().clear(); 
        googleButton.getStyleClass().add("social-button");
        googleButton.setMaxWidth(Double.MAX_VALUE);
        googleButton.setPrefHeight(44); // fixed height
        HBox.setHgrow(googleButton, Priority.ALWAYS);

        githubButton.getStyleClass().clear(); 
        githubButton.getStyleClass().add("social-button");
        githubButton.setMaxWidth(Double.MAX_VALUE);
        githubButton.setPrefHeight(44); // fixed height
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
