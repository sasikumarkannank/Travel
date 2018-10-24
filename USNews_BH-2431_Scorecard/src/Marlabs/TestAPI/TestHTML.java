package Marlabs.TestAPI;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

public class TestHTML {

	public static void main(String[] args) {
		
		try {
			System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");
			WebDriver driver= new ChromeDriver();
			//HtmlUnitDriver driver = new HtmlUnitDriver();
			driver.get("https://health-uat3.usnews.com/best-hospitals/area/mo/childrens-mercy-hospitals-and-clinics-6630340");
			Thread.sleep(2000);
			
			//driver.setJavascriptEnabled(true);
			WebElement element= driver.findElement(By.xpath("(//a[@href='#children-rankings'])[1]"));
			Thread.sleep(10000);
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		
			Thread.sleep(2000);
			//driver.switchTo().defaultContent();)
			
			//System.out.println(driver.getPageSource());
			WebElement ele= driver.findElement(By.xpath("//h4[contains(text(),'Nationally Ranked')]"));
			String Actual_Value1=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", ele);
			System.out.println(Actual_Value1);
			
			
			
			List<WebElement> elemnt= driver.findElements(By.xpath("//div[@class='tooltipster-content']/div[@class='flex-row']"));
			
			Iterator<?> it=elemnt.iterator();
			
			while(it.hasNext()) {
				String Actual_Value=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", it.next());
				System.out.println(Actual_Value);
			}
			
			
			System.out.println(elemnt.size());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
