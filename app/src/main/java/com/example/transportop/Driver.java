package com.example.transportop;

import java.util.ArrayList;

/**
 * This class stores the driver user information
 */

public class Driver extends User {

    private static Driver m_Driver = null;
    private String m_CompanyName;
    public ArrayList<VehicleModel> m_VehiclList;

    Driver() {
        super();
        m_CompanyName = "";
        m_VehiclList = new ArrayList<>();
    }

    Driver(String username, String fName, String lName, String email, String password) {
        super(username, fName, lName, email, password);
        this.m_CompanyName = "";
        m_VehiclList = new ArrayList<>();
    }

    Driver(String username, String fName, String lName, String email, String password, String companyName) {
        super(username, fName, lName, email, password);
        m_CompanyName = companyName;
        m_VehiclList = new ArrayList<>();
    }

    public void SetCompanyName(String companyName) { this.m_CompanyName = companyName; }
    public String GetCompanyName() { return this.m_CompanyName; }
}
