#Apache camel swagger plugin
This plugin supports following goal

- generateSwagger - To generate swagger file from RestConfigurationDefinition 

Library [camel-swagger-java](https://camel.apache.org/components/latest/others/swagger-java.html) exposes swagger only during runtime. There is a need to work with static swagger file in some tools. For example during a build of maven project a swagger file is used in [camel-restdsl-swagger](https://github.com/apache/camel/blob/master/tooling/maven/camel-restdsl-swagger-plugin/src/main/docs/camel-restdsl-swagger-plugin.adoc) plugin to generate consumer REST DSL RouteBuilder source code.

## Adding plugin to your pom.xml
```xml
    <plugin>
                <groupId>cz.kahle.maven</groupId>
                <artifactId>apache-camel-swagger-plugin</artifactId>
                <version>1.0.5-SNAPSHOT</version>
                <dependencies>
                    <dependency>
                        <groupId>XY</groupId>
                        <artifactId>YY</artifactId>
                        <version>1.0.0-SNAPSHOT</version>
                        <scope>compile</scope>
                        <exclusions>
                            <exclusion>
                                <groupId>com.fasterxml.jackson.core</groupId>
                                <artifactId>jackson-core</artifactId>
                            </exclusion>
                            <exclusion>
                                <groupId>com.sun.xml.bind</groupId>
                                <artifactId>*</artifactId>
                            </exclusion>
                        </exclusions>
                    </dependency>
                </dependencies>
                <configuration>
                    <outputDir>${project.basedir}/target/generated-resources/swaggers</outputDir>
                    <routeBuilders>
                        <!-- List of Router classes Dependency should be defined also on project level-->
                        <routeBuilder>xy.CamelRouter</routeBuilder>
                    </routeBuilders>
                    <schemes>
                        <scheme>https</scheme>
                        <scheme>http</scheme>
                    </schemes>
                </configuration>
                <executions>
                    <execution>
                        <id>generateSwagger</id>
                        <goals>
                            <goal>generateSwagger</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

##Don't forget
The Spring Boot Maven and Gradle plugins both package our application as executable JARs – such a file can't be used in another project since class files are put into BOOT-INF/classes. This is not a bug, but a feature.
Classifier exec can solve this problem....
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <classifier>exec</classifier>
    </configuration>
</plugin>
```


## Run integration tests
```console
mvn clean verify -P run-its
```

## Release plugin
```console
mvn release:prepare -P ossrh,release-sign-artifacts -Dresume=false
mvn release:perform -P ossrh,release-sign-artifacts
```