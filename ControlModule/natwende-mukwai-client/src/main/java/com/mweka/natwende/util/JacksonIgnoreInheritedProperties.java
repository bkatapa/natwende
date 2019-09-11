package com.mweka.natwende.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.types.Town;

public class JacksonIgnoreInheritedProperties {

	private static class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5545927617870919692L;

		@Override
		public boolean hasIgnoreMarker(final AnnotatedMember m) {
			return m.getDeclaringClass() == BaseVO.class
					|| m.getDeclaringClass() == BaseSearchVO.class
					|| super.hasIgnoreMarker(m);
		}
	}
	
	public static JacksonAnnotationIntrospector getIntrospetor() {
		return new IgnoreInheritedIntrospector();
	}

	public static void main(String[] args) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new IgnoreInheritedIntrospector());
		final TripSearchVO searchVO = new TripSearchVO(Town.LUSAKA, Town.KITWE, new java.util.Date());
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(searchVO));
	}

}