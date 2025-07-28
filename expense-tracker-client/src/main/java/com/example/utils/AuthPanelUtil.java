package com.example.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

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
        
        SVGPath previewIcon = new SVGPath();
        previewIcon.setContent("M8.58,17.25L9.5,13.36L6.5,10.78L10.45,10.41L12,6.8L13.55,10.45L17.5,10.78L14.5,13.36L15.42,17.25L12,15.19L8.58,17.25M12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2M12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20A8,8 0 0,0 20,12A8,8 0 0,0 12,4Z");
        previewIcon.setFill(Color.valueOf("#000000"));
        previewIcon.setScaleX(1.1);
        previewIcon.setScaleY(1.1);

        Label title = new Label("Incoming Features");   
        title.getStyleClass().add("preview-card-header");
        header.getChildren().addAll(previewIcon, title);
        
        // Features
        VBox features = new VBox(12);

        SVGPath financeIcon = new SVGPath();
        financeIcon.setContent("M6,16.5L3,19.44V11H6M11,14.66L9.43,13.32L8,14.64V7H11M16,13L13,16V3H16M18.81,12.81L17,11H22V16L20.21,14.21L13,21.36L9.53,18.34L5.75,22H3L9.47,15.66L13,18.64");

        SVGPath moneyIcon = new SVGPath();
        moneyIcon.setContent("M7,15H9C9,16.08 10.37,17 12,17C13.63,17 15,16.08 15,15C15,13.9 13.96,13.5 11.76,12.97C9.64,12.44 7,11.78 7,9C7,7.21 8.47,5.69 10.5,5.18V3H13.5V5.18C15.53,5.69 17,7.21 17,9H15C15,7.92 13.63,7 12,7C10.37,7 9,7.92 9,9C9,10.1 10.04,10.5 12.24,11.03C14.36,11.56 17,12.22 17,15C17,16.79 15.53,18.31 13.5,18.82V21H10.5V18.82C8.47,18.31 7,16.79 7,15Z");
        
        SVGPath notificationIcon = new SVGPath();
        notificationIcon.setContent("M23 4.5C23 6.43 21.43 8 19.5 8S16 6.43 16 4.5 17.57 1 19.5 1 23 2.57 23 4.5M19.5 10C19.33 10 19.17 10 19 10V19H5V5H14.03C14 4.84 14 4.67 14 4.5C14 4 14.08 3.5 14.21 3H5C3.89 3 3 3.89 3 5V19C3 20.11 3.9 21 5 21H19C20.11 21 21 20.11 21 19V9.79C20.5 9.92 20 10 19.5 10Z");
    
        SVGPath linkIcon = new SVGPath();
        linkIcon.setContent("M10.59,13.41C11,13.8 11,14.44 10.59,14.83C10.2,15.22 9.56,15.22 9.17,14.83C7.22,12.88 7.22,9.71 9.17,7.76V7.76L12.71,4.22C14.66,2.27 17.83,2.27 19.78,4.22C21.73,6.17 21.73,9.34 19.78,11.29L18.29,12.78C18.3,11.96 18.17,11.14 17.89,10.36L18.36,9.88C19.54,8.71 19.54,6.81 18.36,5.64C17.19,4.46 15.29,4.46 14.12,5.64L10.59,9.17C9.41,10.34 9.41,12.24 10.59,13.41M13.41,9.17C13.8,8.78 14.44,8.78 14.83,9.17C16.78,11.12 16.78,14.29 14.83,16.24V16.24L11.29,19.78C9.34,21.73 6.17,21.73 4.22,19.78C2.27,17.83 2.27,14.66 4.22,12.71L5.71,11.22C5.7,12.04 5.83,12.86 6.11,13.65L5.64,14.12C4.46,15.29 4.46,17.19 5.64,18.36C6.81,19.54 8.71,19.54 9.88,18.36L13.41,14.83C14.59,13.66 14.59,11.76 13.41,10.59C13,10.2 13,9.56 13.41,9.17Z");
        
        HBox feature1 = createFeaturePreviewItem(financeIcon, "Expense Forecasting", "Predict future spending.");
        HBox feature2 = createFeaturePreviewItem(moneyIcon, "Dynamic Budgeting", "Create adaptive budgets.");
        HBox feature3 = createFeaturePreviewItem(notificationIcon, "Real-Time Alerts", "Get notified of unusual spending.");
        HBox feature4 = createFeaturePreviewItem(linkIcon, "Bank Integration", "Connect with your bank for tracking.");
        
        features.getChildren().addAll(feature1, feature2, feature3, feature4);
        
        previewCard.getChildren().addAll(header, features);
        return previewCard;
    }

    private static HBox createFeaturePreviewItem(SVGPath icon, String title, String description) {
        HBox item = new HBox(14);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("feature-preview-item");
        item.setPadding(new Insets(4, 8, 4, 8));
        
        SVGPath clonIcon = new SVGPath();
        clonIcon.setContent(icon.getContent());
        clonIcon.setFill(Color.valueOf("#000000"));
        clonIcon.setScaleX(0.8);
        clonIcon.setScaleY(0.8);   
        
        VBox textContainer = new VBox(4);
        textContainer.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("preview-card-title");
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("preview-card-description");

        textContainer.getChildren().addAll(titleLabel, descriptionLabel);
        item.getChildren().addAll(clonIcon, textContainer);
        item.getStyleClass().add("preview-card-item");
        return item;
    }
}