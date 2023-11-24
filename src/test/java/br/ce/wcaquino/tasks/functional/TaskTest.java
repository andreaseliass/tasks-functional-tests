package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TaskTest {

	public WebDriver acessarAplicacao() throws MalformedURLException {
	    // Set the path to your ChromeDriver executable
	    //System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\seleniumDrivers\\chromedriver.exe");
	    // Initialize ChromeDriver
	    //WebDriver driver = new ChromeDriver();
	    DesiredCapabilities cap = DesiredCapabilities.chrome();
	    WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.188:4444/wd/hub"), cap);
	    driver.navigate().to("http://192.168.1.188:8001/tasks/");
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!",message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void nãoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description",message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void nãoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date",message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void nãoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste de data passada via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past",message);
		}finally {
			driver.quit();
		}
	}
	
	

}
