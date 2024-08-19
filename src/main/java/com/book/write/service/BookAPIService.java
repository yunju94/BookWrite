package com.book.write.service;

import com.book.write.entity.Book;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
@Transactional
@AllArgsConstructor
public class BookAPIService {
    public static StringBuilder apiCall(String input) throws IOException {
        String apiurl = "https://dapi.kakao.com/v3/search/book?query=";
        String encodeSearch = URLEncoder.encode(input,"UTF-8");
        apiurl += encodeSearch;
        //URL 객체 생성
        URL url = new URL(apiurl);
        //url을 이용해서 연결을 실행 하고 HttpURLConnection 객체를 생성
        // 변수 HttpURLConnection con 객체를 받는다.
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET"); //GET 방식 (Get, Post, Put, Delete, Patch)
        // 접근 권한 확인 "Authorization" : "KakaoAK 1879f921e1ec192b0dbccb7ece8724db"
        con.setRequestProperty("Authorization","KakaoAK "+"1879f921e1ec192b0dbccb7ece8724db");
        //연결됭 InputStream -> InputStreamReader -> BufferReader
        // Kakao 서버에서 나온 결과를 받기 위해서
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder content = new StringBuilder();
        // 반복문 돌면서 결과를 StringBuilder로 추가해서 하나로 만듭니다.
        while((inputLine = in.readLine())!= null){
            content.append(inputLine);
        }
        in.close(); // BufferReader 종료
        con.disconnect(); // HttpURLConnection 종료
        // System.out.println(content.toString()); // 출력
        return content; //StringBuilder
    }

    public static ArrayList<Book> stringtoJSON(StringBuilder content){
        // Gson 객체 생성
        Gson gson = new Gson();
        // 문자열 -> Json 바꾼다.
        JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);
        System.out.println(jsonObject);
        // Json -> JsonArray
        // documents 왜 ? 카카오 Json 최상단 키를 documents
        JsonArray jsonArray = jsonObject.getAsJsonArray("documents");
        System.out.println(jsonArray);
        //ArrayList 객체를 생성 제네릭 Book
        ArrayList<Book> books = new ArrayList<>();
        // 결과를 jsonArray 만든 것을 하나씩 JsonElement로 뺀다.
        // 데이터가 없을 때 까지
        for(JsonElement je : jsonArray){
            // JsonElement -> JsonObject(객체)
            JsonObject jo = je.getAsJsonObject();
            // Book 객체 생성
            Book b = new Book();
            // JsonObject -> 키 title 가지고 값을 추출하면
            // 추출한 값이 null이 아니면 실행
            if(jo.get("title")!=null){
                //Book에 JsonObject -> key: title 에 값을 문자열로 변경해서 대입
                b.setTitle(jo.get("title").getAsString());
            }
            // JsonObject -> 키 authors 가지고 값을 추출하면
            // 추출한 값이 null이 아니면 실행
            if(jo.get("authors")!=null){
                //작성자가 2명일 수 있기 때문에 JsonObject -> JsonArray
                JsonArray jsonArray1 = jo.get("authors").getAsJsonArray();
                String result = "";
                //JsonArray -> JsonElement로 하나씩 받아서
                //데이터 없을 때까지 실행
                for(JsonElement e : jsonArray1){
                    //JsonElement를 문자열로 변경해서 result 변수에
                    //중첩해서 데이터를 담는다.
                    result += e.getAsString();
                }
                //result를 Book객체에 authors에 대입
                b.setAuthors(result);
            }
            // JsonObject -> 키 publisher 가지고 값을 추출하면
            // 추출한 값이 null이 아니면 실행
            if(jo.get("publisher")!=null){
                b.setPublisher(jo.get("publisher").getAsString());
            }
            // JsonObject -> 키 thumbnail 가지고 값을 추출하면
            // 추출한 값이 null이 아니면 실행
            if(jo.get("thumbnail")!=null){
                b.setThumbnail(jo.get("thumbnail").getAsString());
            }
            //Book에 데이터가 모두 들어갑니다. -> ArrayList<Book>에 추가를 합니다.
            books.add(b);
        }
        //for(Book b : books){
       //     System.out.println(b);
       // }
        //ArrayList<Book>을 리턴합니다.
        return books;
    }
}
