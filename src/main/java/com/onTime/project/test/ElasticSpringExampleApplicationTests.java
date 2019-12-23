package com.onTime.project.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.onTime.project.model.es.ElasticApi;

@RunWith(OnTimeApplication.class)
@SpringBootTest
public class ElasticSpringExampleApplicationTests {

	@Autowired
	ElasticApi elasticApi;

	private final String ELASTIC_INDEX = "test_index";
	private final String ELASTIC_TYPE = "test_type";

	@Test
	public void 엘라스틱서치_POST_전송() {
		String url = ELASTIC_INDEX + "/" + ELASTIC_TYPE;
		Weather weather = new Weather();
		weather.setCity("Seoul");
		weather.setTemperature(10.2);
		weather.setSeason("Winter");

		Map<String, Object> result = elasticApi.callElasticApi("POST", url, weather, null);
		System.out.println(result.get("resultCode"));
		System.out.println(result.get("resultBody"));
	}


	@Test
	public void 엘라스틱서치_PUT_전송() {
		String id = "122345";
		String url = ELASTIC_INDEX + "/" + ELASTIC_TYPE+"/"+id;
		Weather weather = new Weather();
		weather.setCity("Tokyo");
		weather.setTemperature(12.3);
		weather.setSeason("Winter");

		Map<String, Object> result = elasticApi.callElasticApi("PUT", url, weather, null);
		System.out.println(result.get("resultCode"));
		System.out.println(result.get("resultBody"));
	}


	@Test
	public void 앨라스틱서치_GET_전송() {
		String id = "122345";
		String url = ELASTIC_INDEX + "/" + ELASTIC_TYPE+"/"+id;
		Map<String, Object> result = elasticApi.callElasticApi("GET", url, null, null);
		System.out.println(result.get("resultCode"));
		System.out.println(result.get("resultBody"));
	}


	@Test
	public void 앨라스틱서치_DELETE_전송() {
		String id = "122345";
		String url = ELASTIC_INDEX + "/" + ELASTIC_TYPE+"/"+id;
		Map<String, Object> result = elasticApi.callElasticApi("DELETE", url, null, null);
		System.out.println(result.get("resultCode"));
		System.out.println(result.get("resultBody"));
	}
