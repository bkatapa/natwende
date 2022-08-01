package com.mweka.natwende.payment.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.PaymentStatus;

@Stateless
public class PaymentDataFacade extends AbstractDataFacade<PaymentVO, Payment> {

    public PaymentDataFacade() {
        super(PaymentVO.class, Payment.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Payment updateEntity(PaymentVO vo) {
    	Payment entity = vo.getId() > 0 ? findById(vo.getId()) : new Payment();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Payment entity, PaymentVO vo) {
    	convertBaseEntityToVO(entity, vo);
    	
    	vo.setAddress(entity.getAddress());
    	vo.setAmount(entity.getAmount());
    	vo.setBeneficiary(entity.getBeneficiary());
    	vo.setCustomerName(entity.getCustomerName());
    	vo.setPaymentStatus(entity.getPaymentStatus());
    	vo.setPaymentOption(entity.getPaymentOption());
    	vo.setPhoneNumber(entity.getPhoneNumber());
    	vo.setRef(entity.getRef());
    	vo.setCardNumber(entity.getCardNumber());
    	vo.setExpDate(entity.getExpDate());
    	vo.setCvv2(entity.getCvv2());
    	vo.setCustomerNrc(entity.getCustomerNrc());
    	vo.setNameOnCard(entity.getNameOnCard());
    	vo.setDescription(entity.getDescription());
    }

    @Override
    public Payment convertVOToEntity(PaymentVO vo, Payment entity) {
        convertBaseVOToEntity(vo, entity);
        
        entity.setAddress(vo.getAddress());
    	entity.setAmount(vo.getAmount());
    	entity.setBeneficiary(vo.getBeneficiary());
    	entity.setCustomerName(vo.getCustomerName());
    	entity.setPaymentStatus(vo.getPaymentStatus());
    	entity.setPaymentOption(vo.getPaymentOption());
    	entity.setPhoneNumber(vo.getPhoneNumber());
    	entity.setRef(vo.getRef());
    	entity.setCardNumber(vo.getCardNumber());
    	entity.setExpDate(vo.getExpDate());
    	entity.setCvv2(vo.getCvv2());
    	entity.setCustomerNrc(vo.getCustomerNrc());
    	entity.setNameOnCard(vo.getNameOnCard());
    	entity.setDescription(vo.getDescription());
    	
        return entity;
    }

    @Override
    public PaymentVO update(PaymentVO vo) {
    	Payment entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<PaymentVO> getPaymentList() {
    	return transformList(findAll());
    }
    
    public PaymentVO getByRef(String ref) {
    	List<Payment> resultList = createNamedQuery(Payment.QUERY_FIND_BY_REF, getEntityClass())
    			.setParameter(Payment.PARAM_REF, ref)
    			.getResultList();
    	return getVOFromList(resultList);
    }
    
    public List<PaymentVO> getPaymentListByNrcAndStatus(String customerNrc, PaymentStatus paymentStatus) {
    	List<Payment> resultList = createNamedQuery(Payment.QUERY_FIND_BY_NRC_AND_PAYMENT_STATUS, getEntityClass())
    			.setParameter(Payment.PARAM_CUSTOMER_NRC, customerNrc)
    			.setParameter(Payment.PARAM_PAYMENT_STATUS, paymentStatus)
    			.getResultList();
    	return transformList(resultList);
    }
    
    public List<PaymentVO> getPaymentListByPhoneAndStatus(String phoneNumber, PaymentStatus paymentStatus) {
    	List<Payment> resultList = createNamedQuery(Payment.QUERY_FIND_BY_PHONE_AND_PAYMENT_STATUS, getEntityClass())
    			.setParameter(Payment.PARAM_PHONE_NUMBER, phoneNumber)
    			.setParameter(Payment.PARAM_PAYMENT_STATUS, paymentStatus)
    			.getResultList();
    	return transformList(resultList);
    }
}
