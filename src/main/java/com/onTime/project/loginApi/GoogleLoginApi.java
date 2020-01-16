package com.onTime.project.loginApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class GoogleLoginApi {

	@Value("${google.client.id}")
	private String CLIENT_ID;
	@Value("${google.client.secret}")
	private String CLIENT_SECRET;
	private final static String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
	private final static String REDIRECT_URI = "http://192.168.22.109:9000/googleCallback";
	private final static String SESSION_STATE = "oauth_state";
	private final static String SCOPE = "profile";
	
	public String getAuthorizationUrl(HttpSession session) {
        String state = generateRandomString();
        setSession(session,state);
        
        OAuth20Service oauthService = serviceBuilder();
        
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        additionalParams.put("prompt", "consent");
        
        return oauthService.createAuthorizationUrlBuilder()
					                .state(state)
					                .additionalParams(additionalParams)
					                .build();
	}
	
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException, InterruptedException, ExecutionException {
		if(StringUtils.pathEquals(getSession(session), state)){
	        OAuth20Service oauthService = serviceBuilder();
	        OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
	        accessToken = oauthService.refreshAccessToken(accessToken.getRefreshToken());
	        
	        return accessToken;
		}
		return null;
	}
	
	public JSONObject getUserProfile(OAuth2AccessToken oauthToken) throws ParseException, IOException, InterruptedException, ExecutionException {
        OAuth20Service oauthService = serviceBuilder();
        
    	JSONParser parser = new JSONParser();
    	OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    	oauthService.signRequest(oauthToken, request);
    	
        return (JSONObject)parser.parse(oauthService.execute(request).getBody());
	}
	
	/* OAuth2.0 ServiceBuilder */
	private OAuth20Service serviceBuilder() {
		return new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .defaultScope(SCOPE) // replace with desired scope "email" or "profile"
                .callback(REDIRECT_URI)
                .build(GoogleApi20.instance());
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
