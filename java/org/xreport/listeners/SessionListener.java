package org.xreport.listeners;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.tomcat.util.http.fileupload.FileUtils;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		String path=session.getServletContext().getRealPath("/");
		String sessionId = (String)session.getId();  
        new File(path+"/result/"+sessionId+"/").mkdirs();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		String path=session.getServletContext().getRealPath("/");
		String sessionId = (String)session.getId();  
		try {  
            FileUtils.deleteDirectory(new File(path+"/result/"+sessionId+"/"));  
        } catch (IOException e) {e.printStackTrace();}  
	}

}
