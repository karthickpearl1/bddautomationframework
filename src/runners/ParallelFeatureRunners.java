package runners;



import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.ParallelAbstractTestNGCucumberRunner;

@CucumberOptions(features = { "features/SampleTest.feature", "features/SampleTest2.feature",
		"features/SampleTest3.feature"
										  , "features/SampleTest4.feature",
										  "features/SampleTest5.feature"
										  }, glue = { "steps" })
public class ParallelFeatureRunners extends ParallelAbstractTestNGCucumberRunner {

	@BeforeSuite
	void beforeSuite() {

	}

	@Test
	void test() {

	}

	@AfterSuite
	void afterSuite() {

	}

}
