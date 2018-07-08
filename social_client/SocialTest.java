package social_client;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SocialTest
{
  public static void main(String[] args)
  {
    //Test initialization...

    //Fully-initialized profile
    ProfileInterface katieProfile = new Profile("Katie", "Vincent");
    System.out.println("Katie has successfully been initialized");
    SetInterface<ProfileInterface> katieFollowing = new Set<>(5);
    System.out.println("\tKatie's following list has successfully been initialized");

    //Profile with null fields
    ProfileInterface vincentProfile = new Profile();
    System.out.println("Vincent has successfully been initialized");
    SetInterface<ProfileInterface> vincentFollowing = new Set<>();
    System.out.println("\tVincent's following list has successfully been initialized");

    //Profile with one field
    ProfileInterface brianProfile = new Profile("Brain", null);
    System.out.println("Brian has successfully been initialized");
    SetInterface<ProfileInterface> brianFollowing = new Set<>(0);
    System.out.println("\tBrian's following list has successfully been initialized");

    ProfileInterface tommyProfile = new Profile("Tommy", "Alex");
    ProfileInterface phillipProfile = new Profile("Phillip", "Kayla");
    ProfileInterface nicholasProfile = new Profile("Nicholas", "Lindsay");
    ProfileInterface andrewProfile = new Profile("Andrew", "Suji");
    ProfileInterface momProfile = new Profile("Kim", "Mom");
    ProfileInterface dadProfile = new Profile("Tom", "Dad");

    //Test setName and getName methods...
    System.out.println("Vincent's name: " + vincentProfile.getName());
    try
    {
      vincentProfile.setName(null);
    }
    catch(IllegalArgumentException e)
    {
      System.out.println("IllegalArgumentException thrown");
    }
    vincentProfile.setName("Vincent");
    System.out.println("Vincent's name is: " + vincentProfile.getName());
    brianProfile.setName("Brian");
    System.out.println("Brian's name is: " + brianProfile.getName());

    //Test setAbout and getAbout methods...
    try
    {
      vincentProfile.setAbout(null);
    }
    catch(IllegalArgumentException e)
    {
      System.out.println(e.getMessage());
    }

    System.out.println("Vincent's about: " + vincentProfile.getAbout());
    vincentProfile.setAbout("Loves Katie very, very much!!!");
    System.out.println("Vincent's about: " + vincentProfile.getAbout());

    brianProfile.setAbout("Lindsay Vaughan");

    //Test follow and unfollow methods...
    try
    {
      System.out.println("Does Katie follow Vincent? " + (katieProfile.follow(null)? "yes :)":"no :("));
    }
    catch(IllegalArgumentException e)
    {
      System.out.println(e.getMessage());
    }
    System.out.println("Does Katie follow Vincent? " + (katieProfile.follow(vincentProfile)? "yes :)":"no :("));
    System.out.println("How about now? " + (katieProfile.unfollow(vincentProfile)? "no :(":"yes :)"));
    System.out.println("Did Katie unfollow Brian? " + (katieProfile.unfollow(brianProfile)? "yes":"no"));
    katieProfile.follow(vincentProfile);
    katieProfile.follow(andrewProfile);
    katieProfile.follow(nicholasProfile);
    katieProfile.follow(momProfile);
    katieProfile.follow(dadProfile);
    vincentProfile.follow(katieProfile);
    vincentProfile.follow(andrewProfile);
    vincentProfile.follow(nicholasProfile);
    vincentProfile.follow(momProfile);
    vincentProfile.follow(dadProfile);
    momProfile.follow(dadProfile);
    momProfile.follow(brianProfile);
    momProfile.follow(phillipProfile);

    //Test following method...
    ProfileInterface[] katieList = katieProfile.following(10);
    System.out.println("Katie is following: ");
    for(ProfileInterface profile: katieList)
    {
      System.out.println(profile.getName());
    }
    ProfileInterface[] vincentList = vincentProfile.following(10);
    ProfileInterface[] momList = momProfile.following(10);

    //Test recommend method...
    ProfileInterface recommendation = katieProfile.recommend();
    System.out.println(recommendation.getName());
  }






}
