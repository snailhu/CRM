package com.zifangdt.ch.base.dto.ticket;

import com.google.common.net.InetAddresses;


public class IP{
    private String ipAddress;

    public IP(String ipAddr) {
        this.ipAddress = ipAddr;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public Integer getInt() {
        return InetAddresses.coerceToInteger(InetAddresses.forString(ipAddress));
    }

    public static IP fromInt(Integer ip) {
        return new IP(InetAddresses.fromInteger(ip).getHostAddress());
    }
    public static void main (String[] args) {
        String ipaddress = "192.168.1.1";
        IP ip = new IP(ipaddress);
        System.out.println(ip.getInt());
        System.out.println(IP.fromInt(ip.getInt()));
        assert ipaddress.equals(IP.fromInt(ip.getInt()).ipAddress);

        ipaddress = "0.0.0.0";
        ip = new IP(ipaddress);
        System.out.println(ip.getInt());
        System.out.println(IP.fromInt(ip.getInt()));
        assert ipaddress.equals(IP.fromInt(ip.getInt()).ipAddress);
    }
}
