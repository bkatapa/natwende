package com.mweka.natwende.location.vo;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.mweka.natwende.types.Town;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class EmbeddedAddressVO implements Serializable {
		private static final long serialVersionUID = 1L;

		@Size(max = 45)
		private String premises;

		@Size(max = 255)
		private String line1;

		@Size(max = 255)
		private String street;

		@Size(max = 255)
		private String surbab;
		
		private Town town;

		public String getPremises() {
			return premises;
		}

		public void setPremises(String premises) {
			this.premises = premises;
		}

		public String getLine1() {
			return line1;
		}

		public void setLine1(String line1) {
			this.line1 = line1;
		}

		public String getSurbab() {
			return surbab;
		}

		public void setSurbab(String surbab) {
			this.surbab = surbab;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public Town getTown() {
			return town;
		}

		public void setTown(Town town) {
			this.town = town;
		}

		@Override
		public String toString() {
			StringBuilder address = new StringBuilder();
			if (StringUtils.isNotEmpty(premises)) {
				address.append(premises).append(SINGLE_SPACE);
			}
			if (StringUtils.isNotEmpty(line1)) {
				address.append(line1).append(SINGLE_SPACE);
			}
			if (StringUtils.isNotEmpty(street)) {
				address.append(street).append(SINGLE_SPACE);
			}
			if (StringUtils.isNotEmpty(surbab)) {
				address.append(surbab).append(", ");
			}
			if (town != null) {
				address.append(town);
			}
			if (StringUtils.isEmpty(address)) {
				return super.toString();
			}
			return address.toString();
		}
		
		private static final transient String SINGLE_SPACE = " ";
	}