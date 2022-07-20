package io.github.ignamlrz.autotrader.core;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public class MongoInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private MongoDBContainer container;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        MongoDBContainer container = getMongoContainerSingleton();
        var addedProperties = List.of(
                "spring.data.mongodb.uri=" + container.getReplicaSetUrl()
        );
        TestPropertyValues.of(addedProperties).applyTo(applicationContext.getEnvironment());
    }

    private MongoDBContainer getMongoContainerSingleton() {
        if(this.container != null) return this.container;
        this.container = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                .withReuse(true);
        container.start();
        return container;
    }
}
