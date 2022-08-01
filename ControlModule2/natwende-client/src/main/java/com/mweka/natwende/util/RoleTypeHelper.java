package com.mweka.natwende.util;

import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleTypeHelper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private boolean systemAdministrator,
    				natwendeAdministrator,
    				busUser,
    				busOperator,
    				busAdministrator,
    				busHost,
    				busDriver,
    				passenger;

	private UserVO userVO;

    public RoleTypeHelper() {
    }

    public RoleTypeHelper(UserVO userVO, List<RoleVO> roleVOs) {
        this.userVO = userVO;
        setRoleVOs(roleVOs);
    }

    public RoleTypeHelper(List<RoleVO> roleVOs) {
        setRoleVOs(roleVOs);
    }
    
    public boolean isSystemAdministrator() {
        return systemAdministrator;
    }

    public void setSystemAdministrator(boolean systemAdministrator) {
        this.systemAdministrator = systemAdministrator;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }    

    public boolean isNatwendeAdministrator() {
		return natwendeAdministrator;
	}

	public void setNatwendeAdministrator(boolean natwendeAdministrator) {
		this.natwendeAdministrator = natwendeAdministrator;
	}

	public boolean isBusUser() {
		return busUser;
	}

	public void setBusUser(boolean busUser) {
		this.busUser = busUser;
	}

	public boolean isBusOperator() {
		return busOperator;
	}

	public void setBusOperator(boolean busOperator) {
		this.busOperator = busOperator;
	}

	public boolean isBusAdministrator() {
		return busAdministrator;
	}

	public void setBusAdministrator(boolean busAdministrator) {
		this.busAdministrator = busAdministrator;
	}
	
	public boolean isBusDriver() {
		return busDriver;
	}

	public void setBusDriver(boolean busDriver) {
		this.busDriver = busDriver;
	}
	
	public boolean isBusHost() {
		return busHost;
	}

	public void setBusHost(boolean busHost) {
		this.busHost = busHost;
	}
	
	public boolean isPassenger() {
		return passenger;
	}

	public void setPassenger(boolean passenger) {
		this.passenger = passenger;
	}

	public List<RoleVO> getRoleVOs() {
        List<RoleVO> roles = new ArrayList<RoleVO>();
        if (systemAdministrator) {
            roles.add(new RoleVO(RoleType.SYSTEM_ADMINISTRATOR));
        }
        if (natwendeAdministrator) {
            roles.add(new RoleVO(RoleType.NATWENDE_ADMINISTRATOR));
        }
        if (busUser) {
            roles.add(new RoleVO(RoleType.BUS_USER));
        }
        if (busOperator) {
            roles.add(new RoleVO(RoleType.BUS_OPERATOR));
        }
        if (busAdministrator) {
            roles.add(new RoleVO(RoleType.BUS_ADMINISTRATOR));
        }
        if (busHost) {
            roles.add(new RoleVO(RoleType.BUS_HOST));
        }
        if (busDriver) {
            roles.add(new RoleVO(RoleType.BUS_DRIVER));
        }
        if (passenger) {
            roles.add(new RoleVO(RoleType.PASSENGER));
        }
        return roles;
    }

    public void setRoleVOs(List<RoleVO> roles) {
        for (RoleVO roleVO : roles) {
            if (roleVO.getRoleType() == RoleType.SYSTEM_ADMINISTRATOR) {
                this.systemAdministrator = true;
            }
            if (roleVO.getRoleType() == RoleType.BUS_USER) {
                this.busUser = true;
            }
            if (roleVO.getRoleType() == RoleType.NATWENDE_ADMINISTRATOR) {
                this.natwendeAdministrator = true;
            }
            if (roleVO.getRoleType() == RoleType.BUS_OPERATOR) {
                this.busOperator = true;
            }
            if (roleVO.getRoleType() == RoleType.BUS_ADMINISTRATOR) {
                this.busAdministrator = true;
            }
            if (roleVO.getRoleType() == RoleType.BUS_HOST) {
                this.busHost = true;
            }
            if (roleVO.getRoleType() == RoleType.BUS_DRIVER) {
                this.busDriver = true;
            }
            if (roleVO.getRoleType() == RoleType.PASSENGER) {
                this.passenger = true;
            }
        }
    }

}
