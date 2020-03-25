package com.example.transportop;

import android.widget.ImageView;

public class StationModel {

    private String m_CompanyName;
    private String m_Address;
    private float m_Reg;
    private float m_Mid;
    private float m_Prem;
    private float m_Diesel;


    /**
     * Default Constructor sets gas image to null
     * company name and address to empty strings
     * and all gass prices to 0.0f
     */
    StationModel() {
        this.m_CompanyName = "";
        this.m_Address = "";
        this.m_Reg = 0.0f;
        this.m_Mid = 0.0f;
        this.m_Prem = 0.0f;
        this.m_Diesel = 0.0f;
    }

    StationModel(String companyName, String address, float reg, float mid, float prem, float diesel){
        this.m_CompanyName = companyName;
        this.m_Address = address;
        this.m_Reg = reg;
        this.m_Mid = mid;
        this.m_Prem = prem;
        this.m_Diesel = diesel;
    }

    // get methods
    public String GetCompanyName() { return this.m_CompanyName; }
    public String GetAddress() { return this.m_Address; }
    public float GetRegPrice() { return this.m_Reg; }
    public float GetMidPrice() { return this.m_Mid; }
    public float GetPremPrice() { return this.m_Prem; }
    public float GetDieselPrice() { return this.m_Diesel; }

    // set methods
    public void SetCompanyName( String companyName) { this.m_CompanyName = companyName; }
    public void SetAddress(String address) { this.m_Address = address; }
    public void SetRegPrice(float reg) { this.m_Reg = reg; }
    public void SetMidPrice(float mid) { this.m_Mid = mid; }
    public void SetPremPrice(float prem) { this.m_Prem = prem; }
    public void SetDieselPrice(float diesel) { this.m_Diesel = diesel; }

}
