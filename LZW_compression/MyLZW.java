/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

import java.util.Arrays;

public class MyLZW {
    private static final int MAX = 65535;   // Maximum possible codeword (2^16 - 1)
    private static final int R = 256;       // number of input chars
    private static int L = 512;             // number of codewords = 2^W
    private static int W = 9;               // codeword width
    private static final double MAX_RATIO = 1.1;

    public static void compress(char mode)
    {
        TST<Integer> codebook = new TST<Integer>();           // Create new TST of Integers

        String add = null;
        // Initialize codebook to all single characters
        for (int i = 0; i < R; i++)     // For all input characters
        {
            add = "" + (char) i;
            // System.out.println("Add: " + add + ", " + i);
            codebook.put(add, i);   // Store integer value at character key
        }

        BinaryStdOut.write((byte)mode);

        switch(mode)
        {
            case 'n':
                nothingModeCompress(codebook);
                break;
            case 'r':
                resetModeCompress(codebook);
                break;
            case 'm':
                monitorModeCompress(codebook);
                break;
            default:
                break;
        }

    }

    private static void nothingModeCompress(TST<Integer> codebook)
    {
        String input = BinaryStdIn.readString();        // Read input

        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0)
        {
            // System.out.println("\nLoop\n----------");
            if(code < MAX && code == L)
            {
                // System.out.println("INCREASE");
                L *= 2;
                W++;
            }

            String s = codebook.longestPrefixOf(input);  // Find max prefix match
            BinaryStdOut.write(codebook.get(s), W);      // Print s's encoding
            // System.out.println("Max prefix: " + codebook.get(s) + ", " + W);
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table
            {
                String add = input.substring(0, t + 1);
                // System.out.println("Add: " + add + ", " + code);
                codebook.put(add, code++);
            }

            input = input.substring(t);            // Scan past s in input

        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    private static void resetModeCompress(TST<Integer> codebook)
    {
        String input = BinaryStdIn.readString();        // Read input
        // While input still exists

        int code = R+1;  // R is codeword for EOF
        // System.out.println(code);
        while (input.length() > 0)
        {
            System.out.println("\nLoop\n----------");
            System.out.println("L: " + L + ", W: " + W);
            if(code == L)
            {
                if(W==16)
                {
                    System.out.println("RESET");
                    codebook = new TST<Integer>();
                    L = 512;
                    W = 9;
                    for (int i = 0; i < R; i++)     // For all input characters
                    {
                        // System.out.println("Add: " + add + ", " + i);
                        codebook.put("" + (char) i, i);   // Store integer value at character key
                    }
                    code = R+1;
                }

                else
                {
                    System.out.println("INCREASE");
                    L *= 2;
                    W++;
                }
            }

            String s = codebook.longestPrefixOf(input);  // Find max prefix match
            BinaryStdOut.write(codebook.get(s), W);      // Print s's encoding
            System.out.println("s: " + codebook.get(s) + ", " + W);
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table
            {
                String add = input.substring(0, t + 1);
                System.out.println("Add: " + add + ", " + code);
                codebook.put(add, code++);
            }

            input = input.substring(t);            // Scan past s in input

            // String s = codebook.longestPrefixOf(input);  // Find max prefix match
            // BinaryStdOut.write(codebook.get(s), W);      // Print s's encoding
            // // System.out.println("Max prefix: " + codebook.get(s) + ", " + W);
            // t = s.length();
            // if(code < L)
            // {
            //     if (t < input.length())    // Add s to symbol table
            //     {
            //         add = input.substring(0, t + 1);
            //         codebook.put(add, code++);
            //         // System.out.println("Add: " + add + ", " + code);
            //     }
            //     input = input.substring(t);            // Scan past s in input
            // }
            // else if(code >= L)
            // {
            //     if(W < 16)
            //     {
            //         L *= 2;
            //         W++;
            //     }
            //     else
            //     {
            //         // System.out.println("MAXXED");
            //         codebook = new TST<Integer>();
            //         L = 512;
            //         W = 9;
            //         for (int i = 0; i < R; i++)     // For all input characters
            //         {
            //             add = "" + (char) i;
            //             // System.out.println("Add: " + add + ", " + i);
            //             codebook.put(add, i);   // Store integer value at character key
            //         }
            //         code = R+1;
            //
            //         add = input.substring(0, t + 1);
            //         codebook.put(add, code++);
            //     }
            //
            //
            // }
        }
    }

    private static void monitorModeCompress(TST<Integer> codebook)
    {
        String input = BinaryStdIn.readString();        // Read input
        // While input still exists

        int originalSize = 0;
        int compressedSize = 0;
        double oldRatio = 0.0;
        double newRatio = 0.0;
        double ratioOfRatios = 0.0;

        int code = R+1;  // R is codeword for EOF
        String add = null;
        while (input.length() > 0)
        {
            // System.out.println(code);
            if(ratioOfRatios < MAX_RATIO)
            {
                if(code < MAX && code == L)
                {
                    L *= 2;
                    W++;
                }
                if(code == MAX)
                {
                    oldRatio = (double)originalSize/compressedSize;
                    code = MAX+2;
                }

                String s = codebook.longestPrefixOf(input);  // Find max prefix match
                BinaryStdOut.write(codebook.get(s), W);      // Print s's encoding
                // System.out.println("Max prefix: " + codebook.get(s) + ", " + W);
                int t = s.length();

                originalSize += (t*16);
                compressedSize += W;
                newRatio = (double)originalSize/compressedSize;
                ratioOfRatios = oldRatio/newRatio;

                if (t < input.length() && code < L)    // Add s to symbol table
                {
                    add = input.substring(0, t + 1);
                    codebook.put(add, code++);
                    // System.out.println("Add: " + add + ", " + code);
                }

                input = input.substring(t);            // Scan past s in input
            }
            else
            {
                // System.out.println("TRIPPED");
                codebook = new TST<Integer>();
                L = 512;
                W = 9;
                for (int i = 0; i < R; i++)     // For all input characters
                {
                    add = "" + (char) i;
                    // System.out.println("Add: " + add + ", " + i);
                    codebook.put(add, i);   // Store integer value at character key
                }
                code = R+1;
            }
        }

    }

    public static void expand()
    {
        String[] codebook = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            codebook[i] = "" + (char) i;
        codebook[i++] = "";                        // (unused) lookahead for EOF

        // int codeword = BinaryStdIn.readInt(W);
        // if (codeword == R) return;           // expanded message is empty string
        // String val = codebook[codeword];
        //
        // while (true)
        // {
        //     if(i<MAX && i == (L-1))
        //     {
        //         System.out.println("INCREASE");
        //         L *= 2;
        //         W++;
        //         codebook = Arrays.copyOf(codebook, L);
        //     }
        //     BinaryStdOut.write(val);
        //     codeword = BinaryStdIn.readInt(W);
        //     if (codeword == R) break;
        //     String s = codebook[codeword];
        //     System.out.println("s: " + s + ", codeword: " + codeword);
        //     if (i == codeword) s = val + val.charAt(0);   // special case hack
        //     if (i < L)
        //     {
        //         String add = val + s.charAt(0);
        //         System.out.println("add: " + add + ", i: " + i);
        //         codebook[i++] = add;
        //     }
        //
        //     val = s;
        // }
        // BinaryStdOut.close();

        // String[] codebook = new String[L];
        // int i; // next available codeword value
        //
        // // initialize symbol table with all 1-character strings
        // for (i = 0; i < R; i++)
        // {
        //     codebook[i] = "" + (char) i;
        //     // System.out.println("Add: " + codebook[i] + ", " + i);
        // }
        // codebook[i++] = "";                        // (unused) lookahead for EOF
        //
        char mode = BinaryStdIn.readChar();

        // System.out.println("Mode: " + mode);
        switch(mode)
        {
            case 'n':
                nothingModeExpand(codebook);
                break;
            case 'r':
                resetModeExpand(codebook);
                break;
            case 'm':
                monitorModeExpand(codebook);
                break;
            default:
                System.out.println("Error in determining mode");
                break;
        }
        //
        // BinaryStdOut.close();
    }

    private static void nothingModeExpand(String[] codebook)
    {
        // int i = R+1;
        // int codeword = BinaryStdIn.readInt(W);
        // if (codeword == R)
        //     return;           // expanded message is empty string
        // String val = codebook[codeword];
        // while(true)
        // {
        //     if(i == (L-1))
        //     {
        //         // System.out.println("\ni has changed to " + L + "\n");
        //         L *= 2;
        //         W++;
        //         codebook = Arrays.copyOf(codebook, L);
        //     }
        //
        //     BinaryStdOut.write(val);
        //     codeword = BinaryStdIn.readInt(W);
        //     // System.out.println(val + ", " + codeword);
        //
        //     if (codeword == R)
        //         break;
        //     String s = codebook[codeword];
        //     if (i == codeword)
        //         s = val + val.charAt(0);   // special case hack
        //     if (i < L)
        //     {
        //         codebook[i++] = val + s.charAt(0);
        //     }
        //
        //     val = s;
        // }

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = codebook[codeword];
        int i = R+1;
        while (true)
        {
            if(i<MAX && i == (L-1))
            {
                // System.out.println("INCREASE");
                L *= 2;
                W++;
                codebook = Arrays.copyOf(codebook, L);
            }
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = codebook[codeword];
            // System.out.println("s: " + s + ", codeword: " + codeword);
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L)
            {
                String add = val + s.charAt(0);
                // System.out.println("add: " + add + ", i: " + i);
                codebook[i++] = add;
            }

            val = s;
        }
        BinaryStdOut.close();
    }

    private static void resetModeExpand(String[] codebook)
    {
        int i = R+1;
        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R)
            return;           // expanded message is empty string
        String val = codebook[codeword];
        while(true)
        {
            System.out.println("\nLoop\n----------");
            System.out.println("L: " + L + ", W: " + W);
            boolean skip = false;
            if(i<MAX && i == (L-1))
            {
                if(W==16)
                {

                }
                else
                {
                    System.out.println("INCREASE");
                    L *= 2;
                    W++;
                    codebook = Arrays.copyOf(codebook, L);
                }
            }
            else if(i==MAX+1)
            {
                System.out.println("RESET");
                L = 512;
                W = 9;
                codebook = new String[L];
                for (i = 0; i < R; i++)
                {
                    codebook[i] = "" + (char) i;
                    // System.out.println("add: " + codebook[i] + ", i: " + i);
                }

                codebook[i++] = "";
                i = R+1;
                skip = true;

            }
            if(!skip)
                BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = codebook[codeword];
            System.out.println("s: " + s + ", codeword: " + codeword);
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L)
            {
                String add = val + s.charAt(0);
                System.out.println("add: " + add + ", i: " + i);
                codebook[i++] = add;
            }

            val = s;
            // if(i < (L-1))
            // {
            //     // BinaryStdOut.write(val);
            //     codeword = BinaryStdIn.readInt(W);
            //     System.out.println(val + ", " + codeword);
            //
            //     if (codeword == R)
            //         break;
            //     String s = codebook[codeword];
            //     // System.out.println(codeword);
            //     System.out.print(s);
            //     if (i == codeword)
            //         // if(s!=null)
            //             s = val + val.charAt(0);   // special case hack
            //     if (i < L)
            //     {
            //         // if(s!=null)
            //             codebook[i++] = val + s.charAt(0);
            //     }
            //     // if(s!=null)
            //         val = s;
            // }
            // else if(i >= (L-1))
            // {
            //     if(W < 16)
            //     {
            //         System.out.println("\ni has changed to " + L + "\n");
            //         L *= 2;
            //         W++;
            //         codebook = Arrays.copyOf(codebook, L);
            //     }
            //     else
            //     {
            //         System.out.println("MAXXED");
            //         L = 512;
            //         W = 9;
            //         codebook = new String[L];
            //         for (i = 0; i < R; i++)
            //             codebook[i] = "" + (char) i;
            //         codebook[i++] = "";
            //         i = R+1;
            //     }
            //
            // }

        }
    }

    private static void monitorModeExpand(String[] codebook)
    {
        int originalSize = 0;
        int compressedSize = 0;
        double oldRatio = 0.0;
        double newRatio = 0.0;
        double ratioOfRatios = 0.0;

        int i = R+1;
        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R)
            return;           // expanded message is empty string
        String val = codebook[codeword];
        while(true)
        {
            // System.out.println(ratioOfRatios);
            // System.out.println(oldRatio);
            // System.out.println(newRatio);
            if(ratioOfRatios < MAX_RATIO)
            {
                if(i == (L-1))
                {
                    // System.out.println("\ni has changed to " + L + "\n");
                    L *= 2;
                    W++;
                    codebook = Arrays.copyOf(codebook, L);
                }
                if(i == MAX)
                {
                    // System.out.println("\ni has maxed\n");
                    oldRatio = (double)originalSize/compressedSize;
                    i = MAX+2;
                }

                BinaryStdOut.write(val);
                codeword = BinaryStdIn.readInt(W);
                // System.out.println(val + ", " + codeword);

                if(codeword == R)
                    break;
                String s = codebook[codeword];
                // System.out.println(s);
                int t = 0;
                if(s != null)
                {
                    t = s.length();
                }
                // System.out.println(t);
                originalSize += (t*16);
                compressedSize += W;
                newRatio = (double)originalSize/compressedSize;

                if(newRatio != 0)
                    ratioOfRatios = oldRatio/newRatio;
                else
                    ratioOfRatios = 0.0;

                if (i == codeword)
                    s = val + val.charAt(0);   // special case hack
                if (i < L)
                {
                    codebook[i++] = val + s.charAt(0);
                }

                val = s;
            }
            else
            {
                System.out.println("TRIPPED");
                L = 512;
                W = 9;
                codebook = new String[L];
                for (i = 0; i < R; i++)
                    codebook[i] = "" + (char) i;
                codebook[i++] = "";
                i = R+1;
            }

        }
    }

    public static void main(String[] args)
    {
        if(args[0].equals("-"))
        {
            if(!(args[1].equals("n") || args[1].equals("r") || args[1].equals("m")))
                throw new IllegalArgumentException("Illegal command line argument");
            compress(args[1].charAt(0));
        }

        else if(args[0].equals("+"))
            expand();
        else
            throw new IllegalArgumentException("Illegal command line argument");
    }

}
