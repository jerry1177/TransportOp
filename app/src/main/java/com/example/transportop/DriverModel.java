package com.example.transportop;

import android.widget.ImageView;

public class DriverModel {
    private int m_DriverImage;
    private String m_CompanyName;
    private String m_VehicleModel;
    private int m_MilesPerGalon;
    private int m_TankSize;
    private boolean m_IsDieselOnly;

    /**
     * Default constructor sets image to 0
     * company name and vehicle model are set to empty strings
     * MPG and tank size is set to 0
     * and is diesel only boolean is set to false
     */
    DriverModel() {
        this.m_CompanyName = "";
        this.m_VehicleModel = "";
        this.m_MilesPerGalon = 0;
        this.m_TankSize = 0;
        this.m_IsDieselOnly = false;
    }


    DriverModel(String companyName, String vehicleModel, int milesPerGalon, int tankSize, boolean isDieselOnly){
        this.m_CompanyName = companyName;
        this.m_VehicleModel = vehicleModel;
        this.m_MilesPerGalon = milesPerGalon;
        this.m_TankSize = tankSize;
        this.m_IsDieselOnly = isDieselOnly;
    }

    // get methods
    public String GetCompanyName() { return m_CompanyName; }
    public String GetVehicleModel() { return m_VehicleModel; }
    public int GetMilesPerGalon() { return m_MilesPerGalon; }
    public int GetTankSize() { return m_TankSize; }
    public boolean IsDieselOnly() { return m_IsDieselOnly; }

    // set methods
    public void SetCompanyName(String companyName) { this.m_CompanyName = companyName; }
    public void SetVehicleModel(String vehicleModel) { this.m_VehicleModel = vehicleModel; }
    public void SetMilesPerGalon(int milesPerGalon) { this.m_MilesPerGalon = milesPerGalon; }
    public void SetTankSize(int tankSize) { this.m_TankSize = tankSize; }
    public void SetIsDieselOnly(boolean isDieselOnly) { this.m_IsDieselOnly = isDieselOnly; }

}
