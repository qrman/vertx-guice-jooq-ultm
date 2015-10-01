package io.github.qrman.potato;

import com.google.inject.Guice;
import com.google.inject.Inject;
import io.github.qrman.potato.db.DBCleaner;
import io.github.qrman.potato.db.guice.DbModule;
import io.github.qrman.potato.guice.GuiceModule;
import io.github.qrman.potato.verticle.PotatoVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class IntegrationTests {

    @Inject
    protected DBCleaner dBCleaner;
    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Before
    public void runEnvironment(TestContext context) {

        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.read();
        
        DeploymentOptions dopts = new DeploymentOptions();
        dopts.setConfig(propertiesReader.getAsJson());

        PotatoVerticle potatoVerticle = new PotatoVerticle();
        rule.vertx().deployVerticle(potatoVerticle, dopts, context.asyncAssertSuccess());

        Guice.createInjector(
          new GuiceModuleForTests(propertiesReader.getAsProperties()),
          new DbModule(),
          new GuiceModule(rule.vertx())
        ).injectMembers(this);
        
        //Clear DB before running tests
        dBCleaner.apply();
    }
}
