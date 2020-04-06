package com.example.transportop;

public class VehicleModel {
    private String m_VehicleModel;
    private float m_MilesPerGalon;
    private int m_TankSize;
    private boolean m_IsDieselOnly;

    /**
     * Default constructor sets image to 0
     * company name and vehicle model are set to empty strings
     * MPG and tank size is set to 0
     * and is diesel only boolean is set to false
     */
    VehicleModel() {
        this.m_VehicleModel = "";
        this.m_MilesPerGalon = 0;
        this.m_TankSize = 0;
        this.m_IsDieselOnly = false;
    }


    VehicleModel(String vehicleModel, float milesPerGalon, int tankSize, boolean isDieselOnly){
        this.m_VehicleModel = vehicleModel;
        this.m_MilesPerGalon = milesPerGalon;
        this.m_TankSize = tankSize;
        this.m_IsDieselOnly = isDieselOnly;
    }

    // get methods
    public String GetVehicleModel() { return m_VehicleModel; }
    public float GetMilesPerGalon() { return m_MilesPerGalon; }
    public int GetTankSize() { return m_TankSize; }
    public boolean IsDieselOnly() { return m_IsDieselOnly; }

    // set methods
    public void SetVehicleModel(String vehicleModel) { this.m_VehicleModel = vehicleModel; }
    public void SetMilesPerGalon(float milesPerGalon) { this.m_MilesPerGalon = milesPerGalon; }
    public void SetTankSize(int tankSize) { this.m_TankSize = tankSize; }
    public void SetIsDieselOnly(boolean isDieselOnly) { this.m_IsDieselOnly = isDieselOnly; }

}
