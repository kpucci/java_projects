package social_client;

public final class Profile implements ProfileInterface
{
  private String name;
  private String about;
  private int numFollowing;
  private SetInterface<ProfileInterface> followingSet;

  public Profile()
  {
    this(null, null);
  }

  public Profile(String name, String about)
  {
    if(name == null)
      this.name = "";
    else
      this.name = name;
    if(about == null)
      this.about = "";
    else
      this.about = about;
    numFollowing = 0;
    followingSet = new Set<>();
  }

  /**
   * Sets this profile's name.
   *
   * <p> If newName is not null, then setName modifies this profile so that
   * its name is newName. If newName is null, then setName throws
   * IllegalArgumentException without modifying the profile, for example:
   *
   * <p> {@code throw new IllegalArgumentException("Name cannot be null")}
   *
   * @param newName  The new name
   * @throws IllegalArgumentException  If newName is null
   */
  public void setName(String newName) throws IllegalArgumentException
  {
    if (newName == null)
      throw new IllegalArgumentException("Name cannot be null");
    else
      name = newName;
  }

  /**
   * Gets this profile's name.
   *
   * @return  The name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets this profile's "about me" blurb.
   *
   * <p> If newAbout is not null, then setAbout modifies this profile so that
   * its about blurb is newAbout. If newAbout is null, then setAbout throws
   * IllegalArgumentException without modifying the profile.
   *
   * @param newAbout  The new blurb
   * @throws IllegalArgumentException  If newAbout is null
   */
  public void setAbout(String newAbout) throws IllegalArgumentException
  {
    if(newAbout == null)
      throw new IllegalArgumentException("About cannot be null");
    else
      about = newAbout;
  }

  /**
   * Gets this profile's "about me" blurb
   *
   * @return  The blurb
   */
  public String getAbout()
  {
    return about;
  }

  /**
   * Adds another profile to this profile's following set.
   *
   * <p> If this profile's following set is at capacity, or if other is null,
   * then follow returns false without modifying the profile. Otherwise, other
   * is added to this profile's following set.
   *
   * @param other  The profile to follow
   * @return  True if successful, false otherwise
   */
  public boolean follow(ProfileInterface other)
  {
    boolean result = true;
    if(followingSet.contains(other) || other == null)
      result = false;
    else
    {
      try
      {
        result = followingSet.add(other);
      }
      catch(SetFullException e)
      {
        System.out.println(e.getMessage());
      }
      numFollowing++;
    }

    return result;
  }

  /**
   * Removes the specified profile from this profile's following set.
   *
   * <p> If this profile's following set does not contain other, or if other
   * is null, then unfollow returns false without modifying the profile.
   * Otherwise, this profile in modified in such a way that other is removed
   * from this profile's following set.
   *
   * @param other  The profile to follow
   * @return  True if successful, false otherwise
   */
  public boolean unfollow(ProfileInterface other)
  {
    boolean result = true;
    if(!followingSet.contains(other) || other == null)
      result = false;
    else
    {
      result = followingSet.remove(other);
      numFollowing--;
    }

    return result;
  }

  /**
   * Returns a preview of this profile's following set.
   *
   * <p> The howMany parameter is a maximum desired size. The returned array
   * may be less than the requested size if this profile is following fewer
   * than howMany other profiles. Clients of this method must be careful to
   * check the size of the returned array to avoid
   * ArrayIndexOutOfBoundsException.
   *
   * <p> Specifically, following returns an array of size min(howMany, [number
   * of profiles that this profile is following]). This array is populated
   * with arbitrary profiles that this profile follows.
   *
   * @param howMany  The maximum number of profiles to return
   * @return  An array of size &le;howMany, containing profiles that this
   * profile follows
   */
  public ProfileInterface[] following(int howMany)
  {
    if(howMany > followingSet.getCurrentSize())
      howMany = followingSet.getCurrentSize();
    ProfileInterface[] followingArray = new ProfileInterface[howMany];
    Object[] setArray = followingSet.toArray();
    for(int i = 0; i < howMany; i++)
    {
      followingArray[i] = (ProfileInterface)setArray[i];
    }
    return followingArray;
  }

  /**
   * Recommends a profile for this profile to follow. This returns a profile
   * followed by one of this profile's followed profiles. Should not recommend
   * this profile to follow someone they already follow, and should not
   * recommend to follow oneself.
   *
   * <p> For example, if this profile is Alex, and Alex follows Bart, and Bart
   * follows Crissy, this method might return Crissy.
   *
   * @return  The profile to suggest, or null if no suitable profile is
   * possible.
   */
  public ProfileInterface recommend()
  {
    boolean result = false;
    ProfileInterface recommendation = null;
    ProfileInterface[] followingList = this.following(numFollowing);
    int i = 0;
    while(i<numFollowing && !result)
    {
      ProfileInterface firstFollowing = followingList[i];
      ProfileInterface[] firstFollowingList = firstFollowing.following(10);
      int j = 0;
      while(j<firstFollowingList.length && !result)
      {
        ProfileInterface testRecommendation = firstFollowingList[j];
        if(!(testRecommendation.equals(this)) && !followingSet.contains(testRecommendation))
        {
          recommendation = testRecommendation;
          result = true;
        }
        j++;
      }
      i++;
    }

    return recommendation;
  }

}
