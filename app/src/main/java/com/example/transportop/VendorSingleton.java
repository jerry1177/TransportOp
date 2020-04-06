package com.example.transportop;

public class VendorSingleton {
    private static VendorSingleton m_Singleton = null;

    public Vendor m_Vendor;


    private VendorSingleton() {
        m_Vendor = new Vendor();
    }

    public static synchronized VendorSingleton GetSingleton() {
        if (m_Singleton == null) {
           m_Singleton = new VendorSingleton();
        }
        return m_Singleton;
    }

}
