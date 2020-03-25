package com.example.transportop;

import java.util.ArrayList;

public class VehicleListSingleton {
    private static  VehicleListSingleton m_ListSingleton = null;
    public ArrayList<DriverModel> m_List;

    private VehicleListSingleton() {
        m_List = new ArrayList<>();
    }

    public static synchronized  VehicleListSingleton GetSingleton() {

        if (m_ListSingleton == null)
            m_ListSingleton = new VehicleListSingleton();

        return m_ListSingleton;
    }
}
