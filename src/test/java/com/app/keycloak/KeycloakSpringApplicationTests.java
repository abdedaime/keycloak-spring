package com.app.keycloak;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class KeycloakSpringApplicationTests {

	@Test
	public  void contextLoads() {

		LocalDateTime fromLocalDate = LocalDateTime.parse("10/10/2019", dateTimeFormatter());


	}


	private DateTimeFormatter dateTimeFormatter(){
		DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
				.appendPattern("dd/MM/yyyy")
				.optionalStart()
				.appendPattern("HH:mm")
				.optionalEnd()
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter();
		return  dateTimeFormatter;
	}

}
