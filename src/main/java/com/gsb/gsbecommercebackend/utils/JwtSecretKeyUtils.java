package com.gsb.gsbecommercebackend.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtSecretKeyUtils {

    @PostConstruct
    public void generateAndPrintSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("jwt.secret: \"" + base64Key + "\"");

        } catch (Exception e) {
            System.err.println("Erreur lors de la génération de la clé secrète : " + e.getMessage());
        }
    }
}
