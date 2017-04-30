package com.share.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenUrl {
	public static void open(String url) {
		//启用系统默认浏览器来打开网址。  
        try {  
            URI uri = new URI(url);  
            Desktop.getDesktop().browse(uri);  
        } catch (URISyntaxException e) {  
            e.printStackTrace();
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
	}
}
