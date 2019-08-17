package com.mweka.natwende.route.action;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.action.OperatorRouteLinkAction;
import com.mweka.natwende.route.vo.FareVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StretchVO;

@Named("FareAction")
@SessionScoped
public class FareAction extends MessageHelper<FareVO> {

	private static final long serialVersionUID = 1L;	
	
	//private FareSearchVO searchVO;
	private Map<Long, FareVO> stretchFareMap;
	private List<StretchVO> stretchList;
	private DataTable fareChart;
	
	@Inject
	private OperatorRouteLinkAction operatorRouteLinkAction;

	@PostConstruct
	public void init() {
		init(FareVO.class);
		setSelectedEntity(new FareVO());
		stretchFareMap = new HashMap<>();
		fareChart = new DataTable();
	}
	
	public DataTable getFareChart() {
		return fareChart;
	}

	public void setFareChart(DataTable fareChart) {
		this.fareChart = fareChart;
	}

	@Override
	public List<FareVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return viewEntity();
	}
	
	@Override
	protected String saveEntity() {
		try {
			selectedEntity.setOperator(operatorRouteLinkAction.getSelectedEntity().getOperator());
			setSelectedEntity(serviceLocator.getFareDataFacade().update(selectedEntity));
			BigDecimal avgFare = serviceLocator.getFareFacade().fetchAvarageFare(selectedEntity.getStretch().getId());
			selectedEntity.getStretch().setFareAmount(avgFare);
			serviceLocator.getStretchFacade().saveStretch(selectedEntity.getStretch());
			selectedEntity.setGlobal(false);
			onMessage(SEVERITY_INFO, "Fare for stretch [" + selectedEntity.getStretch() + "] saved successfully.");
			onFareSelect(selectedEntity, false);
			return SUCCESS_PAGE;
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@Override
	public String viewEntity() {
		RequestContext.getCurrentInstance().update("@widgetVar(var_FareViewDlg)");
		RequestContext.getCurrentInstance().execute("PF('var_FareViewDlg').show();");
		return VIEW_PAGE;
	}
	
	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getFareFacade().deleteFare(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public String saveEntity(FareVO fare) {		
		selectedEntity = fare;
		return saveEntity();
	}
	
	public List<StretchVO> getUnconfiguredStretchFareChart() {
		if (operatorRouteLinkAction.getSelectedEntity().getRoute() == null) {
			return Collections.emptyList();
		}
		stretchList = serviceLocator.getStretchFacade().obtainStretchListGivenRouteId(operatorRouteLinkAction.getSelectedEntity().getRoute().getId());
		for (FareVO fare : getEntityList()) {
			StretchVO stretch = null;
			for (StretchVO s : stretchList) {
				stretch = s;
				if (stretch.getFrom().equals(fare.getStretch().getFrom()) && stretch.getTo().equals(fare.getStretch().getTo())) {
					break;
				}
			}
			if (stretch != null) {
				stretchList.remove(stretch);
			}
		}
		return stretchList;
	}
	
	public void onFareSelect(FareVO selectedFare, boolean isSelected) {
		setSelectedEntity(selectedFare);
		selectedEntity.setSelected(isSelected);
		stretchFareMap.put(selectedEntity.getStretch().getId(), selectedFare);
	}
	
	public void amountChanged(ValueChangeEvent event) {
		BigDecimal value = (BigDecimal) ((UIOutput) event.getSource()).getValue();
		System.out.println("AMOUNT CHANGED: " + value);
		selectedEntity.setAmount(value);
		onFareSelect(selectedEntity, selectedEntity.getSelected());
	}
	
	private void loadEntityList() {
		RouteVO route = operatorRouteLinkAction.getSelectedEntity().getRoute();
		Long routeId = route == null ? null : operatorRouteLinkAction.getSelectedEntity().getRoute().getId();
		List<StretchVO> resultList = serviceLocator.getStretchFacade().obtainStretchListGivenRouteId(routeId);
		try {
			long operatorId = operatorRouteLinkAction.getSelectedEntity().getOperator().getId();
			entityList = serviceLocator.getFareFacade().obtainListByRouteIdAndOperatorId(routeId, operatorId);
			for (StretchVO stretch : resultList) {
				FareVO fare = serviceLocator.getFareFacade().fetchtByOperatorIdAndStretchId(operatorId, stretch.getId());
				if (fare == null) {
					fare = new FareVO();
					fare.setOperator(operatorRouteLinkAction.getSelectedEntity().getOperator());
					fare.setStretch(stretch);
					fare.setAmount(stretch.getFareAmount());
					fare.setGlobal(true);
					entityList.add(fare);
				}
			}
			maintainEditSelection();
		}		
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void maintainEditSelection() {
		for (FareVO fare : entityList) {
			if (!stretchFareMap.isEmpty() && stretchFareMap.containsKey(fare.getStretch().getId())) {
				FareVO cachedFare = stretchFareMap.get(fare.getStretch().getId());
				fare.setSelected(cachedFare.getSelected());
				fare.setAmount(cachedFare.getAmount());
			}
		}
	}
	
	public void printFareChart() {
		if (fareChart == null) {
			return;
		}
		for (int index = 0; index < fareChart.getRowCount(); index++) {
			fareChart.setRowIndex(index);
			String rowKey = fareChart.getRowKey().toString();
			System.out.println("Row key [" + rowKey + "], Row data [" + fareChart.getRowData(rowKey) + "]");
		}
	}
	
	private static final String VIEW_PAGE = "/admin/route/fareView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/operator/operatorRouteLinkView?faces-redirect=true";

}
