package com.alumniportal.unmsm.config.s3config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    private final Dotenv dotenv = Dotenv.load();

    String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
    String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
    String region = dotenv.get("AWS_S3_REGION");

    @Bean
    public S3Client s3Client() {
        // Configurar las credenciales de acceso
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Crear el cliente S3
        return S3Client.builder()
                .region(Region.of(region))  // Configurar la región
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

}
