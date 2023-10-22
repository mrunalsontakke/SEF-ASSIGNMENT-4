package sef_assignment4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArtistTest {
	
	private String artistId = "";
	private String artistName = "";
	private String artistAddress = "";
	private String artistDateOfBirth = "";
	private String artistBio = "";
	
	private ArrayList<String> occupations = null;
	private ArrayList<String> genres = null;
	private ArrayList<String> awards = null;
	
	private Artist artist = null;
	
	@Before
	public void setUp() throws Exception {
		// Create a object of artist with the all default correct values. Will change in the function any value needed to test.
		// All Valid input
		artistId = "569MMMRR_%";
	    artistName = "Mrunal Sontakke";
	    artistAddress = "Los Angeles|California|USA";
	    artistDateOfBirth = "15-07-1990";
	    artistBio = "Mrunal Sontakke is a talented singer and songwriter, known for his soulful melodies.";

	    occupations = new ArrayList<>();
	    Collections.addAll(occupations, "Singer", "Songwriter");

	    genres = new ArrayList<>();
	    Collections.addAll(genres, "Pop", "R&B", "Soul");

	    awards = new ArrayList<>();
	    Collections.addAll(awards, "2022, Best New Artist", "2023, Song of the Year");

	    assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}

	

	@After
	public void tearDown() {
		System.setErr(System.err);
		System.setOut(System.out);
	}
	
	// All Valid input
	@Test
	public void testValidInput() {
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
	// Condition 1
	@Test
	public void testArtistId() {
		
		// Valid Input artistId
		artistId = "569MWEER_%";
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input artistId
		artistId = "169MMMRR_%";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input artistId
		artistId = "569MBMRR_*%";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input artistId
		artistId = "56AAPOR_%";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
//	Condition 2
	@Test
	public void testDateFormat() {
		
		//Valid Input dateOfBirth
		artistDateOfBirth = "29-10-1789";
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input dateOfBirth
		artistDateOfBirth = "29/10/1789";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input artistId
		artistDateOfBirth = "29/10-1789";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
//	Condition 3
	@Test
	public void testAddressFormat() {
		
		//Valid Input address
		artistAddress = "Melbourne|Victoria|Australia";
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Input address
		artistAddress = "Melbourne|Victoria|VIC|Australia";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
				
		//Invalid Input address
		artistAddress = "Melbourne||Australia";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
	// Condition 4	
	@Test
	public void testBioLength() {
		//Valid Bio length
		artistBio = "This is the initial bio of the artist which is in correct world limit";
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Bio length
		artistBio = "This is short bio";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Bio length
		artistBio = "This is the initial bio of the artist which is in correct world limit. This is extremely long bio, which is more than 40 words, so it should return false value.";
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
	//Condition 5
	@Test
	public void testOccupation() {
		//Valid Ocuupation length
		occupations = new ArrayList<>();
		Collections.addAll(occupations, "Singer", "Songwriter");
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Ocuupation length
		occupations = new ArrayList<>();
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Ocuupation length
		occupations = new ArrayList<>();
		Collections.addAll(occupations, "Singer", "Songwriter", "Actor", "Lyricsist", "Podcast writer", "Rockstar");
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
	}
	
	//Condition 6
	@Test
	public void testAwards() {
		//Valid award length
		awards = new ArrayList<>();
		Collections.addAll(awards, "2020, Best Song Written For Social Media", "1998, Best Lyric writer 1998", "2000, Best singer of 2000");
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid award length
		awards = new ArrayList<>();
		Collections.addAll(awards, "2021");
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
	}
	
	//Condition 7
	@Test
	public void testGenres() {
		//Valid Genre
		genres = new ArrayList<>();
		Collections.addAll(genres, "Pop", "Classical", "Jazz");
		assertEquals(true, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Genre
		genres = new ArrayList<>();
		Collections.addAll(genres, "Pop", "Classical", "Jazz", "Rock");
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres, awards));
		
		//Invalid Genre
		genres = new ArrayList<>();
		Collections.addAll(genres, "Pop");
		assertEquals(false, artist.addArtist(artistId, artistName, artistAddress, artistDateOfBirth, artistBio, occupations, genres,awards));
	}
}
