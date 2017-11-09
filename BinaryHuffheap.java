import java.util.*;
public class BinaryHuffheap {
	ArrayList<BinaryHuffTreeNode> Huffheap=new ArrayList<>();
	public int size()
	{
		return Huffheap.size();
	}
	public boolean isEmpty()
	{
		return size()==0;
	}
	public BinaryHuffTreeNode ele()
	{
		if(size()==0)
		{
			return null;
		}
		return Huffheap.get(0);
	}
	public void insertMIN(BinaryHuffTreeNode node)
	{
		
		Huffheap.add(node);
		int childIndex=size()-1;
		int parentIndex=(childIndex-1)/2;
		while(childIndex>0)
		{
			BinaryHuffTreeNode parentNode=Huffheap.get(parentIndex);
			BinaryHuffTreeNode childNode=Huffheap.get(childIndex);
			if(parentNode.frequency>childNode.frequency)
			{
				Huffheap.set(parentIndex, childNode);
				Huffheap.set(childIndex, parentNode);
				childIndex=parentIndex;
				parentIndex=(childIndex-1)/2;
			}
			else
			{
				return;
			}
		}
	}
	public BinaryHuffTreeNode removeMIN()
	{
		if(size()==0)
		{
			return null;
		}
		BinaryHuffTreeNode temp=Huffheap.get(0);
		Huffheap.set(0, Huffheap.get(Huffheap.size()-1));
		Huffheap.remove(Huffheap.size()-1);
		if(Huffheap.size()!=0)
		{
			heapifyMIN(0);
		}
		return temp;
	}
	private void heapifyMIN(int index) {
		int leftIndex=2*index+1;
		int rightIndex=2*index+2;
		if(leftIndex>size()-1&&rightIndex>size()-1)
		{
			return;
		}
		int minIndex=index;
		if(Huffheap.get(minIndex).frequency>Huffheap.get(leftIndex).frequency)
		{
			minIndex=leftIndex;
		}
		if((rightIndex<(size()-1)&&(Huffheap.get(minIndex).frequency>Huffheap.get(rightIndex).frequency)))
		{
			minIndex=rightIndex;
		}
		if(minIndex==index)
		{
			return;
		}
		BinaryHuffTreeNode temp=Huffheap.get(minIndex);
		Huffheap.set(minIndex, Huffheap.get(index));
		Huffheap.set(index, temp);
		heapifyMIN(minIndex);
		
	}
}