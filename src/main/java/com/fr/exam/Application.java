package com.fr.exam;

import com.fr.exam.utils.RSAUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author furao
 * @desc
 * @date 2021/2/18
 * @package com.fr.exam
 */
@SpringBootApplication
@MapperScan(value={"com.fr.exam.**.mapper*"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        try {
            RSAUtils.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
