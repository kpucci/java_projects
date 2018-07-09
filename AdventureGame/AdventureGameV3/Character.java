import java.util.Random;
import javafx.application.Application;
  import javafx.event.ActionEvent;
  import javafx.scene.layout.*;
  import javafx.scene.Scene;
  import javafx.scene.control.Button;
  import javafx.scene.control.Label;
  import javafx.scene.control.ChoiceBox;
  import javafx.scene.control.TextField;
  import javafx.scene.control.ListView;
  import javafx.scene.text.*;
  import javafx.stage.Stage;
  import javafx.geometry.*;
  import javafx.scene.image.Image;
  import javafx.scene.image.ImageView;
  import javafx.scene.control.RadioButton;
  import javafx.scene.control.ToggleGroup;

public abstract class Character
{

  public enum Type {ROGUE, PALADIN, JACKIE_CHAN, GOBLIN, SKELETON, WIZARD};

  //Attributes
  private String name = null;
  private String imageType = null;
  private int hitPoints;
  private int initialHP;
  private int strength;
  private Weapon weapon = null;
  private Image characterImg;
  private Type charType;

  private static final String PALADIN_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Paladin.png";
  private static final String ROGUE_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Rogue.png";
  private static final String JACKIE_CHAN_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/JackieChan.png";
  private static final String GOBLIN_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Goblin.png";
  private static final String SKELETON_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Skeleton.png";

  private static final int ROGUE_HP = 55;
  private static final int ROGUE_STRENGTH = 8;
  private static final int PALADIN_HP = 35;
  private static final int PALADIN_STRENGTH = 14;
  private static final int JACKIE_CHAN_HP = 45;
  private static final int JACKIE_CHAN_STRENGTH = 10;
  private static final int GOBLIN_HP = 25;
  private static final int GOBLIN_STRENGTH = 4;
  private static final int SKELETON_HP = 25;
  private static final int SKELETON_STRENGTH = 3;
  private static final int WIZARD_HP = 40;
  private static final int WIZARD_STRENGTH = 8;

  //Methods
  public Character(Type characterType)
  {
    switch (characterType)
    {
      case ROGUE:
        name = "Rogue";
        hitPoints = ROGUE_HP;
        strength = ROGUE_STRENGTH;
        //weapon = new Weapon("Short Sword", Weapon.SHORT_SWORD_MIN, Weapon.SHORT_SWORD_MAX);
        break;
      case PALADIN:
        name = "Paladin";
        hitPoints = PALADIN_HP;
        strength = PALADIN_STRENGTH;
        //weapon = new Weapon("Long Sword", Weapon.LONG_SWORD_MIN, Weapon.LONG_SWORD_MAX);
        break;
      case JACKIE_CHAN:
        name = "Jackie Chan";
        hitPoints = JACKIE_CHAN_HP;
        strength = JACKIE_CHAN_STRENGTH;
        //weapon = new Weapon("Jump Kick", Weapon.JUMP_KICK_MIN, Weapon.JUMP_KICK_MAX);
        break;
      case GOBLIN:
        name = "Goblin";
        hitPoints = GOBLIN_HP;
        strength = GOBLIN_STRENGTH;
        //weapon = new Weapon("Axe", Weapon.AXE_MIN, Weapon.AXE_MAX);
        break;
      case SKELETON:
        name = "Skeleton";
        hitPoints = SKELETON_HP;
        strength = SKELETON_STRENGTH;
        //weapon = new Weapon("Short Sword", Weapon.SHORT_SWORD_MIN, Weapon.SHORT_SWORD_MAX);
        break;
      case WIZARD:
        name = "Wizard";
        hitPoints = WIZARD_HP;
        strength = WIZARD_STRENGTH;
        //weapon = new Weapon("Fire Blast", Weapon.FIRE_BLAST_MIN, Weapon.FIRE_BLAST_MAX);
        break;
    }
    initialHP = hitPoints;
  }

  public Character(String typeString)
  {
    name = typeString;
    switch (typeString)
    {
      case "Rogue":
        hitPoints = ROGUE_HP;
        strength = ROGUE_STRENGTH;
        imageType = ROGUE_IMG;
        charType = Character.Type.ROGUE;
        //weapon = new Weapon("Short Sword", Weapon.SHORT_SWORD_MIN, Weapon.SHORT_SWORD_MAX);
        break;
      case "Paladin":
        hitPoints = PALADIN_HP;
        strength = PALADIN_STRENGTH;
        imageType = PALADIN_IMG;
        charType = Character.Type.PALADIN;
        //weapon = new Weapon("Long Sword", Weapon.LONG_SWORD_MIN, Weapon.LONG_SWORD_MAX);
        break;
      case "Jackie Chan":
        hitPoints = JACKIE_CHAN_HP;
        strength = JACKIE_CHAN_STRENGTH;
        imageType = JACKIE_CHAN_IMG;
        charType = Character.Type.JACKIE_CHAN;
        //weapon = new Weapon("Jump Kick", Weapon.JUMP_KICK_MIN, Weapon.JUMP_KICK_MAX);
        break;
      case "Goblin":
        hitPoints = GOBLIN_HP;
        strength = GOBLIN_STRENGTH;
        imageType = GOBLIN_IMG;
        charType = Character.Type.GOBLIN;
        //weapon = new Weapon("Axe", Weapon.AXE_MIN, Weapon.AXE_MAX);
        break;
      case "Skeleton":
        hitPoints = SKELETON_HP;
        strength = SKELETON_STRENGTH;
        imageType = SKELETON_IMG;
        charType = Character.Type.SKELETON;
        //weapon = new Weapon("Short Sword", Weapon.SHORT_SWORD_MIN, Weapon.SHORT_SWORD_MAX);
        break;
      case "Wizard":
        hitPoints = WIZARD_HP;
        strength = WIZARD_STRENGTH;
        //weapon = new Weapon("Fire Blast", Weapon.FIRE_BLAST_MIN, Weapon.FIRE_BLAST_MAX);
        break;
    }
    characterImg = new Image(imageType);
    initialHP = hitPoints;
  }

  public String getName()
  {
    return name;
  }

  public int getHitPoints()
  {
    return hitPoints;
  }

  public int getStrength()
  {
    return strength;
  }

  public Type getType()
  {
    return charType;
  }

  public void setHitPoints(int hitPoints)
  {
    this.hitPoints = hitPoints;
  }

  public void setStrength(int strength)
  {
    this.strength = strength;
  }

  public void setWeapon(Weapon weapon)
  {
    this.weapon = weapon;
  }

  public void attack(Character opponent)
  {
    int weaponDamage = weapon.getDamage(); //Get weapon damage
    int ATK = strength + weaponDamage; //Get attack value
    //System.out.println(name + " attacks with ATK = " + strength +
    //                   " + " + weaponDamage + " = " + ATK);
    //System.out.print(opponent.getName() + " HP is now " + opponent.getHitPoints() + " - " + ATK);
    opponent.decreaseHitPoints(ATK); //Decrease hit points by attack amount
    //System.out.println(" = " + opponent.getHitPoints() + "\n");
  }

  public void increaseHitPoints(int pointIncrease)
  {
    hitPoints += pointIncrease;
  }

  public void decreaseHitPoints(int pointDecrease)
  {
    hitPoints -= pointDecrease;
  }

  public boolean isDefeated()
  {
    return (hitPoints<=0);
  }

  public Image getImageCopy()
  {
    return new Image(imageType);
  }

  public void resetHP()
  {
    hitPoints = initialHP;
  }

  public static Image getCharacterImage(String characterTypeString)
  {
    String imgString = null;

    switch (characterTypeString)
    {
      case "Rogue":
        imgString = ROGUE_IMG;
        break;
      case "Paladin":
        imgString = PALADIN_IMG;
        break;
      case "Jackie Chan":
        imgString = JACKIE_CHAN_IMG;
        break;
      case "Goblin":
        imgString = GOBLIN_IMG;
        break;
      case "Skeleton":
        imgString = SKELETON_IMG;
        break;
    }

    return new Image(imgString);
  }

}
