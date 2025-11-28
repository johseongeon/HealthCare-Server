package cerberus.HealthCare;

import aws.smithy.kotlin.runtime.content.ByteStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.charset.StandardCharsets;

@SpringJUnitConfig
public class S3ServiceTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public S3Service s3Service() {
            return new S3Service();
        }
    }

    @Autowired
    private S3Service s3Service;

    @Test
    void testUploadImage() throws Exception {
        String key = "test/test-upload.txt";
        String content = "Hello from Java Test!";

        ByteStream bytes = ByteStream.Companion.fromBytes(
                content.getBytes(StandardCharsets.UTF_8)
        );

        s3Service.uploadImageBlocking(key, bytes, "text/plain");
        System.out.println("Upload success!");
    }

    @Test
    void testGetUploadPresignedUrl() throws Exception {
        String key = "test/presigned-upload.txt";
        String url = s3Service.getUploadPresignedUrlBlocking(key, 600);

        System.out.println("Upload Presigned URL: " + url);
    }

    @Test
    void testGetDownloadPresignedUrl() throws Exception {
        String key = "test/test-upload.txt";
        String url = s3Service.getDownloadPresignedUrlBlocking(key, 600);

        System.out.println("Download Presigned URL: " + url);
    }
}