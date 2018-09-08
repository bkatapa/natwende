package com.mweka.natwende.media.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.exceptions.MediaNotFoundException;
import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.media.entity.Media;
import com.mweka.natwende.media.vo.MediaVO;

@Stateless
@LocalBean
public class MediaFacade extends AbstractFacade<MediaVO> {

	public MediaFacade() {
		super(MediaVO.class);
	}	
	
	public MediaVO getMediaVObyId(long mediaId) throws MediaNotFoundException {
		Media media = serviceLocator.getMediaDataFacade().findById(mediaId);
		if (media == null) throw new MediaNotFoundException("Could not locate media with id: " + mediaId);
		return serviceLocator.getMediaDataFacade().getCachedVO(media);	
	}
	

}
