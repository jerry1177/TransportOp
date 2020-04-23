package com.example.transportop;

import java.util.ArrayList;

public class DriverSingleton {
    private static DriverSingleton m_Singleton = null;
    public Driver m_Driver;
    public ArrayList<ResultModel> route;

    private DriverSingleton() {
        m_Driver = new Driver();
        route = new ArrayList<>();
    }

    public static synchronized DriverSingleton GetSignleton() {
        if (m_Singleton == null)
            m_Singleton = new DriverSingleton();

        return m_Singleton;
    }
}
