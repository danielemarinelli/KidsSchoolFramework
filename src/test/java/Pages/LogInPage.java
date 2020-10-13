package Pages;


import Tests.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;

public class LogInPage extends TestBase {

    @FindBy(xpath =".//input[@id='txtUser']")
    private WebElement fieldCodiceUtente;

    @FindBy(xpath =".//input[@id='txtPassword']")
    private WebElement fieldPassword;

    @FindBy(xpath =".//input[@id='btnLogin']")
    private WebElement btnLogIn;

    @FindBy(xpath =".//input[@id='btnForgotPassword']")
    private WebElement btnForgottenPassword;

    @FindBy(xpath =".//span[@id='LblError']")
    private WebElement errorLabel;

    @FindBy(xpath =".//img[@id='IdMateriale']")
    private WebElement btnMaterialeDidattico;

    @FindBy(xpath =".//select[@id='ddlDocente']")
    private WebElement selectListOfMaterials;

    @FindBy(xpath =".//div[@role='alert']")
    private WebElement msgDisplayed;

    @FindBy(xpath =".//input[@id='btnExit']")
    private WebElement btnExit;

    @FindBy(xpath =".//div[@id='content-comunicazioni']")
    private WebElement comunicationDisplayed;

    @FindBy(xpath =".//td[contains(text(),'France')]")
    private WebElement MF;

    @FindBy(xpath =".//td[contains(text(),'Gab')]")
    private WebElement MG;

    @FindBy(xpath =".//img[@id='IdVoti']")
    private WebElement btnPagella;

    @FindBy(xpath =".//div[@role='alert']")
    private WebElement alertPagella;

    @FindBy(xpath =".//span[@id='LblError']")
    private WebElement errorInvUser;

    @FindBy(xpath =".//img[@id='IdREC']")
    private WebElement btnRegistroClasse;

    @FindBy(xpath =".//table[@class='TableRegistroClasseGenitori']/tbody")
    private WebElement tableHomeWork;


    private WebDriver driver;
    public LogInPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public String verifyCredential() throws InterruptedException {
        System.out.println("Insert credential....");
        driver.manage().window().maximize();
        fieldCodiceUtente.click();
        fieldCodiceUtente.sendKeys("11508");
        fieldPassword.click();
        Thread.sleep(2000);
        fieldPassword.sendKeys("not real pw");
        btnLogIn.click();
        String comunication = comunicationDisplayed.getText();
        return comunication;
    }

    public String askNewPassword() throws InterruptedException {
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,150)");
        System.out.println("Clicking on button PASSWORD DIMENTICATA?");
        btnForgottenPassword.click();
        scrollToEndPage();
        Thread.sleep(3000);
        String error = errorLabel.getText();
        String errorParts[] = error.split(" ");
        String secondWord = errorParts[1];
        System.out.println("#### "+error+" ####");
        return secondWord;
    }

    public void switchToLogInPage(){
        Set<String> windows = driver.getWindowHandles();
        for(String window : windows){
            driver.switchTo().window(window);
        }
    }

    public String checkMaterialeDidattico() throws InterruptedException {
        btnMaterialeDidattico.click();
        Select subjectsList = new Select(selectListOfMaterials);
        List<WebElement> allSubjects = subjectsList.getOptions();
        for(WebElement subject:allSubjects){
            System.out.println(subject.getText());
        }
        subjectsList.selectByIndex(4);
        String onVideo = msgDisplayed.getText();
        Thread.sleep(4000);
        System.out.println(onVideo);
        btnExit.click();
        return onVideo;

    }

    public String checkFrancesco(){
        MF.click();
        String name = MF.getText().trim();
        System.out.println("You picked: "+name);
        btnMaterialeDidattico.click();
        btnExit.click();
        return name;
    }

    public boolean checkGabrielesReport() throws InterruptedException {
        MG.click();
        btnPagella.click();
        Thread.sleep(3000);
        System.out.println(alertPagella.getText());
            if (alertPagella.isDisplayed()){
                btnExit.click();
                return true;
            }else
                btnExit.click();
                return false;
    }

    public void closeWindow() {
        if(driver!=null) {
            driver.close();
        }
    }

    public String invalidCredentials() throws InterruptedException {
        System.out.println("Insert invalid credential....");
        driver.manage().window().maximize();
        fieldCodiceUtente.click();
        fieldCodiceUtente.sendKeys("Fake User");
        fieldPassword.click();
        Thread.sleep(2000);
        fieldPassword.sendKeys("invalidpassword....");
        btnLogIn.click();
        String incorrectUser = errorInvUser.getText();
        System.out.println(incorrectUser);
        scrollToEndPage();
        Thread.sleep(2000);
        return incorrectUser;
    }

    public void checkRegistroClasseAle(){
            //estrarre i compiti del giorno per Alessandro!!!!!!!!!!!!!!!
        System.out.println("Check Ale's homework for the day....");
        btnRegistroClasse.click();
        List<WebElement> rowsTable = tableHomeWork.findElements(By.xpath("tr"));
            for(int i=0;i<rowsTable.size();i++){
                List<WebElement> rowChild;
                String homeworkDate="13/10/2020";
                String dateInTable;

                if(i==0){
                    rowChild = rowsTable.get(0).findElements(By.xpath("th"));
                } else{
                    rowChild = rowsTable.get(i).findElements(By.xpath("td"));
                }
                    for(int j=0; j<rowChild.size();j++){
                        if(j==0) {
                            dateInTable = rowChild.get(0).getText();
                            String onlyTheDate = dateInTable.substring(0,10);
                            if(onlyTheDate.equals(homeworkDate)) {
                                   String dayHomework = rowChild.get(2).getText();
                                   System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                   System.out.print(homeworkDate+" ----> "+dayHomework);
                                   System.out.println();
                                   System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
                            }//else
                               //System.out.println("On the selected date: "+homeworkDate+" the aren't homework to do!!");
                        }
                    }

            }
        btnExit.click();
    }

    public void checkRegistroClasseGab(){
        //estrarre i compiti del giorno per Gabriele!!!!!!!!!!!!!!!
        System.out.println("Check Gab's homework for the day....");
        MG.click();
        btnRegistroClasse.click();
        List<WebElement> rowsTable = tableHomeWork.findElements(By.xpath("tr"));
        for(int i=0;i<rowsTable.size();i++){
            List<WebElement> rowChild;
            String homeworkDate="13/10/2020";
            String dateInTable;

            if(i==0){
                rowChild = rowsTable.get(0).findElements(By.xpath("th"));
            } else{
                rowChild = rowsTable.get(i).findElements(By.xpath("td"));
            }
            for(int j=0; j<rowChild.size();j++){
                if(j==0) {
                    dateInTable = rowChild.get(0).getText();
                    String onlyTheDate = dateInTable.substring(0,10);
                    if(onlyTheDate.equals(homeworkDate)) {
                        String dayHomework = rowChild.get(2).getText();
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        System.out.print(homeworkDate+" ----> "+dayHomework);
                        System.out.println();
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    }//else
                     //   System.out.println("On the selected date: "+homeworkDate+" the aren't homework to do!!");
                }
            }

        }
        btnExit.click();
    }


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

}
