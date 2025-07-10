package com.example.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// class made to provide utility methods for creating cozy-styled UI components
public class AuthPanelUtil {

    public static VBox createCozyFeaturePreview() {
        VBox previewCard = new VBox(12);
        previewCard.setAlignment(Pos.CENTER);
        previewCard.getStyleClass().add("preview-card");
        previewCard.setMaxHeight(100);;
        
        previewCard.setPadding(new Insets(16));
        
        // Header
        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label sparkle = new Label("✨");
        Label title = new Label("What's Coming");   
        header.getChildren().addAll(sparkle, title);
        
        // Features
        VBox features = new VBox(12);
        
        HBox feature1 = createFeaturePreviewItem("💰", "Expense Forecasting", "Predict future spending.");
        HBox feature2 = createFeaturePreviewItem("📈", "Dynamic Budgeting", "Create adaptive budgets.");
        HBox feature3 = createFeaturePreviewItem("🔔", "Real-Time Alerts", "Get notified of unusual spending.");
        HBox feature4 = createFeaturePreviewItem("🔗", "Bank Integration", "Connect with your bank for tracking.");
        
        features.getChildren().addAll(feature1, feature2, feature3, feature4);
        
        previewCard.getChildren().addAll(header, features);
        return previewCard;
    }

    private static HBox createFeaturePreviewItem(String icon, String title, String description) {
        HBox item = new HBox(14);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("feature-preview-item");
        item.setPadding(new Insets(4, 8, 4, 8));
        
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("preview-card-icon");   
        
        VBox textContainer = new VBox(4);
        textContainer.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("preview-card-title");
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("preview-card-description");

        textContainer.getChildren().addAll(titleLabel, descriptionLabel);
        item.getChildren().addAll(iconLabel, textContainer);
        item.getStyleClass().add("preview-card-item");
        return item;
    }
}