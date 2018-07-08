package social_client;

public class SetDemo
{
  public static void main(String[] args) throws SetFullException, IllegalArgumentException
  {
    System.out.println("Testing set...");
    SetInterface<String> stringSet = new Set<>();
    SetInterface<String> stringSet2 = new Set<>(2);

    String name1 = "Katie";
    boolean addFirst = stringSet.add(name1);
    boolean addFirstAgain = stringSet.add(name1);

    String last1 = "Pucci";
    String last2 = "Vaughan";
    String last3 = "Ga";
    boolean addLast = stringSet2.add(last1);
    boolean addLast2 = stringSet2.add(last2);
    boolean addLast3 = stringSet2.add(last3);
    String removeItem = stringSet2.remove();
    stringSet.clear();
    stringSet.clear();

    Object[] array1 = stringSet.toArray();
    Object[] array2 = stringSet2.toArray();

    System.out.println("First Set:");
    for(Object item:array1)
      System.out.println(item);
    System.out.println("First Set Size: " + stringSet.getCurrentSize());
    System.out.println("Second Set:");
    for(Object item:array2)
      System.out.println(item);
    System.out.println("Second Set Size: " + stringSet2.getCurrentSize());

  }
}
