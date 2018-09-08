package com.mweka.natwende.media.facade;

import javax.ejb.Stateless;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.media.entity.Media;
import com.mweka.natwende.media.vo.MediaVO;

@Stateless
public class MediaDataFacade extends AbstractDataFacade<MediaVO, Media> {

	public MediaDataFacade() {
		super(MediaVO.class, Media.class);
	}

	@Override
	protected void convertEntitytoVO(Media media, MediaVO mediaVO) {		
		mediaVO.setLength(media.getLength());
		mediaVO.setData(media.getData());
		mediaVO.setFileName(media.getFileName());
		mediaVO.setMime(media.getMime());
	}

	@Override
	protected Media convertVOToEntity(MediaVO mediaVO, Media media) {
		 convertBaseVOToEntity(mediaVO, media);		 
		 media.setLength(mediaVO.getLength());
		 media.setData(mediaVO.getData());
		 media.setFileName(mediaVO.getFileName());
		 media.setMime(mediaVO.getMime());
		return media;
	}

	@Override
	protected Media updateEntity(MediaVO vo) throws EntityNotFoundException {
		Media media;
		if (vo.getId() > 0) {
			media = findById(vo.getId());
		} else {
			media = new Media();
		}
		convertVOToEntity(vo, media);
		update(media);
		return media;
	}

}
