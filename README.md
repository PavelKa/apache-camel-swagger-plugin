#Apache camel swagger plugin
This plugin supports following goal

- generateSwagger - To generate swagger file from RestConfigurationDefinition 

Library [camel-swagger-java](https://camel.apache.org/components/latest/others/swagger-java.html) exposes swagger only during runtime. There is a need to work with static swagger file in some tools. For example during a build of maven project a swagger file is used in [camel-restdsl-swagger](https://github.com/apache/camel/blob/master/tooling/maven/camel-restdsl-swagger-plugin/src/main/docs/camel-restdsl-swagger-plugin.adoc) plugin to generate consumer REST DSL RouteBuilder source code.

## Adding plugin to your pom.xml
```xml
 <plugin>
                <groupId>cz.kahle.maven</groupId>
                <artifactId>apache-camel-swagger-plugin</artifactId>
                <version>1.0.4</version>
                <configuration>
                    <outputDir>${project.basedir}/target/classes/META-INF</outputDir>
                    <routeBuilders>
                        <!-- List of Router classes -->
                        <routeBuilder>cz.kahle.maven.SimpleTestRouter</routeBuilder>
                    </routeBuilders>
                    <schemes>
                        <scheme>https</scheme>
                        <scheme>http</scheme>
                    </schemes>
                </configuration>
                <executions>
                    <execution>
                        <id>generateSwagger</id>
                        <phase>process-classes</phase>
                        <goals>
                            <goal>generateSwagger</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

## Release plugin
```console
mvn release:prepare -P ossrh,release-sign-artifacts -Dresume=false
mvn release:perform -P ossrh,release-sign-artifacts
```