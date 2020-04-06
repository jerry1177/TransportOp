package com.example.transportop;

/**
 * enum to keep track of what view is
 * curently being displayed or on top of the stack
 */
enum CurrentView {
    LOGIN,
    SIGNUP,
    HOME,
    FINDROUTE,
    ADDVEHICLE,
    CREATEVEHICLE,
    ADDSTATION,
    CREATESTATION
}

/**
 * enum to keep track of what view
 * the home fragment should navigate to because
 * findroute and addvehicle fragments just pop back to homeview
 */
enum ToView {
    LOGIN,
    SIGNUP,
    HOME,
    FINDROUTE,
    ADDVEHICLE,
    CREATEVEHICLE,
    ADDSTATION,
    CREATESTATION
}

/**
 * enum to keep track of what type the current user is
 */
enum UserType {
    NONE,
    DRIVER,
    VENDOR
}

public class ViewManagerSingleton {
    private static ViewManagerSingleton m_Singleton = null;
    private CurrentView currentView;
    private ToView toView;
    private UserType userType;

    private ViewManagerSingleton() {
        currentView = CurrentView.LOGIN;
        toView = ToView.LOGIN;
        userType = UserType.NONE;
    }

    public static synchronized ViewManagerSingleton GetSingleton() {
        if (m_Singleton == null)
            m_Singleton = new ViewManagerSingleton();

        return m_Singleton;
    }

    // Set methods
    public void setCurrentView(CurrentView currentView) { this.currentView = currentView; }
    public void setToView(ToView toView) { this.toView = toView; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public CurrentView getCurrentView() { return currentView; }
    public ToView getToView() { return toView; }
    public UserType getUserType() { return userType; }
}
