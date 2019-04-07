package com.mweka.natwende.user.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.payment.vo.CardVO;

@Named("CardAction")
@SessionScoped
public class CardAction extends MessageHelper<CardVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 639186094057241940L;

	@Inject
	private UserAction userAction;
	
	@PostConstruct
	public void init() {
		selectedEntity = new CardVO();
	}
	
	@Override
	public List<CardVO> getEntityList() {
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return null;
	}

	@Override
	public String saveEntity() {
		selectedEntity.setOwner(userAction.getSelectedEntity());
		selectedEntity = serviceLocator.getCardDataFacade().update(selectedEntity);
		RequestContext.getCurrentInstance().execute("PF('var_CardViewDlg').hide();");
		onMessage(SEVERITY_INFO, "Card saved successfully");
		return null;
	}

	@Override
	public String viewEntity() {
		RequestContext.getCurrentInstance().update("@widgetVar(var_CardViewDlg)");
		RequestContext.getCurrentInstance().execute("PF('var_CardViewDlg').show();");
		return null;
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getCardDataFacade().deleteById(selectedEntity.getId());
		} catch (EntityNotFoundException e) {
			log.debug(e);
			onMessage(SEVERITY_ERROR, e.getMessage());
		}		
	}
	
	public void promoteCard() {
		serviceLocator.getCardFacade().updateCards(entityList, false);
		selectedEntity.setPrimary(true);
		serviceLocator.getCardDataFacade().update(selectedEntity);
		onMessage(SEVERITY_INFO, "Primary card changed successfully.");
	}
	
	private void loadEntityList() {
		entityList = serviceLocator.getCardDataFacade().getListByUser(userAction.getSelectedEntity());
	}

}
