package com.tracker.zen.component;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySources;

@Configuration
@PropertySource("classpath:url_list.properties") // 自分で作成したプロパティファイルを指定
public class UrlPropertiesConfig {

	private Map<String, String> allUrls = new HashMap<>();

	@Autowired
	private Environment environment;

	@Autowired
	private ConfigurableEnvironment mastaEnv;

	// プロパティファイルからキーに対応する値を取得
	public String getUrl(String key) {
		return environment.getProperty(key);
	}

	@PostConstruct
	public void init() {
		PropertySources propertySources = mastaEnv.getPropertySources();
		for (org.springframework.core.env.PropertySource<?> propertySource : propertySources) {
			Object source = propertySource.getSource();
			if (source instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> sourceMap = (Map<String, Object>) source;
				sourceMap.forEach((key, value) -> {
					if (key.startsWith("sc")) { // "sc" で始まるキーのみを対象にする
						allUrls.put(key, value.toString());
					}
				});
			}
		}
	}

	public Map<String, String> getAllUrls() {
		return allUrls;
	}
}
