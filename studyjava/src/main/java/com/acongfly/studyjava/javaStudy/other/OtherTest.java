package com.acongfly.studyjava.javaStudy.other;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.acongfly.studyjava.javaStudy.snowflake.NumericConvertUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Maps;

import cn.hutool.json.JSONUtil;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.OpenListResourceBundle;

/**
 * program: study
 *
 * <p>
 * description: 杂乱测试类
 *
 * <p>
 * author: shicong yang
 *
 * <p>
 * createDate: 2018-09-27 11:17
 *
 * <p>
 */
public class OtherTest {

    @Value("#{'${table.name}'.split(',')}")
    private String[] tableName;

    public void getTableName() {
        System.out.println(tableName);
    }

    public static void main(String[] args) throws Exception {

        // Map<Obj
        // long bizId = 33;
        // String outOrderId = "456788765";
        // DecimalFormat df=new DecimalFormat("0.0000");
        // //不够四位自动补偿0
        // String substring = df.format(Long.valueOf(bizId)).replace(".", "").substring(0, 4);
        // System.out.println(String.format("%s%s",substring,outOrderId));
        // int hash = Objects.hash(outOrderId);
        // System.out.println(hash);
        // Set<Object> objects = new TreeSet<>();
        // objects.add(Math.random()*1234);
        // objects.add(Math.random()*1234);
        // objects.add(Math.random()*1234);
        // System.out.println(objects);
        // List<Object> objects1 = new ArrayList<>(objects);
        // System.out.println(objects1);

        // for (int i = 0; i < 10; i++) {
        // try {
        // if (i == 4) {
        // throw new Exception();
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // continue;
        // }
        // System.out.println(i);
        // }
        // List<Integer> lists = Lists.newArrayList();
        // for (int i = 0; i < 10000; i++) {
        // lists.add(i);
        // }
        // List<List<Integer>> partition = Lists.partition(lists, 1000);
        // for (List<Integer> par : partition) {
        // System.out.println(par);
        // }
        //
        // ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
        // builder.put("A", "A1");
        // builder.put("B", "B1");
        // builder.put("C", "C1");
        // builder.put("D", "D1");
        // Map<Object, Object> build = builder.build();
        // Object a = build.get("f");
        // System.out.println(a);
        // System.out.println(a);
        // FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("HH:mm:ss"); //线程安全
        // Date parse = ISO_DATE_FORMAT.parse("10:10:10");
        // Time time = new Time(parse.getTime());
        // System.out.println(time);
        //
        //
        // String n = "1.00";
        // BigDecimal mul = NumberUtil.mul(n, 100 + "");
        // System.out.println(mul.longValue());
        //
        //
        // String amt = "0";
        // BigDecimal bigDecimal = new BigDecimal(amt);
        // System.out.println("=========" + bigDecimal);
        //
        // BigDecimal bg = new BigDecimal(0.00);
        // BigDecimal bg1 = new BigDecimal(0.04);
        //// System.out.println(bg.compareTo(bg1));
        // BigDecimal add = NumberUtil.add(bg, bg1);
        // System.out.println(add.compareTo(new BigDecimal(0.04)));
        //
        // System.out.println(add);
        //
        //
        // int hash = Objects.hash("queryPaymentTxnrwewewer");
        // System.out.println("hash" + Math.abs(hash / 1000000));
        //
        //// double bizAmount=0.00;
        //// BigDecimal amount = new BigDecimal(0.03);
        //// bizAmount = bizAmount + amount.intValue();
        //// System.out.println(bizAmount);
        //
        // //
        // String are = "cong_test";
        // boolean matches = are.matches("^[A-Za-z0-9 .-]{1,100}$");
        // System.out.println(matches);

        // List<String> objects = Lists.newArrayList();
        // for (String s : objects) {
        // System.out.println("0000000000000000");
        // }
        //
        // CountDownLatch countDownLatch = new CountDownLatch(100);
        // for (int i = 0; i < 100; i++) {
        // countDownLatch.countDown();
        // }
        // System.out.println(countDownLatch.getCount() == 0);
        // testObj(1, 2, 3);

        // String base64 =
        // "https://s3-ap-south-1.amazonaws.com/payment-bootstraps-aps1/pay/fintech-payment-service/batch_payment/m1234567/m1234567.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20190523T064524Z&X-Amz-SignedHeaders=host&X-Amz-Expires=900&X-Amz-Credential=AKIATP5CVZLOLH3UZ4K7%2F20190523%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=db214e53c6da462a8843ad9ee260f591ed8000741f57cfea69d32e02572a2631";
        // System.out.println(base64.length());
        // String s = Base64Utils.encodeToString(base64.getBytes());
        // System.out.println(s);
        // System.out.println(s.length());

        // String s = 30+"";
        // int startTimeSecond = 60;
        // if (StringUtils.isNotBlank(s)) {
        // startTimeSecond = Integer.parseInt(s);
        // }
        // System.out.println(startTimeSecond);
        // FileSystemResource fileSystemResource = new FileSystemResource(new
        // File("/Users/shicongyang/workword/M36977092608-TB000000000010-20190531.csv"));
        // URL url = fileSystemResource.getURL();
        // System.out.println(url);
        // System.out.println(fileSystemResource.getURI());
        // System.out.println(fileSystemResource.getFilename());
        // ByteArrayResource byteArrayResource = new ByteArrayResource(new byte[]{});

        // String s = "
        // https://s3-ap-south-1.amazonaws.com/payment-bootstraps-aps1/pay/fintech-payment-service/batch_payment/dev/%2FM36977092609-TB000000000001-20190530.csv?X-Amz-Security-Token=AgoJb3JpZ2luX2VjEFMaCmFwLXNvdXRoLTEiRjBEAiA1Nf1IyB8sjVfiyMGipkXVn0ZJRSArxcnaOyebRaJOvQIgNEnKkn2JkYmdp8p%2B0FkQm6axJ8WPfUxPH33fZlfa6oYq3AMIbBABGgwyNDAzMjI0NjQ0NzYiDJcn8F%2B%2BSmJmOV6%2B7yq5A35oEFqrs6D1dIgA%2FefFWEnuiRDvfVeTYsf%2Fk4coDjnTktAEMqmkHrG%2BIah5%2BTfDqukrjz3FN8fKLIm36BlhNjZ3WNp%2BfZ123a1E11%2FSoUaD0ClJjEV6Dw2zJtiYAXtrTML6xIBavWBMtxAVl3gBgB5ddpwr30p5dAt5GQSfIXmjzkorkfJAtn28iTEf9Rm36bFu35W1d2WOQ6ueDApJUF3RzQFRpxlvQgdsGyRq5Zac4NWueXbHvDHffisn6tdsOOlMAwSKIf36MoW0CW0RtjpncY2YbCDNnPK9UXOwfHmIlzihwAUj3ueyzedFnO06hH%2Bjyjna%2Bn3IJOR82m%2FQ8SBfSsMN16bx0dJoAo7CZCxE%2BpmzWyVJtnBeVUzu8RGbrG91LEJfSN3X7Nyt2RiLuMQIAkXl6vUqRNGN4Rpb7CsDQdmj4Otak%2FSBxH47K1uAJk6zrruILROXme2bbMYvLMakPqRi6RogOMwgStr4IcpnMDkw4DEFs4HP1Whg%2FMz%2Fd9gESezVbFZqKpbWmqsBT7i0XDtRCOzrdPFB12bo4jvRmPQaBbCApU%2F1kRECoKrXHbTHOnpCFxWPVDDYir3nBTq1AczlQi63AvC4epGX2OiwfIhqqpSpcmISZ7MW4iiN79w9ez8MA3CWfaREyFXMHBdSW6SohLGY98pOQbuaexJXRN8XFjCMI45ua1vsZiIQ8%2FZBApqCSmn6ySA%2B%2BD8FKrOASo1WEPQx1RmNkomGd2QtbTbf4Jytg%2BLmmxq8B0hqX3noq39e8EO%2Bziyuqe2fKZZQ0dKtCRTTON2VnC9%2F1sgQJDCYzAvvUOapvkl0r7icDzKKd5J5cZA%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20190530T035241Z&X-Amz-SignedHeaders=host&X-Amz-Expires=900&X-Amz-Credential=ASIATP5CVZLOLCJVTC6X%2F20190530%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=40e4af92b0092e1daaddd3da6e03b6274c807d4922a6c79f44302ab88a83e3e3";
        // System.out.println(s.length());

        // https://s3-ap-south-1.amazonaws.com/payment-static-aps1/src/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png
        // https://img-cdn.shareitpay.in/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png
        // String s =
        // "https://s3-ap-south-1.amazonaws.com/payment-static-aps1/src/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png";
        // String[] split = s.split("src/");
        // System.out.println(split[1]);
        // System.out.println(split[0]);
        //
        // BigDecimal b = new BigDecimal(3);
        // System.out.println(b.compareTo(BigDecimal.ZERO));

        // String h = "";
        // System.out.println("h=" + h.trim());
        // DataTest dataTest = new DataTest();
        //
        // System.out.println("data:" + dataTest.getPushId());
        // try {
        // int i = 0;
        // if (i == 0) {
        // return;
        // }
        //
        // } finally {
        // System.out.println("6666666666666");
        // // }
        // int i = 102;
        // System.out.println(i % 2);
        //
        // System.out.println("payment".hashCode() ^ "testlo".hashCode());
        // System.out.println(0);
        //
        // String utcCurrentDateTimeWithIndiaTimeZone = LocalDateTime.now(Clock.systemUTC())
        // .atOffset(ZoneOffset.ofHoursMinutes(5, 30)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
        //
        // System.out.println(utcCurrentDateTimeWithIndiaTimeZone);
        //
        // SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        // Date parse = dff.parse("2029-10-26T19:35:48+07:00");
        // System.out.println(parse);
        // System.out.println("1111111111111111");
        // DateFormat.getDateTimeInstance().parse("2029-10-26T19:35:48+07:00");

        // Date date = new Date("2029-10-26T19:35:48+07:00");
        // System.out.println(date);

        // System.out.println(parse);

        // System.out.println(Runtime.getRuntime().availableProcessors());

        // Date date = DateTime.of("2029-10-26T19:35:48+07:00",
        // "yyyy-MM-dd'T'HH:mm:sszzz").toJdkDate();
        // String s = fromDateTimeWithTimeZone2UTCDateTime("2029-10-26T19:35:48+07:00");
        // System.out.println(s);

        // List<NameValuePair> params = Lists.newArrayList();
        // params.add(new BasicNameValuePair("redirectUrl","12222"));
        // params.add(new BasicNameValuePair("state","11111"));
        // params.add(new BasicNameValuePair("clientId","tttyy"));
        // params.add(new BasicNameValuePair("scopes","vghijhb"));
        // params.add(new BasicNameValuePair("requestId","vghgvbhj"));
        //// if (StringUtils.isNotBlank(request.getLang())){
        //// params.add(new BasicNameValuePair("lang",request.getLang()));
        //// }
        // try {
        // String s = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
        // System.out.println("www.baidu.com" + "?" + s);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // TestSeam.RiskData riskData = new TestSeam.RiskData();
        Map<String, String> map = Maps.newHashMap();
        map.put("fuzzyDeviceId", "k+OrCqw7QMNxlrT3qU2m0TRYTucd+nrMH2izjtltJ");
        map.put("terminalType", "APP");
        map.put("riskFlag", "00110");
        map.put("realIp", "123.23.12.111");

        // riskData.setFuzzyDeviceId("k+OrCqw7QMNxlrT3qU2m0TRYTucd+nrMH2izjtltJ");
        // riskData.setTerminalType("APP");
        // riskData.setRiskFlag("00110");
        // riskData.setRealIp("123.23.12.111");
        // System.out.println();

        // TestSeam testSeam = new TestSeam();
        // testSeam.setMobile("62-882345678");
        // testSeam.setVerifiedTime("2001-07-04T12:08:56+05:30");
        // testSeam.setExternalUid("TIXxxxxxUID");
        // testSeam.setReqTime("2001-07-04T12:08:56+05:30");
        // testSeam.setReqMsgId(System.currentTimeMillis() + "");
        // testSeam.setRiskData(map.toString());
        //
        // System.out.println(JSONUtil.toJsonStr(testSeam));

        List<String> stringList;

        // for (int j = 0; j < 100; j++) {
        //
        // stringList = new ArrayList<>();
        // stringList.add(System.currentTimeMillis() + "");
        // System.out.println(stringList);
        // Thread.sleep(10);
        //
        // }
        // String a =
        // "Your product #${productName}# failed our inspection and authentication. For the benefit of the buyer, the
        // order is closed and your deposit is charged as a penalty fee and is deducted from your PayPal account. Your
        // product will be returned to your return address within 3 business days.";
        // System.out.println(a.length());

        //
        // BigDecimal mul = NumberUtil.mul("34500", "0.085");
        // System.out.println("amount:"+mul);
        //
        //
        // Currency instance = Currency.getInstance(Locale.JAPAN);
        // System.out.println(instance.getCurrencyCode());

// getCountries();
// String hkd = billidGen("HKD", 1, "20200101", 12345678912345678L);
// System.out.println(hkd);
// System.out.println(hkd.length());
//
// String orderNo = "12345678912345678912345678912345";
// StringBuilder stringBuilder = new StringBuilder();
// for (int j = 0; j < 32; j++) {
// stringBuilder.append(orderNo);
// }
//
// int i1 = HashUtil.fnvHash(stringBuilder.toString());
// System.out.println(i1);
//
// int i2 = "JYP".hashCode();
// System.out.println(i2);
// System.out.println(IdUtil.createSnowflake(5,5).nextId());
// 1275412976266530816
//
// System.out.println(settleIdGen(12345L));
//
// System.out.println(DateUtil.parse("20200901"));

DateTime parse1 = DateUtil.parseDateTime("0000-00-00 00:00:00");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
long time = sdf.parse("0000-00-00 00:00:00").getTime();
DateTime date = DateUtil.date(time);
// DateTime dateTime = DateUtil.parseTime(StringUtils.EMPTY);
System.out.println(parse1);
System.out.println(date);
    }

    private static final DateTimeFormatter ORIGINAL_DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:sszzz");

    private static final DateTimeFormatter TARGET_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * fromDateTimeWithTimeZone2UTCDateTime
     *
     * @param dateTimeWithTimeZone
     * @return
     */
    protected static String fromDateTimeWithTimeZone2UTCDateTime(String dateTimeWithTimeZone) {

        return StringUtils.isNotBlank(dateTimeWithTimeZone) ? TARGET_DATE_FORMAT
            .withZone(ZoneId.of(ZoneOffset.UTC.getId())).format(ORIGINAL_DATE_FORMAT.parse(dateTimeWithTimeZone))
            : null;
    }

    private static String clientDateString(String s) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        df.setTimeZone(tz); // strip timezone
        return df.format(s);
    }

    public static void testObj(Object... objects) {

        System.out.println(JSONUtil.toJsonStr(new Object[] {objects}));
    }

    public static void getCountries() {
        ResourceBundleBasedAdapter resourceBundleBasedAdapter =
            ((ResourceBundleBasedAdapter)LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.CHINA);
        Set<String> data = resource.keySet();
        List<String> twoCodes = data.stream()
            // 提取出国家的二字码，长度为2和全是大写
            .filter(code -> code.length() == 2 && StringUtils.isAllUpperCase(code)).collect(Collectors.toList());
        twoCodes.sort(Comparator.naturalOrder());

        System.out.println("size: " + twoCodes.size());
        System.out.println(twoCodes);
        ImmutableMap.Builder<String, Currency> builder = ImmutableMap.builder();
        twoCodes.forEach(twoCode -> {
            Locale locale = new Locale("", twoCode);
            String threeCode = null;
            try {
                // 获取国家的三字码
                threeCode = locale.getISO3Country();
            } catch (Exception e) {
            }
            // 币种
            Currency instance = Currency.getInstance(locale);
            String format =
                String.format("%-5s %-5s %-5s %-20s\n", twoCode, threeCode, instance, resource.getString(twoCode));
            if (!Objects.equals(instance, null)) {
                builder.put(twoCode, instance);
            }
            System.out.println(format);
        });
        ImmutableMap<String, Currency> build = builder.build();
        System.out.println(build);

    }

    public static String billidGen(String currency, int code, String settleTime, Long uid) {
        int billIdLength = 32;
        long decimalNumber = NumericConvertUtils.toDecimalNumber(currency, 64);
        String billFormat = "%s%s%s";
        String format = String.format(billFormat, code, decimalNumber, settleTime);
        int total = uid.toString().length() + format.length();
        System.out.println("total==" + total);
        if (total > billIdLength) {
            return format.concat(uid.toString().substring(total - billIdLength));
        } else if (total == billIdLength) {
            return format.concat(uid.toString());
        } else {
            return format.concat(String.format("%0" + (billIdLength - total + uid.toString().length()) + "d", uid));
        }
    }

    public static String settleIdGen(Long uid) {
        int settleIdLength = 27;
        Snowflake snowflake = IdUtil.createSnowflake(5, 5);
        int length = uid.toString().length();
        String nexId = snowflake.nextId() + "";
        int total = length + nexId.length();
        if (total > settleIdLength) {
            return 1 + nexId.substring(total - settleIdLength).concat(uid.toString());
        } else if (total == settleIdLength) {
            return 1 + nexId.concat(uid.toString());
        } else {
            return 1 + nexId.concat(String.format("%0" + (settleIdLength - total + length) + "d", uid));
        }
    }

}
//
// @Data
// class DataTest {
//
// private String pushId;
// }
