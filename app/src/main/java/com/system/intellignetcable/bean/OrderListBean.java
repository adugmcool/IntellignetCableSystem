package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by adu on 2018/11/27.
 */

public class OrderListBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * page : {"totalCount":0,"pageSize":10,"totalPage":0,"currPage":1,"list":[{"signsNum":10,"workAddress":"光华路","workOrderId":1,"status":0}]}
     */

    private String msg;
    private int code;
    private PageBean page;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * totalCount : 0
         * pageSize : 10
         * totalPage : 0
         * currPage : 1
         * list : [{"signsNum":10,"workAddress":"光华路","workOrderId":1,"status":0}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * signsNum : 10
             * workAddress : 光华路
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
            private Object list;
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

            public Object getList() {
                return list;
            }

            public void setList(Object list) {
                this.list = list;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
