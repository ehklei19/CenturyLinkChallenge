/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.ehklei19;

/**
 *
 * @author ehklei19
 */
public class ServerInfo
{
    private long timeStamp;
    private double CPULoad;
    private double RAMLoad;

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void setCPULoad(double CPULoad)
    {
        this.CPULoad = CPULoad;
    }

    public void setRAMLoad(double RAMLoad)
    {
        this.RAMLoad = RAMLoad;
    }

    public double getCPULoad()
    {
        return CPULoad;
    }

    public double getRAMLoad()
    {
        return RAMLoad;
    }
}
