package net.djhurley;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by DJHURLEY on 23/04/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "bean")
public class KubernetesConfigBean {

    private String message = "a message that can be changed live";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
