package org.ranestar.test.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	//@Autowired
    //private RestTemplate restTemplate;
	
	/**
	 *	Method : SHA-1로 암호화
	 *	@param String s : 암호화할 문자열
	 *	@return String : 암호화된 문자열
	 */
	public static String sha1(String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	/**
	 *	Method : 파일 및 폴더 삭제
	 *	@param String path : 폴더명
	 *	
	 */
	public static void deleteAllFiles(String path) {

		File file = new File(path);
		// 폴더내 파일을 배열로 가져온다.
		File[] tempFile = file.listFiles();

		if (tempFile != null && tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else {
					// 재귀함수
					deleteAllFiles(tempFile[i].getPath());
				}
				tempFile[i].delete();
			}
			file.delete();
		}
	}
	
	/*
	public void sms(User user) throws Exception {
		
		String smsid = "redbb";
		String smspw = "dlwkdus";
		String dest = user.getUser_tel(); // 수신 번호 : 0~11자리까지 가능 (설정하지 않는 경우는 smsid에 설정된 휴대폰 값으로 적용됨)
		String callback = user.getUser_tel(); // 발신 번호 : 0~11자리까지 가능 (설정하지 않는 경우는 smsid에 설정된 휴대폰 값으로 적용됨)
		String smstitle = "아이디찾기"; // 전송 제목 : 50 Byte까지 가능 (설정하지 않는 경우는 smsid로 적용됨)
		String smsbody = "회원님의 ID는 ["+user.getUser_id()+"] 입니다."; // 전송 내용 : 80 Byte까지 가능 (설정하지 않는 경우는 smsid에 설정된 값으로 적용됨)

		Map params = new HashMap();
		params.put("smsid", smsid);
		params.put("smspw", smspw);
		params.put("dest", dest);
		params.put("callback", callback);
		params.put("smstitle", smstitle);
		params.put("smsbody", smsbody);

		Map result = restTemplate.postForObject("http://biz.hotsms.co.kr/smsform/sms_form_jsp.jsp", params, Map.class);
		
		return;
	}
	*/
}
	