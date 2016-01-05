package com.ist.assemble;
import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;

/**
 * 自定义es全局设置
 * @author qianguobing
 */
public class CustomImmutableSetting {
    
	private Map<String, String> settings = new LinkedHashMap<String, String>();
	private Builder builder;
	private String mappingStr;
	
	public CustomImmutableSetting(){
		this.builder = ImmutableSettings.builder();
	}
	public void setSettings(Map<String, String> settings){
		builder.put(settings);
	}
	public Map<String, String> getSettings() {
		return settings;
	}
	public Builder getBuilder() {
		return builder;
	}
    public String getMappingStr() {
        return mappingStr;
    }
    public void setMappingStr(String mappingStr) {
        this.mappingStr = mappingStr;
    }
	
}
