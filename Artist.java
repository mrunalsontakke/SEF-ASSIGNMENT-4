package sef_assignment4;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Artist {
	
	private String ID;
	private String Name;
	private String Address; 
	private String Birthdate;
	private String Bio; 
	private ArrayList <String> Occupations; // like singer, songwriter, composer
	private ArrayList <String> Genres;// like rock, jazz, blues, pop,classical
	private ArrayList <String> Awards;
	
	public Artist(String id, String name, String address, String birthdate, String bio, 
	ArrayList <String> occupations, ArrayList <String> genres, ArrayList <String> awards)
	{
			ID=id;
			Name=name;
			Address=address;
			Birthdate=birthdate;
			Bio=bio;
			Occupations=occupations;
			Genres=genres;
			Awards=awards;
	}
	

	public Boolean addArtist(String ID, String Name, String Address, String Birthdate, String Bio, ArrayList<String> occupations, ArrayList<String> genres, ArrayList<String> awards)
	{
		// Checking if the artist's information meets the defined conditions
	    String validationResult = ValidArtistInfo();

		
	    if (validationResult.equals("Artist information is valid.")) {
	        // Append the artist's information to a TXT file
	    	try {
	    		 BufferedWriter writer = new BufferedWriter(new FileWriter("artists.txt", true));
	             writer.write("ID: " + ID);
	             writer.newLine();
	             writer.write("Name: " + Name);
	             writer.newLine();
	             writer.write("Address: " + Address);
	             writer.newLine();
	             writer.write("Birthdate: " + Birthdate);
	             writer.newLine();
	             writer.write("Bio: " + Bio);
	             writer.newLine();
	             writer.write("Occupations: " + String.join(", ", Occupations));
	             writer.newLine();
	             writer.write("Genres: " + String.join(", ", Genres));
	             writer.newLine();
	             writer.write("Awards:");
	             for (String award : Awards) {
	                writer.write(award);
	             }
	             writer.newLine();
	             writer.newLine(); // Add an empty line to separate artist entries
	             writer.close();        
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    return true; 
	    } else {
	    return false;
	    }
      }
		 	
	private String ValidArtistInfo() {
		if (!ValidArtistId(ID)) {
	        return "Artist ID is not valid.";
	    }

	    // Validating birthdate format (DD-MM-YYYY)
	    String birthdatePattern = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d{4})$";
	    if (!Birthdate.matches(birthdatePattern)) {
	        return "Birthdate is not in the correct format (DD-MM-YYYY).";
	    }
	    
	    // Validating address format (City|State|Country)
	    String addressPattern = "^[A-Za-z]+\\|[A-Za-z]+\\|[A-Za-z]+$";
	    if (!Address.matches(addressPattern)) {
	        return "Address is not in the correct format (City|State|Country).";
	    }

	    // Bio should be between 10 to 30 words
	    int bioWordCount = Bio.split("\\s+").length;
	    if (bioWordCount < 10 || bioWordCount > 30) {
	        return "Bio should be between 10 to 30 words.";
	    }

	    // 	Validating occupation, Artist should have at least one occupation and a maximum of five
	    if (Occupations.isEmpty() || Occupations.size() > 5) {
	        return "An artist should have at least one occupation and a maximum of five.";
	    }
	    
	    // An artist can have zero to a maximum of three awards
	    // Each award format: Year, Title (Title should be between 4 to 10 words)
	    for (String award : Awards) {
	        String[] parts = award.split(",");
	        if (parts.length != 2) {
	            return "Awards should be in the format Year, Title.";
	        }
	        
	        String title = parts[1];
	        String[] words = title.split("\\s+");
	        int titleWordCount = words.length;
	        if (titleWordCount < 4 || titleWordCount > 10) {
	            return "Award titles should have 4 to 10 words.";
	        }
	    }
	    if (Awards.size() > 3) {
	        return "An artist can have a maximum of three awards.";
	    }
	    
		 // An artist should be active in at least two music genres and a maximum of five
	    // Artists cannot be active in 'pop' and 'rock' genres at the same time
		if (Genres.size() < 2 || Genres.size() > 5 || (Genres.contains("pop") && Genres.contains("rock"))) {
		    return "An artist should have min 2 genres and a max of 5. And 'pop' and 'rock' genres can't be chosen at the same time.";
		}

		return "Artist information is valid.";
	}
	

	private boolean ValidArtistId(String id) {
	    if (id.length() != 10) {
	        return false;
	    }

	    for (int i = 0; i < 3; i++) {
	        char ch = id.charAt(i);
	        if (ch < '5' || ch > '9') {
	            return false;
	        }
	    }

	    for (int i = 3; i < 8; i++) {
	        char ch = id.charAt(i);
	        if (ch < 'A' || ch > 'Z') {
	            return false;
	        }
	    }

	    char secondLastChar = id.charAt(8);
	    char lastChar = id.charAt(9);
	    // Define the special characters that are allowed
	    String specialCharacters = "!@#$%^&*()_+\\\\-=\\\\[\\\\]{};':\\\"\\\\|,.<>?/";
	    if (!specialCharacters.contains(String.valueOf(secondLastChar)) || !specialCharacters.contains(String.valueOf(lastChar))) {
	        return false;
	    }

	    return true;
	}

	
	public boolean updateArtist(String newID, String newName, String newAddress, String newBirthdate, String newBio, ArrayList<String> newOccupations, ArrayList<String> newGenres, ArrayList<String> newAwards) {
	    // Check if the new artist information meets the defined conditions
	    String validationResult =ValidArtistInfo();

	    if (validationResult.equals("Artist information is valid.")) {
	        // Check if the artist was born before 2000
	        int birthYear = Integer.parseInt(newBirthdate.split("-")[2]);
	        if (birthYear < 2000) {
	            // Artist was born before 2000, so the occupations cannot be changed
	            if (!newOccupations.equals(Occupations)) {
	                return false;
	            }

	            // Awards given before 2000 cannot be changed
	            for (String newAward : newAwards) {
	                String[] parts = newAward.split(", ");
	                if (parts.length != 2) {
	                    return false;
	                }
	                int awardYear = Integer.parseInt(parts[0]);
	                if (awardYear < 2000 && !Awards.contains(newAward)) {
	                    return false;
	                }
	            }
	        }

	        try {
	            File inputFile = new File("artists.txt");
	            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	            String line;
	            StringBuilder updatedArtistInfo = new StringBuilder();

	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("\\|");
	                String existingID = parts[0];

	                if (existingID.equals(ID)) {
	                    updatedArtistInfo.append("ID: ").append(newID).append("\n");
	                    updatedArtistInfo.append("Name: ").append(newName).append("\n");
	                    updatedArtistInfo.append("Address: ").append(newAddress).append("\n");
	                    updatedArtistInfo.append("Birthdate: ").append(newBirthdate).append("\n");
	                    updatedArtistInfo.append("Bio: ").append(newBio).append("\n");
	                    updatedArtistInfo.append("Occupations: ").append(String.join(", ", newOccupations)).append("\n");
	                    updatedArtistInfo.append("Genres: ").append(String.join(", ", newGenres)).append("\n");
	                    updatedArtistInfo.append("Awards:").append("\n");

	                    // Read the existing awards and update the titles as needed
	                    ArrayList<String> existingAwards = new ArrayList<>();
	                    while (true) {
	                        line = reader.readLine();
	                        if (line != null && line.startsWith("  ")) {
	                            existingAwards.add(line.substring(2));
	                        } else {
	                            break;
	                        }
	                    }

	                    for (String newAward : newAwards) {
	                        String[] parts1 = newAward.split(", ");
	                        if (parts1.length != 2) {
	                            return false;
	                        }
	                        int awardYear = Integer.parseInt(parts1[0]);
	                        String awardTitle = parts1[1];

	                        if (awardYear >= 2000) {
	                            updatedArtistInfo.append("  ").append(newAward).append("\n");
	                        } else {
	                            // Find the existing award with the same year and update the title
	                            for (String existingAward : existingAwards) {
	                                String[] existingParts = existingAward.split(", ");
	                                if (existingParts.length == 2) {
	                                    int existingAwardYear = Integer.parseInt(existingParts[0]);
	                                    if (awardYear == existingAwardYear) {
	                                        existingAwards.remove(existingAward);
	                                        updatedArtistInfo.append("  ").append(existingAwardYear).append(", ").append(awardTitle).append("\n");
	                                        break;
	                                    }
	                                }
	                            }
	                        }
	                    }

	                    updatedArtistInfo.append("End Artist").append("\n");
	                } else {
	                    updatedArtistInfo.append(line).append("\n");
	                }
	                reader.close();
	            }

	         
	            // Write the updated artist information back to the file
	            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
	            writer.write(updatedArtistInfo.toString());
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // Update the current artist instance with the new information
	        ID = newID;
	        Name = newName;
	        Address = newAddress;
	        Birthdate = newBirthdate;
	        Bio = newBio;
	        Occupations = newOccupations;
	        Genres = newGenres;
	        Awards = newAwards;

	        return true;
	    }

	    return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter artist information:");
        
        System.out.print("ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Address (City|State|Country): ");
        String address = scanner.nextLine();
        
        System.out.print("Birthdate (DD-MM-YYYY): ");
        String birthdate = scanner.nextLine();
        
        System.out.print("Bio: ");
        String bio = scanner.nextLine();
        
        System.out.print("Occupations (comma-separated): ");
        String[] occupationArray = scanner.nextLine().split(", ");
        ArrayList<String> occupations = new ArrayList<>();
        for (String occupation : occupationArray) {
            occupations.add(occupation);
        }
        
        System.out.print("Genres (comma-separated): ");
        String[] genreArray = scanner.nextLine().split(", ");
        ArrayList<String> genres = new ArrayList<>();
        for (String genre : genreArray) {
            genres.add(genre);
        }
        
        System.out.print("Awards (Year, Title) - Enter 'done' when finished: ");
        ArrayList<String> awards = new ArrayList<>();
        String awardInput = scanner.nextLine();
        while (!awardInput.equalsIgnoreCase("done")) {
            awards.add(awardInput);
            System.out.print("Next Award (Year, Title) or 'done': ");
            awardInput = scanner.nextLine();
        }
        
        
        while (true) {
            System.out.println("SEF Music App Menu:");
            System.out.println("1. Add New Artist");
            System.out.println("2. Update Existing Artist");
            System.out.println("3. Exit");

            System.out.print("Enter your choice (1/2/3): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
	                 Artist artist = new Artist(id, name, address, birthdate, bio, occupations, genres, awards);
	                 Boolean validationMessage = artist.addArtist(id, name, address, birthdate, bio, occupations, genres, awards);
	                 String validationResult = artist.ValidArtistInfo();
                                      
                   if (validationMessage==true) {
                   	System.out.println(validationMessage);
                       System.out.println("Artist information added successfully.");
                   } else {
                       System.out.println("Artist information did not meet the conditions and was not added.");
                       System.out.println("Validation Error: " + validationResult);
                   }
                   break;
                   
                case 2:
                	Artist updateartist = new Artist(id, name, address, birthdate, bio, occupations, genres, awards);
	                 Boolean validationMessage1 = updateartist.addArtist(id, name, address, birthdate, bio, occupations, genres, awards);
	                 String validationResult1 = updateartist.ValidArtistInfo();
                                     
                  if (validationMessage1==true) {
                  	System.out.println(validationMessage1);
                      System.out.println("Artist information added successfully.");
                  } else {
                      System.out.println("Artist information did not meet the conditions and was not added.");
                      System.out.println("Validation Error: " + validationResult1);
                  }
                   break;
                case 3:
                    // Exit the program
                    System.out.println("Exiting SEF Music App. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        
        
        
          }

	}


    

}
	
	
	
                    
           
	
	






























