/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.ehklei19;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author ehklei19
 */
@Path("/servertrack/{servername}")
public class ServerTrack
{
    
    ServerTrackingBean  serverTrackingBean = new ServerTrackingBean();

        
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServerTrack
     */
    public ServerTrack()
    {
    }

    /**
     * Retrieves representation of an instance of com.mac.ehklei19.ServerTrack
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getXml(@PathParam("servername") String serverName)
    {
        return serverTrackingBean.DisplayLoad(serverName);
    }

    /**
     * PUT method for updating or creating an instance of ServerTrack
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content)
    {
        String key = "";
        String value = "";
        String serverName = "";
        double cpuLoad = 0.0;
        double ramLoad = 0.0;
        StringReader reader = new StringReader(content);
        JsonParser parser = Json.createParser(reader);
        while(parser.hasNext())
        {
            Event jEvent = parser.next();
            if (jEvent.equals(JsonParser.Event.KEY_NAME))
            {
                key = parser.getString();
            }
            else if (jEvent.equals(JsonParser.Event.VALUE_STRING))
            {
                if (!key.isEmpty())
                {
                    value = parser.getString();
                    switch (key.toLowerCase())
                    {
                        case "servername":
                            serverName = value;
                            break;
                        case "cpuload":
                            cpuLoad = Double.valueOf(value);
                            break;
                        case "ramload":
                            ramLoad = Double.valueOf(value);
                            break;
                        default:
                            break;
                    }
                }
                key = "";
            }
        }
        serverTrackingBean.RecordLoad(serverName, cpuLoad, ramLoad);
    }

}