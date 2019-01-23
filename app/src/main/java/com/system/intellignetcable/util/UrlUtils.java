package com.system.intellignetcable.util;

/**
 * Created by zydu on 2018/11/26.
 */

public class UrlUtils {
    public static final String TEST_URL = "http://47.99.148.150:8081/api/";
    public static final String RELEASE_URL = "http://47.99.148.150/api/";
    // 请求成功
    public static final String METHOD_POST_SUCCESS = "success";
    // 用户管理-登录
    public static final String METHOD_POST_LOGIN = "user/login";
    // 用户管理-获取用户信息
    public static final String METHOD_POST_GET_USER = "user/getUser";
    // 用户管理-退出
    public static final String METHOD_POST_LOGOUT = "user/logout";
    // 用户管理-注册
    public static final String METHOD_POST_REGISTER = "user/register";
    // 用户管理-按类型查询用户列表信息
    public static final String METHOD_POST_USERS = "user/users";
    // 工单管理-工单列表
    public static final String METHOD_POST_WORK_ORDER_LIST = "work-order/list";
    // 工单管理-工单详情
    public static final String METHOD_POST_WORK_ORDER_DETAIL = "work-order/detail";
    // 工单管理-创建工单
    public static final String METHOD_POST_WORK_ORDER_CREATE = "work-order/create";
    // 工单管理-审核工单
    public static final String METHOD_POST_WORK_ORDER_CHECK = "work-order/check";
    // 工单管理-查询模板列表
    public static final String METHOD_POST_WORK_ORDER_TEMPLATES = "work-order/templates";
    // 工单管理-更新工单基本信息
    public static final String METHOD_POST_WORK_ORDER_UPDATE = "work-order/update";
    // 标识牌管理-更新或修改标识牌
    public static final String METHOD_POST_SIGN_BOARD_SAVE_UPDATE= "sign-board/saveOrUpdate";
    // 标识牌管理-扫描
    public static final String METHOD_POST_SIGN_BOARD_SCAN= "sign-board/scan";
    // 系统管理-获取地图统计
    public static final String METHOD_POST_SYS_MAP_DATA= "sys/findMapDataStat";
    // 系统管理-获取最新版本
    public static final String METHOD_POST_SYS_APP_VERSION= "sys/getLastAppVersion";
    public static final String FINDBYDETAILADDRESS= "sign-board/findByDetailAddress";

    public static final String METHOD_POST_SIGN_BOARD_LIST= "sign-board/list";
    public static final String FINDMAPEPCDATASTAT= "sys/findMapEPCDataStat";
    public static final String SIGNBOARDDETAIL= "sign-board/detail";


}
