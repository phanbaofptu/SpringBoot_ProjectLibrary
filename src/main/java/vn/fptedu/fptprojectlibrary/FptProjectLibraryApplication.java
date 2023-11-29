package vn.fptedu.fptprojectlibrary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import vn.fptedu.fptprojectlibrary.service.StorageProperties;
import vn.fptedu.fptprojectlibrary.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FptProjectLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FptProjectLibraryApplication.class, args);
    }
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
