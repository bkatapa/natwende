package com.mweka.natwende.route.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.Config;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.route.search.vo.StretchSearchVO;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.util.ReverseTransitComparator;

@Named("StretchAction")
@SessionScoped
public class StretchAction extends MessageHelper<StretchVO> {

	private static final long serialVersionUID = 1L;

	// private StretchSearchVO searchVO;
	private int currentIndex, initialGraphLen;
	private boolean mirror;
	private List<StopVO> allRouteStationList;
	private List<StretchVO> finalEntityList, allStretchList;
	private Config config;
	private StretchSearchVO searchVO;

	@Inject
	private RouteAction routeAction;

	@PostConstruct
	//@Override
	public void init() {
		setSelectedEntity(new StretchVO());
		entityList = new ArrayList<>();
		finalEntityList = new ArrayList<>();
		allStretchList = new ArrayList<>();
		allRouteStationList = new ArrayList<>();
		currentIndex = 0;
		initialGraphLen = 0;
		mirror = false;
		config = new Config();
		if (searchVO != null) {
			searchVO.clearSearch();
		}
	}

	@Override
	public List<StretchVO> getEntityList() {		
		return entityList;
	}
	
	public List<StretchVO> getFinalEntityList() {		
		return finalEntityList;
	}
	
	public List<StretchVO> getFinalEntityListDB() {		
		return serviceLocator.getStretchFacade().obtainStretchListGivenRouteId(routeAction.getSelectedEntity().getId());
	}
	
	public Date getTotalTravelTime() {
		return new Date();
	}

	public boolean isMirror() {
		return mirror;
	}

	public int getInitialGraphLen() {
		return initialGraphLen;
	}

	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}
	
	public Config getConfig() {
		return config;
	}

	public StretchSearchVO getSearchVO() {
		if (searchVO == null) {
			searchVO = new StretchSearchVO();
		}
		return searchVO;
	}

	public void setSearchVO(StretchSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	@Override
	public String createEntity() {
		init();
		return viewEntity();
	}

	@Override
	public String saveEntity() {
		try {
			serviceLocator.getStretchFacade().saveStretch(getSelectedEntity());
			executeScript("PF('var_StretchViewDlg').hide();");
		} catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return "";//SUCCESS_PAGE;
	}
	
	public void saveEntityList() throws Exception {
		try {
			serviceLocator.getStretchFacade().saveStretchList(finalEntityList);
			serviceLocator.getRouteStretchLinkFacade().createNewRouteStretchLinkList(routeAction.getSelectedEntity(), finalEntityList);
			if (mirror) {
				mirror = false;
				executeScript(SHOW_CONFIRM_MIRROR_DIALOG);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}

	@Override
	public String viewEntity() {
		return "";//VIEW_PAGE;
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getStretchFacade().deleteStretch(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		} catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}

	public List<StopVO> getAllRouteStationList() {
		return allRouteStationList;
	}
	
	public void getAllRouteStationList(List<StopVO> allRouteStationList) {
		this.allRouteStationList = allRouteStationList;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public void loadEntityList() {
		entityList = serviceLocator.getStretchDataFacade().findBySearchVO(getSearchVO());
	}

	public void onPageLoad() {
		activateLast();
		allRouteStationList.clear();
		allRouteStationList.add(routeAction.getSelectedEntity().getStart());
		allRouteStationList.addAll(routeAction.getTransitStationList());
		allRouteStationList.add(routeAction.getSelectedEntity().getStop());			
		try {
			allStretchList.clear();
			Iterator<StopVO> it = allRouteStationList.iterator();
			while (it.hasNext()) {
				StopVO from = it.next();
				for (int i = allRouteStationList.indexOf(from) + 1; i < allRouteStationList.size(); i++) {
					StopVO to = allRouteStationList.get(i);
					StretchVO result = serviceLocator.getStretchFacade().getStretchByEndpoints(from.getId(), to.getId());
					result = result == null ? new StretchVO(from, to) : result;
					if (!allStretchList.contains(result)) {
						allStretchList.add(result);
					}
				}
			}
			StretchVO nextStretch = fetchNextStretch();
			setSelectedEntity(nextStretch);
			config.setStretchLen(allRouteStationList.size() - 1);
			updateStep5();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}		
	}	
	
	public void addToEntityList() {
		if (allRouteStationList.size() > currentIndex) {
			entityList.add(selectedEntity);
			activateLast();
			setCurrentIndex(currentIndex + 1);
			
			if (allRouteStationList.size() - 1 == getCurrentIndex()) { // done.
				try { // Compute travel times between all route stretch permutations
					finalEntityList = serviceLocator.getStretchHelper().computeAllTravelTimePermutations(entityList, allStretchList);
					log.debug("Step5 complete");
					onMessage(SEVERITY_INFO, "All travel time estimates for all route stretches configured successfully.");
				}
				catch (Exception ex) {
					ex.printStackTrace();
					onMessage(SEVERITY_ERROR, ex.getMessage());
				}
			}
			else {
				setSelectedEntity(fetchNextStretch());				
			}
		}
		updateStep5();
		updateComponent("@(.stretchTable-class(stretch-table))");
	}
	
	public void onEntityRemove() {
		if (entityList.isEmpty()) {
			return;
		}
		currentIndex = entityList.size() - 1;
		setSelectedEntity(entityList.get(currentIndex));
		//allStretchList.clear();
		updateStep5();
	}
	
	//@Transactional
	public String createMirror() {
		try {			
			// 1. Reverse route name.
			String[] splitRouteName = routeAction.getSelectedEntity().getName().split("-");
			String reversedRouteName = splitRouteName.length == 2 ? splitRouteName[1] + " - " + splitRouteName[0].trim()
					: new StringBuilder(routeAction.getSelectedEntity().getName()).reverse().toString();
			
			// 2. Check if a potential mirror exists. If so, stop processing and alert the user.
			RouteVO duplicateRoute = serviceLocator.getRouteFacade().fetchByNameStartAndFinalStopStations(reversedRouteName, routeAction.getSelectedEntity().getStop().getId(), routeAction.getSelectedEntity().getStart().getId());
			if (duplicateRoute != null) {
				throw new Exception("Possible duplicate mirror route was detected [" + reversedRouteName + "]");
			}
			
			// 3. Reverse transit routes.
			List<RouteStopLinkVO> transitStationList = new ArrayList<>(serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(routeAction.getSelectedEntity()));
			Collections.sort(transitStationList, new ReverseTransitComparator());
			for (RouteStopLinkVO link : transitStationList) {
				link.setStationIndex(transitStationList.indexOf(link));
				link.setId(-1L);
				link.setUniqueId(link.getUniqueId() + "x");
			}
			
			// 4. Save route and mirrors.
			StopVO from = routeAction.getSelectedEntity().getStop();
			StopVO to = routeAction.getSelectedEntity().getStart();
			RouteVO reversedRoute = new RouteVO(reversedRouteName, from, to);
			reversedRoute.setMirrorRoute(routeAction.getSelectedEntity());			
			reversedRoute = serviceLocator.getRouteFacade().saveRoute(reversedRoute);						
			serviceLocator.getRouteStopLinkFacade().saveRouteStopLinkList(transitStationList, reversedRoute);
			
			routeAction.getSelectedEntity().setMirrorRoute(reversedRoute);
			serviceLocator.getRouteFacade().updateRoute(routeAction.getSelectedEntity());
			
			// 5. Reverse route-stretch end-points.
			List<StretchVO> stretchList = serviceLocator.getStretchFacade().obtainStretchListGivenRouteId(routeAction.getSelectedEntity().getId());
			List<StretchVO> reverseStretchList = new ArrayList<>();
			for (StretchVO s : stretchList) {
				from = s.getTo();
				to = s.getFrom();
				StretchVO result = serviceLocator.getStretchFacade().getStretchByEndpoints(from.getId(), to.getId());
				reverseStretchList.add(result == null ? new StretchVO(from, to, s.getEstimatedTravelTime()) : result);
			}
			reverseStretchList = serviceLocator.getStretchFacade().saveStretchList(reverseStretchList);
			serviceLocator.getRouteStretchLinkFacade().createNewRouteStretchLinkList(reversedRoute, reverseStretchList);
			executeScript(HIDE_CONFIRM_MIRROR_DIALOG);
			//return ROUTE_SUCCESS_PAGE;
			updateComponent(SUCCESS_DIALOG_BOX);
			executeScript(SHOW_SUCCESS_DIALOG);
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}
	
	public void onStretchSelect() {		
	}
	
	private void activateLast() {
		if (entityList.isEmpty()) {
			return;
		}
		for (StretchVO graph : entityList) {
			graph.setSelected(true);
		}
		entityList.get(entityList.size() - 1).setSelected(false);
	}
	
	public void handleClose() {		
	}
	
	private StretchVO fetchNextStretch() {
		StopVO from = allRouteStationList.get(currentIndex);
		StopVO to = allRouteStationList.get(currentIndex + 1);
		StretchVO nextStretch = null;
		try {
			nextStretch = serviceLocator.getStretchFacade().getStretchByEndpoints(from.getId(), to.getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return nextStretch == null ? new StretchVO(from, to) : nextStretch;
	}
	
	private void updateStep5() {
		config.setCurIndex(currentIndex);
		if (selectedEntity != null) {
			config.setEditTravelTime(selectedEntity.getId() == -1L);
		}
		config.setDone(config.getStretchLen() == config.getCurIndex());
	}
	
	private static transient final String SHOW_CONFIRM_MIRROR_DIALOG = "PF('var_ConfirmMirrorDlg').show();";
	private static transient final String HIDE_CONFIRM_MIRROR_DIALOG = SHOW_CONFIRM_MIRROR_DIALOG.replace("show", "hide");
	private static transient final String SUCCESS_DIALOG_BOX = "@widgetVar(var_RouteCreateSuccessDlg)";
	private static transient final String SHOW_SUCCESS_DIALOG = "PF('var_RouteCreateSuccessDlg').show();";
	//private static transient final String HIDE_SUCCESS_DIALOG = SHOW_SUCCESS_DIALOG.replace("show", "hide");

}
