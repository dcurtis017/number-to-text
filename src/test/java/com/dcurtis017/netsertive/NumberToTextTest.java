package com.dcurtis017.netsertive;

import java.util.regex.Matcher;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumberToTextTest {
	private ConvertNumber ntt;
	@BeforeTest
	public void setUp() throws Exception 
	{
		ntt = new ConvertNumber();
	}
	
	@Test(expectedExceptions = NumberFormatException.class, enabled=false)
	public void testIsInvalidNumberNaN()
	{
		ntt.validateNumber("ababa");
	}	
	
	@Test(expectedExceptions = NumberFormatException.class, enabled=false)
	public void testIsInvalidMinusSignInNumber()
	{
		ntt.validateNumber("1-525");
	}
	
	@Test(expectedExceptions = NumberFormatException.class, enabled=false)
	public void testIsInvalidCharacters()
	{
		ntt.validateNumber("1.250");
	}
	
	@Test
	public void testIsValidNumberHasCommas()
	{
		Matcher m = ntt.validateNumber("1,000");
		Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testIsValidNumberNoCommas()
	{
		Matcher m = ntt.validateNumber("12000");
		Assert.assertTrue(m.matches());		
	}
	
	@Test
	public void testIsValidNumberHasNegative()
	{
		Matcher m = ntt.validateNumber("-978,040");
		Assert.assertTrue(m.matches());		
	}
	
	@DataProvider(name = "zero-cases")
	public static Object[][] zeroCases()
	{
		return new Object[][]
				{
					{"0,000", "zero"},
					{"0", "zero"}
				};
	}	
	
	@Test(dataProvider = "zero-cases")
	public void testZeroCases(String param, String expected){
	}
	
	@DataProvider(name = "partial-conversions")
	public static Object[][] partialConvsersions()
	{
		return new Object[][]
				{
					{"999", "nine hundred ninety nine"},
					{"101", "one hundred one"},
					{"12", "twelve"},
					{"7", "seven"},
					{"300", "three hundred"}
				};
	}
	
	@Test(dataProvider = "partial-conversions")
	public void testPartialConvertions(String param, String expected)
	{
		Assert.assertTrue(ntt.convertPartToWord(param).equals(expected));
	}

	@DataProvider(name = "full-conversions")
	public static Object[][] fullConvsersions()
	{
		return new Object[][]
				{
					{"1001", "one thousand one"},
					{"999221", "nine hundred ninety nine thousand two hundred twenty one"},
					{"857120", "eight hundred fifty seven thousand one hundred twenty"},
					{"102,000", "one hundred two thousand"},
					{"9", "nine"},
					{"15", "fifteen"},
					{"17", "seventeen"},
					{"13425", "thirteen thousand four hundred twenty five"},
					{"6890", "six thousand eight hundred ninety"},
					{"401", "four hundred one"},
					{"700", "seven hundred"},
					{"2000", "two thousand"},
					{"499", "four hundred ninety nine"},
					{"-1001", "negative one thousand one"},
					{"-999221", "negative nine hundred ninety nine thousand two hundred twenty one"},
					{"-857120", "negative eight hundred fifty seven thousand one hundred twenty"},
					{"-102,000", "negative one hundred two thousand"},
					{"-9", "negative nine"},
					{"-15", "negative fifteen"},
					{"-17", "negative seventeen"},
					{"-13425", "negative thirteen thousand four hundred twenty five"},
					{"-6890", "negative six thousand eight hundred ninety"},
					{"-401", "negative four hundred one"},
					{"-700", "negative seven hundred"},
					{"-2000", "negative two thousand"},
					{"-499", "negative four hundred ninety nine"},
					{"1", "one"},
					{"10", "ten"},
					{"20", "twenty"},
					{"30", "thirty"},
					{"40", "fourty"},
					{"50", "fifty"},
					{"60", "sixty"},
					{"70", "seventy"},
					{"80", "eighty"},
					{"90", "ninety"},
					{"94", "ninety four"},
					{"11", "eleven"},
					{"12", "twelve"},
					{"13", "thirteen"},
					{"14", "fourteen"},
					{"15", "fifteen"},
					{"16", "sixteen"},
					{"17", "seventeen"},
					{"18", "eighteen"},
					{"19", "nineteen"},
				};
		//add negative numbers as well as all tys and teens
	}
	
	@Test(dataProvider = "full-conversions")
	public void testFullConvertions(String param, String expected)
	{
		Assert.assertTrue(ntt.convertToWord(param).equals(expected));
	}
}
