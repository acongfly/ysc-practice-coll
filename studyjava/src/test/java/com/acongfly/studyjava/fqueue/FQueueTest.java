package com.acongfly.studyjava.fqueue;

import cn.hutool.json.JSONUtil;
import com.acongfly.studyjava.fqueue.exception.FileFormatException;
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
            f2 = new FQueue("/Users/shicongyang/applogs/test", 10 * 1024 * 1024);
            for (int i = 0; i < 1024; i++) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("name", "name" + i);
                map.put("value", i);
                f2.add(JSONUtil.toJsonStr(map).getBytes());
            }

            byte[] poll = f2.poll();

//            Message next = f2.next();
            while (poll != null) {
                String s = new String(poll, CHARSET);
                System.out.println(s);
            }
            // 保存消费状态
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            f2.close();
        }


    }

}