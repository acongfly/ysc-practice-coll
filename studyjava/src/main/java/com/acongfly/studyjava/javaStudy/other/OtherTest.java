package com.acongfly.studyjava.javaStudy.other;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * program: study<p>
 * description: 杂乱测试类<p>
 * author: shicong yang<p>
 * createDate: 2018-09-27 11:17<p>
 **/

public class OtherTest {


    @Value("#{'${table.name}'.split(',')}")
    private String[] tableName;

    public void getTableName() {
        System.out.println(tableName);
    }

    public static void main(String[] args) throws Exception {


//        Map<Obj
//        long bizId = 33;
//        String outOrderId = "456788765";
//        DecimalFormat df=new DecimalFormat("0.0000");
//        //不够四位自动补偿0
//        String substring = df.format(Long.valueOf(bizId)).replace(".", "").substring(0, 4);
//        System.out.println(String.format("%s%s",substring,outOrderId));
//        int hash = Objects.hash(outOrderId);
//        System.out.println(hash);
//        Set<Object> objects = new TreeSet<>();
//        objects.add(Math.random()*1234);
//        objects.add(Math.random()*1234);
//        objects.add(Math.random()*1234);
//        System.out.println(objects);
//        List<Object> objects1 = new ArrayList<>(objects);
//        System.out.println(objects1);


//        for (int i = 0; i < 10; i++) {
//            try {
//                if (i == 4) {
//                    throw new Exception();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                continue;
//            }
//            System.out.println(i);
//        }
//        List<Integer> lists = Lists.newArrayList();
//        for (int i = 0; i < 10000; i++) {
//            lists.add(i);
//        }
//        List<List<Integer>> partition = Lists.partition(lists, 1000);
//        for (List<Integer> par : partition) {
//            System.out.println(par);
//        }
//
//        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
//        builder.put("A", "A1");
//        builder.put("B", "B1");
//        builder.put("C", "C1");
//        builder.put("D", "D1");
//        Map<Object, Object> build = builder.build();
//        Object a = build.get("f");
//        System.out.println(a);
//        System.out.println(a);
//        FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("HH:mm:ss");      //线程安全
//        Date parse = ISO_DATE_FORMAT.parse("10:10:10");
//        Time time = new Time(parse.getTime());
//        System.out.println(time);
//
//
//        String n = "1.00";
//        BigDecimal mul = NumberUtil.mul(n, 100 + "");
//        System.out.println(mul.longValue());
//
//
//        String amt = "0";
//        BigDecimal bigDecimal = new BigDecimal(amt);
//        System.out.println("=========" + bigDecimal);
//
//        BigDecimal bg = new BigDecimal(0.00);
//        BigDecimal bg1 = new BigDecimal(0.04);
////        System.out.println(bg.compareTo(bg1));
//        BigDecimal add = NumberUtil.add(bg, bg1);
//        System.out.println(add.compareTo(new BigDecimal(0.04)));
//
//        System.out.println(add);
//
//
//        int hash = Objects.hash("queryPaymentTxnrwewewer");
//        System.out.println("hash" + Math.abs(hash / 1000000));
//
////        double bizAmount=0.00;
////        BigDecimal amount = new BigDecimal(0.03);
////        bizAmount = bizAmount + amount.intValue();
////        System.out.println(bizAmount);
//
//        //
//        String are = "cong_test";
//        boolean matches = are.matches("^[A-Za-z0-9 .-]{1,100}$");
//        System.out.println(matches);

//        List<String> objects = Lists.newArrayList();
//        for (String s : objects) {
//            System.out.println("0000000000000000");
//        }
//
//        CountDownLatch countDownLatch = new CountDownLatch(100);
//        for (int i = 0; i < 100; i++) {
//            countDownLatch.countDown();
//        }
//        System.out.println(countDownLatch.getCount() == 0);
//        testObj(1, 2, 3);


//        String base64 = "https://s3-ap-south-1.amazonaws.com/payment-bootstraps-aps1/pay/fintech-payment-service/batch_payment/m1234567/m1234567.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20190523T064524Z&X-Amz-SignedHeaders=host&X-Amz-Expires=900&X-Amz-Credential=AKIATP5CVZLOLH3UZ4K7%2F20190523%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=db214e53c6da462a8843ad9ee260f591ed8000741f57cfea69d32e02572a2631";
//        System.out.println(base64.length());
//        String s = Base64Utils.encodeToString(base64.getBytes());
//        System.out.println(s);
//        System.out.println(s.length());

//        String s = 30+"";
//        int startTimeSecond = 60;
//        if (StringUtils.isNotBlank(s)) {
//            startTimeSecond = Integer.parseInt(s);
//        }
//        System.out.println(startTimeSecond);
//        FileSystemResource fileSystemResource = new FileSystemResource(new File("/Users/shicongyang/workword/M36977092608-TB000000000010-20190531.csv"));
//        URL url = fileSystemResource.getURL();
//        System.out.println(url);
//        System.out.println(fileSystemResource.getURI());
//        System.out.println(fileSystemResource.getFilename());
//        ByteArrayResource byteArrayResource = new ByteArrayResource(new byte[]{});

//        String s = " https://s3-ap-south-1.amazonaws.com/payment-bootstraps-aps1/pay/fintech-payment-service/batch_payment/dev/%2FM36977092609-TB000000000001-20190530.csv?X-Amz-Security-Token=AgoJb3JpZ2luX2VjEFMaCmFwLXNvdXRoLTEiRjBEAiA1Nf1IyB8sjVfiyMGipkXVn0ZJRSArxcnaOyebRaJOvQIgNEnKkn2JkYmdp8p%2B0FkQm6axJ8WPfUxPH33fZlfa6oYq3AMIbBABGgwyNDAzMjI0NjQ0NzYiDJcn8F%2B%2BSmJmOV6%2B7yq5A35oEFqrs6D1dIgA%2FefFWEnuiRDvfVeTYsf%2Fk4coDjnTktAEMqmkHrG%2BIah5%2BTfDqukrjz3FN8fKLIm36BlhNjZ3WNp%2BfZ123a1E11%2FSoUaD0ClJjEV6Dw2zJtiYAXtrTML6xIBavWBMtxAVl3gBgB5ddpwr30p5dAt5GQSfIXmjzkorkfJAtn28iTEf9Rm36bFu35W1d2WOQ6ueDApJUF3RzQFRpxlvQgdsGyRq5Zac4NWueXbHvDHffisn6tdsOOlMAwSKIf36MoW0CW0RtjpncY2YbCDNnPK9UXOwfHmIlzihwAUj3ueyzedFnO06hH%2Bjyjna%2Bn3IJOR82m%2FQ8SBfSsMN16bx0dJoAo7CZCxE%2BpmzWyVJtnBeVUzu8RGbrG91LEJfSN3X7Nyt2RiLuMQIAkXl6vUqRNGN4Rpb7CsDQdmj4Otak%2FSBxH47K1uAJk6zrruILROXme2bbMYvLMakPqRi6RogOMwgStr4IcpnMDkw4DEFs4HP1Whg%2FMz%2Fd9gESezVbFZqKpbWmqsBT7i0XDtRCOzrdPFB12bo4jvRmPQaBbCApU%2F1kRECoKrXHbTHOnpCFxWPVDDYir3nBTq1AczlQi63AvC4epGX2OiwfIhqqpSpcmISZ7MW4iiN79w9ez8MA3CWfaREyFXMHBdSW6SohLGY98pOQbuaexJXRN8XFjCMI45ua1vsZiIQ8%2FZBApqCSmn6ySA%2B%2BD8FKrOASo1WEPQx1RmNkomGd2QtbTbf4Jytg%2BLmmxq8B0hqX3noq39e8EO%2Bziyuqe2fKZZQ0dKtCRTTON2VnC9%2F1sgQJDCYzAvvUOapvkl0r7icDzKKd5J5cZA%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20190530T035241Z&X-Amz-SignedHeaders=host&X-Amz-Expires=900&X-Amz-Credential=ASIATP5CVZLOLCJVTC6X%2F20190530%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=40e4af92b0092e1daaddd3da6e03b6274c807d4922a6c79f44302ab88a83e3e3";
//        System.out.println(s.length());

        //https://s3-ap-south-1.amazonaws.com/payment-static-aps1/src/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png
//        https://img-cdn.shareitpay.in/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png
//        String s = "https://s3-ap-south-1.amazonaws.com/payment-static-aps1/src/omc-gateway/dev/%E8%AF%B4%E6%98%8E.png";
//        String[] split = s.split("src/");
//        System.out.println(split[1]);
//        System.out.println(split[0]);
//
//        BigDecimal b = new BigDecimal(3);
//        System.out.println(b.compareTo(BigDecimal.ZERO));

        String h = "";
        System.out.println("h=" + h.trim());
        DataTest dataTest = new DataTest();

        System.out.println("data:" + dataTest.getPushId());


    }

    public static void testObj(Object... objects) {

        System.out.println(JSONUtil.toJsonStr(new Object[]{objects}));
    }
}


@Data
class DataTest {

    private String pushId;

}

