package com.dcurtis017.netsertive;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertNumber {
	private Pattern regex_pattern;
	private HashMap<Character, String> singleDigit;
	
	ConvertNumber()
	{
		this.regex_pattern = Pattern.compile("^-?(\\d{1,3})(?:,?(\\d{1,3})?)$");
		this.singleDigit = new HashMap<Character, String>();
		this.singleDigit.put('1', "one");
		this.singleDigit.put('2', "two");
		this.singleDigit.put('3', "three");
		this.singleDigit.put('4', "four");
		this.singleDigit.put('5', "five");
		this.singleDigit.put('6', "six");
		this.singleDigit.put('7', "seven");
		this.singleDigit.put('8', "eight");
		this.singleDigit.put('9', "nine");
	}
	
	protected Matcher validateNumber(String number) throws NumberFormatException
	{
		Matcher m = this.regex_pattern.matcher(number);
		if (!m.matches())
		{
			throw new NumberFormatException("The number " + number + " does not match the format '[-]ddd[,ddd]'");
		}
		return m;
	}
	
	protected String convertPartToWord(String number)
	{
		String paddedNumber = org.apache.commons.lang3.StringUtils.leftPad(number, 3, "0");
		if(paddedNumber.equals("000"))
		{
			return "zero";
		}
		StringBuilder sb = new StringBuilder();
		//hundreds place
		if(paddedNumber.charAt(0) != '0' )
		{
			sb.append(this.singleDigit.get(paddedNumber.charAt(0)));
			sb.append(" hundred ");
		}
		//tens place
		switch(paddedNumber.charAt(1))
		{
			case '1':
			case '0':
				break;
			case '2':
				sb.append("twenty");
				sb.append(" ");
				break;
			case '3':
				sb.append("thirty");
				sb.append(" ");
				break;
			case '5':
				sb.append("fifty");
				sb.append(" ");
				break;
			case '8':
				sb.append("eighty");
				sb.append(" ");
				break;
			default:
				sb.append(this.singleDigit.get(paddedNumber.charAt(1)));
				sb.append("ty ");
				break;
		}
		//ones place
		if(paddedNumber.charAt(1) == '1')
		{
			switch(paddedNumber.charAt(2))
			{
				case '0':
					sb.append("ten");
					break;
				case '1':
					sb.append("eleven");
					break;
				case '2':
					sb.append("twelve");
					break;
				case '3':
					sb.append("thirteen");
					break;
				case '5':
					sb.append("fifteen");
					break;
				case '8':
					sb.append("eighteen");
					break;
				default:
					sb.append(this.singleDigit.get(paddedNumber.charAt(2)));
					sb.append("teen");
					break;					
			}
		}
		else if(paddedNumber.charAt(2) != '0')
		{
			sb.append(this.singleDigit.get(paddedNumber.charAt(2)));
		}
		
		return sb.toString().trim();
	}
	
	public String convertToWord(String number) throws NumberFormatException
	{
		StringBuilder sb = new StringBuilder();
		String paddedNumber;
		if ( number.charAt(0) == '-')
		{
			sb.append("negative ");
			paddedNumber = org.apache.commons.lang3.StringUtils.leftPad(number.substring(1), 6, "0");
		}
		else
		{
			paddedNumber = org.apache.commons.lang3.StringUtils.leftPad(number, 6, "0");
		}
		//pad the number first
		
		//since I'm removing the 
		Matcher m = this.validateNumber(paddedNumber);
		//at most there will be two matched groups started at index 1
		int start = (paddedNumber.substring(0, 3).equals("000"))?2:1;
		for(int i = start; i < 3; ++i)
		{
			try{
				String convertedNumber = convertPartToWord(m.group(i));				
				if (convertedNumber.equals("zero"))
				{
					if(i == 1)
					{
						sb.append(convertedNumber);
					}
					break;
				}
				sb.append(convertedNumber);
				if (i == 1)
				{
					sb.append(" thousand ");
				}
			}
			catch(Exception e )
			{
				
			}
		}
		return sb.toString().trim();
	}

	public static void main(String[] args) throws Exception{
		ConvertNumber cn = new ConvertNumber();
		try{
			System.out.println(cn.convertToWord(args[0]));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}        	
	}

}
