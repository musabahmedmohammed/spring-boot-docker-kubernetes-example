package net.djhurley;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by DJHURLEY on 23/04/2017.
 */
@Component
public class KubernetesBean {

    @Autowired
    private KubernetesConfigBean config;

    @Scheduled(fixedDelay = 5000)
    public void hello() {
        System.out.println("The message is: " + config.getMessage());
    }
}
