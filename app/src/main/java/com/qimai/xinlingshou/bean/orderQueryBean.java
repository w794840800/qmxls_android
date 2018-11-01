package com.qimai.xinlingshou.bean;

public class orderQueryBean {


    /**
     * return_code : 01
     * return_msg : 用户支付中
     * result_code : 03
     * pay_type : 010
     * trade_state : USERPAYING
     * merchant_name : 安徽律信投资管理有限公司
     * merchant_no : 836100205000006
     * terminal_id : 10451657
     * terminal_trace : 1539761763
     * terminal_time : 20181017153603
     * pay_trace : PGZ3SF5LOL8N2ZP8Q8
     * pay_time : 20181017153600
     * total_fee : 1
     * end_time : 20181017153603
     * out_trade_no : 104516576321118101715360006148
     * channel_trade_no :
     * channel_order_no :
     * user_id :
     * attach : {"store_id":14299,"terminal_no":"T103178E00029"}
     * receipt_fee : null
     * key_sign : 03e9fb56fc269ac9f45a0558ac8f229c
     * typeMsg :
     */

    private String return_code;
    private String return_msg;
    private String result_code;
    private String pay_type;
    private String trade_state;
    private String merchant_name;
    private String merchant_no;
    private String terminal_id;
    private String terminal_trace;
    private String terminal_time;
    private String pay_trace;
    private String pay_time;
    private String total_fee;
    private String end_time;
    private String out_trade_no;
    private String channel_trade_no;
    private String channel_order_no;
    private String user_id;
    private String attach;
    private Object receipt_fee;
    private String key_sign;
    private String typeMsg;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getTerminal_trace() {
        return terminal_trace;
    }

    public void setTerminal_trace(String terminal_trace) {
        this.terminal_trace = terminal_trace;
    }

    public String getTerminal_time() {
        return terminal_time;
    }

    public void setTerminal_time(String terminal_time) {
        this.terminal_time = terminal_time;
    }

    public String getPay_trace() {
        return pay_trace;
    }

    public void setPay_trace(String pay_trace) {
        this.pay_trace = pay_trace;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getChannel_trade_no() {
        return channel_trade_no;
    }

    public void setChannel_trade_no(String channel_trade_no) {
        this.channel_trade_no = channel_trade_no;
    }

    public String getChannel_order_no() {
        return channel_order_no;
    }

    public void setChannel_order_no(String channel_order_no) {
        this.channel_order_no = channel_order_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Object getReceipt_fee() {
        return receipt_fee;
    }

    public void setReceipt_fee(Object receipt_fee) {
        this.receipt_fee = receipt_fee;
    }

    public String getKey_sign() {
        return key_sign;
    }

    public void setKey_sign(String key_sign) {
        this.key_sign = key_sign;
    }

    public String getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(String typeMsg) {
        this.typeMsg = typeMsg;
    }

    @Override
    public String toString() {
        return "orderQueryBean{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", result_code='" + result_code + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", trade_state='" + trade_state + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", merchant_no='" + merchant_no + '\'' +
                ", terminal_id='" + terminal_id + '\'' +
                ", terminal_trace='" + terminal_trace + '\'' +
                ", terminal_time='" + terminal_time + '\'' +
                ", pay_trace='" + pay_trace + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", end_time='" + end_time + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", channel_trade_no='" + channel_trade_no + '\'' +
                ", channel_order_no='" + channel_order_no + '\'' +
                ", user_id='" + user_id + '\'' +
                ", attach='" + attach + '\'' +
                ", receipt_fee=" + receipt_fee +
                ", key_sign='" + key_sign + '\'' +
                ", typeMsg='" + typeMsg + '\'' +
                '}';
    }
}
