/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.ehklei19;

import javax.ejb.Local;

/**
 *
 * @author ehklei19
 */
@Local
public interface ServerTrackingBeanLocal
{

    void RecordLoad(String MachineName, double CPULoad, double RAMLoad);

    String DisplayLoad(String MachineName);
    
}
