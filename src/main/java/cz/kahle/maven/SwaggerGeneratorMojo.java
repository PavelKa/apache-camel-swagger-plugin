package cz.kahle.maven;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.engine.DefaultClassResolver;
import org.apache.camel.model.rest.RestsDefinition;
import org.apache.camel.swagger.RestSwaggerReader;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;


@Mojo(name = "generateSwagger", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class SwaggerGeneratorMojo extends AbstractMojo {


    private static final String FILE_SEPARTOR = File.separator;
    /**
     * Swagger output directory.
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/resources", property = "outputDir", required = true)
    private String outputDir;

    /**
     * RouteBuilders classes
     */
    @Parameter(property = "routeBuilders", required = true)
    private List<String> routeBuilders;


    /**
     * Swagger host
     */
    @Parameter(property = "host", defaultValue = "localhost", required = true)
    private String host;

    /**
     * Swagger port
     */
    @Parameter(property = "port", defaultValue = "8080", required = true)
    private String port;

    /**
     * Swagger basePath
     */
    @Parameter(property = "basePath", defaultValue = "/camel", required = true)
    private String basePath;


    /**
     * Swagger schemes
     */
    @Parameter(property = "schemes", required = true)
    private String[] schemes;




    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log theLog = this.getLog();

        for (String it : routeBuilders) {
            String swagger;
            try {
                swagger = getSwagger(it, SwaggerGeneratorMojo.class.getClassLoader() );
            } catch (Exception e) {
                theLog.error("Swagger creation fails: " + e.getMessage(), e);
                throw new MojoFailureException("Swagger creation fails: " + e.getMessage(), e);
            }
            File fItJavaPath = createDirs(outputDir);
            try {
                FileUtils.writeStringToFile(new File(fItJavaPath + FILE_SEPARTOR + it + ".json"), swagger, "UTF-8");
                theLog.info("Swagger  written to file " + fItJavaPath + FILE_SEPARTOR + it);
            } catch (IOException e) {
                theLog.error("Cannot write to file " + fItJavaPath + FILE_SEPARTOR + it, e);
                throw new RuntimeException("Cannot write to file " + fItJavaPath + FILE_SEPARTOR + it, e);
            }


        }
    }


    private String getSwagger(String routeBuilder, ClassLoader classLoader) throws Exception {

        Class<?> clazz = classLoader.loadClass(routeBuilder);
        Constructor<?> ctor = clazz.getConstructor();
        RouteBuilder cr = (RouteBuilder) ctor.newInstance();
        cr.configure();
        RestsDefinition rd = cr.getRestCollection();

        BeanConfig config = new BeanConfig();
        config.setHost(String.format("%s:%s", host, port));
        config.setSchemes(schemes);
        config.setBasePath(basePath);
        RestSwaggerReader reader = new RestSwaggerReader();

        Swagger swagger = reader.read(rd.getRests(), null, config, null, new DefaultClassResolver());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(swagger);

    }

    private File createDirs(String itJavaPath) {
        File fItJavaPath = new File(itJavaPath);
        if (!fItJavaPath.exists()) {
            boolean sucess = fItJavaPath.mkdirs();

        }
        return fItJavaPath;
    }


}
