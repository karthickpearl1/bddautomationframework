package listeners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;

public class ParallelAbstractTestNGCucumberRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		
		Object[][] obj =super.scenarios();
		return obj;
	}
}
