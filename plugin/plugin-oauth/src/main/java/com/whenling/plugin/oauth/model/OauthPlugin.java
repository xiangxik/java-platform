package com.whenling.plugin.oauth.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import com.whenling.plugin.model.Plugin;
import com.whenling.plugin.model.PluginConfig;

public abstract class OauthPlugin extends Plugin {

	public static final String CLIENT_ID_ATTRIBUTE_NAME = "client_id";

	public static final String CLIENT_SECRET_ATTRIBUTE_NAME = "client_secret";

	/**
	 * 获取客户端ID
	 * 
	 * @return
	 */
	public String getClientId() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(CLIENT_ID_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取客户端密钥
	 * 
	 * @return
	 */
	public String getClientSecret() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(CLIENT_SECRET_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取授权URL
	 * 
	 * @return
	 */
	public abstract String getAuthorizationUrl();

	public abstract Map<String, Object> getAuthorizationParameterMap();

	public abstract String exchangeCodeForAccessToken(String code);

	public abstract void accessApi(String accessToken);

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	protected String post(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				((CloseableHttpClient) httpClient).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	protected String get(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(
					url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				((CloseableHttpClient) httpClient).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
