package Astar;


public class Node {
	
	//variables that represent the co-ordinates of this node
	private int xCoord;
	private int yCoord;
	
	//variables that represent the parent co-ords of this node
	private int parentXCoord;
	private int parentYCoord;
	
	//variables that are used in the a* algorithm
	private int f;//this is a cost of the node calculated by adding g+h
	private int g;//this is the cost of getting to the node from the start node
	private int h;//this is the cost of getting to the end node from this node.
	
	private boolean isClosed;// this is true if the node is not viable to use anymore
	//private boolean isWall;
	
	public Node() //this constructor is used only for the initial initialisation of the array.
	{
		isClosed=false;
	}
	
	public Node(int xCoord, int yCoord)
	{
		//each node on initialisation is set to open and is assigned  co-ordinates that represent the square they correspond to in the map array
		isClosed=false;
		this.xCoord=xCoord; 
		this.yCoord=yCoord;
	}

	public void setIsClosed()
	{
		isClosed=true;
	}
	
	public boolean getIsClosed()
	{
		return isClosed;
	}
	
	public void setG(int value)
	{
		g=value;
	}
	
	public void setF(int value)
	{
		f=value;
	}
	public void setH(int value)
	{
		h=value;
	}
	
	public int getXCoord()
	{
		return xCoord;
	}
	
	public int getYCoord()
	{
		return yCoord;
	}
	
	public int getFValue()
	{
		return f ;
	}
	
	public int getGValue()
	{
		return g;
	}
	
	public int getHValue()
	{
		return h;
	}
	
	public int getParentXCoord()
	{
		return parentXCoord;
	}
	
	public int getParentYCoord()
	{
		return parentYCoord;
	}
	
	public void setParentXCoords(int x,int y)
	{
		this.parentXCoord=x;
		this.parentYCoord=y;
	}
	
}

