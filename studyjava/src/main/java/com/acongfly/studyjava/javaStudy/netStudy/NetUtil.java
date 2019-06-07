package com.acongfly.studyjava.javaStudy.netStudy;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * program: study<p>
 * description: 网络编程相关<p>
 * author: shicong yang<p>
 * createDate: 2019-01-03 15:26<p>
 **/

public class NetUtil {

    /**
     * description: 获取所有IPv4的IP地址 <p>
     * param: [] <p>
     * return: java.util.List<java.lang.String> <p>
     * author: shicong yang<p>
     * date: 2019/1/3 <p>
     */
    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    /**
     * description: 获取本地IP <p>
     * param: [] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2019/1/3 <p>
     */
    public static String getLocalIP() {
        String hostAddress = null;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostAddress = inetAddress.getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }
}
