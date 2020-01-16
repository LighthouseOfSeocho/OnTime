package com.onTime.project.loginApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoLoginApi {
	@Value("${kakao.key}")
	private String kakaoKey;
	
	public String getAuthUrl() {
		return "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoKey
				+ "&redirect_uri=http://192.168.22.109:9000/oauth&response_type=code";
	}
	
	public String getAccessKakaoToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write("grant_type=authorization_code&client_id=" + kakaoKey +"&redirect_uri=http://192.168.22.109:9000/oauth&code=" + authorize_code);
			bw.flush();
			
			int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			br.lines().forEach(v -> sb.append(v));
			
			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonElement element = JsonParser.parseString(sb.toString());
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}
	
	public JSONObject getUserInfo(String access_Token) {
		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		JSONObject userInfo = new JSONObject();

		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			br.lines().forEach(v -> sb.append(v));
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(sb.toString());

			String id = element.getAsJsonObject().get("id").getAsString();
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();

			userInfo.put("id", id);
			userInfo.put("nickname", nickname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
}
