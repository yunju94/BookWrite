package com.book.write.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KisConfig {
    //public static final String REST_BASE_URL = "https://openapi.koreainvestment.com:9443";
    public static final String REST_BASE_URL = "https://openapivts.koreainvestment.com:29443";
    public static final String WS_BASE_URL = "ws://ops.koreainvestment.com:21000";
    public static final String APPKEY = "PSWtKwmKHZvpgUKG8jHmv0WCdZnocGY8Fgw1";       // your APPKEY
    public static final String APPSECRET = "LLaMWW2U4Ib9EnDZd3UtA6yWBEg0k60hRtWYan24rnuor7QeyRY32negPAARxjxQUk8fU1lr/LbETHozQH34bxEBpzDQG6NCn8dPXykK4dFMW5WHpqk1ODqCZ3tBRXIztL9syelCJ9vTPxOAhknr2FL58pQolU9HwIAKaY38tlpW3PO3HNI=";  // your APPSECRET

    public static final String FHKUP03500100_PATH = "/uapi/domestic-stock/v1/quotations/inquire-daily-indexchartprice";
    public static final String FHKST03030100_PATH = "/uapi/overseas-price/v1/quotations/inquire-daily-chartprice";
}
