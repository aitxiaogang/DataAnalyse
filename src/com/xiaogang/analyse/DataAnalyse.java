package com.xiaogang.analyse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

public class DataAnalyse {
	
	private int persent = 85;
	
	public Map<String, List<String>> analyse() throws Exception {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("data");
		Scanner scanner = new Scanner(resourceAsStream);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			List<String> list = map.get(line);
			if (map.size() < 1) {
				list = new ArrayList<String>();
				list.add(line);
				map.put(line, list);
			}else{
				Set<Entry<String,List<String>>> entrySet = map.entrySet();
				int size = entrySet.size();
				int index = 1;
				for (Entry<String, List<String>> entry : entrySet) {
					String key = entry.getKey();
					double calLike = calLike(key, line);
					if (calLike >= persent) {
						list = map.get(key);
						if (list == null) {
							list = new ArrayList<String>();
							map.put(line, list);
						}
						list.add(line);
						break;
					}else if(index == size){
						list = new ArrayList<String>();
						list.add(line);
						map.put(line, list);
					}
					index ++;
				}
			}
		}
		return map;
	}

	
	@Test
	public void testResult() throws Exception {
		Map<String, List<String>> map = analyse();
		Set<Entry<String,List<String>>> entrySet = map.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			List<String> value = entry.getValue();
			System.out.println(entry.getKey()+":"+value.size());
			for (String string : value) {
				System.out.println("                                 "+string);
			}
		}
	}
	
	@Test
	public void testSSS() throws Exception {
		
		String string = "(BOSSID:ACAZ25128,PRODUCTID:qwc000000000023)失败原因:java.net.SocketException:SocketClosed";
		String string2 = "(BOSSID:ACAZ25477,PRODUCTID:qwc000000000102)失败原因:java.net.SocketTimeoutException:Readtimedout";
		System.out.println(calLike(string, string2));
	}
	
	public double calLike(String str1, String str2) throws Exception {
		char[] charArray = str1.toCharArray();
		char[] charArray2 = str2.toCharArray();
		// 相似=相似字符个数/大字符数组长
		double like = 0;
		if (charArray.length > charArray2.length) {
			double eq = 0;
			double len = charArray.length;
			for (char c : charArray2) {
				if (str1.contains(c + "")) {
					eq++;
				}
			}
			like = eq / len;
		} else {
			double eq = 0;
			double len = charArray2.length;
			for (char c : charArray) {
				if (str2.contains(c + "")) {
					eq++;
				}
			}
			like = eq / len;
		}
		return like*100;
	}
	@Test
	public void testLike() throws Exception {
		String str1 = "abcde1aaa";
		String str2 = "abcde1";
		double calLike = calLike(str1, str2);
		System.out.println(calLike);
	}
}
