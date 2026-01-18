package com.triadtdd;

import com.triadtdd.repository.PromotionDAO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class TriadtddApplicationTests {

	@MockitoBean
	private PromotionDAO promotionDAO;

	@Test
	void contextLoads() {
	}
}
