package org.xreport.services;

import java.util.List;

import org.granite.messaging.service.annotations.RemoteDestination;

import org.xreport.entities.Welcome;


@RemoteDestination
public interface WelcomeService {

    public Welcome hello(String name);
    
    public List<Welcome> findAll();
}
