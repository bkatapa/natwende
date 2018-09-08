package com.mweka.natwende.operator.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.helper.SeatImporter;
import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.types.SeatClass;

@Named("SeatAction")
@SessionScoped
public class SeatAction extends MessageHelper<SeatVO> {

private static final long serialVersionUID = 1L;
	
	//private SeatSearchVO searchVO;
	private UploadedFile file;	
	
	@Inject
	private BusAction busAction;
	
	@Inject
	private SeatImporter seatImporter;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new SeatVO());
	}	
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public void fileListener(FileUploadEvent event) {
		file = event.getFile();
		RequestContext.getCurrentInstance().update("@(uploadSeat-formClass(uploadSeat-form))");
	}
	
	public SeatClass[] getSeatClassList() {
		return SeatClass.values();
	}

	@Override
	public List<SeatVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		try {
			getSelectedEntity().setBus(busAction.getSelectedEntity());
			serviceLocator.getSeatFacade().saveSeat(getSelectedEntity());
			RequestContext.getCurrentInstance().execute(HIDE_SEAT_ENTRY_DLG);
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return SUCCESS_PAGE;
	}
	
	@Override
	public String viewEntity() {
		return VIEW_PAGE;
	}
	
	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getSeatFacade().deleteSeat(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public void importSeats() {
		if (file == null) {
			onMessage(SEVERITY_ERROR, "No file selected. Please specify file to upload.");
			return;
		}
		try {
			List<SeatVO> resultList = seatImporter.process(file.getInputstream());
			for (SeatVO result : resultList) {
				checkDuplicate(result);
				result.setBus(busAction.getSelectedEntity());
				serviceLocator.getSeatFacade().saveSeat(result);
			}
			onMessage(SEVERITY_INFO, "Seats imported successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getSeatFacade().obtainSeatListByBus(busAction.getSelectedEntity().getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void checkDuplicate(SeatVO seat) throws Exception {
		boolean result = serviceLocator.getSeatFacade().doesSeatExist(
				seat.getSeatNo(), 
				seat.getCoordinates(), 
				busAction.getSelectedEntity().getId()
		);
		if (result) {
			throw new Exception("Seat number [" + seat.getSeatNo() + "] already exists on this bus. You should rather edit it.");
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/seatView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/operator/seatSuccess?faces-redirect=true";
	private static final String HIDE_SEAT_ENTRY_DLG = "PF('var_NewSeatDlg').hide();";
}
