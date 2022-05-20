package com.sample.httpssample;

/**
 * {
 * 	"code": 1,
 * 	"data": {
 * 		"name": "再次爱上你",
 * 		"url": "http://music.163.com/song/media/outer/url?id=1940334604",
 * 		"picurl": "http://p4.music.126.net/PWnGbGh3-xc7ALQlOaB9lQ==/109951167325585406.jpg",
 * 		"artistsname": "WiFi歪歪"
 *        }
 * }
 * @param <T>
 */
public class Data<T> {
    private int code;
    private String message;
    private T data;
 
    public int getCode() {
        return code;
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public T getData() {
        return data;
    }
 
    public void setData(T data) {
        this.data = data;
    }
}