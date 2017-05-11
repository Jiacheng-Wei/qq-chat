package cn.seeonce.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSON;

import cn.seeonce.data.Account;
import cn.seeonce.data.XMLObject;
import cn.seeonce.library.QQMessage;
import cn.seeonce.library.QQTool;
import cn.seeonce.model.QQModel;

public class QQSignFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton        sign;
	private JTextField     username;
	private JPasswordField password;

	public QQSignFrame(){
		initAssembly();
		setTitle("See Once Sign");
		setSize(480, 320);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//默认退出  
	}
	
	public void initAssembly(){

		sign     = new JButton("Sign");
		username = new JTextField();
		password = new JPasswordField();
		
		sign.addActionListener(new ButtonEvent());
		
		username.setBounds(100, 100, 280, 30);
		password.setBounds(100, 160, 280, 30);
		sign.setBounds(200, 240, 80, 30);
		

		add(sign);
		add(username);
		add(password);
		
	}
	
	
	class ButtonEvent implements ActionListener{
		private void sign(String username, String password){
			Socket server;
			try {
				server = new Socket("localhost", 9998);
				
				ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
				ObjectInputStream  input  = new ObjectInputStream(server.getInputStream());
				//发送登录请求
				output.writeObject(QQMessage.cmSign(username, username, password));
				//接受服务器响应
				XMLObject msgXML = (XMLObject)input.readObject();
				//显示服务器返回的信息
				JOptionPane.showMessageDialog(null, msgXML.get("message"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object obj = arg0.getSource();
			
			if(obj == sign){
				sign(username.getText(), password.getText());
				System.out.println("gg");
			}
		}
		
	}

}
