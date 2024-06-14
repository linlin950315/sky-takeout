package com.sky.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {
    // 这四个属性 会配置在云上 application.yml 文件中
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
