package com.mweka.natwende.mail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.mweka.natwende.report.ReportFacade;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.user.vo.UserPasswordResetVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.DateConverter;
import com.mweka.natwende.util.SystemConfigurationBean;
import java.io.ByteArrayOutputStream;
import javax.ejb.Asynchronous;
import com.mweka.natwende.mail.MailTemplates;
import com.mweka.natwende.types.ConfigAttribute;

@Stateless
@LocalBean
public class MailerFacade {

    private javax.mail.Session session;

    Log log = LogFactory.getLog(this.getClass().getName());

    private String smtpServer;
    private String smtpPort;
    private String smtpUser;
    private String smtpPassword;
    private String smtpAuth;
    private String defaultFromAddress;
    
    private VelocityEngine engine;    
    //private Map<String, String> parkingOperatorSettings;

    @EJB
    SystemConfigurationBean systemConfigurationBean;
    
    @EJB
    ReportFacade reportFacade;

    @PostConstruct
    public void initialise() {
        smtpServer = System.getProperty("SMTP_SERVER", "127.0.0.1");
        smtpPort = System.getProperty("SMTP_PORT", "25");
        smtpUser = System.getProperty("SMTP_USER", "");
        smtpPassword = System.getProperty("SMTP_PASSWORD", "");
        smtpAuth = System.getProperty("SMTP_AUTH", "false");
        defaultFromAddress = System.getProperty("SMTP_DEFAULT_FROM", "no_reply@adaptris.com");

        Properties prop = new Properties();

        prop.put("mail.smtp.host", smtpServer);
        prop.put("mail.smtp.port", smtpPort);
        prop.put("mail.smtp.user", smtpUser);
        prop.put("mail.smtp.password", smtpPassword);
        prop.put("mail.smtp.auth", smtpAuth);

        session = Session.getDefaultInstance(prop);
        
        // Setup velocity engine
        engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        
        // Prepare parking operator settings
        /**/
    }

    public void sendHtmlEmail(MailMessage mailMessage, InternetAddress[] to) {
        try {
            Session mailSession = session;
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(mailMessage.getSubject());

            if (mailMessage.getRecipients() != null) {
                String recipientList = recipientListToString(mailMessage.getRecipients());
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientList, false));
            }

            if (mailMessage.getCc() != null) {
                String ccList = recipientListToString(mailMessage.getCc());
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccList, false));
            }
	    
	    if (mailMessage.getCc() != null) {
                String bccList = recipientListToString(mailMessage.getBcc());
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccList, false));
            }

            if (mailMessage.getFromAddress() == null) {
                mailMessage.setFromAddress(defaultFromAddress);
            }
            message.setFrom(new InternetAddress(mailMessage.getFromAddress()));

            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(mailMessage.getHtmlBody(), "text/html");
            mp.addBodyPart(htmlPart);

            if (mailMessage.getMediaHeader() != null) {
                for (AttachmentHeader mh : mailMessage.getMediaHeader()) {
                    MailAttachment media = mh.getMailAttachment();
                    if (media != null && media.getData() != null && media.getData().length > 0) {
                        MimeBodyPart attachment = new MimeBodyPart();
                        attachment.setFileName(mh.getName());
                        if (mh.getMimeType() == null) {
                            mh.setMimeType("application/octet-stream");
                        }
                        attachment.setContent(mh.getMailAttachment().getData(), mh.getMimeType());
                        if (mh.getContentId() != null) {
                            attachment.addHeader("Content-ID", mh.getContentId());
                        }

                        mp.addBodyPart(attachment);
                    }
                }
            }
            message.setContent(mp);

            log.info("Sending mail : " + message.toString());

            Transport.send(message);
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void sendHtmlEmail(MailMessage mailMessage) {
        try {
            InternetAddress[] internetAddresses = InternetAddress.parse(recipientListToString(mailMessage.getRecipients()), false);
            sendHtmlEmail(mailMessage, internetAddresses);
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private String recipientListToString(final List<String> recipients) {
        StringBuilder recipientStringBuilder = new StringBuilder("");
        for (String recipient : recipients) {
            if (recipient != null && !recipient.isEmpty()) {
                recipientStringBuilder.append(recipient).append(",");
            }
        }
        if (recipientStringBuilder.length() > 0 && recipientStringBuilder.charAt(recipientStringBuilder.length() - 1) == ',') {
            recipientStringBuilder.deleteCharAt(recipientStringBuilder.length() - 1);
        }
        return recipientStringBuilder.toString();
    }

    public void sendUserWelcomeEmail(UserPasswordResetVO userPasswordResetVO, Map<String, String> operatorSettingsMap) {

        if (systemConfigurationBean.mustSendWelcomeEmails() == false) {
            //return; //Don't send out any e-mails
        }
        UserVO userVO = userPasswordResetVO.getUserVO();
        operatorSettingsMap.put(ConfigAttribute.OPERATOR_LOGO.name(), systemConfigurationBean.getBaseURL() + "/resources/images/" + operatorSettingsMap.get(ConfigAttribute.OPERATOR_LOGO.name()));
        
        Template template = engine.getTemplate(MailTemplates.USER_WELCOME_EMAIL.getLocation());
        VelocityContext context = new VelocityContext();        
        context.put("userPasswordResetVO", userPasswordResetVO);
        context.put("userVO", userVO);
        context.put("operatorSettingsMap", operatorSettingsMap);
        context.put("baseURL", systemConfigurationBean.getBaseURL());        
        StringWriter writer = new StringWriter();
        template.merge(context, writer); 

        String content = writer.toString();
        log.debug(content);

        MailMessage msg = new MailMessage();
        List<String> recipients = new ArrayList<String>();
        
        recipients.add(userVO.getEmail());

        List<String> bcc = new ArrayList<String>();
        List<String> cc = new ArrayList<String>();
        cc.add(operatorSettingsMap.get(ConfigAttribute.EMAIL_SUPPORT.name()));
        bcc.add("bkatapa@gmail.com");
        msg.setHtmlBody(content);
        msg.setRecipients(recipients);
        msg.setBcc(bcc);
        msg.setCc(cc);
        msg.setSubject(operatorSettingsMap.get(ConfigAttribute.OPERATOR_NAME.name()) + " Parking Portal Client Registration: " + userVO.getOperator() == null ? "SkiData" : userVO.getOperator().getName().getDisplay());
        sendHtmlEmail(msg);
    }
    
    
    /** 
     * 
     * The e-mail sent when a Company Administrator created a new user for their company 
     * 
     * @param userPasswordResetVO
     */
    public void sendNewUserCreatedEmail(UserPasswordResetVO userPasswordResetVO, Map<String, String> parkingOperatorSettingsMap) {
    	sendUserWelcomeEmail(userPasswordResetVO, parkingOperatorSettingsMap);    	
	}

    
    public void resendUserWelcomeEmail(UserPasswordResetVO userPasswordResetVO) {

        if (systemConfigurationBean.mustSendWelcomeEmails() == false) {
            return; //Don't send out any e-mails
        }
        UserVO userVO = userPasswordResetVO.getUserVO();

        StringBuilder sb = new StringBuilder();

        sb.append("<!doctype html>");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        sb.append("<style type='text/css'>");
        sb.append("");
        sb.append("html, body {");
        sb.append("  font-family: Arial;");
        sb.append("}");
        sb.append("");
        sb.append("table, th, tr, td {");
        sb.append("	border-collapse: collapse;");
        sb.append("}");
        sb.append("");
        sb.append("th {");
        sb.append("  font-weight: bold;");
        sb.append("  text-align: left;	");
        sb.append("}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">");
        sb.append("       <tr>");
        sb.append("       	  <td width=\"100%\" valign=\"top\" bgcolor=\"#ffffff\" style=\"padding-top:10px\">");
        sb.append("       	     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" bgcolor=\"#151f6d\">");
        sb.append("       	        <tr>");
        sb.append("       	           <td valign=\"top\" style=\"padding:0\">");
        sb.append("       	              <img src=\"${baseURL}/images/mail/top-logo.png\" style=\"display: block; padding: 5px\"/>");
        sb.append("       	           </td>");
        sb.append("       	           <td style=\"padding-right: 30px\" align=\"right\" valign=\"middle\">");
        sb.append("       	               <a style=\"text-decoration: none; color: white;\" href='http://www.montegoportal.co.za'>www.montegoportal.co.za</a>");
        sb.append("       	           </td>");
        sb.append("       	        </tr>");
        sb.append("       	     </table>");
        sb.append("       	  </td>");
        sb.append("      </tr>");
        sb.append("      <tr>");
        sb.append("       	  <td valign=\"top\" style=\"padding-top: 10px\">");
        sb.append("       	  ");

        sb.append(String.format("<p>Dear %s</p>", userVO.getFullname()));

        sb.append("<p>We notice you haven&#39;t logged into the Portal as yet.</p>");
        sb.append("<p>In the unfortunate case wherein you&#39;ve tried to access the portal already and were unable to set your password, the cause of this issue has been resolved. We apologise for any inconvenience.</p>");
        sb.append("<p>You are already registered to use the portal, simply click on the link below to set your password and log into the portal.</p>");
        sb.append("<br><br>       	  </td>");
        sb.append("       </tr>");

        sb.append("<tr>");
        sb.append("<td valign=\"top\" align=\"center\" bgcolor=\"#F68121\" style=\"color: #FFFFFF; padding:10px 30px 10px 30px;font-size: larger\">");
        sb.append("<p>Your login details</p>");
        sb.append(String.format("<p>Username: %s</span></p>", userVO.getUsername()));
        sb.append("</td>");
        sb.append("</tr>");

        sb.append("<tr><td valign=\"top\" align=\"center\" style=\"color: #FFFFFF; padding:10px 30px 10px 30px;font-size: larger\">");
        sb.append("</td></tr>");

        sb.append("<tr>");
        sb.append("<td valign=\"top\" align=\"center\" bgcolor=\"#252b83\" style=\"color: #FFFFFF; padding:10px 30px 10px 30px;font-size: larger\">");
        sb.append(String.format("<p>Please use your account number %s as reference when making payments.</p>", userVO.getOperator().getName()));
        sb.append("</td></tr>");

        sb.append("       <tr><td align='center' style=\"padding-top: 10px\"><table><tr>");
        sb.append("         <td valign=\"top\" style=\"vertical-align: top; padding:10px 30px 10px 30px;\" bgcolor=\"#099F0D\" align=\"center\">");
        sb.append(String.format("<a style=\"color:#FFFFFF; text-decoration:none;font-size: 18px\" href='${baseURL}/public/user-password-reset-view.xhtml?resetPin=%s'>Click here to set your password.</a>", userPasswordResetVO.getResetPin()));
        sb.append("         </td>");
        sb.append("      </tr>");
        sb.append("		 </table></td></tr>");

        sb.append("      <tr>");
        sb.append("        <td valign=\"top\" bgcolor=\"#ffffff\" style=\"padding-top:40px\">");
        sb.append("          <table width=\"100%\" cellspacing=\"0\" border=\"0\" cellpadding=\"0\">");
        sb.append("              <tr>");
        sb.append("                <td width=\"100%\" height=\"8px\" bgcolor=\"#f68121\" ></td>");
        sb.append("              </tr>");
        sb.append("	          <tr>            ");
        sb.append("	            <td valign=\"top\" align=\"center\" bgcolor=\"#252b83\">");
        sb.append("	            	<p style=\"color: white; margin: 2px;\"><b>WHOLESOME GOODNESS</b></p>");
        sb.append("					<p style=\"color: white; margin: 2px;\"><b>Montego</b> &copy; 2000, All Rights Reserved.</p>");
        sb.append("					<p style=\"color: white; margin: 2px;\"><span style=\"color: #f68121\">Need training or help?</span>&nbsp;|&nbsp;<b>T</b> 011 267 8686 | <b>E&nbsp;</b><a style=\"color: white; text-decoration: none;\" href=\"mailto:za.support@adaptris.com\">za.support@adaptris.com*</a></p>");
        sb.append("					<p style=\"color: white; margin: 2px;\"><small>*This is a third party support by the portal developer and not Montego staff.</small></p>	            ");
        sb.append("	            </td>");
        sb.append("	          </tr>");
        sb.append("	        </table>");
        sb.append("        </td>");
        sb.append("      </tr>");
        sb.append("      ");
        sb.append("    </table>");
        sb.append("");
        sb.append("</body>");
        sb.append("</html>");

        String content = sb.toString();

        content = content.replaceAll(Pattern.quote("${baseURL}"), systemConfigurationBean.getBaseURL());

        log.debug(content);

        MailMessage msg = new MailMessage();
        List<String> recipients = new ArrayList<String>();
        
        recipients.add(userVO.getOperator().getName().getDisplay());

        List<String> bcc = new ArrayList<String>();
        List<String> cc = new ArrayList<String>();
        bcc.add("lesiba.malatji@adaptris.com");
        msg.setHtmlBody(content);
        msg.setRecipients(recipients);
        msg.setBcc(bcc);
        msg.setCc(cc);
        msg.setSubject("Montego Retail Portal Customer Registration: " + userVO.getOperator().getName());
        sendHtmlEmail(msg);

    }

    public void sendPasswordResetEmail(UserPasswordResetVO userPasswordResetVO, Map<String, String> parkingOperatorSettingsMap) {
        UserVO userVO = userPasswordResetVO.getUserVO();
        parkingOperatorSettingsMap.put(ConfigAttribute.OPERATOR_LOGO.name(), systemConfigurationBean.getBaseURL() + "/resources/images/" + parkingOperatorSettingsMap.get(ConfigAttribute.OPERATOR_LOGO.name()));
        Template template = engine.getTemplate(MailTemplates.USER_RESET_PASSWORD_EMAIL.getLocation());
        VelocityContext context = new VelocityContext();       
        context.put("userPasswordResetVO", userPasswordResetVO);
        context.put("userVO", userVO);
        context.put("parkingOperatorSettingsMap", parkingOperatorSettingsMap);
        context.put("baseURL", systemConfigurationBean.getBaseURL());        
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String content = writer.toString();
        log.debug(content);
        MailMessage msg = new MailMessage();
        List<String> recipients = new ArrayList<String>();
        recipients.add(userVO.getEmail());
        List<String> bcc = new ArrayList<String>();
        List<String> cc = new ArrayList<String>();
        cc.add(parkingOperatorSettingsMap.get(ConfigAttribute.EMAIL_SUPPORT.name()));
        bcc.add("bkatapa@yahoo.com");
        msg.setBcc(bcc);
        msg.setCc(cc);
        msg.setHtmlBody(content);
        msg.setRecipients(recipients);
        msg.setSubject(parkingOperatorSettingsMap.get(ConfigAttribute.OPERATOR_NAME.name()) + " Parking Portal password reset request: " + userVO.getUsername());
        sendHtmlEmail(msg);
    }

    @Asynchronous
	public void emailBookingVoucher(BookingVO bookingVO, Map<String,String> operatorSettingsMap) throws Exception {    	
        operatorSettingsMap.put(ConfigAttribute.OPERATOR_LOGO.name(), systemConfigurationBean.getBaseURL() + "/resources/images/" + operatorSettingsMap.get(ConfigAttribute.OPERATOR_LOGO.name()));
        Template template = engine.getTemplate(MailTemplates.BOOKING_VOUCHER_EMAIL.getLocation());
        VelocityContext context = new VelocityContext();       
        context.put("bookingVO", bookingVO);
        context.put("DateConverter", DateConverter.class);
        context.put("parkingOperatorSettingsMap", operatorSettingsMap);
        context.put("baseURL", systemConfigurationBean.getBaseURL());        
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        
        String content = writer.toString();
        log.debug(content);

		MailMessage msg = new MailMessage();
		List<String> recipients = new ArrayList<String>();
		List<String> bcc = new ArrayList<String>();
		List<String> cc = new ArrayList<String>();

		//recipients.add(bookingVO.getVisitorEmail());

		try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
			reportFacade.generateBookingVoucher(Integer.valueOf("" + bookingVO.getId()), byteArrayOutputStream);

			msg.setHtmlBody(content);
			msg.setRecipients(recipients);
			msg.setBcc(bcc);
			msg.setCc(cc);
			msg.setFromAddress(bookingVO.getOperatorName().getDisplay().replaceAll("[^A-Za-z0-9()\\\\[\\\\]]", "")+"@adaptris.com");
			msg.addMediaHeader("BookingVoucher.pdf", byteArrayOutputStream.toByteArray());
			msg.setSubject(bookingVO.getOperatorName() + " - Parking Voucher for " + DateConverter.convertDateToString(bookingVO.getUpdateDate()));
			sendHtmlEmail(msg);
		}
	}    
   
	@Asynchronous
	public void emailBookingVoucher(BookingVO bookingVO) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("<!doctype html>");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		sb.append("<style type='text/css'>");
		sb.append("");
		sb.append("html, body {");
		sb.append("  font-family: Arial;");
		sb.append("}");
		sb.append("");
		sb.append("table, th, tr, td {");
		sb.append("	border-collapse: collapse;");
		sb.append("}");
		sb.append("");
		sb.append("th {");
		sb.append("  font-weight: bold;");
		sb.append("  text-align: left;	");
		sb.append("}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("Attention: "+bookingVO+" "+bookingVO+"</br>" +
			"</br>" +
			"Please find below a Parking Booking made for you by "+bookingVO.getOperatorName()+" as follows:</br>" +
			"</br>" +
			"Valid From:	"+DateConverter.convertDateTimeToString(bookingVO.getUpdateDate())+"</br>" +
			"Valid Until: 	"+DateConverter.convertDateTimeToString(bookingVO.getUpdateDate())+" </br>" +
			//"Bay Number:	"+bookingVO.getBayNumber()+"</br>" +
			"Vehicle Make:	"+(bookingVO.getTrip() == null ? "" : bookingVO.getTo())+"</br>" +
			"Vehicle Reg:	"+(bookingVO.getTrip().getBusReg() == null ? "" : bookingVO.getFrom())+"</br>" +
			"</br>" +
			"Address: "+bookingVO.getOperatorName()+"</br>" +
			"Comments: "+bookingVO.getTrip()+"</br>" +
			"</br>Kind Regards</br>" +
			"</br>"+bookingVO.getOperatorName()+"</br>" +
			"</br>"+bookingVO.getOperatorName()+"</br>");
		sb.append("");
		sb.append("</body>");
		sb.append("</html>");

		String content = sb.toString();

		content = content.replaceAll(Pattern.quote("${baseURL}"), systemConfigurationBean.getBaseURL());

		log.debug(content);

		MailMessage msg = new MailMessage();
		List<String> recipients = new ArrayList<String>();
		List<String> bcc = new ArrayList<String>();
		List<String> cc = new ArrayList<String>();

		recipients.add(bookingVO.getClass().toString());

		try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
			reportFacade.generateBookingVoucher(Integer.valueOf("" + bookingVO.getId()), byteArrayOutputStream);

			msg.setHtmlBody(content);
			msg.setRecipients(recipients);
			msg.setBcc(bcc);
			msg.setCc(cc);
			msg.setFromAddress(bookingVO.toString().replaceAll("[^A-Za-z0-9()\\\\[\\\\]]", "")+"@adaptris.com");
			msg.addMediaHeader("BookingVoucher.pdf", byteArrayOutputStream.toByteArray());
			msg.setSubject(bookingVO.getOperatorName() + " - Parking Voucher for " + DateConverter.convertDateToString(bookingVO.getUpdateDate()));
			sendHtmlEmail(msg);
		}
	}
}
