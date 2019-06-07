package com.acongfly.studyjava.fqueueChange;

import cn.hutool.json.JSONUtil;
import com.acongfly.studyjava.fqueueChange.exception.FileFormatException;
import com.acongfly.studyjava.fqueueChange.log.Message;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * TODO:
 *
 * @author shicong yang
 * @version 2018-12-07 11:44
 */
public class FQueueTest {

    @Test
    public void test() throws IOException, FileFormatException {
        Charset CHARSET = Charset.forName("UTF-8");

        FQueue f2 = null;
        try {
            f2 = new FQueue(1, "/Users/shicongyang/applogs/test", 10 * 1024 * 1024);
//            for (int i = 0; i < 1024; i++) {
//                Map<String, Object> map = Maps.newHashMap();
//                map.put("name", "name" + i);
//                map.put("value", i);
//                f2.add(JSONUtil.toJsonStr(map).getBytes());
//            }
            Message next = f2.next();
            while (next != null) {
                if (next.validate()) {
                    byte[] bytes = next.getBytes();
                    String s = new String(bytes, CHARSET);
                    System.out.println(s);
//                    if (s.contains("name1000")){
//                        break;
//                    }
                }
                next = f2.next();
            }
            // 保存消费状态
        } finally {
            f2.commitCursor();
            f2.close();
        }


    }

}