package Tests;


import core.DriverFactory;
import core.JSONDataProvider;
import core.TestConfig;
import core.TestReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestBase {

    private WebDriver driver;
    private TestReporter reporter;

    @BeforeSuite
    public void initSuite() throws Exception {
        TestConfig.load(System.getenv("env"));
        TestConfig.addProperty("browser",System.getenv("browser"));
        TestConfig.addProperty("env",System.getenv("env"));
        reporter = new TestReporter();
    }

    @BeforeClass
    public void initDriver() {
        driver =  new DriverFactory().getDriver(TestConfig.getProperty("browser"));
    }

    @DataProvider
    public Object[][] getData(Method testCase) throws Exception {
        File testDataLocation = new File("src/test/resources/testdata");
        List<HashMap<String,String>> extractedData = null;
        String dataSource = TestConfig.getProperty("dataSource");
        String envName  =  TestConfig.getProperty("env").toUpperCase();

        // Setting the data source
        JSONDataProvider json = new JSONDataProvider(testDataLocation+"/data."+envName+".json");
        extractedData = json.getAllData(testCase.getName());
        return this.createDataProvider(extractedData);
    }

    private Object[][] createDataProvider(Object dataSet){
        int rowNo = ((ArrayList)dataSet).size();
        Object[][] dataArray = new Object[rowNo][2];
        int dim = 0;
        for(int iRow=0;iRow<rowNo;iRow++) {
            dataArray[dim][0] = iRow+1;
            dataArray[dim][1] = ((ArrayList)dataSet).get(iRow);
            dim++;
        }
        return dataArray;
    }

    public WebDriver driver() {
        return driver;
    }

    public void moveOverElementInPage(WebElement ele){
        Actions action = new Actions(driver());
        action.moveToElement(ele).perform();
    }

    @BeforeMethod
    public void launchApp() {
        driver.get(TestConfig.getProperty("appHomeURL"));
    }

    @BeforeMethod
    public void initTestReport(Method method){
        reporter.startReporting(method.getName(), driver);
    }

    public TestReporter reporter(){
        if(reporter!=null){
            return reporter;
        }
        return null;
    }

    @AfterMethod
    public void closeReport(){
        reporter.endReporting();
    }

    @AfterClass
    public void cleanUp() {
        if(driver!=null) {
            driver.close();
        }
    }

    @AfterSuite
    public void clearReport(){
        reporter.flushReport();
    }

    @AfterMethod
    public void takeScreenShotIfFailure(ITestResult result) throws IOException {
        if(ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot camera =((TakesScreenshot)driver);
            File screenShot = camera.getScreenshotAs(OutputType.FILE);
            System.out.println("Screenshot taken: " + screenShot.getAbsolutePath());
            File DestFile = new File("./ScreenShots/"+result.getName()+"_Fail.png");
            FileHandler.copy(screenShot,DestFile);
        }

    }
/*
    public void scrollToEndPage(){
        try {
            Object lastHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000);

                Object newHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
                if (newHeight.equals(lastHeight)) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

*/
}