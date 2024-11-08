package com.alumniportal.unmsm.config.lambdaConfig;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
public class LambdaAWS {

    private final Dotenv dotenv = Dotenv.load();

    String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
    String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
    String region = dotenv.get("AWS_S3_REGION");

    @Bean
    public LambdaClient lambdaClient() {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return LambdaClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}