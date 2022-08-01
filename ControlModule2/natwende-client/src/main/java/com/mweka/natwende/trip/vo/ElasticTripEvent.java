package com.mweka.natwende.trip.vo;

import java.io.Serializable;

 public class ElasticTripEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4829690007365560855L;
	private final ElasticTripVO elasticTripVO;
	
	public ElasticTripEvent(ElasticTripVO elasticTripVO) {
		this.elasticTripVO = elasticTripVO;
	}
	
	public ElasticTripVO getObject() {
		return elasticTripVO;
	}
}

