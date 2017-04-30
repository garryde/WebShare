package com.share.fram;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.share.main.Main;
import com.share.service.EstablishConnection;
import com.share.service.IdentifyCode;
import com.share.service.JsonHandle;
import com.share.service.SocketListener;

public class MainFram extends JFrame {
	private JPanel contentPane;
	private JButton contectService = null;
	private JLabel notice = null;
	private EstablishConnection establishConnection = null;
	private JTextField codeTextField;
	private IdentifyCode identifyCode = new IdentifyCode();
	private String ip;
	private Integer port;
	

	public MainFram(String ip, Integer port) {
		this.ip = ip;
		this.port = port;

		setFram();
		this.setTray();
		this.setVisible(true);
	}
	
	//设置主窗体
	public void setFram() {
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		notice = new JLabel("<html>请在下方输入框输入六位纯数字识别代码,点击连接服务器！</html>");
		contentPane.add(notice);
		
		codeTextField = new JTextField("请输入六位数字!");
		contentPane.add(codeTextField);
		codeTextField.setColumns(10);
		
		contectService = new JButton("连接服务器");
		contectService.addActionListener(new ActionListener() {
			// 连接服务器按钮点击事件
			public void actionPerformed(ActionEvent e) {
				if (Main.isConnect) {
					JOptionPane.showMessageDialog(null, "已连接服务器，或正在尝试重连，请稍候再试。");
				} else {
					//若是重连，销毁Socket
					if (Main.socket != null){
						try {
							Main.socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Main.socket = null;
					}
					//获取用户输入的识别码
					String conCode = codeTextField.getText();
					//本地判断识别码是否合法
					boolean rs = regularIdentifyCode(conCode);
					if (rs) {
						//检查Code是否存在
						boolean codeRs = identifyCode.verify(conCode);
						if (codeRs) {
							establishConnection = new EstablishConnection(ip, port, conCode);
							boolean conResult = establishConnection.connect();
							//判断连接结果
							if (conResult) {
								JOptionPane.showMessageDialog(null,"服务器连接成功,关闭窗口自动最小化至托盘！");
							} else {
								JOptionPane.showMessageDialog(null,"连接失败,请重试！");
							}
							//con.start();
						} else {
							JOptionPane.showMessageDialog(null,"识别码已存在，请更换识别码！");
							conCode = "";
							codeTextField.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null,"输入错误，请输入六位纯数字！");
						codeTextField.setText("");
					}
				}


			}
		});
		
		// 监听鼠标是否进入或退出文本框，监听鼠标左键右键事件
		codeTextField.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (codeTextField.getText().equals("请输入六位数字!")) {
					codeTextField.setText("");
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		contentPane.add(contectService);
	}
	
	// 添加托盘显示
	public void setTray() {
		//1.先判断当前平台是否支持托盘显示
		if (SystemTray.isSupported()) {
			// 创建托盘实例
			SystemTray tray = SystemTray.getSystemTray();
			// 创建托盘图标：
			// 1.创建Image图像
			//Image image = Toolkit.getDefaultToolkit().getImage("img/icon.png");
			Image image = null;
			try {
				image = ImageIO.read(getClass().getResource("/img/icon.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 2.停留提示text
			String text = "网站推送助手";
			// 3.弹出菜单popupMenu
			PopupMenu popMenu = new PopupMenu();
			MenuItem itmOpen = new MenuItem("Open");
			itmOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Show();
				}
			});
			MenuItem itmHide = new MenuItem("Show");
			itmHide.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UnVisible();
				}
			});
			MenuItem itmExit = new MenuItem("Exit");
			itmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Exit();
				}
			});
			popMenu.add(itmOpen);
			popMenu.add(itmHide);
			popMenu.add(itmExit);

			// 创建托盘图标
			TrayIcon trayIcon = new TrayIcon(image, text, popMenu);
			// 将托盘图标加到托盘上
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//正则匹配识别码
	private boolean regularIdentifyCode(String code) {
		String regex = "^[0-9]\\d{5}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(code);
		boolean rs = matcher.matches();
		return rs;
	}
	
	// 内部类中不能直接调用外部类（this不能指向）
	public void UnVisible() {
		this.setVisible(false);
	}

	public void Show() {
		this.setVisible(true);
	}

	public void Exit() {
		System.exit(0);
	}
}
