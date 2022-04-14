package com.ordjoy.web.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPathUtils {

    public static final String LOGIN_PAGE_URL = "/login";
    public static final String SUCCESS_HANDLER_URL = "/success";
    public static final String DISPATCHER_SERVLET_URL_PATTERN = "/";
    public static final String ID_PATH_VARIABLE = "id";
    public static final String TITLE_PARAM = "title";
    public static final String USERNAME_PARAM = "username";
    public static final String LIMIT_PARAM = "limit";
    public static final String OFFSET_PARAM = "offset";
    public static final String REDIRECT_ALBUM = "redirect:/auth/album/";
    public static final String REDIRECT_MIX = "redirect:/mix/";
    public static final String REDIRECT_TRACK_UPDATE_FORM = "redirect:/admin/track/update/";
    public static final String REDIRECT_MIXES_PAGE = "redirect:/auth/mix/all?limit=5&offset=0";
    public static final String REDIRECT_TRACKS_PAGE = "redirect:/user/tracks";
    public static final String REDIRECT_USER_ORDERS_PAGE = "redirect:/user/order/all?limit=5&offset=0";
    public static final String REDIRECT_ORDER_MAIN_PAGE = "redirect:/admin/order/";
    public static final String REDIRECT_LOGIN_PAGE = "redirect:/account/login";
    public static final String REDIRECT_USERS_PAGE = "redirect:/admin/account/all?limit=5&offset=0";
    public static final String REDIRECT_ACCOUNT_INFO = "redirect:/user/account/info";
    public static final String REDIRECT_ALL_ALBUMS_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/auth/album/all?limit=5&offset=0";
    public static final String REDIRECT_MIXES_PAGE_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/auth/mix/all?limit=5&offset=0";
    public static final String REDIRECT_TRACKS_PAGE_WITH_LIMIT_OFFSET = "redirect:/auth/track/all?limit=5&offset=0";
    public static final String REDIRECT_ORDERS_PAGE_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/admin/order/all?limit=5&offset=0";
    public static final String REDIRECT_ALBUM_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/auth/review/album/all?limit=5&offset=0";
    public static final String REDIRECT_MIX_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/auth/review/mix/all?limit=5&offset=0";
    public static final String REDIRECT_TRACK_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET = "redirect:/auth/review/track/all?limit=5&offset=0";
    public static final String REDIRECT_ADD_ALBUM_REVIEW = "redirect:/user/review/add-album-review";
    public static final String REDIRECT_ADD_MIX_REVIEW = "redirect:/user/review/add-mix-review";
    public static final String REDIRECT_ADD_TRACK_REVIEW = "redirect:/user/review/addTrackReview";
    public static final String REDIRECT_MAKE_ORDER = "redirect:/user/order/make-order";
    public static final String REDIRECT_ADD_TRACK_TO_MIX = "redirect:/admin/track/addTrackToMix";
    public static final String REDIRECT_ALBUM_UPDATE_FORM = "redirect:/admin/album/update/";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_USER_MAIN_PAGE = "redirect:/user/account/welcome";
    public static final String REDIRECT_ADMIN_MAIN_PAGE = "redirect:/admin/account/admin-main";
}