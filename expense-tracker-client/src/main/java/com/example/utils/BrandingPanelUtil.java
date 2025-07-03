package com.example.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BrandingPanelUtil {
    public static VBox createBrandingPanel(){
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

    private static HBox createStatsSection() {
        HBox statsSection = new HBox(32);
        statsSection.getStyleClass().add("stats-container");
        statsSection.setAlignment(Pos.CENTER);

        VBox stat1 = createStatItem("$2.5M+", "Money Tracked");
        VBox stat2 = createStatItem("50K+", "Active Users");
        VBox stat3 = createStatItem("99.9%", "Uptime");

        statsSection.getChildren().addAll(stat1, stat2, stat3);
        return statsSection;
    }

    private static VBox createStatItem(String number, String label) {
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

    private static VBox createFeaturesSection() {
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

    private static HBox createFeatureItem(String icon, String title, String description){
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

    private static VBox createHeadingSection() {
        VBox headingSection = new VBox(8);

        Label mainHeading = new Label("Take Control of Your");
        mainHeading.getStyleClass().add("main-heading");

        Label accentHeading = new Label("Finances");
        accentHeading.getStyleClass().add("main-heading-accent");

        headingSection.getChildren().addAll(mainHeading, accentHeading);
        return headingSection;
    }

    private static HBox createLogoSection() {

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

    // helper method to create proper blur decorations
    private static Circle createBlurDecoration(double width, double height, String color, double opacity) {
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
}
