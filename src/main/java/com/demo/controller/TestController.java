package com.demo.controller;

import com.demo.service.KafkaSender;
import com.demo.utils.ApolloConfigUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 测试类
 * @Date 2020/2/26 16:58
 * @Author GaoX
 */
@RestController
public class TestController {
    @Autowired
    KafkaSender kafkaSender;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApolloConfigUtil apolloConfigUtil;

    @Value("${source.database.list}")
    private String aaa;

    @GetMapping("/test")
    public String test(String str) {
//        String sql = "INSERT INTO `ods_iccs_ace_loan_info`(`UPDATE_TIME`, `CINO`, `CUST_NO`, `CUST_NAME`, `PRODUCT_ID`, `PRODUCT_NAME`, `APPLY_NO`, `CONTRACT_NO`, `PUTOUT_NO`, `START_DATE`, `END_DATE`, `BR_NO`, `ACT_BR_NO`, `LOAN_STATUS`, `TERM`, `TERM_TYPE`, `LOAN_AMOUNT`, `LOAN_BALANCE`, `RTN_INTERVAL`, `PAYMENT_TYPE`, `INT_RATE`, `PUTOUT_CARD_NO`, `RPT_CARD_NO`, `RPT_DAY`, `TOTAL_UNPAY_PRINCIPAL`, `TOTAL_UNPAY_INT`, `TOTAL_UNPAY_PINT`, `TOTAL_UNPAY_CINT`, `TOTAL_UNPAY_FEE`, `CURRENCY`, `FIVE_CATEGORY`, `NEXT_PAYMENT_DATE`, `CRT_TIME`, `CRT_USER`, `LST_UPD_TIME`, `LST_UPD_USER`, `LST_UPD_DATE`, `STATE`, `TOT_PERI`, `GRANT_STAT`, `REDOND_FLAG`, `RTN_PERI`, `LAST_RTN_DATE`, `PERI_AMT`, `ACT_DATE`, `APPLY_DATE`, `CALC_STATUS`, `MORATORIUM_DAYS`, `PENALTY_INTRATE`, `PROVISION_INT_AMT`, `LOAN_ONCE_FEE_AMT`, `TRADE_SEQ_NO`, `LF_ONCE_INT_AMT`, `NEXT_RTN_INT_DATE`, `FIRST_RTN_INT_DATE`, `FREE_RATE_DAY`, `APPLY_CHANNEL`, `CUST_MGR_NO`, `OVER_DATE`, `RTN_FREQ`, `ID_TYPE`, `ID_NO`, `PHONE_NO`, `REPAY_COVER_IND`, `IS_PRE_INT`, `LOAN_SPLIT_AMT`, `BATCH_DEDUCT_FLAG`, `GRACE_PERIOD`, `FINANCES_NO`, `CHANNEL_CODE`, `FIVE_HAND`, `DATA_VERSION`, `REPAYMENT_FLAG`, `COMPANY_NAME`, `IS_EXTEND`, `COMPEN_FREQ`, `IS_FIXED_DATE`, `ODS_CREATED_TIME`, `ODS_UPDATED_TIME`, `ODS_IS_ACTIVE`) VALUES ('2020-03-23 12:35:13.000', '2', '18', 'ceshi', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-03-23 12:35:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '0', NULL, NULL, NULL, NULL, '2020-03-23 12:35:22', '2020-03-23 12:35:22', 1);\n";
        try {
//            kafkaSender.send();
//            jdbcTemplate.execute(sql);
            System.out.println(aaa);
            System.out.println(apolloConfigUtil.getVal("source.database.list", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "成功==============================";
    }

}