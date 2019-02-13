package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageManagementBean implements Serializable {
    private String msg;
    private int code;
    private SignBoardBean signBoard;

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

    public SignBoardBean getSignBoard() {
        return signBoard;
    }

    public void setSignBoard(SignBoardBean signBoard) {
        this.signBoard = signBoard;
    }

    public static class SignBoardBean {

        private int workOrderId;
        private int userId;
        private String epc;
        private String imagesUrls;
        private Object longitude;
        private Object latitude;
        private Object detailAddress;
        private List<TemplateValuesBean> templateValues;

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getEpc() {
            return epc;
        }

        public void setEpc(String epc) {
            this.epc = epc;
        }

        public String getImagesUrls() {
            return imagesUrls;
        }

        public void setImagesUrls(String imagesUrls) {
            this.imagesUrls = imagesUrls;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(Object detailAddress) {
            this.detailAddress = detailAddress;
        }

        public List<TemplateValuesBean> getTemplateValues() {
            return templateValues;
        }

        public void setTemplateValues(List<TemplateValuesBean> templateValues) {
            this.templateValues = templateValues;
        }

        public static class TemplateValuesBean {
            /**
             * userTemplateValueId : 131
             * fieldValue : 1
             * dicts : [{"dictCode":"1","dictText":"海淀供电公司"}]
             * fieldName : operatingUnit
             * fieldMustInput : N
             * isNull : Y
             * orderNum : 2
             * showType : list
             * fieldDesc : 运行单位
             */

            private int userTemplateValueId;
            private String fieldValue;
            private String fieldName;
            private String fieldMustInput;
            private String isNull;
            private int orderNum;
            private String showType;
            private String fieldDesc;
            private List<DictsBean> dicts;

            public int getUserTemplateValueId() {
                return userTemplateValueId;
            }

            public void setUserTemplateValueId(int userTemplateValueId) {
                this.userTemplateValueId = userTemplateValueId;
            }

            public String getFieldValue() {
                return fieldValue;
            }

            public void setFieldValue(String fieldValue) {
                this.fieldValue = fieldValue;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldMustInput() {
                return fieldMustInput;
            }

            public void setFieldMustInput(String fieldMustInput) {
                this.fieldMustInput = fieldMustInput;
            }

            public String getIsNull() {
                return isNull;
            }

            public void setIsNull(String isNull) {
                this.isNull = isNull;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getShowType() {
                return showType;
            }

            public void setShowType(String showType) {
                this.showType = showType;
            }

            public String getFieldDesc() {
                return fieldDesc;
            }

            public void setFieldDesc(String fieldDesc) {
                this.fieldDesc = fieldDesc;
            }

            public List<DictsBean> getDicts() {
                return dicts;
            }

            public void setDicts(List<DictsBean> dicts) {
                this.dicts = dicts;
            }

            public static class DictsBean {
                /**
                 * dictCode : 1
                 * dictText : 海淀供电公司
                 */

                private String dictCode;
                private String dictText;

                public String getDictCode() {
                    return dictCode;
                }

                public void setDictCode(String dictCode) {
                    this.dictCode = dictCode;
                }

                public String getDictText() {
                    return dictText;
                }

                public void setDictText(String dictText) {
                    this.dictText = dictText;
                }
            }
        }
    }
}
