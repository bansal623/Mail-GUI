import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.net.URL;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.sql.*;

class Gmail implements ActionListener , TreeSelectionListener,ListSelectionListener
{
	private static final long serialVersionUID=1L;
	JFrame f,f1; 
	JScrollPane jsp1,jsp2,jsp3,jsp4;       
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15; 
	JLabel l1,l2;    
	JPanel p1,p2,p3,p4,p5,p6,p7,p8;
    JTextField jt1,jt2; 
    JTextArea jta1; 
	private JTree tree;                    
	JSplitPane sp1;    	        
	DefaultTableModel model;
	JTable table;                         
    Message msg; 	
	Properties props;  	                   
	Session session;             
	String from,pwd,to,sub,body,no,date;
	Connection con;                        
	Statement stmt;				
	int no1;
	String dm;
	ResultSet rs;
	Folder inboxFolder; 				   
	Store store;          		
	String username,password,where;   	
	String arr1[],arr2[],arr3[],arr4[],arr5[];	 
	int flag=0;	 
	
	Stack st=new Stack();
		
	class MyDefaultTableModel extends DefaultTableModel
	 {
	    MyDefaultTableModel(Object[][] data, Object[] columnNames) 
	    {
	    	super(data, columnNames);
	    }
	    
			@Override
		public Class getColumnClass(int columns)
			{
			switch(columns)
				{
			case 0: return Integer.class;
			case 1: return String.class;
			case 2: return String.class;
			case 3: return String.class;
			case 4: return String.class;
			case 5: return Boolean.class;
			default: return Boolean.class;				
				}
			}
	    public boolean isCellEditable(int row, int columns) 
	    {
	    	if(columns==5)
	    	return true;
	    	else 
	    	return false;
	    }
	 }
	 
	 public Gmail()
	{	
		arr1=new String[100];arr2=new String[100];
		arr3=new String[100];arr4=new String[100];arr5=new String[100];
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	        con=DriverManager.getConnection("Jdbc:Odbc:Mail");
		  	stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		}
		catch(Exception e5)
		{
			e5.printStackTrace();
		}
		f=new JFrame();
	    f.setSize(700,500);
	    b1=new JButton("Compose");              
		b2=new JButton("Receive");              
		b3=new JButton("Receive All");
		b4=new JButton("Forward");
		b5=new JButton("Delete");
	    b6=new JButton("Delete All");
	    b7=new JButton("Restore");
	    b8=new JButton("Restore All");
	    b1.addActionListener(this);
		b2.addActionListener(this);
	    b3.addActionListener(this);
	    b4.addActionListener(this);
	    b5.addActionListener(this);
	    b6.addActionListener(this);
	    b7.addActionListener(this);
	    b8.addActionListener(this);
	    l1=new JLabel();  l2=new JLabel();
	    JTextField t1=new JTextField();
	    p1=new JPanel(new GridLayout(2,4));
	    p1.add(b1); p1.add(b2);p1.add(b3);p1.add(b4);p1.add(b5);p1.add(b6);p1.add(b7);p1.add(b8);
	    p2=new JPanel();
	    p2.add(p1);
	    jsp1=new JScrollPane(p2);
	    jsp1.setMinimumSize(new Dimension(200,200));
	    jsp1.setPreferredSize(new Dimension(500,500));  
	    Object rows[][] = {
	    			
						  };
					  
		String columns[] = {"no","To" ,"Subject", "Date","Content","Checkbox"};
		model = new MyDefaultTableModel(rows, columns);
//		table = new JTable(model)
//		{
//		//	private static final long serialVersionUID = 1L;
//			@Override
//		public Class getColumnClass(int columns)
//			{
//			switch(columns)
//				{
//			case 0: return Integer.class;
//			case 1: return String.class;
//			case 2: return String.class;
//			case 3: return String.class;
//			case 4: return String.class;
//			case 5: return Boolean.class;
//			default: return Boolean.class;				
//				}
//			}
//		}; 	
		table=new JTable(model);
		//	table.setPreferredScrollableViewportSize(table.getPreferredSize());	
      	p7=new JPanel(new GridLayout(5,1));
      	b11=new JButton("Inbox"); 
      	b12=new JButton("Sent"); 
      	b13=new JButton("Draft"); 
      	b14=new JButton("Trash");
     	p7.add(b11); p7.add(b12);p7.add(b13);p7.add(b14);
      	p8=new JPanel();
      	p8.add(p7);
      	b11.addActionListener(this); 
      	b12.addActionListener(this);
      	b13.addActionListener(this);
      	b14.addActionListener(this);
      	
      	jsp3=new JScrollPane(p8);	
 		jsp4=new JScrollPane(table);
    	jsp2=new JScrollPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp3,jsp4));
    	sp1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jsp1,jsp2);
		f.add(sp1);
		jsp3.setPreferredSize(new Dimension(200,200));
	    f.setVisible(true);
        f1=new JFrame();
        f1.setSize(700,300);
        p3=new JPanel(new GridLayout(2,2));
        l1=new JLabel("To");   jt1=new JTextField(20);
        p3.add(l1);  p3.add(jt1);
        l2=new JLabel("Subject");    jt2=new JTextField(20); 
        p3.add(l2);  p3.add(jt2);
       	p4=new JPanel();
        p4.add(p3);
        p5=new JPanel(new GridLayout(1,3));
        b9=new JButton("Send");   b10=new JButton("Save");  b11=new JButton("Cancel");
        b9.addActionListener(this); 
        b10.addActionListener(this);
        b11.addActionListener(this);
        p5.add(b9);  p5.add(b10);  p5.add(b11);
       	jta1=new JTextArea();
       	f1.add(jta1);
       	f1.add(p4,BorderLayout.NORTH);
        f1.add(p5,BorderLayout.SOUTH);
		
		from="mohit.bansal623@gmail.com";
		pwd="himanshu2931";
		props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port", "465"); // default port 25
		props.put("mail.smtp.auth","true"); 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.debug", "false");
		session = Session.getDefaultInstance(props,new SimpleMailAuthenticator(from,pwd));
 //Session.getDefaultInstance(Properties, Authenticator);   
	}
	public void actionPerformed(ActionEvent e1)
	{
		String str=e1.getActionCommand();
		
		if(str.equals("Compose"))
		{
			jt1.setText("");
			jt2.setText("");
			jta1.setText("");
			f1.setVisible(true);
		}
		if(str.equals("Send"))
		{
			if(flag==0)
			{
				if(jt1.getText().equals("")||jt2.getText().equals("")||jta1.getText().equals(""))
				{
				JOptionPane.showMessageDialog(f1,"Pl+ease Insert the desired Fields","Alert",JOptionPane.INFORMATION_MESSAGE);
				}
			flag++;
			}
		else
		{
			
		
			 to=jt1.getText();
			 sub=jt2.getText();
			 body=jta1.getText();
			try
			{
			msg=new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));		
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
			msg.setSubject(sub);
			msg.setText(body);
			Transport.send(msg);
			System.out.println("Sent Sucessfully");
			}
		catch(MessagingException me)
			{
			me.printStackTrace();
			}
			
			try
		    {
		    	 rs=stmt.executeQuery("SELECT * FROM Sent");
		    		if(rs.last())
	                 {
	    	            no=rs.getString("No");
	    	            no1=Integer.parseInt(no);
	    	            no1=no1+1;
	    	            System.out.println(jt1.getText()+" "+jt2.getText());
	                 }
	         dm=Calendar.getInstance().getTime().toString();
	    	 System.out.println(dm);        
	         String insert_Query="insert into Sent values(' " + no1 +" ',' " + jt1.getText() +" ',' " + jt2.getText() + " ',' " + dm + " ',' " + jta1.getText() + " ')";
			 stmt.execute(insert_Query);
			 JOptionPane.showMessageDialog(f1,"Your Message has been sent Successfully","Thank You",JOptionPane.INFORMATION_MESSAGE);	    
			}
			catch(Exception e4)
			{
				System.out.println(e4.getMessage());
			}
			        	
		}
		}	 
			
		if(str.equals("Save"))
		{
			try
		   {
		    rs=stmt.executeQuery("SELECT * FROM Draft");
		    if(rs.last())
	        {
	    	    no=rs.getString("No");
	    	    no1=Integer.parseInt(no);
	    	    no1=no1+1;
	        }
	    		String dm=Calendar.getInstance().getTime().toString();
	    		System.out.println(dm);
		        String insert_Query="insert into Draft values(' " + no1 +" ',' " + jt1.getText() +" ',' " + jt2.getText() + " ',' " + dm +" ',' " + jta1.getText() + " ')";
			    stmt.execute(insert_Query);	
			    JOptionPane.showMessageDialog(f1,"Your Message has sent to the Draft","Thank You",JOptionPane.INFORMATION_MESSAGE);
			    jt1.setText("");
			    jt2.setText("");
			    jta1.setText("");
			  }
				catch(Exception e4)
					{
					System.out.println(e4.getMessage());
					}	
		}  
		if(str.equals("Sent"))
		{
			st.add("Sent");
			if(model.getRowCount()!=0)
			{
				for(int i=model.getRowCount()-1;i>0;i--)
				{
					model.removeRow(i);
				}
				int k=model.getRowCount();
				System.out.println(k);
				model.removeRow(k-1);
						
			}
		
		
				try
				{
				 rs=stmt.executeQuery("SELECT * FROM Sent");
					while(rs.next())
					{
					no=rs.getString("No");
					to=rs.getString("To");
					sub=rs.getString("Subject");
					date=rs.getString("Date1");
					body=rs.getString("Body");
					}			
				}
			
						catch(Exception e)
						{
							e.printStackTrace();
						}
		}
		
		if(str.equals("Draft"))
			{
				st.add("Draft");
				 if(model.getRowCount()!=0)
				 {
				 	for(int i=model.getRowCount()-1;i>0;i--)
					{
						model.removeRow(i);
					}
						int k=model.getRowCount();
						System.out.println(k);
						model.removeRow(k-1);
						
				  }
				try
				{
				 rs=stmt.executeQuery("SELECT * FROM Draft");
					while(rs.next())
					{
					no=rs.getString("No");
					to=rs.getString("To");
					sub=rs.getString("Subject");
					date=rs.getString("Date1");
					body=rs.getString("Body");
					model.addRow(new Object[]{no,to,sub,date,body,false});	
					}
				}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if(str.equals("Inbox"))
		{
			st.add("Inbox");
			if(model.getRowCount()!=0)
				{
					for(int i=model.getRowCount()-1;i>0;i--)
					{
						model.removeRow(i);
					}
						int k=model.getRowCount();
						System.out.println(k);
						model.removeRow(k-1);
				}
		
				try
				{
				 	rs=stmt.executeQuery("SELECT * FROM Inbox");
					while(rs.next())
					{
					no=rs.getString("No");
					to=rs.getString("To");
					sub=rs.getString("Subject");
					date=rs.getString("Date1");
					body=rs.getString("Body");
					model.addRow(new String[]{no, to,sub,date,body});
					}
				}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}			

		}
		if(str.equals("Receive"))
		{
			receive();
			
		}
		if(str.equals("Trash"))
		{
			if(model.getRowCount()!=0)
				{
					for(int i=model.getRowCount()-1;i>0;i--)
					{
						model.removeRow(i);
					}
						int k=model.getRowCount();
						System.out.println(k);
						model.removeRow(k-1);
				}
				try
				{
				 rs=stmt.executeQuery("SELECT * FROM Trash");	
				 		while(rs.next())
						{
							no=rs.getString("No");
							to=rs.getString("To");
							sub=rs.getString("Subject");
							date=rs.getString("Date1");
							body=rs.getString("Body");
							where=rs.getString("Where");
							System.out.println(where);
							model.addRow(new Object[]{no, to,sub,date,body,false});
						}
			
				}
				catch(Exception e)
				{
				e.printStackTrace();
				}
		}	
			
			if(str.equals("Delete"))
			{
				int[] xa = select();
				for(int j=0; j<xa.length;j++)
				{
						System.out.println("mohit Tick No  "+xa[j]);
				
				}
				int i=0;
				int rowIndex=table.getSelectedRow();
				int colIndex=table.getSelectedColumn();
			//	System.out.println(rowIndex+"   "+colIndex);
				String m=st.peek().toString();
				try
		    		{
	    		    	rs=stmt.executeQuery("SELECT * FROM "+m);
	    		    	for(i=0;i<xa.length;i++)
	    		    	{
	    		    		String insert_Query="insert into "+m +"(Check) values('" + xa[i]+"')";
	    		    			stmt.execute(insert_Query);	
	    		    		System.out.println("Completed");
	    		    	}
			     	    rs.absolute(rowIndex+1);
	           //  	   	System.out. println(arr1[i]);
						arr1[i]=rs.getString("No");
	    	            arr2[i]=rs.getString("To");
	    	            arr3[i]=rs.getString("Subject");
	    	            arr4[i]=rs.getString("Date1");
	    	            arr5[i]=rs.getString("Body"); 
	    	        
	    	            	++i; 
	    	            	
		    		   // 	System.out.println(i);
		    		    int	p=i;
		    		    for( i=0;i<p;i++)
		    		    {
		    		    	
		    		    	String insert_Query="insert into Trash values('" + arr1[i]+"','" + arr2[i] +"','" + arr3[i]+ "','" + arr4[i]+ "','" + arr5[i] + "' , '" + m + "')";
			         		stmt.execute(insert_Query);	
			         	//	System.out.println("Mohit");		
		    		    }
		    		    
		    		    try
						{
							String delete_Query="Delete from "+m+" where No= "+rowIndex;
							stmt.execute(delete_Query);
				
						}
						
						catch(Exception e4)
						{
						e4.printStackTrace();
						}
		    		}
		    			catch(Exception e4)
						{
						e4.printStackTrace();
						}
						
						if(model.getRowCount()!=0)
						{
						model.removeRow(rowIndex);
					//	int k=model.getRowCount();
					//	System.out.println(k);
					//	model.removeRow(k-1);
						}	
						
					
				
			}
			
			if(str.equals("Restore"))
			{
				int	i=0;
				int rowIndex=table.getSelectedRow();
				int colIndex=table.getSelectedColumn();
				System.out.println(rowIndex+"   "+colIndex);
				
				try
				{
					 rs=stmt.executeQuery("SELECT * FROM Trash");
		    		    rs.absolute(rowIndex+1);
	             	    		arr1[i]=rs.getString("No");
	    	           		    arr2[i]=rs.getString("To");
	    	            		arr3[i]=rs.getString("Subject");
	    	            		arr4[i]=rs.getString("Date1");
	             	    		arr5[i]=rs.getString("Where");
	             	    		i++;
	             	    		
				}
				catch(Exception e4)
				{
							e4.printStackTrace();
				}
				int p=i;
				for(i=0;i<p-1;i++)
				{
						System.out.println(arr5[i]);
						try
						{
						 rs=stmt.executeQuery("SELECT * FROM "+arr5[i]);
						 System.out.println(arr5[i]);	
						// while(rs.next())
						// {
						 	String insert_Query="insert into " +arr5[i]+" values('" + arr1[i]+"','" + arr2[i] +"','" + arr3[i]+ "','" + arr4[i]+ "','" + arr5[i] + "' )";
						 	System.out.println();
						 	System.out.println(insert_Query);
			         		stmt.execute(insert_Query);	
			         	//		break;
					//	 }	
						}
						catch(Exception e4)
						{
							e4.printStackTrace();
						}
						
						
				}
				try
						{
							String delete_Query="Delete from Trash where No= "+rowIndex;
							stmt.execute(delete_Query);
				
						}
						
						catch(Exception e4)
						{
						e4.printStackTrace();
						}
						
						if(model.getRowCount()!=0)
						{
						model.removeRow(rowIndex);
						}
				
				
				
				
			}
			
			if(str.equals("Delete All"))
			{
				int i=0;
				String m=st.peek().toString();
				System.out.println(m+"dddddddddddddddddddddddddd");
					
					try
		    		{
		    		    rs=stmt.executeQuery("SELECT * FROM "+m);
		    		    while(rs.next())
	             	    	{
	             	   	System.out. println(arr1[i]);
						arr1[i]=rs.getString("No");
	    	            arr2[i]=rs.getString("To");
	    	            arr3[i]=rs.getString("Subject");
	    	            arr4[i]=rs.getString("Date1");
	    	            arr5[i]=rs.getString("Body"); 
	    	        
	    	            	++i; 
	    	              
		    		    	}
		    		    	System.out.println(i);
		    		    int	p=i;
		    		    for( i=0;i<p;i++)
		    		    {
		    		    	
		    		    	String insert_Query="insert into Trash values('" + arr1[i]+"','" + arr2[i] +"','" + arr3[i]+ "','" + arr4[i]+ "','" + arr5[i] + "' , '" + m + "')";
			         		stmt.execute(insert_Query);	
			         		System.out.println("Mohit");		
		    		    }
		    		    
		    		    try
						{
							String delete_Query="Delete from "+m;
							stmt.execute(delete_Query);
				
						}
						
						catch(Exception e4)
						{
						e4.printStackTrace();
						}
						
						if(model.getRowCount()!=0)
						{
						for( i=model.getRowCount()-1;i>0;i--)
						{
						model.removeRow(i);
						}
						int k=model.getRowCount();
						System.out.println(k);
						model.removeRow(k-1);
						}
							
		    		    
		    	  	}
		    	  	
	            		
				catch(Exception e4)
				{
							e4.printStackTrace();
				}
		    	  	
			}
			
			if(str.equals("Restore All"))
			{
				
			int	i=0;
				try
				{
					 rs=stmt.executeQuery("SELECT * FROM Trash");
		    		    while(rs.next())
	             	    	{
	             	    		arr1[i]=rs.getString("No");
	    	           		    arr2[i]=rs.getString("To");
	    	            		arr3[i]=rs.getString("Subject");
	    	            		arr4[i]=rs.getString("Date1");
	             	    		arr5[i]=rs.getString("Where");
	             	    		i++;
	             	    	}	
				}
				catch(Exception e4)
				{
							e4.printStackTrace();
				}
				int p=i;
				for(i=0;i<p;i++)
				{
						System.out.println(arr5[i]);
						try
						{
						 rs=stmt.executeQuery("SELECT * FROM "+arr5[i]);
						 System.out.println(arr5[i]);	
						// while(rs.next())
						// {
						 	String insert_Query="insert into " +arr5[i]+" values('" + arr1[i]+"','" + arr2[i] +"','" + arr3[i]+ "','" + arr4[i]+ "','" + arr5[i] + "' )";
						 	System.out.println();
						 	System.out.println(insert_Query);
			         		stmt.execute(insert_Query);	
			         	//		break;
					//	 }	
						}
						catch(Exception e4)
						{
							e4.printStackTrace();
						}
						
						
				}
				try
						{
							String delete_Query="Delete from Trash";
							stmt.execute(delete_Query);
				
						}
						
						catch(Exception e4)
						{
						e4.printStackTrace();
						}
						
						if(model.getRowCount()!=0)
						{
						for( i=model.getRowCount()-1;i>0;i--)
						{
						model.removeRow(i);
						}
						int k=model.getRowCount();
						System.out.println(k);
						model.removeRow(k-1);
						}
				
					
					
			}
			if(str.equals("Forward"))
			{
				int i=0;
			//	JOptionPane.showMessageDialog(f1,"Not Coded Yet","Sorry",JOptionPane.INFORMATION_MESSAGE);
				System.out.println(st);
			String m=st.peek().toString();
			System.out.println(m);
				f1.setVisible(true);
					int rowIndex=table.getSelectedRow();
			
				try
				{
					 rs=stmt.executeQuery("SELECT * FROM "+m);
		    		    rs.absolute(rowIndex+1);
	             	    	
	    	           		 // arr2[i]=rs.getString("To");
	    	            		arr3[i]=rs.getString("Subject");
	    	            		arr4[i]=rs.getString("Body");
	             	    	//	jt1.setText(arr2[i]);
	             	    		jt2.setText(arr3[i]);
	             	    		jta1.setText(arr4[i]);
	             	    		
				}
				catch(Exception e4)
				{
							e4.printStackTrace();
				}
				
				
				
			}
			
			if(str.equals("Receive All"))
			{
				JOptionPane.showMessageDialog(f1,"Not Coded Yet","Sorry",JOptionPane.INFORMATION_MESSAGE);
				receive();
			}
		
					
	}
	public void valueChanged(TreeSelectionEvent e)
	 	 {

    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    	System.out.println(node.getUserObject());
    }
    
    public void valueChanged(ListSelectionEvent e) 
    {
    	
    }
    
    void receive()
    {
    	try
			{
			 props = new Properties();
			 props.put("mail.imap.host", "imap.gmail.com");
			 props.put("mail.imap.port", "993");// default port 143
			 props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		 	 session = Session.getInstance(props,null);
			 username = "jmapidemo@gmail.com";
			 password = "rediscover";
			 store = session.getStore("imap");
			 store.connect(username,password);
		     inboxFolder = store.getFolder("inbox");
			 inboxFolder.open(Folder.READ_ONLY);
			 Message[] arr = inboxFolder.getMessages();
			 System.out.println("No of Message : "+arr.length);
			 for(int i=0; i<arr.length;i++)
			 {
				System.out.println("\n--------------------------Message"+(i+1)+"--------------------------");
				//arr[i].writeTo(System.out);
				Address[] from = arr[i].getFrom();
				System.out.println("From : " + from[0]);
				System.out.println("Subject : " + arr[i].getSubject());
				System.out.println("Date : " + arr[i].getSentDate());
				System.out.println("Message : " + arr[i].getContent());
				try
		    	{
		    		rs=stmt.executeQuery("SELECT * FROM Inbox");
		    	 	if(rs.last())
	                {
	    	           no=rs.getString("No");
	    	           no1=Integer.parseInt(no);
	    	           no1=no1+1;
	    	        }
	    	        
		    	     String insert_Query="insert into Inbox values(' " + no1 +" ',' " + from[0] +" ',' " + arr[i].getSubject()+ " ',' " + arr[i].getSentDate()+ " ',' " + arr[i].getContent() + " ')";
			         stmt.execute(insert_Query);    
			}
			catch(Exception e4)
			{
						System.out.println(e4.getMessage());
			}
				
				
				
			}
			inboxFolder.close(true);	
		//	store.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
    }
    
    	public int[] select()
			{
				int a3 = table.getRowCount();
				int[] ax= new int[a3];
				int j=0;
				for(int i=0;i<a3;i++)
				{
						if((Boolean)table.getValueAt(i,5))
						{
							ax[j]=i+1;
							j++;
						}
				}
				
				return ax;		
			}
		public static void main(String args[])
	{
	//	Gmail gm=new Gmail();
		SwingUtilities.invokeLater(new Runnable()
				{
					@Override
						public void run()
						{
							Gmail gm=new Gmail();
							
						}
				});
	}
}			