package org.xreport.services;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xreport.constants.Constants;
import org.xreport.entities.ReportResult;
import org.xreport.entities.ReportSchedule;

@Component("scheduledReportRunner")
public class ScheduledReportRunner {

	private String smtpDomain="";
	private String smtpHost="";
	private String smtpPort="";
	
	private String smtpFrom="";
	private String smtpUser="";
	private String smtpPwd="";
	
	@Autowired ServletContext servletContext=null;


	public void checkShedule(){
		
		if(servletContext==null){
			System.out.println("ScheduledReportRunner.checkShedule Null Context");
			return;
		}
		/*
		else{
			System.out.println("ScheduledReportRunner.checkShedule started");
		}
		*/
		
		/**/
		XReportServiceImpl service= new XReportServiceImpl();
		List<ReportSchedule> items= service.getSchedule();
		int idx=0;
		if(items!=null && !items.isEmpty()){
			for(ReportSchedule item : items){
				processSchedule(item, service, idx);
				service.setScheduleComplited(item.getId());
				idx++;
			}
		}
		/**/
	}
	
	private void processSchedule(ReportSchedule item, XReportServiceImpl service, int idx){
		
		String reportPath = (String) servletContext.getRealPath("/");
		reportPath+="/"+Constants.REPORTS_FOLDER+"/";
		
		String path= (String) servletContext.getInitParameter(Constants.OUT_FOLDER_INIT_PARAMETER)+"/";
		
		ReportResult result=service.buildLocal(item.getReport(), item.getSource(), "_"+String.valueOf(idx), reportPath, path);
		String body=item.getLabel() +" ("+item.getReport()+")";
		if(result.isHasError()){
			body=body+ "\n";
			body=body+ "Ошибка: \n"+result.getError();
			sendmail(item.getSend_to(),item.getLabel(),body,"");
		}else{
			sendmail(item.getSend_to(),item.getLabel(),body,result.getPath());
		}
	}
	
	private void sendmail(String sendTo, String subject, String body,String  attach){
		// DomainAuthenticator will concatenate the domain and username,
        // separated by "\" - see below
        DomainAuthenticator auth = new DomainAuthenticator(smtpDomain, smtpUser, smtpPwd);
		
		Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.allow8bitmime", "true");
        
        props.setProperty("mail.smtp.submitter", auth.getPasswordAuthentication().getUserName());
        props.setProperty("mail.smtp.auth", "true");
        
        props.setProperty("mail.host", smtpHost);
        /*
        props.setProperty("mail.user", smtpUser);
        if ((smtpPwd != null) && (smtpPwd.length()>0)){
            props.setProperty("mail.smtp.auth", "true");
        	props.setProperty("mail.password", smtpPwd);
        }
        Session session = Session.getDefaultInstance(props, null);//javax.mail.Session
        */
        Session session = Session.getInstance(props, auth);
        try{
            // Create a message
            MimeMessage msg = new MimeMessage(session);
            // extracts the senders and adds them to the message
            // Sender is a comma-separated list of e-mail addresses as per RFC822
            {
               InternetAddress[] theAddresses = InternetAddress.parse(smtpFrom);
               msg.addFrom(theAddresses);
            }
            // Extract the recipients and assign them to the message.
            // Recipient is a comma-separated list of e-mail addresses as per RFC822.
            {
               InternetAddress[] theAddresses = InternetAddress.parse(sendTo);
               msg.addRecipients(Message.RecipientType.TO,theAddresses);
            }
            // Subject field
            msg.setSubject(subject, "UTF-8");

            // Create the Multipart to be added the parts to
            Multipart mp = new MimeMultipart();
            // Create and fill the first message part
            {
               MimeBodyPart mbp = new MimeBodyPart();
               //mbp.setText(Body);
               mbp.setText(body != null ? body.toString() : "", "UTF-8");
               // Attach the part to the multipart
               mp.addBodyPart(mbp);
            }

            // Attach the files to the message
            if (null != attach && attach.length()>0) {
            	MimeBodyPart mbp = new MimeBodyPart();
            	FileDataSource fds = new FileDataSource(attach);
            	mbp.setDataHandler(new DataHandler(fds));
            	mbp.setFileName(fds.getName());
            	mbp.setDisposition(Part.ATTACHMENT);
            	mbp.setHeader("Content-Type", "application/xls");
            	mp.addBodyPart(mbp);
            }
            // Add the Multipart to the message
            msg.setContent(mp);
            // set the Date: header
            msg.setSentDate(new Date());
            // Send the message
            Transport.send(msg);

        }catch(MessagingException MsgException) {
        	MsgException.printStackTrace();
        	/*
            ErrorMessage[0] = MsgException.toString();
            Exception TheException = null;
            if ((TheException = MsgException.getNextException()) != null)
              ErrorMessage[0] = ErrorMessage[0] + "\n" + TheException.toString();
              */
         }
	}

	public String getSmtpDomain() {
		return smtpDomain;
	}

	public void setSmtpDomain(String smtpDomain) {
		this.smtpDomain = smtpDomain;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPwd() {
		return smtpPwd;
	}

	public void setSmtpPwd(String smtpPwd) {
		this.smtpPwd = smtpPwd;
	}

	public String getSmtpFrom() {
		return smtpFrom;
	}

	public void setSmtpFrom(String smtpFrom) {
		this.smtpFrom = smtpFrom;
	}

	private static class DomainAuthenticator extends Authenticator {

        private String domain;
        private String username;
        private String password;

        public DomainAuthenticator(String domain, String username, String password) {
              super();
              this.domain = domain;
              this.username = username;
              this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
              StringBuilder user = new StringBuilder();
              if(this.domain!=null && !this.domain.isEmpty()) user.append(this.domain).append('\\');
              user.append(username);
              return new PasswordAuthentication(user.toString(), this.password);
        }

  }

}
