package com.share.service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/* 
 * java气泡提示效果 
 * @author noobjava 
 * @version 1.0 
 * @since JDK1.6(建议) 
 * 
 */

import com.share.fram.TipWindow;

public class SendTips extends Thread {
	private Map<String, String> feaMap = null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date now = new Date();
	
	public SendTips(String notic) {
		feaMap = new HashMap<String, String>();
		feaMap.put("name", "通知");
		feaMap.put("release", dateFormat.format(now));
		feaMap.put("feature", notic);
		super.start();
	}

	public void run() {
			final TipWindow tw = new TipWindow(300, 220);
			tw.setTitle("网站推送助手通知");
			JPanel headPan = new JPanel();
			JPanel feaPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JPanel btnPan = new JPanel();
			JButton update = new JButton("确定");
			
			// feaPan.setBorder(BorderFactory.createMatteBorder(1, 2, 3, 0,
			// Color.gray));
			JLabel head = new JLabel(feaMap.get("name") + ",含以下功能");
			head.setPreferredSize(new Dimension(250, 30));
			head.setForeground(Color.black);
			JTextArea feature = new JTextArea(feaMap.get("feature"));
			feature.setEditable(false);
			feature.setForeground(Color.red);
			feature.setFont(new Font("宋体", Font.PLAIN, 18));
			// feature.setBackground(Color.ORANGE);
			
			feature.setPreferredSize(new Dimension(280, 60));
			
			JScrollPane jfeaPan = new JScrollPane(feature);
			jfeaPan.setPreferredSize(new Dimension(283, 80));
			// jfeaPan.setBorder(null);
			jfeaPan.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			
			JLabel releaseLabel = new JLabel("发布日期" + feaMap.get("release").substring(0, 19));
			releaseLabel.setForeground(Color.gray);
			
			feaPan.add(jfeaPan);
			feaPan.add(releaseLabel);
			//headPan.add(head);
			btnPan.add(update);
			tw.add(headPan, BorderLayout.NORTH);
			tw.add(feaPan, BorderLayout.CENTER);
			tw.add(btnPan, BorderLayout.SOUTH);
			
			update.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tw.dispose();
				}
			});
			tw.setAlwaysOnTop(true);
			tw.setResizable(false);
			tw.setVisible(true);
			tw.run();
	}
}