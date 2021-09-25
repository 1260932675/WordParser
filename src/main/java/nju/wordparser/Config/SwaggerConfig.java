package nju.wordparser.Config;
/*
 * @ClassName SwaggerConfig
 * @Description TODO 对Swagger进行配置
 * @Author ling
 * @Date 2021/9/25 11:10
 * @Version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @Author ling
     * @Date 2021/9/25
     * @Description //TODO 创建API应用
     * @return springfox.documentation.spring.web.plugins.Docket
     **/
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("nju.wordparser.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("文档解析接口")
                .description("用于word文档的解析，支持doc、docx、wps、pdf格式文件")
                .version("1.0")
                .build();
    }
}
