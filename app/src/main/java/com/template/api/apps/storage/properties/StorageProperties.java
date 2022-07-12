package com.template.api.apps.storage.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Storage properties.
 *
 * @author eomjeongjae
 * @since 2019 /10/15
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {

    private String location;

}
