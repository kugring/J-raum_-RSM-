
package com.kugring.back.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.kugring.back.entity.OrderListEntity;
import jakarta.transaction.Transactional;

@SpringBootTest()
public class OrderListRepositoryTest{

  @Autowired
  private OrderListRepository repository;

  @Test
  @Transactional
  @Rollback
  @DisplayName("주문 조회 테스트")
  public void orderListSelectTest(){
    OrderListEntity entity = repository.findByOrderListId(7);
    assertEquals(entity.getOrderItems().size(), 10);
    assertEquals(entity.getOrderItems().get(0).getMenu().getMenuName(), "뜨거운 아메리카노");
  }





  
}


// 이게 그냥 진행한것
// OrderListEntity findByOrderListId(Integer orderListId);

// Hibernate: 
//     /* <criteria> */ select
//         ole1_0.order_list_id,
//         ole1_0.complete_order_date,
//         ole1_0.create_order_date,
//         ole1_0.order_status,
//         ole1_0.pay_method,
//         ole1_0.user_id 
//     from
//         `order_list` ole1_0 
//     where
//         ole1_0.order_list_id=?
// Hibernate: 
//     select
//         oi1_0.order_list_id,
//         oi1_0.order_item_id,
//         oi1_0.menu_id,
//         oi1_0.order_item_quantity 
//     from
//         `order_item` oi1_0 
//     where
//         oi1_0.order_list_id=?
// Hibernate: 
//     select
//         me1_0.menu_id,
//         me1_0.category,
//         me1_0.espresso_shot,
//         me1_0.image,
//         me1_0.menu_name,
//         me1_0.menu_price,
//         me1_0.temperature 
//     from
//         `menu` me1_0 
//     where
//         me1_0.menu_id=?






