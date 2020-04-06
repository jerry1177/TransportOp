package com.example.transportop;

public class DriverSingleton {
    private static DriverSingleton m_Singleton = null;
    public Driver m_Driver;

    private DriverSingleton() {
        m_Driver = new Driver();
    }

    public static synchronized DriverSingleton GetSignleton() {
        if (m_Singleton == null)
            m_Singleton = new DriverSingleton();

        return m_Singleton;
    }
}
