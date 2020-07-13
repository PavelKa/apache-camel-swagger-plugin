package cz.kahle.maven;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SwaggerGeneratorMojoTest {
    private static final String TARGET_TEST_CLASSES_PROJECT_TO_TEST = "target/test-classes/project-to-test/";
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() {
        }

        @Override
        protected void after() {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testSwaggerCreation()
            throws Exception {

        File pom = new File(TARGET_TEST_CLASSES_PROJECT_TO_TEST);
        assertNotNull(pom);
        assertTrue(pom.exists());

        SwaggerGeneratorMojo myMojo = (SwaggerGeneratorMojo) rule.lookupConfiguredMojo(pom, "generateSwagger");
        assertNotNull(myMojo);
        myMojo.execute();

        File f6 = new File(TARGET_TEST_CLASSES_PROJECT_TO_TEST + "target/cz.kahle.maven.SimpleTestRouter.json");
        assertTrue("SimpleTestRouter.json exists", f6.exists());


    }


}

