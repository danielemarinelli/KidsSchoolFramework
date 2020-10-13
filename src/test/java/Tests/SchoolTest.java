package Tests;

import Pages.InitialPage;
import Pages.LogInPage;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SchoolTest extends TestBase{
    InitialPage initPage;
    LogInPage login;


    @Test(priority=2)
    public void verifyCorrectCredentials() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        System.out.print("LogIn Page....");
        String primaryWindow = initPage.getCurrentWindow();
        login = new LogInPage(driver());
        login.switchToLogInPage();
        String com = login.verifyCredential();
        Assert.assertEquals(com,"Selezionare prima un Alunno, poi selezionare i dati da visualizzare cliccando su una delle icone sopra");
        login.checkMaterialeDidattico();
        reporter().report(LogStatus.PASS,
                "Checking correct credentials",
                "Insert valid Credentials Test is Successfull");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
        //driver().close();
    }

    @Test(priority=1)
    public void verifyForgottenPasswordButton() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        String primaryWindow = initPage.getCurrentWindow();
        login = new LogInPage(driver());
        login.switchToLogInPage();
        String msgword = login.askNewPassword();
        Assert.assertEquals(msgword,"FALLITA","Incorrect page");
        reporter().report(LogStatus.PASS,
                "Checking forgotten password button",
                "Verify button 'PASSWORD DIMENTICATA?' Test is Successfull");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
        //driver().close();
    }

    @Test(priority=3)
    public void verifyStudentName() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        login = new LogInPage(driver());
        String primaryWindow = initPage.getCurrentWindow();
        login.switchToLogInPage();
        login.verifyCredential();
        String studentFrancesco = login.checkFrancesco();
        Assert.assertEquals(studentFrancesco,"Marinelli Francesco","Incorrect student name");
        reporter().report(LogStatus.PASS,
                "Checking student name",
                "Verify button 'MARINELLI FRANCESCO' Test is Successfull");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
        //driver().close();
    }

    @Test(priority=4)
    public void checkGabReport() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        login = new LogInPage(driver());
        String primaryWindow = initPage.getCurrentWindow();
        login.switchToLogInPage();
        login.verifyCredential();
        boolean obj = login.checkGabrielesReport();
        Assert.assertTrue(obj);
        reporter().report(LogStatus.PASS,
                "Checking Gabriele's report",
                "Verify button 'PAGELLA' Test is Successfull");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
        //driver().close();
    }

    @Test(priority=5)
    public void verifyInvalidCredentials() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        login = new LogInPage(driver());
        String primaryWindow = initPage.getCurrentWindow();
        login.switchToLogInPage();
        String errorSentence = login.invalidCredentials();
        Assert.assertEquals(errorSentence,"Utente non presente o password errata, verifica di accedere nel RE della tua scuola.","Incorrect Page");
        reporter().report(LogStatus.PASS,
                "Checking Invalid Credentials",
                "Verify invalid credentials Test is Successfull");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
        //driver().close();
    }

    @Test(priority=6)
    public void verifyHomeWorkAle() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        login = new LogInPage(driver());
        String primaryWindow = initPage.getCurrentWindow();
        login.switchToLogInPage();
        login.verifyCredential();
        login.checkRegistroClasseAle();
        reporter().report(LogStatus.PASS,
                "Checking Alessandro's homework",
                "Verify Ale's homework on the selected date Test");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
    }

    @Test(priority=7)
    public void verifyHomeWorkGab() throws InterruptedException {
        initPage = new InitialPage(driver());
        initPage.registroElettonicoInput();
        login = new LogInPage(driver());
        String primaryWindow = initPage.getCurrentWindow();
        login.switchToLogInPage();
        login.verifyCredential();
        login.checkRegistroClasseGab();
        reporter().report(LogStatus.PASS,
                "Checking Gabriele's homework",
                "Verify Gab's homework on the selected date Test");
        login.closeWindow();
        driver().switchTo().window(primaryWindow);
    }

    @Test(priority=8)
    public void verifyLanguagesOnSite() throws InterruptedException {
        initPage = new InitialPage(driver());
        int totalLanguages = initPage.languages();
        Assert.assertEquals(totalLanguages,76,"The languages number isn't correct");
        reporter().report(LogStatus.PASS,
                "Checking how many languages on site",
                "Verify total number ot languages Test");
    }

}
