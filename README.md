#Apache camel swagger plugin
This plugin supports following goal

- generateSwagger - To generate swagger file from RestConfigurationDefinition 

Library camel-swagger-java exposes swagger only during runtime. There is a need to work with static swagger file in some tools. For example during a build of maven project a swagger file is used in camel-restdsl-swagger plugin to generate consumer REST DSL RouteBuilder source code.