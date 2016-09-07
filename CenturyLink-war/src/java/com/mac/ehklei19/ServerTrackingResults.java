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
public class ServerTrackingResults
{
    private long Timestamp;
    private double CpuLoadAverage;
    private double RamLoadAverage;

    public void setTimestamp(long Timestamp)
    {
        this.Timestamp = Timestamp;
    }

    public void setCpuLoadAverage(double CpuLoadAverage)
    {
        this.CpuLoadAverage = CpuLoadAverage;
    }

    public void setRamLoadAverage(double RamLoadAverage)
    {
        this.RamLoadAverage = RamLoadAverage;
    }

    public long getTimestamp()
    {
        return Timestamp;
    }

    public double getCpuLoadAverage()
    {
        return CpuLoadAverage;
    }

    public double getRamLoadAverage()
    {
        return RamLoadAverage;
    }
}
