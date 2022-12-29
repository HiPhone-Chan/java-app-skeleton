package tech.hiphone.shop.utils;

import org.apache.commons.lang3.RandomUtils;

public class OrderUtil {

    private static SnowFlake snowFlake;

    private OrderUtil() {
    }

    public static void init(long uid) {
        init((uid / SnowFlake.MAX_MACHINE_NUM) % SnowFlake.MAX_DATACENTER_NUM, uid % SnowFlake.MAX_MACHINE_NUM);
    }

    public static void init(long datacenterId, long machineId) {
        while (datacenterId == 0 && machineId == 0) {
            datacenterId = RandomUtils.nextLong(0, SnowFlake.MAX_DATACENTER_NUM);
            machineId = RandomUtils.nextLong(0, SnowFlake.MAX_MACHINE_NUM);
        }
        snowFlake = new SnowFlake(datacenterId, machineId);
    }

    public static long generateOrderId() {
        return snowFlake.nextId();
    }

}
