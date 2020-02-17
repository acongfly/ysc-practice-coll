package com.acongfly.yscutils.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * https://jayden.matchcess.com/2018/07/08/java/Java%E8%AF%BB%E5%8F%96%E5%A4%A7%E6%95%B0%E6%8D%AE%E9%87%8F%E7%9A%84CSV%E6%96%87%E4%BB%B6/
 * description: 对于大数据量的CSV文件，不能一下子全部加载出来，要不然会造成内存溢出，要做的就是 将部分的内容放在一定大小的缓冲池中，分批读取，直到读取完毕，首先向将File转换成inputstream
 * ，然后用IoUtils的read方法进行读取，其中遇到的问题就是，加入csv文件中包含了中文，分批 读取的时候可能会将一个中文拆开读取，为了避免这种现象，如果读取到的内容不是以\n结尾，说明
 * 读取到的内容不是整数行，然后将读取到内容分成两段，一段是最后一个\n之前的内容，第二个是剩余 的不到一行的内容，然后将剩余的部分加到下一次的读取中。
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2020-01-15
 * <p>
 */
public class ReadCsvUtil {

    private static Logger log = Logger.getLogger(ReadCsvUtil.class);

    /**
     * 加载csv的内容，如果加载的内容此时刚好不够一行，则截取到上一行的末尾，这样加载出来的数据都是整数倍的行数
     *
     * @param csvPath
     */
    public static void loadCsvData(String csvPath) {
        try {

            File dataFile = new File(csvPath);
            byte[] lastByte = {};
            Integer length = 94372 * 5;
            Boolean isOver = false;
            FileInputStream input = new FileInputStream(dataFile);
            Map<String, Object> rv = null;
            String content = "";
            String charset = "GBK";
            while (!isOver) {

                if (rv != null && rv.containsKey("lastByte")) {
                    lastByte = (byte[])rv.get("lastByte");
                }
                rv = loadContent(length, input, lastByte, charset);
                isOver = (Boolean)rv.get("isOver");
                content = (String)rv.get("content");
                log.info("content:" + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载inputsteam的数据
     *
     * @param length
     * @param input
     * @param charset
     * @return
     * @throws IOException
     */
    private static Map<String, Object> loadContent(Integer length, InputStream input, byte[] la, String charset)
        throws IOException {
        Map<String, Object> rv = new HashMap<String, Object>();
        byte[] buffer = new byte[la.length + length];
        copyToBuffer(buffer, la);
        Integer read = IOUtils.read(input, buffer, la.length, length);
        byte hh = 10;// 换行符
        int lastN = ArrayUtils.lastIndexOf(buffer, hh);
        byte[] conArray = ArrayUtils.subarray(buffer, 0, lastN);
        byte[] lastByte = ArrayUtils.subarray(buffer, lastN, buffer.length);
        String content = new String(conArray, 0, conArray.length, charset);

        rv.put("isOver", read < length);
        rv.put("content", content);
        rv.put("lastByte", lastByte);
        return rv;
    }

    /**
     * copy byte
     *
     * @param buffer
     * @param la
     */
    private static void copyToBuffer(byte[] buffer, byte[] la) {
        for (int i = 0; i < la.length; i++) {
            buffer[i] = la[i];
        }
    }

    public static void main(String[] args) {
        String csvPath = "C:\\20180630115435.csv";
        loadCsvData(csvPath);
    }

}