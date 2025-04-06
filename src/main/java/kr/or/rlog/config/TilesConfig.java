package kr.or.rlog.config;

import kr.or.rlog.common.TileViewPreparer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.SimpleSpringPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


@Configuration
public class TilesConfig implements WebMvcConfigurer {

    //뷰 세팅1
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer configurer = new TilesConfigurer();
        
        //타일즈 설정파일이 위치하는 디렉토리+파일명
        //configurer.setDefinitions(new String[]{"/WEB-INF/tiles/tiles.xml"});
        configurer.setDefinitions("classpath:/WEB-INF/tiles/tiles.xml");
        configurer.setCheckRefresh(true);
        configurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);
        return configurer;
    }

    //뷰 세팅2
    @Bean
    public TilesViewResolver tilesViewResolver() {
        final TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setOrder(1);  //뷰 우선순위
        return tilesViewResolver;
    }






}