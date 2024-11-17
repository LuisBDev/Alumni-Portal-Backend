package com.alumniportal.unmsm.config.awsConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {


    String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
    String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
    String region = System.getenv("AWS_S3_REGION");

    @Bean
    public LambdaClient lambdaClient() {


        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return LambdaClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

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
