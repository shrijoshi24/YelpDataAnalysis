package com.shri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.json.exceptions.JSONParsingException;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class YelpQuickJSON
{
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		QuickJSONParseYelpData pyd = new QuickJSONParseYelpData();
		pyd.readJSONFile(args);
		long end = System.currentTimeMillis();
		System.out.println("Total time taken: " + (end - start)/1000 + " seconds");
	}
}

class QuickJSONParseYelpData
{
	private BufferedWriter userBW;

	private BufferedWriter reviewBW;

	private BufferedWriter businessBW;
	
	private BufferedWriter reviewNWBW;

	void readJSONFile(String[] args)
	{
		String jsonFilePath = "", outFilesPath = "";
		if(args.length > 0)
		{
			jsonFilePath = args[0];
			outFilesPath = args[1];
		}
		createOutputFiles(outFilesPath);
		BufferedReader jsonFileBR;
		String eachLine = "";
		int lineNo = 1;
		try
		{
			jsonFileBR = new BufferedReader(new FileReader(new File(jsonFilePath)));
			eachLine = jsonFileBR.readLine();
			while(eachLine != null)
			{
				JsonParserFactory factory = JsonParserFactory.getInstance();
				JSONParser parser = factory.newJsonParser();
//				System.out.println("Line No.: " + lineNo);
				Map jsonDataMap = null;
				try
				{
					jsonDataMap = parser.parseJson(eachLine);
				}
				catch(JSONParsingException ex)
				{
//					System.out.println("Line No.: " + lineNo);
//					ex.printStackTrace();
				}
				if(jsonDataMap != null)
				{
					String value = (String) jsonDataMap.get("type");
					if("review".equalsIgnoreCase(value))
					{
						getReviewDetails(jsonDataMap, lineNo);
					}
					else if("business".equalsIgnoreCase(value))
					{
						getBusinessDetails(jsonDataMap, lineNo);
					}
					else if("user".equalsIgnoreCase(value))
					{
						getUserDetails(jsonDataMap, lineNo);
					}
					else
					{
						System.out.println("Some other type!!!");
						System.out.println("Line No.: " + lineNo);
					}
				}
				++lineNo;
				eachLine = jsonFileBR.readLine();
			}
			jsonFileBR.close();
			userBW.flush();
			userBW.close();
			reviewBW.flush();
			reviewBW.close();
			businessBW.flush();
			businessBW.close();
			reviewNWBW.flush();
			reviewNWBW.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void getBusinessDetails(Map jsonDataMap, int lineNo)
	{
		StringBuilder eachLine = new StringBuilder();
		try
		{
			// String separator = System.getProperty( "line.separator" );
			String text = "";
			text = (String) jsonDataMap.get("business_id");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("full_address");
			// text = text.replace(separator, " ");
			text = text.replace("\\n", " ");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			ArrayList<String> schoolsArray = (ArrayList<String>) jsonDataMap.get("schools");
			for(int i = 0; i < schoolsArray.size(); ++i)
			{
				eachLine.append(schoolsArray.get(i));
				if(i < schoolsArray.size() - 1)
					eachLine.append("#");
			}
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("open");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			jsonDataMap.get("categories");
			ArrayList<String> categoriesArray = (ArrayList<String>) jsonDataMap.get("categories");
			for(int i = 0; i < categoriesArray.size(); ++i)
			{
				text = categoriesArray.get(i);
				text = text.replace(",", "*");
				eachLine.append(text);
				if(i < categoriesArray.size() - 1)
					eachLine.append("#");
			}
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("photo_url");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("city");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("review_count");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("name");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			jsonDataMap.get("neighborhoods");
			ArrayList<String> neighborhoodsArray = (ArrayList<String>) jsonDataMap.get("neighborhoods");
			for(int i = 0; i < neighborhoodsArray.size(); ++i)
			{
				text = neighborhoodsArray.get(i);
				text = text.replace(",", "*");
				eachLine.append(text);
				if(i < neighborhoodsArray.size() - 1)
					eachLine.append("#");
			}
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("url");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("longitude");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("state");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("stars");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("latitude");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("type");
			text = text.replace(",", "*");
			eachLine.append(text);
			// eachLine.append(",");
			
			businessBW.write(eachLine.toString());
			businessBW.newLine();
		}
		catch(IOException ex)
		{
			System.out.println("Line No.: " + lineNo);
			ex.printStackTrace();
		}
	}

	private void getReviewDetails(Map jsonDataMap, int lineNo)
	{
		StringBuilder eachLine = new StringBuilder();
		StringBuilder reviewNWLine = new StringBuilder();
		try
		{
			// String separator = System.getProperty( "line.separator" );
			HashMap votesMap = (HashMap) jsonDataMap.get("votes");
			eachLine.append("funny:" + votesMap.get("funny"));
			eachLine.append("#");
			eachLine.append("useful:" + votesMap.get("useful"));
			eachLine.append("#");
			eachLine.append("cool:" + votesMap.get("cool"));
			eachLine.append(",");
			
			String text = (String) jsonDataMap.get("user_id");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			//Also add userID to ReviewNW csv for Vertex1, Vertex2.
			reviewNWLine.append(text);
			reviewNWLine.append(",");
			
			text = (String) jsonDataMap.get("review_id");
			text = text.replace(",", "*");
			String reviewID = text;
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("stars");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("date");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("text");
			// text = text.replace(separator, " ");
			text = text.replace("\\n", "");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("type");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("business_id");
			text = text.replace(",", "*");
			eachLine.append(text);
			// eachLine.append(",");
			reviewNWLine.append(text);
			
			
			reviewBW.write(eachLine.toString());
			reviewBW.newLine();
			
			reviewNWLine.append(",");
			reviewNWLine.append(reviewID);
			reviewNWBW.write(reviewNWLine.toString()); //userID,businessID,reviewID
			reviewNWBW.newLine();
		}
		catch(IOException ex)
		{
			System.out.println("Line No.: " + lineNo);
			ex.printStackTrace();
		}
	}

	private void getUserDetails(Map jsonDataMap, int lineNo)
	{
		StringBuilder eachLine = new StringBuilder();
		try
		{
			HashMap votesMap = (HashMap) jsonDataMap.get("votes");
			eachLine.append("funny:" + votesMap.get("funny"));
			eachLine.append("#");
			eachLine.append("useful:" + votesMap.get("useful"));
			eachLine.append("#");
			eachLine.append("cool:" + votesMap.get("cool"));
			eachLine.append(",");
			
			String text = (String) jsonDataMap.get("user_id");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("name");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("url");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("average_stars");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("review_count");
			text = text.replace(",", "*");
			eachLine.append(text);
			eachLine.append(",");
			
			text = (String) jsonDataMap.get("type");
			text = text.replace(",", "*");
			eachLine.append(text);
			// eachLine.append(",");
			
			userBW.write(eachLine.toString());
			userBW.newLine();
		}
		catch(IOException ex)
		{
			System.out.println("Line No.: " + lineNo);
			ex.printStackTrace();
		}
	}

	private void createOutputFiles(String outFilesPath)
	{
		File outputDir = new File(outFilesPath);
		if(outputDir.exists() && outputDir.isDirectory())
		{
			File userFile = new File(outFilesPath + "//" + "UserDetails.csv");
			if(userFile.exists())
			{
				userFile.delete();
			}
			try
			{
				userBW = new BufferedWriter(new FileWriter(userFile));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				userBW.write("votes,user_id,name,url,average_stars,review_count,type");
				userBW.newLine();
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			File reviewsFile = new File(outFilesPath + "//" + "ReviewsDetails.csv");
			if(reviewsFile.exists())
			{
				reviewsFile.delete();
			}
			try
			{
				reviewBW = new BufferedWriter(new FileWriter(reviewsFile));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				reviewBW.write("votes,user_id,review_id,stars,date,text,type,business_id");
				reviewBW.newLine();
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			File businessesFile = new File(outFilesPath + "//" + "BusinessesDetails.csv");
			if(businessesFile.exists())
			{
				businessesFile.delete();
			}
			try
			{
				businessBW = new BufferedWriter(new FileWriter(businessesFile));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				businessBW
					.write("business_id,full_address,schools,open,categories,photo_url,city,review_count,name,neighborhoods,url,longitude,state,stars,latitude,type");
				businessBW.newLine();
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			
			File reviewsNWFile = new File(outFilesPath + "//" + "ReviewNW.csv");
			if(reviewsNWFile.exists())
			{
				reviewsNWFile.delete();
			}
			try
			{
				reviewNWBW = new BufferedWriter(new FileWriter(reviewsNWFile));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
