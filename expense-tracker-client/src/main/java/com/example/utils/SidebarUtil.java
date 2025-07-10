package com.example.utils;

import com.example.views.AuthView;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class SidebarUtil {

    private static AuthView colorController = new AuthView();
    
    private static boolean sidebarExpanded = false; // start closed
    private static boolean themePickerVisible = false; // only visible when sidebar is expanded
    
    private static VBox currentSidebar;
    private static Label toggleIcon;
    private static Button themePickerBtn;
    private static VBox themePickerBox;
    private static ScrollPane currentThemeContent;
    private static ScrollPane themePickerScrollPane;    
    
    private static VBox currentFeaturesPreview;
    private static VBox featuresPreview;
    private static HBox logoSection;

    private static Button[] themeButtons = new Button[7];
    private static int currentActiveIndex = 1; // track which button is currently active

    private static void changeTheme(String themeName){
        if(colorController == null) return;
        System.out.println("Changing theme to: " + themeName);
        colorController.switchTheme(themeName); 

        //if (currentThemeOption != null){ 
        //  currentThemeOption.getStyleClass().add("theme-picker-option-active");
        //}
    }
    
    public static VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(sidebarExpanded ? 320 : 64);
        sidebar.setMinWidth(64);
        sidebar.setMaxWidth(320);
        currentSidebar = sidebar;
        
        HBox header = createSidebarHeader();
        VBox navigation = createSidebarNavigation();

        themePickerBox = createThemePickerBox();
        themePickerBox.setVisible(true);
        themePickerBox.setManaged(true);

        themePickerScrollPane = new ScrollPane(themePickerBox);
        themePickerScrollPane.setFitToWidth(true);
        themePickerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        themePickerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        themePickerScrollPane.setMaxHeight(0); // Start collapsed
        themePickerScrollPane.setMinHeight(0);
        themePickerScrollPane.setPrefHeight(0);
        themePickerScrollPane.setVisible(false);
        themePickerScrollPane.setManaged(false);
        themePickerScrollPane.getStyleClass().add("theme-picker-scroll-hidden-bar");

        featuresPreview = createFeaturesPreview();
        featuresPreview.setPadding(new Insets(0, 0, 0, 0));
        featuresPreview.setVisible(sidebarExpanded);
        featuresPreview.setManaged(sidebarExpanded);

        VBox footer = createSidebarFooter();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        sidebar.getStyleClass().add("default-sidebar");
        sidebar.getChildren().addAll(header, navigation, themePickerScrollPane, featuresPreview, spacer, footer);
        return sidebar;
    }
    
    private static HBox createSidebarHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setMinHeight(62);
        header.setAlignment(Pos.CENTER_LEFT);
        
        logoSection = new HBox(12);
        logoSection.setAlignment(Pos.CENTER_LEFT);
        
        Label logoText = new Label("📜"); 
        logoText.getStyleClass().add("sidebar-logo-icon");
        
        Label appTitle = new Label("CozyTracker");
        appTitle.getStyleClass().add("sidebar-title");
        
        logoSection.getChildren().addAll(logoText, appTitle);
        logoSection.setVisible(sidebarExpanded);
        logoSection.setManaged(sidebarExpanded);
        
        Button toggleBtn = new Button();
        toggleBtn.setFocusTraversable(false);
        toggleBtn.getStyleClass().add("sidebar-toggle-button");

        toggleIcon = new Label(sidebarExpanded ? "<" : ">");
        toggleIcon.getStyleClass().add("sidebar-toggle-icon");
        toggleBtn.setGraphic(toggleIcon);
        
        toggleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleSidebar();
            }
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(logoSection, spacer, toggleBtn);
        return header;
    }
    
    private static VBox createSidebarNavigation() {
        VBox navigation = new VBox(8);
        navigation.getStyleClass().add("sidebar-navigation");
        navigation.setPadding(new Insets(5, 5, 5, 5));
        navigation.setMinHeight(64);
        navigation.setAlignment(Pos.CENTER);
        
        themePickerBtn = new Button();
        themePickerBtn.setFocusTraversable(false);
        themePickerBtn.getStyleClass().add("sidebar-nav-button");
        
        updateNavigationButton(themePickerBtn);
        
        themePickerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleThemePicker();
            }
        });

        navigation.getChildren().add(themePickerBtn);
        return navigation;
    }
    
    private static void updateNavigationButton(Button themePickerBtn) {
        if (sidebarExpanded) {
            HBox btnContent = new HBox(8);
            btnContent.setAlignment(Pos.CENTER_LEFT);
            btnContent.setPadding(new Insets(4, 4, 4, 4));

            Label icon = new Label("🎨");
            icon.getStyleClass().add("sidebar-nav-button-text");
            icon.setPadding(new Insets(0, 0, 0, 4));

            Label text = new Label("Customize Theme");
            text.getStyleClass().add("sidebar-nav-button-text");

            btnContent.getChildren().addAll(icon, text);
            themePickerBtn.setGraphic(btnContent);
            themePickerBtn.setMaxWidth(Double.MAX_VALUE);
        } else {
            Label icon = new Label("🎨");
            themePickerBtn.setGraphic(icon);
            themePickerBtn.setVisible(true);
        }
    }
    
    private static VBox createThemePicker() {
        VBox themePicker = new VBox(12);
        themePicker.getStyleClass().add("theme-picker-card");
        themePicker.setPadding(new Insets(0));

        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("🎨");
        icon.getStyleClass().add("theme-picker-text-icon");

        Label title = new Label("Theme Customizer");
        title.getStyleClass().add("theme-picker-text");
        header.getChildren().addAll(icon, title);

        VBox themes = new VBox(8);

        themeButtons[0] = createThemeOption("Cozy Café", "Coffee & mint", 0, "#815331", "#9b6c4b", "#ad876d");
        themeButtons[1] = createThemeOption("Soft Amethyst", "Lilac & rose", 1, "#e995dc", "#f7c6fd", "#faddfa");
        themeButtons[2] = createThemeOption("Cherry Blossom", "Pink & gentle", 2, "#d97390", "#bf5a7a", "#fef4f7");
        themeButtons[3] = createThemeOption("Sunset Cove", "Peach & lavender", 3, "#e55a5a", "#ff6b6b", "#fff2eb");
        themeButtons[4] = createThemeOption("Ocean Breeze", "Blue & teal", 4, "#4682b4", "#2f4f4f", "#f4faff");
        themeButtons[5] = createThemeOption("Pixel Garden", "Brown & sage", 5, "#654321", "#8b4513", "#f2f6e2");
        themeButtons[6] = createThemeOption("Twilight Purple", "Deep & mystical", 6, "#7c3aed", "#5b21b6", "#b3a2c2");

        for (int i = 0; i < themeButtons.length; i++) {
            final int index = i;

            themeButtons[i].setOnMouseClicked(e -> {
                currentActiveIndex = index;
                System.out.println("Theme button clicked: " + themeButtons[index].getText());
                switchToTheme(currentActiveIndex, getThemeNameByIndex(currentActiveIndex));
            });
        }

        themes.getChildren().addAll(themeButtons);
        HBox footer = new HBox();
        footer.setPadding(new Insets(0));
        footer.getStyleClass().add("theme-picker-active-badge");

        Label activeBadge = new Label("Cozy Café Active");
        activeBadge.getStyleClass().add("theme-picker-text");
        footer.getChildren().add(activeBadge);

        themePicker.getChildren().addAll(header, themes, footer);
        return themePicker;
    }

    private static void switchToTheme(int newActiveIndex, String themeName) {
        System.out.println("Switching to theme index: " + newActiveIndex);
        if (newActiveIndex < 0 || newActiveIndex >= themeButtons.length) {
            return;
        }

        for (int i = 0; i < themeButtons.length; i++) {
            if (themeButtons[i] != null) {
                themeButtons[i].getStyleClass().remove("theme-picker-option-active");
            }
        }

        currentActiveIndex = newActiveIndex;

        if (themeButtons[newActiveIndex] != null) {
            themeButtons[newActiveIndex].getStyleClass().add("theme-picker-option-active");
        }

        changeTheme(themeName);
        System.out.println("Theme switched to: " + themeName);
    }
    
    private static String getThemeNameByIndex(int index) {
        String[] themeNames = {
            "Cafe", "PixelGarden", "CherryBlossom", 
            "SunsetCove", "OceanBreeze", "ForestCabin", "TwilightPurple"
        };
        return (index >= 0 && index < themeNames.length) ? themeNames[index] : "Unknown";
    }
    
    private static void updateActiveBadge(String themeName) {
        System.out.println("Updating active badge to: " + themeName);
        // TODO: Implement logic to update the active badge text
    }

    private static Circle createColorSwatch(String color) {
        Circle swatch = new Circle(10);
        swatch.setFill(Color.web(color));
        return swatch;
    }
    
    private static Button createThemeOption(String name, String description, int index, String color1, String color2, String color3) {
        Button themeBtn = new Button();
        themeBtn.getStyleClass().add("theme-picker-option");
        themeBtn.setMinWidth(180); 
        themeBtn.setPrefWidth(220); 

        HBox content = new HBox(12);
        content.setAlignment(Pos.CENTER_LEFT);

        HBox swatches = new HBox(4);
        swatches.setAlignment(Pos.CENTER_LEFT);
        swatches.getChildren().addAll(
            createColorSwatch(color1),
            createColorSwatch(color2),
            createColorSwatch(color3)
        );

        VBox textContent = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("theme-option-name");
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("theme-option-desc");
        textContent.getChildren().addAll(nameLabel, descLabel);

        content.getChildren().addAll(swatches, textContent);
        themeBtn.setGraphic(content);

        if (index == currentActiveIndex) {
            themeBtn.getStyleClass().add("theme-picker-option-active");
        }

        return themeBtn;
    }

    private static VBox createFeaturesPreview() {
        VBox preview = new VBox(12);
        preview.setAlignment(Pos.CENTER);
        
        VBox previewCard = AuthPanelUtil.createCozyFeaturePreview();
        preview.getChildren().add(previewCard);
        
        currentFeaturesPreview = preview; 
        return preview;
    }
    
    private static VBox createSidebarFooter() {
        VBox footer = new VBox();
        footer.getStyleClass().add("sidebar-footer");
        footer.setPadding(new Insets(16));
        footer.setAlignment(Pos.CENTER);
        
        if (sidebarExpanded) {
            Label text = new Label("Hello, CozyTracker!");
            text.getStyleClass().add("footer-text");
            footer.getChildren().add(text);
        } else {
            Label icon = new Label("⭐");
            icon.getStyleClass().add("footer-icon");
            footer.getChildren().add(icon);
        }
        
        return footer;
    }
    
    public static void toggleSidebar() {
        System.out.println("Theme Picker Visible: " + themePickerVisible + " at toggleSidebar");

        sidebarExpanded = !sidebarExpanded;
        double endWidth = sidebarExpanded ? 320 : 64;

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(currentSidebar.prefWidthProperty(), endWidth);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(130), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        timeline.setOnFinished(e -> {
            toggleIcon.setText(sidebarExpanded ? "<" : ">");
            logoSection.setVisible(sidebarExpanded);
            logoSection.setManaged(sidebarExpanded);
            updateNavigationButton(themePickerBtn);

            if (currentFeaturesPreview != null) {
                currentFeaturesPreview.setVisible(sidebarExpanded);
                currentFeaturesPreview.setManaged(sidebarExpanded);
            }

            VBox footer = (VBox) currentSidebar.lookup(".sidebar-footer");
            if (footer != null) {
                updateFooterContent(footer);
            }
        });

        timeline.play();

        FadeTransition fade = new FadeTransition(Duration.millis(150), currentSidebar);
        fade.setFromValue(1.0);
        fade.setToValue(0.8);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.play();

        updateSidebarVisibility(currentThemeContent, currentFeaturesPreview);

        featuresPreview.setVisible(sidebarExpanded);
        featuresPreview.setManaged(sidebarExpanded);

        if (!sidebarExpanded) {
            themePickerVisible = false;
            themePickerScrollPane.setVisible(false);
            themePickerScrollPane.setManaged(false);
            themePickerScrollPane.setMaxHeight(0);
            themePickerScrollPane.setMinHeight(0);
            themePickerScrollPane.setPrefHeight(0);
            themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
        }

        System.out.println("themePickerVisible: " + themePickerVisible + " after toggleSidebar");
    }

    private static void updateFooterContent(VBox footer) {
        footer.getChildren().clear();

        if (sidebarExpanded) {
            Label text = new Label("Hello, CozyTracker!");
            text.getStyleClass().add("footer-text");
            footer.getChildren().add(text);
        } else {
            Label icon = new Label("⭐");
            icon.getStyleClass().add("footer-icon");
            footer.getChildren().add(icon);
        }
    }
    
    public static void toggleThemePicker() {
        if (!sidebarExpanded) {
            toggleSidebar();
        }

        System.out.println("Theme Picker Visible: " + themePickerVisible + " at toggleThemePicker");
        themePickerVisible = !themePickerVisible;

        System.out.println("Theme Picker Visible: " + themePickerVisible + " after toggling");

        double expandedHeight = 320;
        double collapsedHeight = 0;

        if (themePickerVisible) {
            themePickerBtn.getStyleClass().add("sidebar-nav-button-active");
            themePickerScrollPane.setVisible(true);
            themePickerScrollPane.setManaged(true);

            Timeline expand = new Timeline(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(themePickerScrollPane.maxHeightProperty(), collapsedHeight),
                    new KeyValue(themePickerScrollPane.minHeightProperty(), collapsedHeight),
                    new KeyValue(themePickerScrollPane.prefHeightProperty(), collapsedHeight)
                ),
                new KeyFrame(Duration.millis(200),
                    new KeyValue(themePickerScrollPane.maxHeightProperty(), expandedHeight),
                    new KeyValue(themePickerScrollPane.minHeightProperty(), expandedHeight),
                    new KeyValue(themePickerScrollPane.prefHeightProperty(), expandedHeight)
                )
            );
            expand.play();

            System.out.println("Theme Picker Expanded: " + themePickerVisible);
        } else {
            themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
            Timeline collapse = new Timeline(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(themePickerScrollPane.maxHeightProperty(), themePickerScrollPane.getHeight()),
                    new KeyValue(themePickerScrollPane.minHeightProperty(), themePickerScrollPane.getHeight()),
                    new KeyValue(themePickerScrollPane.prefHeightProperty(), themePickerScrollPane.getHeight())
                ),
                new KeyFrame(Duration.millis(200),
                    new KeyValue(themePickerScrollPane.maxHeightProperty(), collapsedHeight),
                    new KeyValue(themePickerScrollPane.minHeightProperty(), collapsedHeight),
                    new KeyValue(themePickerScrollPane.prefHeightProperty(), collapsedHeight)
                )
            );
            collapse.setOnFinished(e -> {
                themePickerScrollPane.setVisible(false);
                themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
                themePickerScrollPane.setManaged(false);
            });
            collapse.play();
            System.out.println("Theme Picker Collapsed: " + themePickerVisible + " after collapse");
        }
        //themePickerVisible = !themePickerVisible;
        System.out.println("Theme Picker Visible: " + themePickerVisible + " after toggleThemePicker");

    }

    private static void updateSidebarVisibility(ScrollPane themeContent, VBox featuresPreview) {
        if (themeContent != null && featuresPreview != null) {
            themeContent.setVisible(sidebarExpanded && themePickerVisible);
            themeContent.setManaged(sidebarExpanded && themePickerVisible);
            
            featuresPreview.setVisible(sidebarExpanded && !themePickerVisible);
            featuresPreview.setManaged(sidebarExpanded && !themePickerVisible);
        }
    }

    private static VBox createThemePickerBox() {
        VBox box = new VBox(12);
        box.setPadding(new Insets(10));
        box.getChildren().add(createThemePicker());
        return box;
    }
}