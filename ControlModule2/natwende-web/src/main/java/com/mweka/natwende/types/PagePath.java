/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.types;

public enum PagePath {

    HOME("Home"),
    CREATE_USER_VIEW("/admin/user/userView"),
    BOOKING_MANAGEMENT_VIEW("BookingManagementView"),
    BOOKING_MANAGEMENT_LIST("BookingManagementList"),
    BOOKING_MANAGEMENT_EDIT("BookingManagementEdit"),
    PARKING_SITE_ADMIN_LIST("ParkingSiteList"),
    PARKING_SITE_ADMIN_VIEW("ParkingSiteAdmin"),
    PARKING_SITE_LINK_TENANT("ParkingSiteLinkTenant"), 
    PARKING_SITE_ARTICLE_ADMIN_VIEW("ParkingSiteArticleView"),
    PARKING_SITE_ARTICLE_ADMIN_LIST("ParkingSiteArticleList"),
    PARKING_SITE_VALIDATION_PROVIDER_ADMIN_VIEW("ParkingSiteValidationProviderView"),
    PARKING_SITE_VALIDATION_PROVIDER_ADMIN_LIST("ParkingSiteValidationProviderList"), 
    BOOKING_TYPE_VIEW("BookingTypeView"),
    CREATE_BULK_BOOKING_VIEW("CreateBulkBooking"),
    BULK_BOOKING_LIST("BulkBookingList"),
    GENERATE_REPORT_VIEW("GenerateReportView"),
    ;

    private PagePath(String path) {
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }

}
