import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
/*BinaryHuffheap heap=new BinaryHuffheap();	
BinaryHuffTreeNode node=new BinaryHuffTreeNode("a", 40);
heap.insertMIN(node);
node=new BinaryHuffTreeNode("b", 15);
heap.insertMIN(node);
node=new BinaryHuffTreeNode("c", 20);
heap.insertMIN(node);
node=new BinaryHuffTreeNode("d", 6);
heap.insertMIN(node);
node=new BinaryHuffTreeNode("e", 4);
heap.insertMIN(node);
BinaryHuffTreeNode nodeElement=createTree(heap);
//System.out.println(nodeElement.frequency+" "+nodeElement.data);
printHufftree(nodeElement);*/
public class Huffman {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file=new File("C:\\Users\\USER\\Desktop\\Fidel.txt");
		HashMap<Character, FrequencyCode> map=fileReadCreateHashMap(file);
		BinaryHuffTreeNode nodeElement=crateHuffheapUsingMap(map);
		//printHufftree(nodeElement);
		generateCode(nodeElement,map,"");
		HashMap<String, Character> reverseMap=createCodeCharMap(map);
		String byteString=createStringFileRead(map,file);
		//System.out.println(byteString);
		ArrayList<Byte> byteCode=createByteCode(byteString);
		//System.out.println(byteCode.get(0));
		//System.out.println(Integer.toBinaryString(byteCode.get(0)%256));
		byte arr[]=new byte[byteCode.size()];
		//System.out.println(arr.length);
		for(int i=0;i<arr.length;i++)
		{
			arr[i]=byteCode.get(i);
		}
		File file2=new File("C:\\Users\\USER\\Desktop\\byteCode1.txt");
		writeBytes(file2,arr);
		//extractBytes(file2);
		//System.out.println(file2.length());
		extractBytesWriteFile(file2,reverseMap);
		//System.out.println("mappppppp"+map.get('y').code);
		//stringOperation("01010010", reverseMap);
		//printMap(reverseMap);
	}
	//01010010
	private static void extractBytesWriteFile(File file2, HashMap<String, Character> reverseMap) {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = null;
	      byte[] bFile = new byte[(int) file2.length()];
	      try
	      {
	         fileInputStream = new FileInputStream(file2);
	         fileInputStream.read(bFile);
	         fileInputStream.close();
	         String  ByteString="";
	         for (int i = 0; i < bFile.length; i++)
	         {
	            String presenByte=Integer.toBinaryString(bFile[i]%256);
	            if(presenByte.length()<8)
	            {
	            	//System.out.println(bFile[i]+"  "+presenByte+"  "+presenByte.length());
	            	String a=presenByte;
	            	for(int j=8;j>presenByte.length();j--)
	            	{
	            		//System.out.println(j);
	            		a='0'+a;
	            	}
	            	presenByte=a;
	            }
	            else if(presenByte.length()>8)
	            {
	            	presenByte=presenByte.substring(presenByte.length()-8);
	            }
	          //  System.out.println(presenByte+" "+bFile[i]);
	            ByteString+=presenByte;
	         }
	         //System.out.println(ByteString);
	         //System.out.println(ByteString.length());
	         extractPrint(ByteString,reverseMap);
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	}
	private static void extractPrint(String byteString, HashMap<String, Character> reverseMap) {
		// TODO Auto-generated method stub
		while(byteString.length()>8)
		{
			String exp="";
			int i=0;
			Character c;
			for(;i<byteString.length();i++)
			{
				exp+=byteString.charAt(i);
				if(reverseMap.get(exp)!=null)
				{
					c=reverseMap.get(exp);
					System.out.print(c);
					break;
				}
			}
			byteString=byteString.substring(i+1);
		}
		//System.out.println(byteString);
	}
	private static void extractBytes(File file2) {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = null;
	      byte[] bFile = new byte[(int) file2.length()];
	      try
	      {
	         fileInputStream = new FileInputStream(file2);
	         fileInputStream.read(bFile);
	         fileInputStream.close();
	         for (int i = 0; i < bFile.length; i++)
	         {
	            System.out.print(bFile[i]);
	         }
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	}
	private static void writeBytes(File file2, byte[] arr) {
		// TODO Auto-generated method stub
		 FileOutputStream fos;
		try {
			fos = new FileOutputStream(file2);
			fos.write(arr);
		    fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
	}
	private static ArrayList<Byte> createByteCode(String byteString) {
		// TODO Auto-generated method stub
		ArrayList<Byte> bytecode=new ArrayList<>();
		int bytesum=0;
		while(byteString.length()>=8)
		{	bytesum=0;
			String presentByte=byteString.substring(0, 8);
			//System.out.println(presentByte);
			byteString=byteString.substring(8);
			for(int i=0;i<presentByte.length();i++)
			{
				bytesum+=(presentByte.charAt(i)-'0')*Math.pow(2, presentByte.length()-i-1);
			}
			//System.out.println(presentByte+"  "+(byte)(bytesum));
			bytecode.add((byte)(bytesum));
		}
		bytesum=0;
		String a="";
		for(int i=0;i<8-byteString.length();i++)
		{
			a+='0';
		}
		byteString+=a;
		for(int i=0;i<byteString.length();i++)
		{
			bytesum+=(byteString.charAt(i)-'0')*Math.pow(2, byteString.length()-i-1);
		}
		//System.out.println(byteString+"  "+(byte)(bytesum));
		bytecode.add((byte)(bytesum));
		return bytecode;
	}
	private static String createStringFileRead(HashMap<Character, FrequencyCode> map,File file) {
		// TODO Auto-generated method stub
		String codeString="";
		BufferedReader reader;
			try {
				reader = new BufferedReader(
					    new InputStreamReader(
					        new FileInputStream(file),
					        Charset.forName("UTF-8")));
				int c;
				while((c = reader.read()) != -1) {
					  char character = (char) c;
					  //System.out.println(map.get(character).code);
					  codeString+=map.get(character).code;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		return codeString;
	}
	private static HashMap<String, Character> createCodeCharMap(HashMap<Character, FrequencyCode> map) {
		// TODO Auto-generated method stub
		HashMap<String, Character> revmap=new HashMap<>();
		for (Entry<Character, FrequencyCode> entry : map.entrySet()) {
			Character key = entry.getKey();
			String value = entry.getValue().code;
			revmap.put(value, key);
		}
		return revmap;
	}
	private static void generateCode(BinaryHuffTreeNode nodeElement, HashMap<Character, FrequencyCode> map,String string) {
		boolean leftFlag=false,rightFlag=false;
		if(nodeElement.left!=null)
		{
			leftFlag=true;
			generateCode(nodeElement.left, map, string+"0");
		}
		if(nodeElement.right!=null)
		{
			rightFlag=true;
			generateCode(nodeElement.right, map, string+"1");
		}
		if(!(leftFlag||rightFlag))
		{
			FrequencyCode node=map.get(nodeElement.data);
			FrequencyCode newNode=new FrequencyCode();
			newNode.frequency=node.frequency;
			newNode.code=string;
			//System.out.println(node.code+"    "+node.frequency+"   "+nodeElement.data);
			map.put(nodeElement.data, newNode);
			return;
		}
	}
	public static void printMap(HashMap<String, Character> map)
	{
		for (Entry<String,Character> entry : map.entrySet()) {
			String key = entry.getKey();
			Character value = entry.getValue();
			System.out.println(key+"    "+value);
		}
	}
	private static BinaryHuffTreeNode crateHuffheapUsingMap(HashMap<Character, FrequencyCode> map) {
		BinaryHuffheap heap=new BinaryHuffheap();
		for (Entry<Character, FrequencyCode> entry : map.entrySet()) {
		Character key = entry.getKey();
		Integer value = entry.getValue().frequency;
		BinaryHuffTreeNode node=new BinaryHuffTreeNode(key, value);
		heap.insertMIN(node);
		}
		BinaryHuffTreeNode root=createTree(heap);
		return root;
	}
	private static HashMap<Character, FrequencyCode> fileReadCreateHashMap(File file) {
		// TODO Auto-generated method stub
		HashMap<Character, FrequencyCode> map=new HashMap<>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(
				    new InputStreamReader(
				        new FileInputStream(file),
				        Charset.forName("UTF-8")));
			int c;
			while((c = reader.read()) != -1) {
			  char character = (char) c;
			  if(map.get(character)!=null)
			  {
				  if(map.get(character).frequency!=0)
				  {
					  FrequencyCode node=map.remove(character);
					  node.frequency+=1;
					  map.put(character, node);
				  }
			  }
			  else
			  {
				  FrequencyCode node=new FrequencyCode();
				  node.frequency=1;
				  node.code="";
				  map.put(character,node);
			  }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/*private static void printHufftree(BinaryHuffTreeNode node) {
		// TODO Auto-generated method stub
		Queue<BinaryHuffTreeNode> queue=new Queue<>();
		queue.enque(node);
		while(!queue.isEmpty())
		{
			BinaryHuffTreeNode currnode=queue.deque();
			String print=currnode.frequency+" ( "+currnode.data+" )"+" :";
			if(currnode.left!=null)
			{
				print+=currnode.left.frequency+" ( "+currnode.left.data+" )"+",";
				queue.enque(currnode.left);
			}
			if(currnode.right!=null)
			{
				print+=currnode.right.frequency+" ( "+currnode.right.data+" )";
				queue.enque(currnode.right);
			}
			System.out.println(print);
		}
	}*/
	private static BinaryHuffTreeNode createTree(BinaryHuffheap heap) {
		// TODO Auto-generated method stub
		while(!heap.isEmpty())
		{
			BinaryHuffTreeNode firstNode=heap.removeMIN();
			if(heap.isEmpty())
			{
				return firstNode;
			}
			BinaryHuffTreeNode secondnode=heap.removeMIN();
			BinaryHuffTreeNode newNode=new BinaryHuffTreeNode(null, firstNode.frequency+secondnode.frequency);
			newNode.left=firstNode;
			newNode.right=secondnode;
			heap.insertMIN(newNode);
		}
		return null;
	}
	
}