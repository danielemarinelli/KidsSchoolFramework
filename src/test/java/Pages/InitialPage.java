package Pages;

import Tests.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InitialPage extends TestBase {

    @FindBy(xpath =".//img[@alt='Registro Elettronico Famiglie']/..")
    private WebElement btnRegistroElettronico;

    @FindBy(xpath =".//select[@class='goog-te-combo']")
    private WebElement languageOnSite;


    private WebDriver driver;
    public InitialPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void registroElettonicoInput() throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");
        btnRegistroElettronico.click();

         }

    public String getCurrentWindow(){
        return driver.getWindowHandle();
    }

    public int languages(){
        Select languagesList = new Select(languageOnSite);
        List<WebElement> allLanguages = languagesList.getOptions();
        for(WebElement language:allLanguages){
            System.out.println(language.getText());
        }
        System.out.println("There are "+allLanguages.size()+" languages you can pick");
        int numberLanguages = allLanguages.size();
        return numberLanguages;
    }

}
