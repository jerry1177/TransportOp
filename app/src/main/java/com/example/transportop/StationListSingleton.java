package com.example.transportop;

import java.util.ArrayList;

public class StationListSingleton {

    private static  StationListSingleton m_ListSignleton = null;

    public ArrayList<StationModel> m_List;

    private StationListSingleton() {
        m_List = new ArrayList<>();
    }

    public static synchronized StationListSingleton GetStationListSingleton() {
        if (m_ListSignleton == null)
            m_ListSignleton = new StationListSingleton();
        return m_ListSignleton;
    }
}
