package chat;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class test {

	public static void main(String[] args) {
		String str = " Hello, this is a sample       string!.";
		String a = "hello";
		String b = "hello-*";
		PriorityQueue<Integer> d = new PriorityQueue<Integer>();
		d.add(2);
		d.add(1);
			System.out.println(d.poll());
		 /*
		System.out.println("'" + str.replaceAll("[,!?;,.]", " ").replaceAll(" +", " ").replaceFirst(" +$", "") + "'");
		b = b.replaceFirst("\\*", "");
		System.out.println("b = '" + b + "'");
		System.out.println(d.length);

		LinkedList<String> c =  new LinkedList<String>();
		c.push("as");
		c.push("Hello");
		System.out.println(c.contains("ass"));
		System.out.println(c.contains("ass".replaceFirst("s", "")));
		*/
	}
}
