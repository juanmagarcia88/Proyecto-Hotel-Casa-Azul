package es.hotel_back_v2.hotelV2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelV2Application {

	public static void main(String[] args) {
		SpringApplication.run(HotelV2Application.class, args);
	}

}
