package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by adu on 2018/11/28.
 */

public class OrderInfoDetailBean implements Serializable {

    /**
     * msg : success
     * workOrder : {"signsNum":10,"workAddress":"光华路","workUserId":4,"workUserName":"测试普通用户","sendUserId":3,"sendUserName":"测试管理员","userTeamplateName":"建国路","userTeamplateId":3,"createDate":"2018-11-27 02:31:11","workOrderId":1,"status":0}
     * code : 0
     */

    private String msg;
    private WorkOrderBean workOrder;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WorkOrderBean getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderBean workOrder) {
        this.workOrder = workOrder;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class WorkOrderBean {
        /**
         * signsNum : 10
         * workAddress : 光华路
         * workUserId : 4
         * workUserName : 测试普通用户
         * sendUserId : 3
         * sendUserName : 测试管理员
         * userTeamplateName : 建国路
         * userTeamplateId : 3
         * createDate : 2018-11-27 02:31:11
         * workOrderId : 1
         * status : 0
         */

        private int signsNum;
        private String workAddress;
        private int workUserId;
        private String workUserName;
        private int sendUserId;
        private String sendUserName;
        private String userTeamplateName;
        private int userTeamplateId;
        private String createDate;
        private int workOrderId;
        private int status;

        public int getSignsNum() {
            return signsNum;
        }

        public void setSignsNum(int signsNum) {
            this.signsNum = signsNum;
        }

        public String getWorkAddress() {
            return workAddress;
        }

        public void setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
        }

        public int getWorkUserId() {
            return workUserId;
        }

        public void setWorkUserId(int workUserId) {
            this.workUserId = workUserId;
        }

        public String getWorkUserName() {
            return workUserName;
        }

        public void setWorkUserName(String workUserName) {
            this.workUserName = workUserName;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public String getUserTeamplateName() {
            return userTeamplateName;
        }

        public void setUserTeamplateName(String userTeamplateName) {
            this.userTeamplateName = userTeamplateName;
        }

        public int getUserTeamplateId() {
            return userTeamplateId;
        }

        public void setUserTeamplateId(int userTeamplateId) {
            this.userTeamplateId = userTeamplateId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
