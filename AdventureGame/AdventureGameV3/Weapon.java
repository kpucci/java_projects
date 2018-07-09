import java.util.Random;
  import javafx.scene.image.Image;




public class Weapon
{
  //Public constants
  static final int SHORT_SWORD_MIN = 1, SHORT_SWORD_MAX = 4;
  static final int LONG_SWORD_MIN = 3, LONG_SWORD_MAX = 7;
  static final int MACE_MIN = 2, MACE_MAX = 6;
  static final int JUMP_KICK_MIN = 2, JUMP_KICK_MAX = 6;
  static final int AXE_MIN = 2, AXE_MAX = 6;
  static final int FIRE_BLAST_MIN = 4, FIRE_BLAST_MAX = 10;

  private static final String AXE_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Axe.png";
  private static final String MACE_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/Mace.png";
  private static final String SHORT_SWORD_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/ShortSword.png";
  private static final String LONG_SWORD_IMG = "file:/Users/katievaughan/Dropbox/School/COE401/Assignments/AdventureGameV3/art/LongSword.png";

  //Attributes
  private String name;
  private String imageLocation;
  private Image weaponImg;
  private int minDamage;
  private int maxDamage;

  //Methods
  public Weapon(String name, int minDamage, int maxDamage)
  {
    this.name = name; //This reference copy is okay because Strings are immutable
    this.minDamage = minDamage;
    this.maxDamage = maxDamage;
  }

  public Weapon(String name)
  {
    this.name = name;

    switch(name)
    {
      case "Short Sword":
        this.minDamage = SHORT_SWORD_MIN;
        this.maxDamage = SHORT_SWORD_MAX;
        imageLocation = SHORT_SWORD_IMG;
        break;
      case "Long Sword":
        this.minDamage = LONG_SWORD_MIN;
        this.maxDamage = LONG_SWORD_MAX;
        imageLocation = LONG_SWORD_IMG;
        break;
      case "Mace":
        this.minDamage = MACE_MIN;
        this.maxDamage = MACE_MAX;
        imageLocation = MACE_IMG;
        break;
      case "Axe":
        this.minDamage = AXE_MIN;
        this.maxDamage = AXE_MAX;
        imageLocation = AXE_IMG;
        break;
    }
    weaponImg = new Image(imageLocation);
  }

  public String getName()
  {
    return name; //This is okay because Strings are immutable
  }

  public int getMinDamage()
  {
    return minDamage;
  }

  public int getMaxDamage()
  {
    return maxDamage;
  }

  public int getDamage()
  {
    Random rand = new Random();
    int damage = rand.nextInt(maxDamage - minDamage + 1) + minDamage;
    return damage;
  }

  public Image getImageCopy()
  {
    return new Image(imageLocation);
  }

}
