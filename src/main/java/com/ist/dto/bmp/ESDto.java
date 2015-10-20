package com.ist.dto.bmp;

import java.io.Serializable;

import com.ist.dto.BaseDto;

/**
 * elasticsearch搜索和索引实体
 * 
 * @author qianguobing
 */
public class ESDto extends BaseDto implements Serializable {
    /**
     * 序列化Id
     */
    private static final long serialVersionUID = 7712247850042953111L;
    /**
     * 机构key
     */
    private String organkey;
    public String getOrgankey() {
        return organkey;
    }
    public void setOrgankey(String organkey) {
        this.organkey = organkey;
    }
}
