package com.mweka.natwende.helper;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.mweka.natwende.exceptions.MediaNotFoundException;
import com.mweka.natwende.media.vo.MediaVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("ImageStreamer")
@SessionScoped
public class ImageStreamer implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceLocator serviceLocator;

	@Inject
	FacesContext ctx;

	private DefaultStreamedContent getEmptyStream() {
		return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]));
	}

	private DefaultStreamedContent getNoImageStream() {
		return new DefaultStreamedContent(ctx.getExternalContext().getResourceAsStream("/resources/images/no-image2.png"), "image/png");
	}

	private DefaultStreamedContent cachableImageStream(MediaVO media) {
		if (media == null || media.getData() == null || media.getData().length <= 0) {
			return getNoImageStream();
		} else {
			//Set the pragma header to an emtpy string, else the server adds no-cache
			ctx.getExternalContext().setResponseHeader("Pragma", "");
			return new DefaultStreamedContent(new ByteArrayInputStream(media.getData()), media.getMime());
		}
	}

	private DefaultStreamedContent getMediaStream(Long mediaId) {
		try {
			if (mediaId == null) {
				return getNoImageStream();
			}
			MediaVO mediaVO = serviceLocator.getMediaFacade().getMediaVObyId(mediaId);
			byte[] data = mediaVO.getData();

			if (data == null) {
				return getNoImageStream();
			}

			return new DefaultStreamedContent(new ByteArrayInputStream(data), mediaVO.getMime());

		} catch (MediaNotFoundException e) {
		}
		return getNoImageStream();
	}

	public StreamedContent getImageStream() {

		long mediaId = NumberUtils.toLong(ctx.getExternalContext().getRequestParameterMap().get("mediaId"), -1L);

		if (mediaId == -1L || (ctx.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)) {
			// So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
			return getEmptyStream();
		} else {
			//Requesting the image data        	
			return getMediaStream(mediaId);
		}
	}

//	public StreamedContent getProductImageStream() {		
//		if (ctx.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//            return getEmptyStream();
//        } else {
//        	Map<String, String> parameters = ctx.getExternalContext().getRequestParameterMap();
//        	String supplierCode = parameters.get("supplierCode");
//        	String supplierProductCode = parameters.get("supplierProductCode");        	        	
//        	String imageSize = parameters.get("imageSize");
//        	
//        	if (supplierCode == null || supplierProductCode == null) return getEmptyStream();
//        	
//        	ProductVO productVO;        	
//        	try {
//				productVO = serviceLocator.getProductDataFacade().getBySupplierCodeAndProductCode(supplierCode, supplierProductCode);
//			} catch (ProductNotFoundException e) {
//				return getEmptyStream();
//			}        	        	
//        	Long mediaId;        	
//        	if (imageSize == null || imageSize.equals("small")) {
//        		mediaId = productVO.getSmallImageId();        			
//        	} else {
//        		mediaId = productVO.getNormalImageId();
//        	}        	
//        	return getMediaStream(mediaId);     			
//        }
//	}
//	public StreamedContent getDashboardImageStream() {
//		
//		long dashboardId = NumberUtils.toLong(ctx.getExternalContext().getRequestParameterMap().get("dashboardId"), -1L);
//		
//		if (ctx.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE || dashboardId == -1) {
//			//So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//			return getEmptyStream();
//		} else {
//			MediaVO mediaVO = serviceLocator.getDashboardDataFacade().getDashboardImage(dashboardId);
//			return cachableImageStream(mediaVO);			
//		}		
//	}
//	
}
