/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.ehklei19;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 *
 * @author ehklei19
 */
public class ServerData
{
    public static final long MILLIS_PER_SECOND = 1000;
    public static final long SECONDS_PER_MINUTE = 60;
    public static final long MINUTES_PER_HOUR = 60;
    public static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * SECONDS_PER_MINUTE;
    public static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * MINUTES_PER_HOUR;
    public static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    
    public ArrayDeque<ServerInfo> serverInfo = new ArrayDeque<>();
    
    public void AddEntry(double CPULoad, double RAMLoad)
    {
        ServerInfo si = new ServerInfo();
        si.setCPULoad(CPULoad);
        si.setRAMLoad(RAMLoad);
        si.setTimeStamp(System.currentTimeMillis());
        serverInfo.push(si);
    }
    
    private ArrayList<ServerTrackingResults> GetAverages(long timeConstantA, long timeConstantB)
    {
        ArrayList<ServerTrackingResults> returnValues = new ArrayList<>();
        
        ServerInfo start = serverInfo.peekFirst();
        long curMinute = serverInfo.peekFirst().getTimeStamp();
        int readings = 0;
        double cpuTotal = 0.0;
        double ramTotal = 0.0;
        for(ServerInfo si : serverInfo)
        {
            long tstamp = si.getTimeStamp();
            if (start.getTimeStamp() - tstamp >= timeConstantA)
                break;      // went past long time constant so stop
            else if (curMinute - si.getTimeStamp() <= timeConstantB)
            {
                readings++;
                cpuTotal += si.getCPULoad();
                ramTotal += si.getRAMLoad();
            }
            else
            {
                cpuTotal /= readings;
                ramTotal /= readings;
                ServerTrackingResults svrTrack = new ServerTrackingResults();
                svrTrack.setTimestamp(curMinute);
                svrTrack.setCpuLoadAverage(cpuTotal);
                svrTrack.setRamLoadAverage(ramTotal);
                returnValues.add(svrTrack);
                curMinute = si.getTimeStamp();
                cpuTotal = 0.0;
                ramTotal = 0.0;
                readings = 0;
            }
        }
                        
        return returnValues;
    }
    
    public ArrayList<ServerTrackingResults> GetMinuteAverages()
    {
        return GetAverages(MILLIS_PER_HOUR, MILLIS_PER_MINUTE);
    }
    
    public ArrayList<ServerTrackingResults> GetHourlyAverages()
    {
        return GetAverages(MILLIS_PER_DAY, MILLIS_PER_HOUR);
    }
    
    public void FlushEntries()
    {
        serverInfo.clear();
    }
}
