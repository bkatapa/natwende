package com.mweka.natwende.helper;

import java.io.File;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.util.FileReceiverUtils;

public class DownloadHelper {
	
	public static void sendBytes(byte[] data, String mime, String fileName) {
		Log log = LogFactory.getLog(DownloadHelper.class);
		
		ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
				
		HttpServletResponse response = (HttpServletResponse) extCtx.getResponse();
	    try {
	         ServletOutputStream os = response.getOutputStream();         

	         response.setHeader("Expires", "0");
	         response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	         response.setHeader("Pragma", "public");
	         // setting the content type
	       
	         response.setContentType(mime);
	         response.setHeader("Content-Disposition", String.format("attachement; filename=\"%s\"", fileName));
	       
	         // the contentlength	         
	         response.setContentLength(data.length);

	         os.write(data);
	         os.flush();
	         os.close();
	         FacesContext.getCurrentInstance().responseComplete();
	     } catch (Exception e) {
	         log.error(e, e);
	     }
	}	
	
	public static void sendFile(File file, String mime, String fileName) {		
		FileReceiverUtils fr = new FileReceiverUtils();
		sendBytes(fr.getBytesFromFile(file), mime, fileName);
	}
}
