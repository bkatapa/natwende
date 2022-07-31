package com.mweka.natwende.types;

public enum Town implements Displayable {
	
	// Copperbelt
	KITWE("Kitwe", Province.COPPERBELT),
    NDOLA("Ndola", Province.COPPERBELT),
    LUANSHYA("Luanshya", Province.COPPERBELT),
    CHINGOLA("Chingola", Province.COPPERBELT),
    MUFULIRA("Mufulira", Province.COPPERBELT),
    CHILILABOMBWE("Chililabombwe", Province.COPPERBELT),
    KALULUSHI("Kalulushi", Province.COPPERBELT),
    
    // Central
    KAPIRI("Kapiri", Province.CENTRAL),
    KABWE("Kabwe", Province.CENTRAL),
    MKUSHI("Mkushi", Province.CENTRAL),
    SERENJE("Serenje", Province.CENTRAL),
    //TUTA("Tuta turn-off", Province.CENTRAL),
    MUMBWA("Mumbwa", Province.CENTRAL),
    
    // Lusaka
    LUSAKA("Lusaka", Province.LUSAKA),
    KAFUE("Kafue", Province.LUSAKA),
    CHONGWE("Chongwe", Province.LUSAKA),
    LUANGWA("Luangwa", Province.LUSAKA),
    MWEMBESHI("Mwembeshi", Province.LUSAKA),
    CHILANGA("Chilanga", Province.LUSAKA),
    
    // Southern
    MAZABUKA("Mazabuka", Province.SOUTHERN),
    MONZE("Monze", Province.SOUTHERN),
    CHOMA("Choma", Province.SOUTHERN),
    KALOMO("Kalomo", Province.SOUTHERN),
    ZIMBA("Zimba", Province.SOUTHERN),
    LIVINGSTONE("Livingstone", Province.SOUTHERN),
    KAZUNGULA("Kazungula", Province.SOUTHERN),
    
    // Muchinga
    MPIKA("Mpika", Province.MUCHINGA),
    CHINSALI("Chinsali", Province.MUCHINGA),
    ISOKA("Isoka", Province.MUCHINGA),
    NAKONDE("Nakonde", Province.MUCHINGA),
    
    // Northern
    KASAMA("Kasama", Province.NORTHERN),
    MBALA("Mbala", Province.LUAPULA),
    MPOROKOSO("Mporokoso", Province.NORTHERN),
    MPULUNGU("Mpulungu", Province.NORTHERN),
    
    // Luapula
    MANSA("Mansa", Province.LUAPULA),
    SAMFYA("Samfya", Province.LUAPULA),
    CHEMBE("Chembe", Province.LUAPULA),
    MWENSE("Mwense", Province.LUAPULA),
    KAWAMBWA("Kawambwa", Province.LUAPULA),
    
    // North-western
    SOLWEZI("Solwezi", Province.NORTH_WESTERN),
    ZAMBEZI("Zambezi", Province.NORTH_WESTERN),
    KASEMPA("Kasempa", Province.NORTH_WESTERN),
    MWINILUNGA("Mwinilunga", Province.NORTH_WESTERN),
    KABOMPO("Kabompo", Province.NORTH_WESTERN),
    
    // Eastern
    CHIPATA("Chipata", Province.EASTERN),
    NYIMBA("Nyimba", Province.EASTERN),
    PETAUKE("Petauke", Province.EASTERN),
    SINDA("Sinda", Province.EASTERN),
    LUNDAZI("Lundazi", Province.EASTERN),
    
    // Western
    MONGU("Mongu", Province.WESTERN),
	SENANGA("Senanga", Province.WESTERN),
	KALABO("Kalabo", Province.WESTERN),
	KAOMA("Kaoma", Province.WESTERN),
	LUKULU("Lukulu", Province.WESTERN);
	
    private final String display;
    private final Province province;

    private Town(String display, Province province) {;
        this.display = display;
        this.province = province;
    }

	@Override
	public String getDisplay() {		
		return display;
	}
	
	public Province getProvince() {		
		return province;
	}
	
	@Override
	public String toString() {
		return display 
				+ " " 
				+ province.getDisplay() 
				+ ", " 
				+ province.getCountry().getDisplay(); 
	}
	
	public static Town getByDisplay(String display) {
		for (Town t : values()) {
			if (t.getDisplay().equals(display)) {
				return t;
			}
		}
		return null;
 	}
}
