package com.acongfly.studyjava.javaStudy.streamStudy;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program: study
 * @description:
 * @author: shicong yang
 * @create: 2019-06-06 15:52
 **/
@Slf4j
public class TestStream {

    private static final String TRANSACTION_REGEX = "^[A-Za-z0-9 .-]{1,100}$";

    /**
     * batch csv file 商户提供的订单号
     */
    public static final String CSV_ROW_ONE_ORDERID = "orderId";
    /**
     * batch csv file 收款账号
     */
    public static final String CSV_ROW_TWO_PAYEE_ACCOUNT_NO = "payeeAccountNo";
    /**
     * batch csv file 收款姓名
     */
    public static final String CSV_ROW_THREE_PAYEE_NAME = "payeeName";
    /**
     * batch csv file 金额元
     */
    public static final String CSV_ROW_FOUR_AMT = "amt";
    /**
     * batch csv file 备注
     */
    public static final String CSV_ROW_FIVE_REMARK = "remark";
    /**
     * batch csv file IFSCode
     */
    public static final String CSV_ROW_SIX_IFSCODE = "IFSCode";

    public static void checkTotalNumAndAmt(int totalNums, BigDecimal totalAmts, String fileName) {
        try {
//            File localFile = ifFileExistDel(DEFAULT_LOCAL_FILE_PATH + fileName);
//            File batchFile = FileUtil.writeBytes(fileContent, localFile);
            CsvReadConfig csvReadConfig = new CsvReadConfig();
            csvReadConfig.setContainsHeader(true);
            csvReadConfig.setSkipEmptyRows(true);
            CsvReader csvReader = new CsvReader(csvReadConfig);
            CsvData read = csvReader.read(new File("/Users/shicongyang/workword/SP131956-CricketPaytm2019070401.csv"));
            List<CsvRow> rows = read.getRows();
            /**此处校验总笔数，总金额，金额小于等于零，以及订单号重复或者为空*/
            long start = System.currentTimeMillis();
            int totalNum = rows.size();
//            assertEquals("笔数不对", totalNum, totalNums);
            System.out.println(System.currentTimeMillis() - start);

            start = System.currentTimeMillis();
            /**校验不为空的备注字段需要符合规范*/
            List<CsvRow> notRegexList = rows.stream()
                    .filter(elem -> (StringUtils.isNotBlank(elem.getByName(CSV_ROW_FIVE_REMARK)) && !Validator.isMactchRegex(TRANSACTION_REGEX, elem.getByName(CSV_ROW_FIVE_REMARK))))
                    .collect(Collectors.toList());
            System.out.println(System.currentTimeMillis() - start);
//            assertTrue(notRegexList.size() <= 0);
//            BizException.checkException(notRegexList.size() > 0, ErrorCodeEnum.TRANSACTION_NOT_MATCH_REGEX);
            /**小于零*/
            start = System.currentTimeMillis();
            List<CsvRow> lessZero = rows.stream()
                    .filter(elem -> (NumberUtil.toBigDecimal(elem.getByName(CSV_ROW_FOUR_AMT).trim()).compareTo(BigDecimal.ZERO) <= 0))
                    .collect(Collectors.toList());
//            assertTrue(lessZero.size() <= 0);
            System.out.println(System.currentTimeMillis() - start);

//            BizException.checkException(lessZero.size() > 0, ErrorCodeEnum.LESS_THAN_ZERO);
            /**订单号不为空不重复*/
            start = System.currentTimeMillis();
            List<CsvRow> orderIdSet = rows.stream()
                    .filter(elem -> (StringUtils.isNotBlank(elem.getByName(CSV_ROW_ONE_ORDERID).trim())))
                    .filter(distinctByKey(elem -> elem.getByName(CSV_ROW_ONE_ORDERID).trim()))
                    .collect(Collectors.toList());
//            assertTrue(Objects.equals(orderIdSet.size(), totalNum));
            System.out.println(System.currentTimeMillis() - start);
//            BizException.checkException(!Objects.equals(orderIdSet.size(), totalNum), ErrorCodeEnum.PARAMETERS_ARA_EMPTY);
            /**账号check*/
            start = System.currentTimeMillis();
            List<CsvRow> accountList = rows.stream()
                    .filter(elem -> StringUtils.isNotBlank(elem.getByName(CSV_ROW_TWO_PAYEE_ACCOUNT_NO)))
                    .collect(Collectors.toList());
//            assertTrue(Objects.equals(accountList.size(), totalNum));
//            BizException.checkException(!Objects.equals(accountList.size(), totalNum), ErrorCodeEnum.BATCH_FILE_ACCOUNT_INFO_NULL);
            System.out.println(System.currentTimeMillis() - start);
            /**用户名check*/
            start = System.currentTimeMillis();
            List<CsvRow> payeeNameList = rows.stream()
                    .filter(elem -> elem.getByName(CSV_ROW_THREE_PAYEE_NAME).length() > 45)
                    .collect(Collectors.toList());
//            assertTrue(payeeNameList.size() <= 0);
//            BizException.checkException(payeeNameList.size() > 0, ErrorCodeEnum.PAYEE_NAME_LENGTH_ERROR);
            System.out.println(System.currentTimeMillis() - start);
            /**总金额*/
            start = System.currentTimeMillis();
            BigDecimal totalAmt = rows.stream()
                    .filter(elem -> (StringUtils.isNotBlank(elem.getByName(CSV_ROW_FOUR_AMT))))
                    .map(elem -> elem.getByName(CSV_ROW_FOUR_AMT))
                    .map(elem -> NumberUtil.toBigDecimal(elem))
                    .reduce(BigDecimal.ZERO, (x, y) -> NumberUtil.add(x, y));
//            assertTrue(NumberUtil.equals(totalAmt, totalAmts));
//            BizException.checkException(!NumberUtil.equals(totalAmt, totalAmts), ErrorCodeEnum.AMOUNT_INCORRECT);
            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error("batch payment file format error fileName=[{}]", fileName);
//            throw new BizException(ErrorCodeEnum.BATCH_FILE_FORMAT_ERROR.getCode(), ErrorCodeEnum.BATCH_FILE_FORMAT_ERROR.getMsg());
        }
    }

    /**
     * 按照key进行过滤
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) {
        checkTotalNumAndAmt(10000, new BigDecimal(100), "M36977092608-TB000000000020-20190605.csv");
    }
}
