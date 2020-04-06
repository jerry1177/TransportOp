package com.example.transportop;

import java.util.ArrayList;

public class Vendor extends User{
    private static Vendor m_Vendor = null;

    public ArrayList<StationModel> m_StationList;

    private String m_CompanyName;
    Vendor() {
        super();
        m_CompanyName = "";
        m_StationList = new ArrayList<>();
    }
    Vendor(String username, String fName, String lName, String email, String password, String companyName) {
        super(username, fName, lName, email, password);
        m_CompanyName = companyName;
    }

    /*
    public static synchronized Vendor GetVendor() {
        if (m_Vendor == null)
            m_Vendor = new Vendor();
        return m_Vendor;
    }

     */

    public void SetCompanyName(String companyName) { this.m_CompanyName = companyName; }
    public String GetCompanyName() { return this.m_CompanyName; }
}
