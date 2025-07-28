package com.example.utils;

public class ThemeManager {
    private static ThemeManager instance;
    private String currentTheme;
    private ThemeChangeListener activeListener;

    private ThemeManager() {
        currentTheme = "Cafe"; // Set a default theme
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String theme) {
        currentTheme = theme;
        notifyThemeChange();
        System.out.println("Theme changed to " + theme + "Style");
        // Notify views that the theme has changed if necessary
    }

    private void notifyThemeChange() {
        if (activeListener != null) {
            activeListener.onThemeChanged(currentTheme);
            System.out.println("notified theme change with: " + currentTheme);
        }
    }

    public void setActiveListener(ThemeChangeListener listener){
        this.activeListener = listener;
    }

    public interface ThemeChangeListener {
        void onThemeChanged(String newTheme);
    }
}
