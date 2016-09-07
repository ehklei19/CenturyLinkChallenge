/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.ehklei19;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author ehklei19
 */
@Startup
@Singleton
public class ServerTrackingBean implements ServerTrackingBeanLocal
{
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static HashMap<String, ServerData> trackedServers = new HashMap<>();
    
    @Override
    public void RecordLoad(String MachineName, double CPULoad, double RAMLoad)
    {
        if (trackedServers.containsKey(MachineName))
        {
            trackedServers.get(MachineName).AddEntry(CPULoad, RAMLoad);
        }
        else
        {
            ServerData sd = new ServerData();
            sd.AddEntry(CPULoad, RAMLoad);
            trackedServers.put(MachineName, sd);
        }
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public String DisplayLoad(String MachineName)
    {
        if (trackedServers.containsKey(MachineName))
        {
            ServerData sd = trackedServers.get(MachineName);
            ArrayList<ServerTrackingResults> minResults = sd.GetMinuteAverages();
            ArrayList<ServerTrackingResults> hourResults = sd.GetHourlyAverages();
            
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            JsonObjectBuilder value = factory.createObjectBuilder();
            JsonArrayBuilder minArray = factory.createArrayBuilder();
            minResults.stream().map((tr) ->
            {
                JsonObjectBuilder minEntry = factory.createObjectBuilder();
                minEntry.add("timestamp", Instant.ofEpochMilli(tr.getTimestamp()).atZone(ZoneId.of("GMT-8")).format(formatter));
                minEntry.add("cpuload", tr.getCpuLoadAverage());
                minEntry.add("ramload", tr.getRamLoadAverage());
                return minEntry;
            }).forEach((minEntry) ->
            {
                minArray.add(minEntry);
            });
            
            JsonArrayBuilder hourArray = factory.createArrayBuilder();
            hourResults.stream().map((tr) ->
            {
                JsonObjectBuilder hourEntry = factory.createObjectBuilder();
                hourEntry.add("timestamp", Instant.ofEpochMilli(tr.getTimestamp()).atZone(ZoneId.of("GMT-8")).format(formatter));
                hourEntry.add("cpuload", tr.getCpuLoadAverage());
                hourEntry.add("ramload", tr.getRamLoadAverage());
                return hourEntry;
            }).forEach((hourEntry) ->
            {
                hourArray.add(hourEntry);
            });
            
            
            value.add("ServerName", MachineName);
            value.add("minuteaverage", minArray);
            value.add("houraverage", hourArray);
            
            JsonObject theOutput = value.build();
            
            return theOutput.toString();
        }
        return "";
    }
}
