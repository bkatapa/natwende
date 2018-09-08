package com.mweka.natwende.location.facade;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.location.entity.Address;
import com.mweka.natwende.location.vo.AddressVO;
import javax.ejb.Stateless;

@Stateless
public class AddressDataFacade extends AbstractDataFacade<AddressVO, Address> {

    public AddressDataFacade() {
    	super(AddressVO.class, Address.class);
    }

    @Override
    protected void convertEntitytoVO(Address entity, AddressVO vo) {
		vo.setName(entity.getName());
		vo.setLine1(entity.getLine1());
		vo.setCity(entity.getCity());
		vo.setProvince(entity.getProvince());
		vo.setPostalCode(entity.getPostalCode());
		vo.setCountry(entity.getCountry());
		vo.setStreet(entity.getStreet());
		vo.setSurbub(entity.getSurbub());
    }

    @Override
    protected Address convertVOToEntity(AddressVO vo, Address entity) {
		convertBaseVOToEntity(vo, entity);
		entity.setName(vo.getName());
		entity.setLine1(vo.getLine1());
		entity.setCity(vo.getCity());
		entity.setProvince(vo.getProvince());
		entity.setPostalCode(vo.getPostalCode());
		entity.setCountry(vo.getCountry());
		entity.setStreet(vo.getStreet());
		entity.setSurbub(vo.getSurbub());
		return entity;
    }

    @Override
    public Address updateEntity(AddressVO vo) {
		Address entity = vo.getId() > 0 ? findById(vo.getId()) : new Address();		
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
    }

}
