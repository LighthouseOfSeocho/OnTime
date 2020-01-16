package com.onTime.project.loginApi;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.scribejava.apis.NaverApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class NaverLoginApi {
	
    @Value("${naver.client.id}") //네이버API Client ID
    private String CLIENT_ID;      
    @Value("${naver.client.secret}")
    private String CLIENT_SECRET;                      
    private final static String PROTECTED_RESOURCE_URL = "https://openapi.naver.com/v1/nid/me";
    private final static String REDIRECT_URI = "http://192.168.22.109:9000/callback";
    private final static String SESSION_STATE = "oauth_state";
    
    /* 네이버 아이디로 인증  URL 생성  Method */
    public String getAuthorizationUrl(HttpSession session) {
        String state = generateRandomString();
        setSession(session,state);        
        /* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 */
        OAuth20Service oauthService = serviceBuilder();

        return oauthService.getAuthorizationUrl(state);
    }
    
    /* 네이버아이디로 Callback 처리 및  AccessToken 획득 Method */
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException, InterruptedException, ExecutionException{
        /* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
        if(StringUtils.pathEquals(getSession(session), state)){
            return serviceBuilder().getAccessToken(code);
        }
        return null;
    }

    /* Access Token을 이용하여 네이버 사용자 프로필 API를 호출 */
    public JSONObject getUserProfile(OAuth2AccessToken oauthToken) throws IOException, InterruptedException, ExecutionException, ParseException{
    	
    	OAuth20Service oauthService = serviceBuilder();
    	
    	// 데이터 파싱 전처리
    	JSONParser parser = new JSONParser();
    	OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    	oauthService.signRequest(oauthToken, request);
    	JSONObject jsonObj = (JSONObject)parser.parse(oauthService.execute(request).getBody());
    	return (JSONObject)jsonObj.get("response");
    }
    
	/* OAuth2.0 ServiceBuilder */
	private OAuth20Service serviceBuilder() {
		return new ServiceBuilder(CLIENT_ID)
    			.apiSecret(CLIENT_SECRET)
    			.callback(REDIRECT_URI)
    			.build(NaverApi.instance());
	}
    
    /* 세션 유효성 검증을 위한 난수 생성기 */
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    /* http session에 데이터 저장 */
    private void setSession(HttpSession session,String state){
        session.setAttribute(SESSION_STATE, state);     
    }

    /* http session에서 데이터 가져오기 */ 
    private String getSession(HttpSession session){
        return (String) session.getAttribute(SESSION_STATE);
    }
}
