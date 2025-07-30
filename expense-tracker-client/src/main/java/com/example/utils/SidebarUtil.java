package com.example.utils;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class SidebarUtil {

    public enum SidebarType {
        AUTH_VIEW,
        DASHBOARD_VIEW,
        REPORTS_VIEW
    }
    
    private static boolean sidebarExpanded = false;
    private static boolean themePickerVisible = false;
    
    private static SVGPath expandIcon;
    private static SVGPath collapseIcon;

    static {
        expandIcon = new SVGPath();
        expandIcon.setContent("M8.59,16.58L13.17,12L8.59,7.41L10,6L16,12L10,18L8.59,16.58Z");
        expandIcon.setFill(Color.BLACK);
        expandIcon.setScaleX(1.2);
        expandIcon.setScaleY(1.2);

        collapseIcon = new SVGPath();
        collapseIcon.setContent("M15.41,16.58L10.83,12L15.41,7.41L14,6L8,12L14,18L15.41,16.58Z");
        collapseIcon.setFill(Color.BLACK);
        collapseIcon.setScaleX(1.2);
        collapseIcon.setScaleY(1.2);
    }

    private static VBox currentSidebar;
    private static SVGPath toggleIcon = expandIcon; // sidebar starts closed
    private static Button themePickerBtn;
    private static VBox themePickerBox;
    private static ScrollPane themePickerScrollPane;    
    
    private static VBox currentFeaturesPreview;
    private static VBox featuresPreview;
    private static HBox logoSection;

    private static Button[] themeButtons = new Button[7];
    private static int currentActiveIndex = 0; // default theme

    // Dashboard-specific navigation buttons
    private static Button dashboardBtn;
    private static Button reportsBtn;
    private static Button goalsBtn;
    private static Button logoutBtn;

    // current sidebar type
    private static SidebarType currentSidebarType = SidebarType.AUTH_VIEW;

    // callback interfaces for navigation actions
    public interface NavigationCallback {
        void onNavigate(String destination);
    }

    private static NavigationCallback navigationCallback;

    // set the navigation callback for dashboard actions
    public static void setNavigationCallback(NavigationCallback callback) {
        navigationCallback = callback;
    }

    private static void changeTheme(String themeName){
        ThemeManager.getInstance().setCurrentTheme(themeName);
    }
    
    public static VBox createSidebar(SidebarType sidebarType) {
        currentSidebarType = sidebarType;
        
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(sidebarExpanded ? 320 : 64);
        sidebar.setMinWidth(64);
        sidebar.setMaxWidth(320);
        currentSidebar = sidebar;
        
        HBox header = createSidebarHeader();
        VBox navigation = createSidebarNavigation(sidebarType);

        themePickerBox = createThemePickerBox();
        themePickerBox.setVisible(true);
        themePickerBox.setManaged(true);

        themePickerScrollPane = new ScrollPane(themePickerBox);
        themePickerScrollPane.setFitToWidth(true);
        themePickerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        themePickerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        themePickerScrollPane.setMaxHeight(0);
        themePickerScrollPane.setMinHeight(0);
        themePickerScrollPane.setPrefHeight(0);
        themePickerScrollPane.setVisible(false);
        themePickerScrollPane.setManaged(false);
        themePickerScrollPane.getStyleClass().add("theme-picker-scroll-hidden-bar");

        // create appropriate content based on sidebar type
        VBox contentSection = createContentSection(sidebarType);

        VBox footer = createSidebarFooter();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        sidebar.getStyleClass().add("default-sidebar");
        sidebar.getChildren().addAll(header, navigation, themePickerScrollPane, contentSection, spacer, footer);
        return sidebar;
    }

    private static VBox createContentSection(SidebarType sidebarType) {
        VBox contentSection = new VBox();
        
        if (sidebarType == SidebarType.AUTH_VIEW) {
            // Features preview for auth view
            featuresPreview = createFeaturesPreview();
            featuresPreview.setPadding(new Insets(0, 0, 0, 0));
            featuresPreview.setVisible(sidebarExpanded && !themePickerVisible);
            featuresPreview.setManaged(sidebarExpanded && !themePickerVisible);
            contentSection = featuresPreview;
        } else if (sidebarType == SidebarType.DASHBOARD_VIEW) {
            // Additional dashboard content could go here if needed
            // For now, just return empty content section
            contentSection.setVisible(sidebarExpanded && !themePickerVisible);
            contentSection.setManaged(sidebarExpanded && !themePickerVisible);
        } else if (sidebarType == SidebarType.REPORTS_VIEW) {
            contentSection.setVisible(sidebarExpanded && !themePickerVisible);
            contentSection.setManaged(sidebarExpanded && !themePickerVisible);
        }
        
        currentFeaturesPreview = contentSection;
        return contentSection;
    }
    
    private static HBox createSidebarHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10,10,10,10));
        header.setMinHeight(62);
        header.setAlignment(Pos.CENTER_LEFT);
        
        logoSection = new HBox(12);
        logoSection.setAlignment(Pos.CENTER_LEFT);
        
        SVGPath logoIcon = new SVGPath();
        logoIcon.setContent("M15 10C15 9.45 15.45 9 16 9C16.55 9 17 9.45 17 10S16.55 11 16 11 15 10.55 15 10M22 7.5V14.47L19.18 15.41L17.5 21H12V19H10V21H4.5C4.5 21 2 12.54 2 9.5S4.46 4 7.5 4H12.5C13.41 2.79 14.86 2 16.5 2C17.33 2 18 2.67 18 3.5C18 3.71 17.96 3.9 17.88 4.08C17.74 4.42 17.62 4.81 17.56 5.23L19.83 7.5H22M20 9.5H19L15.5 6C15.5 5.35 15.59 4.71 15.76 4.09C14.79 4.34 14 5.06 13.67 6H7.5C5.57 6 4 7.57 4 9.5C4 11.38 5.22 16.15 6 19H8V17H14V19H16L17.56 13.85L20 13.03V9.5Z");
        logoIcon.setFill(Color.valueOf("#000000"));
        logoIcon.setScaleX(1.3);
        logoIcon.setScaleY(1.3);

        logoIcon.getStyleClass().add("sidebar-logo-icon");
        
        Label appTitle = new Label("CozyTracker");
        appTitle.getStyleClass().add("sidebar-title");
        
        logoSection.getChildren().addAll(logoIcon, appTitle);
        logoSection.setVisible(sidebarExpanded);
        logoSection.setManaged(sidebarExpanded);
        
        Button toggleBtn = new Button();
        toggleBtn.setFocusTraversable(false);
        toggleBtn.getStyleClass().add("sidebar-toggle-button");

        toggleIcon = sidebarExpanded ? collapseIcon : expandIcon;
        toggleBtn.setGraphic(toggleIcon);
        
        toggleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleSidebar();
                toggleBtn.setGraphic(sidebarExpanded ? collapseIcon : expandIcon);
            }
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(logoSection, spacer, toggleBtn);
        return header;
    }
    
    private static VBox createSidebarNavigation(SidebarType sidebarType) {
        VBox navigation = new VBox(8);
        navigation.getStyleClass().add("sidebar-navigation");
        navigation.setPadding(new Insets(5, 5, 5, 5));
        navigation.setMinHeight(64);
        navigation.setAlignment(Pos.CENTER);

        // create navigation SVG icons
        SVGPath dashboardIcon = new SVGPath();
        dashboardIcon.setContent("M19,5V7H15V5H19M9,5V11H5V5H9M19,13V19H15V13H19M9,17V19H5V17H9M21,3H13V9H21V3M11,3H3V13H11V3M21,11H13V21H21V11M11,15H3V21H11V15Z");
        SVGPath reportsIcon = new SVGPath();
        reportsIcon.setContent("M16,11.78L20.24,4.45L21.97,5.45L16.74,14.5L10.23,10.75L5.46,19H22V21H2V3H4V17.54L9.5,8L16,11.78Z");
        SVGPath themePickerIcon = new SVGPath();
        themePickerIcon.setContent("M12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2C17.5,2 22,6 22,11A6,6 0 0,1 16,17H14.2C13.9,17 13.7,17.2 13.7,17.5C13.7,17.6 13.8,17.7 13.8,17.8C14.2,18.3 14.4,18.9 14.4,19.5C14.5,20.9 13.4,22 12,22M12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C12.3,20 12.5,19.8 12.5,19.5C12.5,19.3 12.4,19.2 12.4,19.1C12,18.6 11.8,18.1 11.8,17.5C11.8,16.1 12.9,15 14.3,15H16A4,4 0 0,0 20,11C20,7.1 16.4,4 12,4M6.5,10C7.3,10 8,10.7 8,11.5C8,12.3 7.3,13 6.5,13C5.7,13 5,12.3 5,11.5C5,10.7 5.7,10 6.5,10M9.5,6C10.3,6 11,6.7 11,7.5C11,8.3 10.3,9 9.5,9C8.7,9 8,8.3 8,7.5C8,6.7 8.7,6 9.5,6M14.5,6C15.3,6 16,6.7 16,7.5C16,8.3 15.3,9 14.5,9C13.7,9 13,8.3 13,7.5C13,6.7 13.7,6 14.5,6M17.5,10C18.3,10 19,10.7 19,11.5C19,12.3 18.3,13 17.5,13C16.7,13 16,12.3 16,11.5C16,10.7 16.7,10 17.5,10Z");
        SVGPath goalsIcon = new SVGPath();
        goalsIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,10.84 21.79,9.69 21.39,8.61L19.79,10.21C19.93,10.8 20,11.4 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4C12.6,4 13.2,4.07 13.79,4.21L15.4,2.6C14.31,2.21 13.16,2 12,2M19,2L15,6V7.5L12.45,10.05C12.3,10 12.15,10 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12C14,11.85 14,11.7 13.95,11.55L16.5,9H18L22,5H19V2M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12H16A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8V6Z");
        SVGPath logoutIcon = new SVGPath();
        logoutIcon.setContent("M12,3C10.89,3 10,3.89 10,5H3V19H2V21H22V19H21V5C21,3.89 20.11,3 19,3H12M12,5H19V19H12V5M5,11H7V13H5V11Z");

        
        if (sidebarType == SidebarType.AUTH_VIEW) {
            // Only theme picker for auth view
            themePickerBtn = createNavigationButton(themePickerIcon, "Customize Theme");
            themePickerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    toggleThemePicker();
                }
            });
            navigation.getChildren().add(themePickerBtn);
            
        } else if (sidebarType == SidebarType.DASHBOARD_VIEW) {
            dashboardBtn = createNavigationButton(dashboardIcon, "Dashboard");
            setActiveNavButton(dashboardBtn); // default active
            dashboardBtn.setOnMouseClicked(e -> {
                setActiveNavButton(dashboardBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("dashboard");
            });
            
            reportsBtn = createNavigationButton(reportsIcon, "Reports");
            reportsBtn.setOnMouseClicked(e -> {
                setActiveNavButton(reportsBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("reports");
            });
            
            themePickerBtn = createNavigationButton(themePickerIcon, "Customize Theme");
            themePickerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    toggleThemePicker();
                }
            });
            
            goalsBtn = createNavigationButton(goalsIcon, "Goals");
            goalsBtn.setOnMouseClicked(e -> {
                setActiveNavButton(goalsBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("goals");
            });
            
            logoutBtn = createNavigationButton(logoutIcon, "Logout");
            logoutBtn.setOnMouseClicked(e -> {
                if (navigationCallback != null) navigationCallback.onNavigate("logout");
            });
            
            navigation.getChildren().addAll(dashboardBtn, reportsBtn, themePickerBtn, goalsBtn, logoutBtn);
        } else if (sidebarType == SidebarType.REPORTS_VIEW){
            dashboardBtn = createNavigationButton(dashboardIcon, "Dashboard");
            dashboardBtn.setOnMouseClicked(e -> {
                setActiveNavButton(dashboardBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("dashboard");
            });
            
            reportsBtn = createNavigationButton(reportsIcon, "Reports");
            setActiveNavButton(reportsBtn); // default active
            reportsBtn.setOnMouseClicked(e -> {
                setActiveNavButton(reportsBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("reports");
            });
            
            themePickerBtn = createNavigationButton(themePickerIcon, "Customize Theme");
            themePickerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    toggleThemePicker();
                }
            });
            
            goalsBtn = createNavigationButton(goalsIcon, "Goals");
            goalsBtn.setOnMouseClicked(e -> {
                setActiveNavButton(goalsBtn);
                if (navigationCallback != null) navigationCallback.onNavigate("goals");
            });
            
            logoutBtn = createNavigationButton(logoutIcon, "Logout");
            logoutBtn.setOnMouseClicked(e -> {
                if (navigationCallback != null) navigationCallback.onNavigate("logout");
            });
            
            navigation.getChildren().addAll(dashboardBtn, reportsBtn, themePickerBtn, goalsBtn, logoutBtn);
        }

        return navigation;
    }

    private static Button createNavigationButton(SVGPath icon, String text) {
        Button button = new Button();
        button.setFocusTraversable(false);
        button.getStyleClass().add("sidebar-nav-button");
        
        updateNavigationButton(button, icon, text);
        return button;
    }
    
    private static void updateNavigationButton(Button button, SVGPath icon, String text) {
        if (sidebarExpanded) {
            HBox btnContent = new HBox(8);
            btnContent.setAlignment(Pos.CENTER_LEFT);
            btnContent.setPadding(new Insets(4, 4, 4, 4));

            SVGPath iconClon = new SVGPath();
            iconClon.setContent(icon.getContent());
            iconClon.setFill(Color.valueOf("#000000"));

            Label textLabel = new Label(text);
            textLabel.getStyleClass().add("sidebar-nav-button-text");

            btnContent.getChildren().addAll(iconClon, textLabel);
            button.setGraphic(btnContent);
            button.setMaxWidth(Double.MAX_VALUE);
        } else {
            HBox btnContent = new HBox();
            btnContent.setAlignment(Pos.CENTER);
            btnContent.setPadding(new Insets(8, 4, 8, 4));
            //btnContent.setMinHeight(); 

            SVGPath iconClon = new SVGPath();
            iconClon.setContent(icon.getContent());
            iconClon.setFill(Color.valueOf("#000000"));
            btnContent.getChildren().add(iconClon);
            
            button.setGraphic(btnContent);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setVisible(true);
        }
    }

    private static void setActiveNavButton(Button activeButton) {
        if (dashboardBtn != null) dashboardBtn.getStyleClass().remove("sidebar-nav-button-active");
        if (reportsBtn != null) reportsBtn.getStyleClass().remove("sidebar-nav-button-active");
        if (goalsBtn != null) goalsBtn.getStyleClass().remove("sidebar-nav-button-active");
        
        // Add active class to the clicked button (exclude theme picker and logout)
        if (activeButton != null && activeButton != themePickerBtn && activeButton != logoutBtn) {
            activeButton.getStyleClass().add("sidebar-nav-button-active");
        }
    }

    private static void updateAllNavigationButtons() {
        // load SVG icons
        SVGPath dashboardIcon = new SVGPath();
        dashboardIcon.setContent("M19,5V7H15V5H19M9,5V11H5V5H9M19,13V19H15V13H19M9,17V19H5V17H9M21,3H13V9H21V3M11,3H3V13H11V3M21,11H13V21H21V11M11,15H3V21H11V15Z");
        SVGPath reportsIcon = new SVGPath();
        reportsIcon.setContent("M16,11.78L20.24,4.45L21.97,5.45L16.74,14.5L10.23,10.75L5.46,19H22V21H2V3H4V17.54L9.5,8L16,11.78Z");
        SVGPath themePickerIcon = new SVGPath();
        themePickerIcon.setContent("M12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2C17.5,2 22,6 22,11A6,6 0 0,1 16,17H14.2C13.9,17 13.7,17.2 13.7,17.5C13.7,17.6 13.8,17.7 13.8,17.8C14.2,18.3 14.4,18.9 14.4,19.5C14.5,20.9 13.4,22 12,22M12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C12.3,20 12.5,19.8 12.5,19.5C12.5,19.3 12.4,19.2 12.4,19.1C12,18.6 11.8,18.1 11.8,17.5C11.8,16.1 12.9,15 14.3,15H16A4,4 0 0,0 20,11C20,7.1 16.4,4 12,4M6.5,10C7.3,10 8,10.7 8,11.5C8,12.3 7.3,13 6.5,13C5.7,13 5,12.3 5,11.5C5,10.7 5.7,10 6.5,10M9.5,6C10.3,6 11,6.7 11,7.5C11,8.3 10.3,9 9.5,9C8.7,9 8,8.3 8,7.5C8,6.7 8.7,6 9.5,6M14.5,6C15.3,6 16,6.7 16,7.5C16,8.3 15.3,9 14.5,9C13.7,9 13,8.3 13,7.5C13,6.7 13.7,6 14.5,6M17.5,10C18.3,10 19,10.7 19,11.5C19,12.3 18.3,13 17.5,13C16.7,13 16,12.3 16,11.5C16,10.7 16.7,10 17.5,10Z");
        SVGPath goalsIcon = new SVGPath();
        goalsIcon.setContent("M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,10.84 21.79,9.69 21.39,8.61L19.79,10.21C19.93,10.8 20,11.4 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4C12.6,4 13.2,4.07 13.79,4.21L15.4,2.6C14.31,2.21 13.16,2 12,2M19,2L15,6V7.5L12.45,10.05C12.3,10 12.15,10 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12C14,11.85 14,11.7 13.95,11.55L16.5,9H18L22,5H19V2M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12H16A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8V6Z");
        SVGPath logoutIcon = new SVGPath();
        logoutIcon.setContent("M12,3C10.89,3 10,3.89 10,5H3V19H2V21H22V19H21V5C21,3.89 20.11,3 19,3H12M12,5H19V19H12V5M5,11H7V13H5V11Z");

        if (currentSidebarType == SidebarType.AUTH_VIEW) {
            if (themePickerBtn != null) {
                updateNavigationButton(themePickerBtn, themePickerIcon, "Customize Theme");
            }
        } else if (currentSidebarType == SidebarType.DASHBOARD_VIEW || currentSidebarType == SidebarType.REPORTS_VIEW) {
            if (dashboardBtn != null) updateNavigationButton(dashboardBtn, dashboardIcon, "Dashboard");
            if (reportsBtn != null) updateNavigationButton(reportsBtn, reportsIcon, "Reports");
            if (themePickerBtn != null) updateNavigationButton(themePickerBtn, themePickerIcon, "Customize Theme");
            if (goalsBtn != null) updateNavigationButton(goalsBtn, goalsIcon, "Goals");
            if (logoutBtn != null) updateNavigationButton(logoutBtn, logoutIcon, "Logout");
        }
    }
    
    private static VBox createThemePicker() {
        VBox themePicker = new VBox(12);
        themePicker.getStyleClass().add("theme-picker-card");
        themePicker.setPadding(new Insets(0));

        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);

        SVGPath themePickerIcon = new SVGPath();
        themePickerIcon.setContent("M12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2C17.5,2 22,6 22,11A6,6 0 0,1 16,17H14.2C13.9,17 13.7,17.2 13.7,17.5C13.7,17.6 13.8,17.7 13.8,17.8C14.2,18.3 14.4,18.9 14.4,19.5C14.5,20.9 13.4,22 12,22M12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C12.3,20 12.5,19.8 12.5,19.5C12.5,19.3 12.4,19.2 12.4,19.1C12,18.6 11.8,18.1 11.8,17.5C11.8,16.1 12.9,15 14.3,15H16A4,4 0 0,0 20,11C20,7.1 16.4,4 12,4M6.5,10C7.3,10 8,10.7 8,11.5C8,12.3 7.3,13 6.5,13C5.7,13 5,12.3 5,11.5C5,10.7 5.7,10 6.5,10M9.5,6C10.3,6 11,6.7 11,7.5C11,8.3 10.3,9 9.5,9C8.7,9 8,8.3 8,7.5C8,6.7 8.7,6 9.5,6M14.5,6C15.3,6 16,6.7 16,7.5C16,8.3 15.3,9 14.5,9C13.7,9 13,8.3 13,7.5C13,6.7 13.7,6 14.5,6M17.5,10C18.3,10 19,10.7 19,11.5C19,12.3 18.3,13 17.5,13C16.7,13 16,12.3 16,11.5C16,10.7 16.7,10 17.5,10Z");
        themePickerIcon.setFill(Color.valueOf("#000000"));
        themePickerIcon.setScaleX(1.3);
        themePickerIcon.setScaleY(1.3);

        Label title = new Label("Theme Customizer");
        title.getStyleClass().add("theme-picker-text");
        header.getChildren().addAll(themePickerIcon, title);

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

        Label activeBadge = new Label("Soft Amethyst Active");
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

        if (themePickerVisible) {
            toggleThemePicker(); // This will collapse the theme picker and show features preview
        }
    }
    
    private static String getThemeNameByIndex(int index) {
        String[] themeNames = {
            "Cafe", "SoftAmethyst", "CherryBlossom", 
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
        
        // Only show features preview for auth view
        if (currentSidebarType == SidebarType.AUTH_VIEW) {
            VBox previewCard = AuthPanelUtil.createCozyFeaturePreview();
            preview.getChildren().add(previewCard);
        }
        
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
            SVGPath icon = new SVGPath();
            icon.setContent("M12,6.7L13.45,10.55L17.3,12L13.45,13.45L12,17.3L10.55,13.45L6.7,12L10.55,10.55L12,6.7M12,1L9,9L1,12L9,15L12,23L15,15L23,12L15,9L12,1Z");
            icon.setFill(Color.BLACK);
            icon.setScaleX(0.8);
            icon.setScaleY(0.8);
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
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        timeline.setOnFinished(e -> {
            toggleIcon = sidebarExpanded ? collapseIcon : expandIcon;
            logoSection.setVisible(sidebarExpanded);
            logoSection.setManaged(sidebarExpanded);

            // update all navigation buttons for current sidebar type
            updateAllNavigationButtons();

            // update features preview visibility for AUTH_VIEW
            if (currentFeaturesPreview != null && currentSidebarType == SidebarType.AUTH_VIEW) {
                currentFeaturesPreview.setVisible(sidebarExpanded && !themePickerVisible);
                currentFeaturesPreview.setManaged(sidebarExpanded && !themePickerVisible);
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

        if (!sidebarExpanded) {
            themePickerVisible = false;
            themePickerScrollPane.setVisible(false);
            themePickerScrollPane.setManaged(false);
            themePickerScrollPane.setMaxHeight(0);
            themePickerScrollPane.setMinHeight(0);
            themePickerScrollPane.setPrefHeight(0);
            if (themePickerBtn != null) {
                themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
            }
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
            SVGPath icon = new SVGPath();
            icon.setContent("M12,6.7L13.45,10.55L17.3,12L13.45,13.45L12,17.3L10.55,13.45L6.7,12L10.55,10.55L12,6.7M12,1L9,9L1,12L9,15L12,23L15,15L23,12L15,9L12,1Z");
            icon.setFill(Color.BLACK);
            icon.setScaleX(0.8);
            icon.setScaleY(0.8);
            footer.getChildren().add(icon);
        }
    }
    
    public static void toggleThemePicker() {
        if (!sidebarExpanded) {
            toggleSidebar();
            return; // return early to avoid double execution
        }

        System.out.println("Theme Picker Visible: " + themePickerVisible + " at toggleThemePicker");
        themePickerVisible = !themePickerVisible;

        System.out.println("Theme Picker Visible: " + themePickerVisible + " after toggling");

        double expandedHeight = 320;
        double collapsedHeight = 0;

        if (themePickerVisible) {
            if (themePickerBtn != null) {
                themePickerBtn.getStyleClass().add("sidebar-nav-button-active");
            }
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

            // hide features preview when theme picker is visible (only for AUTH_VIEW)
            if (currentFeaturesPreview != null && currentSidebarType == SidebarType.AUTH_VIEW) {
                currentFeaturesPreview.setVisible(false);
                currentFeaturesPreview.setManaged(false);
            }

            System.out.println("Theme Picker Expanded: " + themePickerVisible);
        } else {
            if (themePickerBtn != null) {
                themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
            }
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
                themePickerScrollPane.setManaged(false);

                // show features preview when theme picker is hidden (only for AUTH_VIEW)
                if (currentFeaturesPreview != null && sidebarExpanded && currentSidebarType == SidebarType.AUTH_VIEW) {
                    currentFeaturesPreview.setVisible(true);
                    currentFeaturesPreview.setManaged(true);
                }
            });
            collapse.play();
            System.out.println("Theme Picker Collapsed: " + themePickerVisible + " after collapse");
        }

        System.out.println("Theme Picker Visible: " + themePickerVisible + " after toggleThemePicker");
    }

    private static VBox createThemePickerBox() {
        VBox box = new VBox(12);
        box.setPadding(new Insets(10));
        box.getChildren().add(createThemePicker());
        return box;
    }

    public static void resetThemePickerState() {
        themePickerVisible = false;
        if (themePickerScrollPane != null) {
            themePickerScrollPane.setVisible(false);
            themePickerScrollPane.setManaged(false);
            themePickerScrollPane.setMaxHeight(0);
            themePickerScrollPane.setMinHeight(0);
            themePickerScrollPane.setPrefHeight(0);
        }
        if (themePickerBtn != null) {
            themePickerBtn.getStyleClass().remove("sidebar-nav-button-active");
        }

        // show features preview if sidebar is expanded and we're in AUTH_VIEW
        if (currentFeaturesPreview != null && sidebarExpanded && currentSidebarType == SidebarType.AUTH_VIEW) {
            currentFeaturesPreview.setVisible(true);
            currentFeaturesPreview.setManaged(true);
        }
    }

    // getters for dashboard navigation buttons
    public static Button getDashboardButton() { return dashboardBtn; }
    public static Button getReportsButton() { return reportsBtn; }
    public static Button getGoalsButton() { return goalsBtn; }
    public static Button getLogoutButton() { return logoutBtn; }
    public static Button getThemePickerButton() { return themePickerBtn; }

    // method to set active navigation button externally
    public static void setActiveNavigation(String navigationName) {
        switch (navigationName.toLowerCase()) {
            case "dashboard":
                if (dashboardBtn != null) setActiveNavButton(dashboardBtn);
                break;
            case "reports":
                if (reportsBtn != null) setActiveNavButton(reportsBtn);
                break;
            case "goals":
                if (goalsBtn != null) setActiveNavButton(goalsBtn);
                break;
            default:
                System.out.println("Unknown navigation: " + navigationName);
        }
    }
}