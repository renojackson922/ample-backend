package com.silkdog.ambulance.restapi.location.controller;

import com.google.gson.Gson;
import com.silkdog.ambulance.restapi.location.domain.ErmctInfoInqire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("data-api")
@RequiredArgsConstructor
public class LocationController {

    public static int INDENT_FACTOR = 4;

    @ResponseBody
    @RequestMapping(path = "getHospitalAvailable", method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
    public ResponseEntity<String> getHospitalAvailable(String stage1, String stage2, String pageNo, String numOfRows) throws IOException, NullPointerException {

        String _stage1 = StringUtils.isNoneEmpty(stage1) ? stage1 : "";
        String _stage2 = StringUtils.isNoneEmpty(stage2) ? stage2 : "";

        String urlString = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire"; /*URL*/
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "="+ "3JSKy8OQAgWy6GIIMYV4ClVy43GIiu2HHfoRGbW1diBBLkHoI9u68zbpYJMVinZPW0m8dwrsH9icXrXAIQxd%2Fw%3D%3D"; /*Service Key*/
        queryParams += '&' + URLEncoder.encode("STAGE1") + '=' + URLEncoder.encode(_stage1); /**/
        queryParams += '&' + URLEncoder.encode("STAGE2") + '=' + URLEncoder.encode(_stage2); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode("1"); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode("30"); /**/

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(new URL(urlString + queryParams));
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        response.close();
        JSONObject object = new JSONObject(response.body().string());

        Gson gson = new Gson();
        ErmctInfoInqire ermctInfoInqire = gson.fromJson(String.valueOf(object), ErmctInfoInqire.class);

        return ResponseEntity.ok("jsonPrettyPrintString");
    }

    @ResponseBody
    @RequestMapping(path = "getHospitalAddr", method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
    public ResponseEntity<String> getHospitalAddr(String stage1, String stage2, String pageNo, String numOfRows) throws IOException, NullPointerException {

        String _stage1 = StringUtils.isNoneEmpty(stage1) ? stage1 : "";
        String _stage2 = StringUtils.isNoneEmpty(stage2) ? stage2 : "";

        String urlString = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire"; /*URL*/
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "="+ "3JSKy8OQAgWy6GIIMYV4ClVy43GIiu2HHfoRGbW1diBBLkHoI9u68zbpYJMVinZPW0m8dwrsH9icXrXAIQxd%2Fw%3D%3D"; /*Service Key*/
        queryParams += '&' + URLEncoder.encode("Q0") + '=' + URLEncoder.encode(_stage1); /**/
        queryParams += '&' + URLEncoder.encode("Q1") + '=' + URLEncoder.encode(_stage2); /**/
        queryParams += '&' + URLEncoder.encode("ORD") + '=' + URLEncoder.encode("NAME"); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode("1"); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode("30"); /**/

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(new URL(urlString + queryParams));
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        JSONObject object = new JSONObject(response.body().string());

        Gson gson = new Gson();
        ErmctInfoInqire ermctInfoInqire = gson.fromJson(String.valueOf(object), ErmctInfoInqire.class);

        return ResponseEntity.ok("jsonPrettyPrintString");
    }

}
