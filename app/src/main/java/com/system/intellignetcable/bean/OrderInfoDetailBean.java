package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by adu on 2018/11/28.
 */

public class OrderInfoDetailBean implements Serializable {
    /**
     * msg : success
     * workOrder : {"signsNum":2,"workAddress":"通灵路","workUserId":4,"workUserName":"测试普通用户","sendUserId":3,"sendUserName":"测试管理员","userTeamplateName":"全部字段","userTeamplateId":1,"createDate":"2018-11-28 06:29:23","workOrderId":12,"list":[{"epc":"A11801001001180800000A24","epcNo":"1","longitude":"116.047246","latitude":"39.935961","detailAddress":"详情地址"},{"epc":"A11801001001180800000A25","epcNo":"2","longitude":"116.096114","latitude":"39.947468","detailAddress":"详情地址"}],"status":3}
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
         * signsNum : 2
         * workAddress : 通灵路
         * workUserId : 4
         * workUserName : 测试普通用户
         * sendUserId : 3
         * sendUserName : 测试管理员
         * userTeamplateName : 全部字段
         * userTeamplateId : 1
         * createDate : 2018-11-28 06:29:23
         * workOrderId : 12
         * list : [{"epc":"A11801001001180800000A24","epcNo":"1","longitude":"116.047246","latitude":"39.935961","detailAddress":"详情地址"},{"epc":"A11801001001180800000A25","epcNo":"2","longitude":"116.096114","latitude":"39.947468","detailAddress":"详情地址"}]
         * status : 3
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
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * epc : A11801001001180800000A24
             * epcNo : 1
             * longitude : 116.047246
             * latitude : 39.935961
             * detailAddress : 详情地址
             */

            private String epc;
            private String epcNo;
            private String longitude;
            private String latitude;
            private String detailAddress;

            public String getEpc() {
                return epc;
            }

            public void setEpc(String epc) {
                this.epc = epc;
            }

            public String getEpcNo() {
                return epcNo;
            }

            public void setEpcNo(String epcNo) {
                this.epcNo = epcNo;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getDetailAddress() {
                return detailAddress;
            }

            public void setDetailAddress(String detailAddress) {
                this.detailAddress = detailAddress;
            }
        }
    }
}
