package dBT;

//Algorithm to find Closure of X under F

import java.io.*;
import java.util.*;

/*class FD{
HashSet<Character> lhs; char rhs;
public FD(HashSet<Character> l, char r){ lhs = l; rhs = r; }
public boolean equals(Object obj){
 FD fd2 = (FD)obj;
 return lhs.equals(fd2.lhs) && rhs == fd2.rhs;
}
};*/

public class FDS{
HashSet<Character> R = new HashSet<Character>(); // all attributes
HashSet<FD> F = new HashSet<FD>(); // the set of FDs

public FDS(String filename){  // 1. split FDs so each FD has a single attribute on the right
 Scanner in = null;
 try {
   in = new Scanner(new File(filename));
 } catch (FileNotFoundException e){
    System.err.println(filename + " not found");
    System.exit(1);
 }
 String line = in.nextLine();
 for (int i = 0; i < line.length(); i++) R.add(line.charAt(i));
 while (in.hasNextLine()){
   HashSet<Character> l = new HashSet<Character>();
   String[] terms = in.nextLine().split(" ");
   for (int i = 0; i < terms[0].length(); i++) l.add(terms[0].charAt(i));
   for (int i = 0; i < terms[1].length(); i++) F.add(new FD(l, terms[1].charAt(i)));
 }
 in.close();
}

HashSet<Character> string2set(String X){
 HashSet<Character> Y = new HashSet<Character>();
 for (int i = 0; i < X.length(); i++) Y.add(X.charAt(i));
 return Y;
}

void printSet(Set<Character> X){
 for (char c: X) System.out.print(c);
}

HashSet<Character> closure(HashSet<Character> X){ // Algorithm to find Closure
 HashSet<Character> Xplus = new HashSet<Character>(X); // 2. initialize
 int len = 0;
 do { // 3. push out
   len = Xplus.size();
   for (FD fd: F)
     if (Xplus.containsAll(fd.lhs) && !Xplus.contains(fd.rhs)) Xplus.add(fd.rhs);
 } while (Xplus.size() > len);  
 return Xplus; // 4. found closure of X
}

boolean follows(FD fd){
	//we compute the closure of lhs of fd and check whether it contains the rhs of fd 
		HashSet<Character> closure = this.closure(fd.lhs);
		return closure.contains(fd.rhs);
}

boolean covers(FDS T){
	//We take a FDS object as input and then check whether all the FDs of this object follow the FDS F
	boolean isCovers = true;
	for(FD fd: T.F)
	{
		if(!this.follows(fd))
		{
			isCovers = false;
			break;
		}
	}
	return isCovers;
}

boolean equivalent(FDS T){
	//if T follows F and F follows T then they are equivalent
		return (this.covers(T) && T.covers(this));
}

/*HashSet<Character> findAKey(){
	We obtain the superkey by checking whether the closure of lhs of any FD contains
	 all the attributes of the relation 
	HashSet<Character> superKey = new HashSet<Character>();
	HashSet<Character> key = new HashSet<Character>();
	for(FD f : this.F)
	{
		if(this.closure(f.lhs).containsAll(this.R)) 
		{
			superKey = f.lhs; 
			break;
		}
	}
	// we now check the minimal of this superkey
	key.addAll(superKey);
	for(Character ch : superKey)
	{
		if(this.closure(ch).containsAll(this.R))
		{
			key = null;
			key.add(ch);
			break;	
		}	
	}
	return key;	
}*/

public static void main(String[] args){
 FDS fds = new FDS("E://Oxygen Workspace/example3_8.txt");
 HashSet<Character> X = fds.string2set("D");
 fds.printSet(fds.closure(X));
 
 HashSet<Character> l = new HashSet<Character>();
 l.add('A');
 l.add('B');
 FD fd = new FD(l, 'D');
 System.out.println(fds.follows(fd));
}
}
