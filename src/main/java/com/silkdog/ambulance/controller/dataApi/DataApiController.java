package com.silkdog.ambulance.controller.dataApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DataApiController {

    public static int INDENT_FACTOR = 4;

    @RequestMapping(path = "/api/getHospitalAvailable", method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
    public @ResponseBody String getHospitalAvailable(String stage1, String stage2, String pageNo, String numOfRows) throws MalformedURLException {
        String _stage1 = (stage1 != null && !stage1.isEmpty()) ? stage1 : ""; //stage1 ?? ""; <!-- 이거 C#, 코틀린에서밖에 안되는거 같다 충격.....
        String _stage2 = (stage2 != null && !stage2.isEmpty()) ? stage2 : ""; //stage2 ?? "";

        String urlString = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire"; /*URL*/
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "="+ "3JSKy8OQAgWy6GIIMYV4ClVy43GIiu2HHfoRGbW1diBBLkHoI9u68zbpYJMVinZPW0m8dwrsH9icXrXAIQxd%2Fw%3D%3D"; /*Service Key*/
        queryParams += '&' + URLEncoder.encode("STAGE1") + '=' + URLEncoder.encode(_stage1); /**/
        queryParams += '&' + URLEncoder.encode("STAGE2") + '=' + URLEncoder.encode(_stage2); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode("1"); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode("30"); /**/

        URL url = new URL(urlString + queryParams);
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        BufferedReader br;

        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();

        try {
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            con.setDoOutput(false);  // 출력은 true; else false

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                    //System.out.println(line);
                }
                br.close();

                //mapper.readValue(sb.toString(), new TypeReference<List<Map<String, Object>>>(){});
                //model.addAttribute("listMap", listMap);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
        //System.out.println(jsonPrettyPrintString);

        return jsonPrettyPrintString;
    }

    @RequestMapping(path = "/api/getHospitalAddr", method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
    public @ResponseBody String getHospitalAddr(String stage1, String stage2, String pageNo, String numOfRows) throws MalformedURLException {
        String _stage1 = (stage1 != null && !stage1.isEmpty()) ? stage1 : ""; //stage1 ?? ""; <!-- 이거 C#, 코틀린에서밖에 안되는거 같다 충격..... null coalescing operator
        String _stage2 = (stage2 != null && !stage2.isEmpty()) ? stage2 : ""; //stage2 ?? "";

        String urlString = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire"; /*URL*/
        String queryParams = '?' + URLEncoder.encode("ServiceKey") + "="+ "3JSKy8OQAgWy6GIIMYV4ClVy43GIiu2HHfoRGbW1diBBLkHoI9u68zbpYJMVinZPW0m8dwrsH9icXrXAIQxd%2Fw%3D%3D"; /*Service Key*/
        queryParams += '&' + URLEncoder.encode("Q0") + '=' + URLEncoder.encode(_stage1); /**/
        queryParams += '&' + URLEncoder.encode("Q1") + '=' + URLEncoder.encode(_stage2); /**/
        queryParams += '&' + URLEncoder.encode("ORD") + '=' + URLEncoder.encode("NAME"); /**/
        queryParams += '&' + URLEncoder.encode("pageNo") + '=' + URLEncoder.encode("1"); /**/
        queryParams += '&' + URLEncoder.encode("numOfRows") + '=' + URLEncoder.encode("30"); /**/

        URL url = new URL(urlString + queryParams);
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        BufferedReader br;

        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();

        try {
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            con.setDoOutput(false);  // 출력은 true; else false

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                    //System.out.println(line);
                }
                br.close();

                //mapper.readValue(sb.toString(), new TypeReference<List<Map<String, Object>>>(){});
                //model.addAttribute("listMap", listMap);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
        //System.out.println(jsonPrettyPrintString);

        return jsonPrettyPrintString;
    }

}
