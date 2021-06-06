package Astar;


import java.awt.Graphics;
import java.math.*;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.Image;

public class BadGuy {
	
	Image myImage;
	int x=0,y=0;
	boolean hasPath=false;
	Node[][] nodeArray= new Node[40][40];
	LinkedList<Node>closedNodes=new LinkedList();
	Stack stack=new Stack();

	public BadGuy( Image i ) {
		myImage=i;
		x = 30;
		y = 10;
		
		
	}
	
	public void reCalcPath(boolean map[][],int targx, int targy) {
		// TO DO: calculate A* path to targx,targy, taking account of walls defined in map[][]
		//hasPath=false;
		
		
		closedNodes.clear();
		
		
		System.out.println("Recalculating path!");
		//initialising the nodeArray with co-ordinates
		for(int i=0;i<40;i++)
		{
			for(int j=0;j<40;j++)
			{
				//int xCoord= i*20;
				//int yCoord= j+20;
				nodeArray[i][j]=new Node(i,j);//INITIALISING THE NODE and passing in co-ordinates
				
				if(map[i][j]==true)//if it is false, it means it is a wall
				{
					nodeArray[i][j].setIsClosed(); //this node would be an unwalkable node
					
					//closedNodes.add(nodeArray[i][j]);//adding the wall nodes to the closed list
					
					System.out.println("INDEXES: "+i+" and "+j+" are closed");
				}
			}
		}
		
		LinkedList <Node>openNodes=new LinkedList(); //declaring a linked list for the nodes that are open.

		
		
		//CALCULATING VALUES FOR START NODE;
		Node startNode=nodeArray[x][y]; //this represensts the starting node
		Node targetNode=nodeArray[targx] [targy];
		
		startNode.setG(0); //COST IS 0 FOR START NODE
		
		int startNodeX= x;
		int startNodeY= y;
		int targetNodeX=targx;
		int targetNodeY=targy;
		int hOfStartNode=0;
		
		hOfStartNode= Math.max( Math.abs(targetNodeX-startNodeX), Math.abs(targetNodeY-startNodeY) );
		//hOfStartNode=Math.abs(targetNodeX-startNodeX)+Math.abs(targetNodeY-startNodeY);
		
		
		int fOfStartNode=0+hOfStartNode;
		
		startNode.setH(hOfStartNode);
		startNode.setF(fOfStartNode);
		
		openNodes.add(startNode); //adding startNode to the open list of nodes.
		
		//BEGINNING OF THE ACTUAL ALGORITHM
		
		int lowestFValue=10000;//arbitrary number as for the first run only StartNode is in the list.
		
		//Node currentNode=startNode;//this is the node that we are expanding
		
		System.out.println("Start node co-ordinates are: "+startNode.getXCoord()+" and "+startNode.getYCoord());
		System.out.println("Target node co-ordinates are: "+targetNode.getXCoord()+" and "+targetNode.getYCoord());
		
		//the code below should be enclosed in a do-while loop that runs while currentNode!=targetNode.
		Node currentNode=startNode;
		
		int nodesExplored=0;
		
		//BEGINNING THE ALGORITHM LOOP
		int iterator=0;
		
		do {
			
			//FIND THE NODE WITH THE LOWEST F VALUE IN THE OPENLIST.
			
		for(Node node:openNodes) //iterate through each node in the openNodes list
		{
		  // System.out.println("\n\nThe open Node right now is: "+ node.getXCoord()+","+node.getYCoord());
			if(node.getFValue()<lowestFValue)
			{
				lowestFValue=node.getFValue();	
				currentNode=node;
				//System.out.println("Most promising F value is : "+node.getFValue());
				
			}
			
	
		}//this loop is used to find the node with the smallest f value
		
		
		System.out.println("\n\nThe most promising node is at the indexes: "+currentNode.getXCoord()+" "+currentNode.getYCoord());
		System.out.println("The f value of the most promising node is: "+currentNode.getFValue());
		
		
		
		//the code below is for checking each of the neighbours of the currentNode and calculating the g,h and f costs
			int neighboursCount=0;
			
		
		  	for(int i=-1;i<2;i++)
		    {
		  		//ignore cases where index can go out of bounds
			
			 for(int j=-1;j<2;j++)
		     {
			  //getting the current Neighbour out of a possible 8 neighbours.
				 
				 //getting rid of the possibility of IndexOutOfBounds
				 if((currentNode.getXCoord()+i==40||currentNode.getXCoord()+i==-1))
				 {
					 System.out.println("Invalid index ");
					 continue;
				 }
			     if(currentNode.getYCoord()+j==40||currentNode.getYCoord()+j==-1)
			     {
			    	 
			    	 System.out.println("Invalid index ");
				  continue;
			     }
			     
			     //initialising a new Node that represent the current Neigbour.
			 // System.out.println("Neighbour index is: "+(currentNode.getXCoord()+i)+"and "+(currentNode.getYCoord()+j));
			  
			  
			  Node currentNeighbourNode= nodeArray[(currentNode.getXCoord()+i)][(currentNode.getYCoord()+j)];
			 
			  //if the node is a wall do nothing
			  if(map[currentNode.getXCoord()+i][currentNode.getYCoord()+j]==true/*||closedNodes.contains(currentNeighbourNode)*/)//checking if one of the neighbours is a wall.
			  {
				  System.out.println("Wall encountered at block : "+(currentNode.getXCoord()+i)+","+(currentNode.getYCoord()+j));
				  continue;
			  }
			  
			  //if the neighbour is the node itself check the next neighbour
			   if(currentNode.getXCoord()==currentNeighbourNode.getXCoord() && currentNode.getYCoord()==currentNeighbourNode.getYCoord())
			  {
				 // System.out.println("The current Node is not a neighbour of itself");
				  continue;
			  }
			   
			  
			  
			  //if the current neighbour being checked is not closed and is not already part of the open list
			  else  if(currentNeighbourNode.getIsClosed()==false && !(openNodes.contains(currentNeighbourNode) && !(closedNodes.contains(currentNeighbourNode)) )  )  
			  {
				  
				nodesExplored++;
				
				//add currentNeighbourNode to the list of open nodes and expand it
				  openNodes.add(currentNeighbourNode);
				  
				  System.out.println(currentNeighbourNode.getXCoord()+","+currentNeighbourNode.getYCoord()+" has been added to the open list");
				  
				  
				  
				  //CALCULATING G VALUES FOR THE NEIGHBOUR NODE
			  
				  //calculating when a diagonal move is necessary. IF A DIAGONAL MOVE IS REQUIRED, X AND Y OF NEIGBHOUR WOULD BE DIFFERENT TO X AND Y OF CURRENT NODE
			     if( (currentNode.getXCoord()>currentNeighbourNode.getXCoord()||currentNode.getXCoord()<currentNeighbourNode.getXCoord()) && (currentNode.getYCoord()<currentNeighbourNode.getYCoord()||currentNode.getYCoord()>currentNeighbourNode.getYCoord()) )
			     {
			    	 currentNeighbourNode.setG((currentNode.getGValue()+14));
			     }
			  
			  //if X co-ordinate differs and Y does not, it would mean a horizontal move is necessary
			     else if( ( (currentNode.getXCoord()>currentNeighbourNode.getXCoord() )||(currentNode.getXCoord()<currentNeighbourNode.getXCoord()) )&& (currentNode.getYCoord()==currentNeighbourNode.getYCoord()) )
			     {
			    	 
			    	 currentNeighbourNode.setG((currentNode.getGValue()+10));  //g cost is 10 for a horizontal move
			     }
		     
		     //If Y Co-ordinate differs and X does not, it would mean a vertical move is necessary
			  
			     else  if( ( (currentNode.getYCoord()>currentNeighbourNode.getYCoord() )|| (currentNode.getYCoord()<currentNeighbourNode.getYCoord() ))&&(currentNode.getXCoord()==currentNeighbourNode.getXCoord()))
			     {
			    	 currentNeighbourNode.setG((currentNode.getGValue()+10)); //g cost is 10 for a vertical move
			     }
			     
			     System.out.println("\n\nThe G value for node: "+currentNeighbourNode.getXCoord()+","+currentNeighbourNode.getYCoord()+" is"+currentNeighbourNode.getGValue());
				  
			     //CALCULATING H VALUES FOR THE NEIGHBOUR NODE
				  
			  //now calculating the h value of the currentNeighbourNode.Using Manhattan distance.
			  
			    // int hValue= Math.abs(targetNodeX-currentNeighbourNode.getXCoord())+Math.abs(targetNodeY-currentNeighbourNode.getYCoord());
			  
			     //calculating the h value of the currentNeighbourNode using Chebyshev Distance(As diagonal moves are allowed).
			    int hValue=Math.max(Math.abs(targetNodeX-currentNeighbourNode.getXCoord()),Math.abs(targetNodeY-currentNeighbourNode.getYCoord() ) );
			    
			     System.out.println("The h value of node: "+currentNeighbourNode.getXCoord()+" "+currentNeighbourNode.getYCoord()+" is "+hValue);
			     currentNeighbourNode.setH(hValue);
			  
			  //CALCULATING F VALUES FOR THE NEIGHBOUR NODE
			  //now calculate the f value of the currentNeighbourNode
			     int fValue=currentNeighbourNode.getGValue()+currentNeighbourNode.getHValue();
			  
			     currentNeighbourNode.setF(fValue);
			  
			     System.out.println("The F value of node: "+currentNeighbourNode.getXCoord()+" "+currentNeighbourNode.getYCoord()+" is "+fValue);
			     
			     System.out.println(" Finished calculating g,h and f value of the current Neighbour\n\n\n");
			     neighboursCount++;
			  
			    // System.out.println("Calculating neighbours: "+neighboursCount);
			  //adding parent node of the currentNeighbourNode
			  
			     currentNeighbourNode.setParentXCoords(currentNode.getXCoord(), currentNode.getYCoord());
			 
			     
			 
			  }//end of the else if 
		      
		   }//end of inner loop
		  
		  }//end of outer loop
		
			//closing the current Node, adding it to the closed list and removing it from the open list
			  
		  	currentNode.setIsClosed();
		  	
		  	if(currentNode==targetNode)
			 {
	
				 break;
			 }
			openNodes.remove(currentNode);//removing the currentNode from the open list
			System.out.println("Node: "+currentNode.getXCoord()+", "+currentNode.getYCoord()+"has been removed from the open list");
		  	closedNodes.add(currentNode);
		  	
		  	lowestFValue=1000;//VERY IMPORTANT
			
		  
		  System.out.println("Open List size is: "+openNodes.size());
		  System.out.println("Closed List size is: "+closedNodes.size());
		  
		  
		 
		  
		 if(openNodes.size()==0)
		 {
			 break;
		 }
		  
		  
		System.out.println("Looping");
		iterator++;
		
		}while(currentNode!=targetNode||openNodes.size()!=0);
		
		
		
		
		if(currentNode==targetNode) //this should work as they are references to the same object
		{
		System.out.println("Path found");
		System.out.println("Nodes Explored: "+nodesExplored);
		
		stack.push(currentNode); 
		
		System.out.println("The co-ordinates of the node pushed into the stack are: "+currentNode.getXCoord()+","+currentNode.getYCoord());
		while(currentNode!=startNode)
		{
			currentNode=nodeArray[currentNode.getParentXCoord()][currentNode.getParentYCoord()];
			stack.push(currentNode);
		}
		hasPath=true;
		
		}
		
		 if(openNodes.size()==0)
		  {
			  System.out.println("No Path can be found!");
			  System.out.println("Nodes Explored: "+nodesExplored);
			  hasPath=false;
		  }
		
		return;
	}
	
	

	public void move(boolean map[][],int targx, int targy) {
		
		int oldTargX=targx;
		int oldTargY=targy;
		
		//if(hasPath==false||oldTargX!=targx||oldTargY!=targy )
		if(hasPath==false)
		{
			
			reCalcPath(map,targx,targy);
		}
		
		
		
		if (hasPath) {
			// TO DO: follow A* path, if we have one defined
			
			while( !(stack.isEmpty()) )
			{
			Node nextStep=(Node)stack.pop();
			
			
			x=nextStep.getXCoord();
			y=nextStep.getYCoord();
			
			
			System.out.println(" \n\nX co-ord of path is:"+x);
			System.out.println(" Y co-ord of path is:"+y);
		    
			
			
			}
			
			
		}
		/*else {
			// no path known, so just do a dumb 'run towards' behaviour
			int newx=x, newy=y;
			if (targx<x)
				newx--;
			else if (targx>x)
				newx++;
			if (targy<y)
				newy--;
			else if (targy>y)
				newy++;
			if (!map[newx][newy]) {
				x=newx;
				y=newy;
			}
		}*/
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, x*20, y*20, null);
	}
	
}


