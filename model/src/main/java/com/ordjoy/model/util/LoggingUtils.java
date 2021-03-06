package com.ordjoy.model.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggingUtils {

    public static final String USER_DETAILS_WAS_TRIGGERED = "User details was triggered in service layer:{}";
    public static final String NEW_ADMIN_WAS_ADDED_CONTROLLER = "New Admin was added in Controller:{}";
    public static final String USER_WAS_REGISTERED_CONTROLLER = "New User was registered in Controller:{}";
    public static final String USER_BALANCE_WAS_SUBTRACTED_IN_CONTROLLER = "User balance was subtracted in controller :{}, :{}";
    public static final String EXCEPTION_WAS_THROWN = "Some Exception was thrown :";
    public static final String ORDER_STATUS_WAS_UPDATED_IN_CONTROLLER = "Order status was updated in Controller:{}, :{}";
    public static final String ORDER_WAS_DELETED_IN_CONTROLLER = "Order was deleted in Controller:{}";
    public static final String ORDER_PRICE_WAS_CALCULATED_IN_CONTROLLER = "Order price was calculated in Controller:{}";
    public static final String ORDER_WAS_UPDATED_IN_CONTROLLER = "Order was updated in Controller:{}";
    public static final String ORDER_WAS_CREATED_IN_CONTROLLER = "Order was created in Controller:{}";
    public static final String REVIEW_WAS_CREATED_IN_CONTROLLER = "Review was created in Controller:{}";
    public static final String REVIEW_WAS_DELETED_IN_CONTROLLER = "Review was deleted in Controller:{}";
    public static final String TRACK_WAS_DELETED_IN_CONTROLLER = "Track was deleted in Controller:{}";
    public static final String TRACK_WAS_UPDATED_IN_CONTROLLER = "Track was updated in Controller:{}";
    public static final String TRACK_WAS_CREATED_IN_CONTROLLER = "Track was created in Controller:{}";
    public static final String TRACK_WAS_ADDED_TO_MIX_IN_CONTROLLER = "Track was added to Mix in Controller:{}, :{}";
    public static final String MIX_WAS_DELETED_IN_CONTROLLER = "Mix was deleted in Controller:{}";
    public static final String MIX_WAS_UPDATED_IN_CONTROLLER = "Mix was updated in Controller:{}";
    public static final String MIX_WAS_CREATED_IN_CONTROLLER = "Mix was created in Controller:{}";
    public static final String ALBUM_WAS_DELETED_IN_CONTROLLER = "Album was deleted in Controller:{}";
    public static final String ALBUM_WAS_UPDATED_IN_CONTROLLER = "Album was updated in Controller:{}";
    public static final String ALBUM_WAS_CREATED_IN_CONTROLLER = "Album was created in Controller:{}";
    public static final String ENTITY_WAS_DELETED_REPO = "Entity was deleted in Repository layer :{}";
    public static final String ENTITY_WAS_UPDATED_REPO = "Entity was updated in Repository layer :{}";
    public static final String ENTITY_WAS_SAVED_IN_REPO = "Entity was saved in Repository layer :{}";
    public static final String USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_REPO = "User discount percentage level was updated in Repository layer :{}";
    public static final String USER_ACCOUNT_BALANCE_WAS_UPDATED_REPO = "User account balance was updated in Repository layer :{}";
    public static final String TRACK_WAS_ADDED_TO_MIX_REPO = "Track was added to mix in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_USER_LOGIN_REPO = "Reviews by user login in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_USER_ID_REPO = "Reviews by user id in Repository layer :{}, :{}";
    public static final String TRACKS_BY_MIX_TITLE_REPO = "Tracks by mix title in Repository layer :{}, :{}";
    public static final String TRACKS_BY_ALBUM_TITLE_REPO = "Tracks by mix id in Repository layer :{}, :{}";
    public static final String TRACKS_BY_ALBUM_ID_REPO = "Tracks by album id in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_MIX_TITLE_REPO = "Reviews by mix title in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_MIX_ID_REPO = "Reviews by mix id in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_TRACK_TITLE_REPO = "Reviews by track title in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_TRACK_ID_REPO = "Reviews by track id in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_ALBUM_TITLE_REPO = "Reviews by album title in Repository layer :{}, :{}";
    public static final String REVIEWS_BY_ALBUM_ID_REPO = "Reviews by album id in Repository layer :{}, :{}";
    public static final String USER_BALANCE_WAS_SUBTRACTED_REPO = "User balance was subtracted in Repository layer :{}, :{}";
    public static final String TRACKS_BY_ALBUM_TITLE_SERVICE = "Tracks by album title in Service layer :{}, :{}";
    public static final String TRACKS_BY_ALBUM_ID_SERVICE = "Tracks by album id in Service layer :{}, :{}";
    public static final String REVIEWS_BY_ALBUM_TITLE_SERVICE = "Reviews by album title in Service layer :{}, :{}";
    public static final String REVIEWS_BY_ALBUM_ID_SERVICE = "Reviews by album id in Service layer :{}, :{}";
    public static final String ALBUM_WAS_SAVED_IN_SERVICE = "Album was saved in Service layer :{}";
    public static final String IS_ALBUM_ALREADY_EXISTS = "Is album already exists :{}, :{}";
    public static final String ALBUM_WAS_UPDATED_IN_SERVICE = "Album was updated in Service layer :{}";
    public static final String ALBUM_WAS_DELETED_IN_SERVICE = "Album was deleted in Service layer :{}";
    public static final String USER_WAS_SAVED_IN_SERVICE = "User was saved in Service layer :{}";
    public static final String ADMIN_WAS_SAVED_IN_SERVICE = "User with ADMIN role was saved in Service layer :{}";
    public static final String USER_RIGHTS_TO_REGISTER = "User has rights to register :{}";
    public static final String USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_SERVICE = "User discount percentage level was updated in Service layer :{}, :{}";
    public static final String USER_BALANCE_WAS_UPDATED_SERVICE = "User account balance was updated in Service layer :{}, :{}";
    public static final String IS_USER_EMAIL_EXISTS = "User with this email exists :{}, :{}";
    public static final String IS_USER_LOGIN_EXISTS = "User with this email exists :{}, :{}";
    public static final String USER_WAS_UPDATED_SERVICE = "User was updated in Service layer :{}";
    public static final String USER_WAS_DELETED_SERVICE = "User was deleted in Service layer :{}";
    public static final String MIX_WAS_SAVED_SERVICE = "Mix was saved in Service layer :{}";
    public static final String IS_MIX_EXISTS_SERVICE = "Mix exists :{}, :{}, :{}";
    public static final String MIX_WAS_UPDATED_SERVICE = "Mix was updated in Service layer :{}";
    public static final String MIX_WAS_DELETE_SERVICE = "Mix was deleted in Service layer :{}";
    public static final String IS_TRACK_EXISTS = "Track exists :{}, :{}, :{}";
    public static final String TRACK_WAS_UPDATED_SERVICE = "Track was updated in Service layer :{}";
    public static final String TRACK_WAS_DELETE_SERVICE = "Track was deleted in Service layer :{}";
    public static final String TRACK_WAS_SAVED_SERVICE = "Track was saved in Service layer :{}";
    public static final String TRACK_WAS_ADDED_TO_MIX_SERVICE = "Track was added to mix in Service layer :{}, :{}";
    public static final String ORDER_WAS_CREATED_SERVICE = "Order was created in Service layer :{}";
    public static final String ORDER_WAS_UPDATED_SERVICE = "Order was updated in Service layer :{}";
    public static final String ORDER_WAS_DELETED_SERVICE = "Order was deleted in Service layer :{}";
    public static final String USER_BALANCE_WAS_SUBTRACTED_SERVICE = "User balance was subtracted in Service layer :{}, :{}";
    public static final String ORDER_PRICE_WAS_CALCULATED = "Order price was calculated in Service layer :{}";
    public static final String ALBUM_REVIEW_WAS_DELETED_SERVICE = "Album review was deleted in Service layer :{}";
    public static final String ALBUM_REVIEW_WAS_UPDATED_SERVICE = "Album review was updated in Service layer :{}";
    public static final String ALBUM_REVIEW_WAS_ADDED_SERVICE = "Album review was added in Service layer :{}";
    public static final String MIX_REVIEW_WAS_ADDED_SERVICE = "Mix review was added in Service layer :{}";
    public static final String MIX_REVIEW_WAS_UPDATED_SERVICE = "Mix review was updated in Service layer :{}";
    public static final String MIX_REVIEW_WAS_DELETED_SERVICE = "Mix review was deleted in Service layer :{}";
    public static final String TRACK_REVIEW_WAS_DELETED_SERVICE = "Track review was added in Service layer :{}";
    public static final String TRACK_REVIEW_WAS_UPDATED_SERVICE = "Track review was updated in Service layer :{}";
    public static final String TRACK_REVIEW_WAS_ADDED_SERVICE = "Track review was deleted in Service layer :{}";
    public static final String ORDER_STATUS_WAS_UPDATED_REPO = "Order status was updated in Repository layer :{}, :{}";
    public static final String ORDER_STATUS_WAS_UPDATED_SERVICE = "Order status was updated in Service layer :{}";
    public static final String USER_BALANCE_WAS_UPDATED_IN_CONTROLLER = "User balance was updated in Controller:{}, :{}";
    public static final String USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_IN_CONTROLLER = "User discount percentage level was updated in Controller:{}, :{}";
    public static final String USER_WAS_DELETED_IN_CONTROLLER = "User was deleted in Controller:{}";
}