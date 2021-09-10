package com.silkdog.ambulance.restapi.location.service.impl;

import com.google.gson.Gson;
import com.silkdog.ambulance.config.ConstVar;
import com.silkdog.ambulance.restapi.location.domain.EgytListInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.EmrrmRltmUsefulSckbdInfoInqire;
import com.silkdog.ambulance.restapi.location.service.HospitalService;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    // FIXME: SocketTimeoutException 핸들링 해야함
    @Override
    public List<EmrrmRltmUsefulSckbdInfoInqire> getHospitalAvailable(String s1, String s2, int pNo, int rowCount) throws IOException {

        String urlString = ConstVar.EMERGENCY_SERVICE_AVAILABLE_API;
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "=" + ConstVar.SERVICE_KEY;
        queryParams += '&' + URLEncoder.encode("STAGE1") + '=' + URLEncoder.encode(s1); /**/
        queryParams += '&' + URLEncoder.encode("STAGE2") + '=' + URLEncoder.encode(s2); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode(Integer.toString(pNo)); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode(Integer.toString(rowCount)); /**/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(logging).build();
        Request.Builder builder = new Request.Builder().url(new URL(urlString + queryParams));
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        JSONObject xmlJSONObj = XML.toJSONObject(Objects.requireNonNull(response.body()).string());

        JSONObject respHeader = xmlJSONObj.getJSONObject("response").getJSONObject("header");
        String resultCode = respHeader.optString("resultCode");
        String resultMsg = respHeader.optString("resultMsg");

        JSONArray respBody = xmlJSONObj
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items").getJSONArray("item");

        List<EmrrmRltmUsefulSckbdInfoInqire> lstErmctInfoInqire = new ArrayList<>();
        EmrrmRltmUsefulSckbdInfoInqire emrrmRltmUsefulSckbdInfoInqire;
        Gson gson = new Gson();

        for (int i = 0; i < respBody.length(); i++) {
            emrrmRltmUsefulSckbdInfoInqire = gson.fromJson(String.valueOf(respBody.getJSONObject(i)), EmrrmRltmUsefulSckbdInfoInqire.class);
            emrrmRltmUsefulSckbdInfoInqire.setResultCode(resultCode);
            emrrmRltmUsefulSckbdInfoInqire.setResultMsg(resultMsg);
            lstErmctInfoInqire.add(emrrmRltmUsefulSckbdInfoInqire);
        }

        response.close();

        return lstErmctInfoInqire;
    }


    @Override
    public List<EgytListInfoInqire> getHospitalAddr(String s1, String s2, int pNo, int rowCount) throws IOException {
        String urlString = ConstVar.HOSPITAL_INFO_API;
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "=" + ConstVar.SERVICE_KEY;
        queryParams += '&' + URLEncoder.encode("STAGE1") + '=' + URLEncoder.encode(s1); /**/
        queryParams += '&' + URLEncoder.encode("STAGE2") + '=' + URLEncoder.encode(s2); /**/
        queryParams += '&' + URLEncoder.encode("ORD") + '=' + URLEncoder.encode("NAME"); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode(Integer.toString(pNo)); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode(Integer.toString(rowCount)); /**/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(logging).build();
        Request.Builder builder = new Request.Builder().url(new URL(urlString + queryParams));
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        JSONObject xmlJSONObj = XML.toJSONObject(Objects.requireNonNull(response.body()).string());

        JSONObject respHeader = xmlJSONObj.getJSONObject("response").getJSONObject("header");
        String resultCode = respHeader.optString("resultCode");
        String resultMsg = respHeader.optString("resultMsg");

        JSONArray respBody = xmlJSONObj
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items").getJSONArray("item");

        List<EgytListInfoInqire> lstEgytListInfoInqire = new ArrayList<>();
        EgytListInfoInqire egytListInfoInqire;
        Gson gson = new Gson();

        for (int i = 0; i < respBody.length(); i++) {
            egytListInfoInqire = gson.fromJson(String.valueOf(respBody.getJSONObject(i)), EgytListInfoInqire.class);
            egytListInfoInqire.setResultCode(resultCode);
            egytListInfoInqire.setResultMsg(resultMsg);
            lstEgytListInfoInqire.add(egytListInfoInqire);
        }

        response.close();

        return lstEgytListInfoInqire;
    }
}
