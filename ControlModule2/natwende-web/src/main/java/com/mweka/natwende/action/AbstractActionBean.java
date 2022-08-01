package com.mweka.natwende.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.ChartModel;

import com.mweka.natwende.base.vo.BaseVO;

public abstract class AbstractActionBean<T extends BaseVO> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private LazyDataModel<T> entityList;

    protected ChartModel chartModel;

    private T selectedEntity;
    private final Class<T> entityClass;

    protected Log log = LogFactory.getLog(this.getClass());

    protected String placeholder;
    protected int first;

    protected void addFacesWarningMessageForComponent(FacesContext context, UIComponent toValidate, String messageText) {
        FacesMessage message = new FacesMessage(messageText);
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        context.addMessage(toValidate.getClientId(), message);
    }

    protected void addFacesErrorMessageForComponent(FacesContext context, UIComponent toValidate, String messageText) {
        FacesMessage message = new FacesMessage(messageText);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(toValidate.getClientId(), message);
    }

    public AbstractActionBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public AbstractActionBean() {
        this.entityClass = null;
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(T selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public LazyDataModel<T> getEntityList() {
        return entityList;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void refresh() {
        getLog().debug("refresh()");

        entityList = new LazyDataModel<T>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
            	return 0;
            }
            
            @Override
            public T getRowData(String rowKey) {
            	long keyValue = NumberUtils.toLong(rowKey, -2L);
            	if (keyValue == -2L) return null;
            	
            	List<T> dataList = (List<T>) getWrappedData();
            	
            	for (T entity : dataList) {
            		if (entity.getId() == keyValue ) {
            			return entity;
            		}            		
            	}
                return null;
            }
            
            @Override
			public String getRowKey(T entity) {
            	return String.valueOf(entity.getId());
			}

            public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<T> result = getListFromFacade();
                setRowCount(result.size());

                log.trace("First: " + first + "/Page Size: " + pageSize + "/Record Size: " + result.size());

                if (first > result.size()) {
                    first = 0;
                }

                int dif = first + pageSize - result.size();

                //refreshChart(result);

                if (dif < 0) {
                    return result.subList(first, first + pageSize);
                } else {
                    return result.subList(first, first + (pageSize - dif));
                }
            }

            public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
                List<T> result = getListFromFacade();
                setRowCount(result.size());

                log.trace("First: " + first + "/Page Size: " + pageSize + "/Record Size: " + result.size());

                if (first > result.size()) {
                    first = 0;
                }

                int dif = first + pageSize - result.size();

                //refreshChart(result);

                if (dif < 0) {
                    return result.subList(first, first + pageSize);
                } else {
                    return result.subList(first, first + (pageSize - dif));
                }
            }			

			@Override
			public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return null;
			}
        };

    }

    protected void refreshChart(List<T> result) {

    }

    @PostConstruct
    public void init() {
        refresh();
    }

    public String showSelectedEntity(T entity) {
        setSelectedEntity(entity);
        return getViewPage();
    }

    public String create() throws InstantiationException, IllegalAccessException {
        this.selectedEntity = entityClass.newInstance();
        return getViewPage();
    }

    /**
     * Action handler - Called when one press the update button in the form
     *
     * @return
     */
    public String update() {
        try {
            getLog().info("###UPDATE###");
            selectedEntity = facadeUpdate(selectedEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Update successful"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", ex.getMessage()));
        }
        return "";
    }

    /**
     * Action handler - Called when one press the delete button in the form
     *
     * @return
     */
    public String delete() {
        return delete(selectedEntity);
    }

    public String delete(T entity) {
        getLog().info("###DELETE###");
        try {
            facadeDelete(entity);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", ex.getMessage()));
        }
        return ""; // will display the customer list in a table
    }

    /**
     * Action handler - returns to the list of customers in the table
     *
     * @return
     */
    public String list() {
        getLog().info("###LIST###");
        return getListPage();
    }

    protected void redirectToPage(String viewPage) {
        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                .getNavigationHandler();

        configurableNavigationHandler.performNavigation(viewPage);
    }

    protected abstract List<T> getListFromFacade();

    protected abstract T facadeUpdate(T dataItem) throws Exception;

    protected abstract void facadeDelete(T dataItem);

    protected abstract String getViewPage();

    protected abstract String getListPage();

    protected void addFacesMessageInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    protected void addFacesMessageWarn(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    protected void addFacesMessageError(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    protected Log getLog() {
        return log;
    }

    public ChartModel getChartModel() {
        if (chartModel == null) {
            refreshChart(getListFromFacade());
        }
        return chartModel;
    }    

}
