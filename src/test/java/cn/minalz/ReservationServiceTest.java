package cn.minalz;

import cn.minalz.pojo.Reservation;
import cn.minalz.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    // 测试添加
    @Test
    void testInsert() {
        Reservation reservation = new Reservation(null, "张三", "男", "13800000000", LocalDateTime.now(), "上海", 5);
        reservationService.insert(reservation);
    }

    // 测试查询
    @Test
    void testFindByPhone() {
        String phone = "13800000000";
        Reservation reservation = reservationService.findByPhone(phone);
        System.out.println(reservation);
    }
}
