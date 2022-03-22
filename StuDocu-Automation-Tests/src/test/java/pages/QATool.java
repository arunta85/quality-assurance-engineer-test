package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.WebDriverClass;

/**
 * This class is designed based on Page Object Model. Page Object Model (POM) is
 * a design pattern, popularly used in test automation that creates Object
 * Repository for web UI elements. The advantage of the model is that it reduces
 * code duplication and improves test maintenance.
 * 
 * Under this model, for each web page in the application, there should be a
 * corresponding Page Class. This Page class will identify the WebElements of
 * that web page and also contains Page methods which perform operations on
 * those WebElements
 * 
 * @author Arun
 *
 */
public class QATool {
	//Xpath and expected UI text is maintained in the class
	String awesomeQAToolxpath = "//header/h1";
	String awesomeQAToolText = "The awesome Q/A tool";

	String createdQuestionsXpath = "//h2[@class='tooltipped-title__title']";
	String createdQuestionsText = "Created questions";

	String sortQuestionsBtnXpath = "//button[@class='btn btn-primary']";
	String sortQuestionsBtnText = "Sort questions";

	String removeQuestionsBtnXpath = "//button[@class='btn btn-danger']";
	String removeQuestionsBtnText = "Remove questions";

	String createnewQuestionXpath = "//h2[@class='tooltipped-title__title']";
	String createnewQuestionText = "Create a new question";

	String questionsXpath = "//label[@for='question']";
	String questionsText = "Question";

	String answerXpath = "//label[@for='answer']";
	String answerText = "Answer";

	String createquestionButton = "//button[@type='submit']";
	String createquestionTextBoxXpath = "//div[@class='question__question']";
	By questionInputBoxID = By.id("question");
	By answerInputBoxID = By.id("answer");
	String listOfQuestions = "//ul[@class='list-group list-group-flush']/li";
	String noofQuestionsXpath = "//div[@class='sidebar']";
	String noquestionsYetXpath = "//div[@class='alert alert-danger']";
	String noquestionsYetText = "No questions yet :-(";
	//Singleton driver instance and WebDriverClass instance is initialize 
	WebDriver driver = WebDriverClass.getWebDriverInstance().getWebDriver();
	WebDriverClass webDriverClass = WebDriverClass.getWebDriverInstance();

	/**
	 * This test method verifies UI text of the webpage/
	 * verifyTextIntheWebElement method accepts xpath and expectedUIText as paramenter and perform the validation
	 */
	@Test(priority = 1)
	public void testVerifyUI() {
		webDriverClass.verifyTextIntheWebElement(awesomeQAToolxpath, awesomeQAToolText);
		webDriverClass.verifyTextIntheWebElement(createdQuestionsXpath, createdQuestionsText);
		webDriverClass.verifyTextIntheWebElement(sortQuestionsBtnXpath, sortQuestionsBtnText);
		webDriverClass.verifyTextIntheWebElement(removeQuestionsBtnXpath, removeQuestionsBtnText);
		webDriverClass.verifyTextIntheWebElement(createnewQuestionXpath, createnewQuestionText);
		webDriverClass.verifyTextIntheWebElement(questionsXpath, questionsText);
		webDriverClass.verifyTextIntheWebElement(answerXpath, answerText);

	}

	/**
	 * This method creates 3 questions with answers. While creating each question, it verifies whether question
	 * is displayed in the webpage.
	 */
	@Test(priority = 2)
	public void testCreateQuestions() {
		String q1 = "C-Is this my first question?";
		String a1 = "Yes. This is my first question";
		String q2 = "B-Is this your second question?";
		String a2 = "Yes. This is my second question";
		String q3 = "A-Is this your third question?";
		String a3 = "Yes. This is my third question";
		//Click on "Remove Questions" button
		webDriverClass.clickOnWebElement(removeQuestionsBtnXpath);
		//Verify "No questions yet" message is displayed"
		webDriverClass.verifyTextIntheWebElement(noquestionsYetXpath, noquestionsYetText);
		//Creates first question (q1) with answer (a1)
		createQuestionsAndAnswers(q1, a1);
		//Creates second question (q2) with answer (a2)
		createQuestionsAndAnswers(q2, a2);
		//Creates third question (q3) with answer (a3)
		createQuestionsAndAnswers(q3, a3);
		//Finds the total no of questions
		int noOfQuestionsint = driver.findElements(By.xpath(listOfQuestions)).size();
		//Construct the UI text with total number of questions information
		String expectedText = "Here you can find " + noOfQuestionsint
				+ " questions. Feel free to create your own questions!";
		//Gets the actual UI text
		String actualText = webDriverClass.getWebElement(noofQuestionsXpath).getText();
		//Verify whether expected text is displayed
		Assert.assertEquals(actualText, expectedText);
	}
	
	/**
	 * This method verifies the sorting functionality.
	 * Prerequisite of this test is to create multiple questions with answers which was performed in the
	 * previous test i.e testCreateQuestions
	 * Step:
	 * 1. An ArrayList object is created and all questions are added in the ArrayList
	 * 2. Programmatically sort the ArrayList instance
	 * 3. Click on "Sort Questions" button
	 * 4. Creates another ArrayList (aftersort)
	 * 5. Get the sorted questions from the text box and add the questions in the ArrayList instance created in the step 4
	 * 6. Compare both the ArrayList instance (step 2 and step 5)
	 */
	@Test(priority = 3)
	public void testSortQuestions() {
		//All questions are added in the beforesort instance
		ArrayList<String> beforesort = new ArrayList<String>();
		List<WebElement> listOfElements = driver.findElements(By.xpath(listOfQuestions));
		for (WebElement element : listOfElements) {
			element.click();
			String actualQuestion = element.findElements(By.tagName("div")).get(0).getText();
			beforesort.add(actualQuestion);
		}
		//Programmatically sort the ArrayList instance
		Collections.sort(beforesort);
		ArrayList<String> aftersort = new ArrayList<String>();
		//Click on "Sort Questions" button
		webDriverClass.clickOnWebElement(sortQuestionsBtnXpath);
		//After Sorting- All questions are added in the aftersort instance
		List<WebElement> listOfElementsAfterSort = driver.findElements(By.xpath(listOfQuestions));
		for (WebElement element : listOfElementsAfterSort) {
			element.click();
			String actualQuestion = element.findElements(By.tagName("div")).get(0).getText();
			aftersort.add(actualQuestion);
		}
		//beforsort and aftersort ArrayList is compared
		boolean sortresult = beforesort.equals(aftersort);
		webDriverClass.assertTrue(sortresult, "Verify whether question is sorted");
		driver.close();
	}
	
	/**
	 * This method creates new question with answer
	 * @param question
	 * @param answer
	 */
	public void createQuestionsAndAnswers(String question, String answer) {
		//writes the question in Question input box
		webDriverClass.sendKeysOnID(questionInputBoxID, question);
		//writes the answer in Answer input box
		webDriverClass.sendKeysOnID(answerInputBoxID, answer);
		//Click on "Create Question" button
		webDriverClass.getWebElement(createquestionButton).click();
		//Click on "Created Questions" text box
		webDriverClass.getWebElement(createquestionTextBoxXpath).click();
		//Logic to determine whether question and answer is displayed in the text box
		boolean questionValidation = false;
		boolean answerValidation = false;
		List<WebElement> listOfElements = driver.findElements(By.xpath(listOfQuestions));
		for (WebElement element : listOfElements) {
			element.click();
			String actualQuestion = element.findElements(By.tagName("div")).get(0).getText();
			String actualAnswer = element.findElements(By.tagName("p")).get(0).getText();

			if (actualQuestion.equals(question)) {
				questionValidation = true;
			}

			if (actualAnswer.equals(answer)) {
				answerValidation = true;
			}

		}
		webDriverClass.assertTrue(questionValidation, "Verify " + question + " is displayed");
		webDriverClass.assertTrue(answerValidation, "Verify " + answer + " is displayed");
	}
}
