package social_client;

import java.io.Serializable;
import java.util.Arrays;

public class Set<E> implements SetInterface<E>
{
  private int size;
  private E[] set;
  private static final int DEFAULT_CAPACITY = 25;

  public Set()
  {
    this(DEFAULT_CAPACITY);
  }

  public Set(int capacity)
  {
    @SuppressWarnings("unchecked")
    E[] tempSet = (E[]) new Object[capacity];
    set = tempSet;
    size = 0;
  }

  /**
   * Determines the current number of entries in this set.
   *
   * @return  The integer number of entries currently in this set
   */
  public int getCurrentSize()
  {
    return size;
  }

  /**
   * Determines whether this set is empty.
   *
   * @return  true if this set is empty; false if not
   */
  public boolean isEmpty()
  {
    return size == 0;
  }

  /**
   * Adds a new entry to this set, avoiding duplicates.
   *
   * <p> If newEntry is not null, this set does not contain newEntry, and this
   * set has available capacity (if fixed), then add modifies the set so that
   * it contains newEntry. All other entries remain unmodified. Duplicates are
   * determined using the .equals() method.
   *
   * <p> If newEntry is null, then add throws IllegalArgumentException without
   * modifying the set. If this set already contains newEntry, then add
   * returns false without modifying the set. If this set has a capacity
   * limit, and does not have available capacity, then add throws
   * SetFullException without modifying the set.
   *
   * @param newEntry  The object to be added as a new entry
   * @return  true if the addition is successful; false if the item already is
   * in this set
   * @throws SetFullException  If this set has a fixed capacity and does not
   * have the capacity to store an additional entry
   * @throws IllegalArgumentException  If newEntry is null
   */
  public boolean add(E element) throws SetFullException, IllegalArgumentException
  {
    boolean result = false;
    if(element == null)
      throw new IllegalArgumentException("You cannot add a null item");
    else if(!this.contains(element))
    {
      if(size >= set.length)
      {
        set = Arrays.copyOf(set, 2*set.length);
      }
      set[size] = element;
      size++;
      result = true;
    }

    return result;
  }

  /**
   * Removes a specific entry from this set, if possible.
   *
   * <p> If this set contains the entry, remove will modify the set so that it
   * no longer contains entry. All other entries remain unmodified. Locating
   * this entry is accomplished using the .equals() method.
   *
   * <p> If this set does not contain entry, remove will return false without
   * modifying the set. If entry is null, then remove throws
   * IllegalArgumentException without modifying the set.
   *
   * @param entry  The entry to be removed
   * @return  true if the removal was successful; false if not
   * @throws IllegalArgumentException  If entry is null
   */
  public boolean remove(E element) throws IllegalArgumentException
  {
    boolean result = false;
    if(element != null)
    {
      int i = 0;
      while(!result && i < set.length)
      {
        if(element.equals(set[i]))
        {
          set[i] = this.remove();
          result = true;
        }
        i++;
      }
    }
    else
      throw new IllegalArgumentException("You cannot remove a null item");

    return result;
  }

  /**
   * Removes an arbitrary entry from this set, if possible.
   *
   * <p> If this set contains at least one entry, remove will modify the set
   * so that it no longer contains one of its entries. All other entries
   * remain unmodified. The removed entry will be returned.
   *
   * <p> If this set is empty, remove will return null without modifying the
   * set. Because null cannot be added, a return value of null will never
   * indicate a successful removal.
   *
   * @return  The removed entry if the removal was successful; null otherwise
   */
  public E remove()
  {
    E result = null;
    if(size > 0)
    {
      result = set[size-1];
      set[size-1] = null;
      size--;
    }
    return result;
  }

  /**
   * Removes all entries from this set.
   *
   * <p> If this set is already empty, clear will not modify the set.
   * Otherwise, the set will be modified so that it contains no entries.
   */
  public void clear()
  {
    E removed = null;
    while(!this.isEmpty())
      removed = this.remove();
  }

  /**
   * Tests whether this set contains a given entry. Equality is determined
   * using the .equals() method.
   *
   * <p> If this set contains entry, then contains returns true. Otherwise
   * (including if this set is empty), contains returns false. If entry is
   * null, then remove throws IllegalArgumentException. The method never
   * modifies this set.
   *
   * @param entry  The entry to locate
   * @return  true if this set contains entry; false if not
   * @throws IllegalArgumentException  If entry is null
   */
  public boolean contains(E element) throws IllegalArgumentException
  {
    boolean result = false;
    if(element != null)
    {
      int i = 0;
      while(!result && (i < size))
      {
        if(element.equals(set[i]))
          result = true;
        i++;
      }
    }
    else
      throw new IllegalArgumentException("Null entry");

    return result;
  }

  /**
   * Retrieves all entries that are in this set.
   *
   * <p> An array is returned that contains a reference to each of the entries
   * in this set. The returned array's length will be equal to the number of
   * elements in this set, and thus the array will contain no null values.
   *
   * <p> If the implementation of set is array-backed, toArray will not return
   * the private backing array. Instead, a new array will be allocated with
   * the appropriate capacity.
   *
   * @return  A newly-allocated array of all the entries in this set
   */
  public E[] toArray()
  {
    @SuppressWarnings("unchecked")
    E[] tempResult = (E[]) new Object[size];
    E[] result = null;
    for(int i = 0; i < size; i++)
    {
      tempResult[i] = set[i];
    }
    result = tempResult;
    return result;
  }

}
