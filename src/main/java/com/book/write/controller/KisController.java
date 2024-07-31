package com.book.write.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.book.write.KisModel.Body;
import com.book.write.KisModel.IndexData;
import com.book.write.config.AccessTokenManager;
import com.book.write.config.KisConfig;
import com.book.write.constant.Order;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.service.MemberService;
import com.book.write.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller

public class KisController {
    @Autowired
    private AccessTokenManager accessTokenManager;

    private final WebClient webClient;
    private String path;
    private String tr_id;

    //--------------------------//
    @Autowired
    private MemberService memberService;
    @Autowired
    private PointService pointService;

    public KisController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(KisConfig.REST_BASE_URL).build();
    }
    @GetMapping("/coinList")
    public String coinList(Model model, Principal principal) {
        if (principal == null){

        }
        Member member = memberService.memberLoginId(principal.getName());
        if (member == null){
            return "member/login";
        }
        List<Point> pointList = pointService.SearchIdtopoint(member.getId());
        int total = 0;
        if (pointList.size() != 0){
            for (int i = 0 ; i < pointList.size() ; i++){
                if (pointList.get(i).getOrderstatus().equals(Order.OK)){
                    total += pointList.get(i).getPoint();
                }
            }
        }
        String totalPoint = total+"";
        System.out.println(totalPoint);
        model.addAttribute("totalPoint", total);

        return "coin/Listup";
    }



    @GetMapping("/index")
    public String index(Model model) {
        return "coin/index";
    }

    @GetMapping("/indices")
    public String majorIndices(Model model) {

        List<Tuple2<String, String>> iscdsAndOtherVariable1 = Arrays.asList(
                Tuples.of("0001", "U"),
                Tuples.of("2001", "U"),
                Tuples.of("1001", "U")
        );

        Flux<IndexData> indicesFlux = Flux.fromIterable(iscdsAndOtherVariable1)
                .concatMap(tuple -> getMajorIndex(tuple.getT1(), tuple.getT2()))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> indicesList = indicesFlux.collectList().block();
        model.addAttribute("indicesKor", indicesList);

        model.addAttribute("jobDate", getJobDateTime());

        return "coin/indices";
    }

    public String getStringToday() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return localDate.format(formatter);
    }

    public String getJobDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public Mono<String> getMajorIndex(String iscd, String fid_cond_mrkt_div_code) {

        if (fid_cond_mrkt_div_code.equals("U")) {
            path = KisConfig.FHKUP03500100_PATH;
            tr_id = "FHKUP03500100";
        } else {
            path = KisConfig.FHKST03030100_PATH;
            tr_id = "FHKST03030100";
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("fid_cond_mrkt_div_code", fid_cond_mrkt_div_code)
                        .queryParam("fid_input_iscd", iscd)
                        .queryParam("fid_input_date_1", getStringToday())
                        .queryParam("fid_input_date_2", getStringToday())
                        .queryParam("fid_period_div_code", "D")
                        .build())
                .header("content-type","application/json")
                .header("authorization","Bearer " + accessTokenManager.getAccessToken())
                .header("appkey",KisConfig.APPKEY)
                .header("appsecret",KisConfig.APPSECRET)
                .header("tr_id",tr_id)
                .retrieve()
                .bodyToMono(String.class);

    }

    @GetMapping("/equities/{id}")
    public Mono<String> CurrentPrice(@PathVariable("id") String id, Model model) {
        String url = KisConfig.REST_BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd=" + id;

        return webClient.get()
                .uri(url)
                .header("content-type","application/json")
                .header("authorization","Bearer " + accessTokenManager.getAccessToken())
                .header("appkey",KisConfig.APPKEY)
                .header("appsecret",KisConfig.APPSECRET)
                .header("tr_id","FHKST01010100")
                .retrieve()
                .bodyToMono(Body.class)
                .doOnSuccess(body -> {
                    model.addAttribute("equity", body.getOutput());
                    model.addAttribute("jobDate", getJobDateTime());
                })
                .doOnError(result -> System.out.println("*** error: " + result))
                .thenReturn("coin/equities");


    }

    @PostMapping("/equities/020120")
    public Mono<ResponseEntity<Map<String, Object>>> currentPriceKDR() {
        String url = KisConfig.REST_BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd=020120";

        return webClient.get()
                .uri(url)
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                .header("appkey", KisConfig.APPKEY)
                .header("appsecret", KisConfig.APPSECRET)
                .header("tr_id", "FHKST01010100")
                .retrieve()
                .bodyToMono(Body.class)
                .map(body -> {
                    // JSON 형식으로 변환
                    Map<String, Object> map= new HashMap<>();
                    map.put("equity",body.getOutput() );
                    Object ob = map.get("equity");

                    return new ResponseEntity<>(map, HttpStatus.OK);
                });

    }


    @PostMapping("/equities/053280")
    public @ResponseBody Mono<ResponseEntity<Map<String, Object>>> CurrentPrice24() {
        String url = KisConfig.REST_BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd=053280";

        return webClient.get()
                .uri(url)
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                .header("appkey", KisConfig.APPKEY)
                .header("appsecret", KisConfig.APPSECRET)
                .header("tr_id", "FHKST01010100")
                .retrieve()
                .bodyToMono(Body.class)
                .map(body -> {
                    // JSON 형식으로 변환
                    Map<String, Object> map= new HashMap<>();
                    map.put("equity",body.getOutput() );
                    Object ob = map.get("equity");

                    return new ResponseEntity<>(map, HttpStatus.OK);
                });

    }

}