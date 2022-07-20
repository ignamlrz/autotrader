package io.github.ignamlrz.autotrader.core.annotation;

import io.github.ignamlrz.autotrader.core.MongoInitializer;
import io.github.ignamlrz.autotrader.core.repository.MainRepositoriesConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataMongoTest
@ContextConfiguration(classes = MainRepositoriesConfiguration.class, initializers = MongoInitializer.class)
public @interface MongoSpringBootTest {
}
