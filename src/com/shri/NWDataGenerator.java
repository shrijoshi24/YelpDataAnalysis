package com.shri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class NWDataGenerator
{
	public static void main(String[] args)
	{
		NodeXLDataGenerator ng = new NodeXLDataGenerator();
		ng.generateNWData(args);
	}
}

class NodeXLDataGenerator
{
	String filesPath;
	
	BufferedReader reviewNWBR;
	
	BufferedReader userDetailsBR;
	
	BufferedReader businessesDetailsBR;
	
	HashMap<String, UserDetails> userDetailsMap = new HashMap<String, UserDetails>();
	
	HashMap<String, BusinessesDetails> businessesDetailsMap = new HashMap<String, BusinessesDetails>();
	
	BufferedWriter nodeXLEdgesBW;
	
	void generateNWData(String[] args)
	{
		if(args[0] != null)
			filesPath = args[0];
		
		initFileHandles(filesPath);
		readFilesData(filesPath);
		populateNodeXLData();
		closeFiles();
	}
	
	private void initFileHandles(String filesPath)
	{
		File reviewsNWFile = new File(filesPath + "//" + "ReviewNW.csv");
		try
		{
			reviewNWBR = new BufferedReader(new FileReader(reviewsNWFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		File userDetailsFile = new File(filesPath + "//" + "UserDetails.csv");
		try
		{
			userDetailsBR = new BufferedReader(new FileReader(userDetailsFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		File businessesDetailsFile = new File(filesPath + "//" + "BusinessesDetails.csv");
		try
		{
			businessesDetailsBR = new BufferedReader(new FileReader(businessesDetailsFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		File nodeXLEdgesFile = new File(filesPath + "//" + "NodeXLEdges.csv");
		if(nodeXLEdgesFile.exists())
		{
			nodeXLEdgesFile.delete();
		}
		try
		{
			nodeXLEdgesBW = new BufferedWriter(new FileWriter(nodeXLEdgesFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void closeFiles()
	{
		try
		{
			reviewNWBR.close();
			userDetailsBR.close();
			businessesDetailsBR.close();
			
			nodeXLEdgesBW.flush();
			nodeXLEdgesBW.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void populateNodeXLData()
	{
		// read user details
		String eachLine;
		try
		{
			eachLine = reviewNWBR.readLine();
			while(eachLine != null)
			{
				String pair[] = eachLine.split(",");
				String userID = pair[0];
				String businessID = pair[1];
				
				UserDetails ud = userDetailsMap.get(userID);
				BusinessesDetails bd = businessesDetailsMap.get(businessID);
				if((ud != null) && (bd != null))
				{
					String userName = ud.getName();
					String businessName = bd.getName();
					int userReviewCount = 0, businesReviewCount = 0;
					try
					{
						//Ranges from 0 to 5,401
						userReviewCount = Integer.parseInt(ud.getReview_count());
						
						//Ranges from 2 to 2874
						businesReviewCount = Integer.parseInt(bd.getReview_count());
					}
					catch (NumberFormatException ex)
					{
						ex.printStackTrace();
					}
					if(userReviewCount >= 1000 && businesReviewCount >= 170)
					{
						String edgeLine = "U__"+userName + "," + "B__"+businessName;
						nodeXLEdgesBW.write(edgeLine);
						nodeXLEdgesBW.newLine();
					}
				}
				eachLine = reviewNWBR.readLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void readFilesData(String filesPath)
	{
		// read user details
		String eachLine;
		try
		{
			eachLine = userDetailsBR.readLine();
			eachLine = userDetailsBR.readLine();
			while(eachLine != null)
			{
				String userArr[] = eachLine.split(",");
				String userID = userArr[1];
				UserDetails ud = new UserDetails(userArr[0],userArr[2],userArr[3],userArr[4],userArr[5],userArr[6]);
				userDetailsMap.put(userID, ud);
				eachLine = userDetailsBR.readLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// read business details
		try
		{
			eachLine = businessesDetailsBR.readLine();
			eachLine = businessesDetailsBR.readLine();
			while(eachLine != null)
			{
				String businessArr[] = eachLine.split(",");
				String businessID = businessArr[0];
				BusinessesDetails bd =
					new BusinessesDetails(businessArr[1], businessArr[2], businessArr[3],
						businessArr[4], businessArr[5], businessArr[6], businessArr[7],
						businessArr[8], businessArr[9], businessArr[10], businessArr[11],
						businessArr[12], businessArr[13], businessArr[14], businessArr[15]);
				businessesDetailsMap.put(businessID, bd);
				eachLine = businessesDetailsBR.readLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	class BusinessesDetails
	{
		private String full_address;
		private String schools;
		private String open;
		private String categories;
		private String photo_url;
		private String city;
		private String review_count;
		private String name;
		private String neighborhoods;
		private String url;
		private String longitude;
		private String state;
		private String stars;
		private String latitude;
		private String type;
		
		public BusinessesDetails( String full_address, String schools, String open, String categories,
		 String photo_url, String city, String review_count, String name, String neighborhoods,
		 String url, String longitude, String state, String stars, String latitude, String type)
		{
			 this.full_address = full_address;
			 this.schools= schools;
			 this.open = open;
			 this.categories = categories;
			 this.photo_url = photo_url;
			 this.city = city;
			 this.review_count = review_count;
			 this.name = name;
			 this.neighborhoods = neighborhoods;
			 this.url = url;
			 this.longitude = longitude;
			 this.state = state;
			 this.stars = stars;
			 this.latitude = latitude;
			 this.type = type;
		}

//		public String getFull_address()
//		{
//			return full_address;
//		}
//
//		public String getSchools()
//		{
//			return schools;
//		}
//
//		public String getOpen()
//		{
//			return open;
//		}
//
//		public String getCategories()
//		{
//			return categories;
//		}
//
//		public String getPhoto_url()
//		{
//			return photo_url;
//		}
//
//		public String getCity()
//		{
//			return city;
//		}
//
		public String getReview_count()
		{
			return review_count;
		}

		public String getName()
		{
			return name;
		}

//		public String getNeighborhoods()
//		{
//			return neighborhoods;
//		}
//
//		public String getUrl()
//		{
//			return url;
//		}
//
//		public String getLongitude()
//		{
//			return longitude;
//		}
//
//		public String getState()
//		{
//			return state;
//		}
//
//		public String getStars()
//		{
//			return stars;
//		}
//
//		public String getLatitude()
//		{
//			return latitude;
//		}
//
//		public String getType()
//		{
//			return type;
//		}

	}
	
	class UserDetails
	{
		private String votes;
//		private String user_id;
		private String name;
		private String url;
		private String average_stars;
		private String review_count;
		private String type;
		
		public UserDetails(String votes, String name, String url, String average_stars, String review_count, String type)
		{
			this.votes = votes;
			this.name = name;
			this.url = url;
			this.average_stars = average_stars;
			this.review_count = review_count;
			this.type = type;
		}

//		public String getVotes()
//		{
//			return votes;
//		}
//

		public String getName()
		{
			return name;
		}

//		public String getUrl()
//		{
//			return url;
//		}
//
//		public String getAverage_stars()
//		{
//			return average_stars;
//		}
//
		public String getReview_count()
		{
			return review_count;
		}
//
//		public String getType()
//		{
//			return type;
//		}
		
	}
	
}