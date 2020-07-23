package com.iiiset.fm.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {



	public static String[] nvl(String[] input) {
		if (input == null) {
			return new String[0];
		}
		return input;
	}

	public static String nvl2(String str, String defaultStr) {
		return str == null ? defaultStr
				: (str == (null)) ? defaultStr : (str == "null") ? defaultStr : (str == "(null)") ? defaultStr : str;
	}

	public static String enterToBr(String str) {
		str = replaceString(str);
		return str == null ? "" : str.replaceAll("\n", " <br/>");
	}

	public static String enterToNull(String str) {
		return str == null ? "" : str.replaceAll("\n", "");
	}


	/**
	 * toString();
	 * 
	 * @param value
	 * @return
	 */
	public static String toString(int value) {
		try {
			return value + "";
		} catch (Exception e) {
			return "";
		}
	}

	public static int toInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String lpad(int value, int length, String prefix) {
		try {
			StringBuilder sb = new StringBuilder();
			String castValue = value + "";

			for (int i = castValue.length(); i < length; i++) {
				sb.append(prefix);
			}
			sb.append(castValue);
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String rpad(int value, int length, String prefix) {
		try {
			StringBuilder sb = new StringBuilder();
			String castValue = value + "";
			sb.append(castValue);
			for (int i = castValue.length(); i < length; i++) {
				sb.append(prefix);
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String cutText(String text, int length, String suffix) {
		StringBuffer sb = new StringBuffer();

		if (!text.isEmpty()) {
			if (text.length() > length) {
				sb.append(text.substring(0, length)).append(suffix);
			} else {
				sb.append(text);
			}
		} else {
			sb.append(text);
		}
		return sb.toString();
	}


	public static String replaceText(String text, String originTxt, String replaceTxt) {
		return text.replaceAll(originTxt, replaceTxt);
	}

	public static String replaceBrTag(String text) {
		return text.replaceAll("\\n", "<br/>");
	}

	public static String removeAllTag(String txt) {
		return txt.replaceAll("(?:<!.*?(?:--.*?--\\s*)*.*?>)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)", "");
	}

	/**
	 * get String of java.util.Map keys and values to log
	 */
	public static String getDatasOfMap(Map map) {
		StringBuffer buf = new StringBuffer();
		buf.append("getDatasOfMap() =>");
		Set set = map.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			String value = (String) map.get(name);
			buf.append("[").append(name).append("|").append(value).append("]");
		}
		return buf.toString();
	}

	public static boolean isNull(String str) {
		boolean bool = true;

		if (str != null && !"".equals(str)) {
			bool = false;
		}

		return bool;
	}

	/**
	 * 검색 결과 날짜표기 MM/DD
	 * 
	 * @param value
	 * @return
	 */
	public static String historyDate(String value) {
		return !(nvl(value, "").length() == 0) && value.length() == 8
				? value = value.substring(4, 6) + "/" + value.substring(6, 8)
				: "";
	}

	/**
	 * 검색 결과 날짜표기 YYYY.MM.DD
	 * 
	 * @param value
	 * @return
	 */
	public static String historyDate2(String value) {
		return !(nvl(value, "").length() == 0) && value.length() == 8
				? value = value.substring(0, 4) + "." + value.substring(4, 6) + "." + value.substring(6, 8)
				: "";
	}

	/**
	 * 콤마 추가
	 * 
	 * @param data
	 * @return
	 */
	public static String addComma(long data) {

		return new DecimalFormat("#,###").format(data);
	}

	/**
	 * 해당 URL 의 HTML 코드를 String 으로 가져옴
	 * 
	 * @param uri
	 * @return
	 */
	public static String getSource(String uri) {
		String str = null;
		try {
			URL url = new URL(uri);
			URLConnection uc = url.openConnection();
			InputStream in = uc.getInputStream();
			int len = uc.getContentLength();
			byte buf[] = new byte[len];
			in.read(buf, 0, buf.length);
			str = new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 길이체크
	 * 
	 * @param min
	 * @param max
	 * @param str
	 * @return
	 */
	public static boolean chkLength(int min, int max, String str) {
		int len = str.length();
		return len < min || len > max;
	}

	/**
	 * 연속된 값 체크
	 * 
	 * @param str
	 * @param cmpCnt
	 * @return
	 */
	public static boolean chkSer(String str, int cmpCnt) {
		ByteBuffer bf = ByteBuffer.wrap(str.getBytes());
		// 연속된 개수
		int serCnt = 1;
		// 첫번째 문자코드
		int curr = bf.get();
		// 에러 여부
		boolean flag = false;

		for (int i = bf.position(), last = bf.capacity(); i < last; i += 1) {
			// 임시 변수
			int tmp = bf.get();
			// 첫번째 문자와 현재 문자의 차이가 1이면
			if (Math.abs(curr - tmp) == 1) {
				// 연속 카운트 1증가
				serCnt += 1;
			} else {
				// 카운트 리셋
				serCnt = 1;
			}
			// 숫자 변경
			curr = tmp;
			// 연속횟수가 설정한 값과 같다면 종료
			/*
			 * if ( (flag = serCnt == cmpCnt) ) { break; }
			 */
			if (serCnt == cmpCnt) {
				return flag;
			}
		}
		return flag;
	}

	/**
	 * 반복값 체크
	 * 
	 * @param str
	 * @param cmpCnt
	 * @return
	 */
	public static boolean chkRpt(String str, int cmpCnt) {
		ByteBuffer bf = ByteBuffer.wrap(str.getBytes());
		// 반복된 개수
		int rptCnt = 1;
		// 첫번째 문자코드
		int curr = bf.get();
		// 에러 여부
		boolean flag = false;

		for (int i = bf.position(), last = bf.capacity(); i < last; i += 1) {
			// 임시 변수
			int tmp = bf.get();
			// 첫번째 문자와 현재 문자의 차이가 1이면
			if (curr == tmp) {
				// 연속 카운트 1증가
				rptCnt += 1;
			} else {
				// 카운트 리셋
				rptCnt = 1;
			}
			// 숫자 변경
			curr = tmp;
			// 연속횟수가 설정한 값과 같다면 종료
			// $ANALYSIS-IGNORE
			if ((flag = rptCnt == cmpCnt)) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 지정한 정보가 문자열에 지정한 개수 이상 포함되었는지 체크
	 * 
	 * @param cnt
	 * @param info
	 * @param str
	 * @return
	 */
	public static boolean chkInfo(int cnt, String info, String str) {
		// 에러 여부
		boolean flag = false;

		if (null == info) {
			return flag;
		} else {
			for (int i = 0, length = info.length() - cnt + 1; i < length; i += 1) {
				// 입력값에 정보가 3자리 이상 포함되면 에러
				/*
				 * if ( (flag = str.indexOf(info.substring(i, i + cnt)) != -1) ) { break; }
				 */
				if (str.indexOf(info.substring(i, i + cnt)) != -1) {
					return flag;
				}
			}
		}
		return flag;
	}

	/**
	 * 지정한 Byte로 문자열을 자르고 지정한 말줄임 문자를 붙인다.
	 * 
	 * @param raw
	 * @param len
	 * @param encoding
	 * @param prefix
	 * @return
	 */
	public static String stringByteCut(String raw, int len, String encoding, String prefix) {
		if (raw == null)
			return null;
		String[] ary = null;
		String result = null;
		try {
			// raw 의 byte
			byte[] rawBytes = raw.getBytes(encoding);
			int rawLength = rawBytes.length;

			int index = 0;
			int minus_byte_num = 0;
			int offset = 0;

			int hangul_byte_num = encoding.equals("UTF-8") ? 3 : 2;

			if (rawLength > len) {
				int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
				ary = new String[aryLength];

				for (int i = 0; i < aryLength; i++) {
					minus_byte_num = 0;
					offset = len;
					if (index + offset > rawBytes.length) {
						offset = rawBytes.length - index;
					}
					for (int j = 0; j < offset; j++) {
						if (((int) rawBytes[index + j] & 0x80) != 0) {
							minus_byte_num++;
						}
					}
					if (minus_byte_num % hangul_byte_num != 0) {
						offset -= minus_byte_num % hangul_byte_num;
					}
					ary[i] = new String(rawBytes, index, offset, encoding);
					index += offset;

				}
				result = ary[0] + prefix;
			} else {
				// ary = new String[]{raw};
				result = raw;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean NumberChk(String str) {
		str = str.trim();
		char c = ' ';

		if (str.length() == 0)
			return false;

		int loopCnt = str.length();
		for (int i = 0; i < loopCnt; i++) {
			c = str.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceString(String str) {
		String returnValue = "";
		if (str == null) {
			returnValue = "";
		} else {
			str = str.replaceAll("&", "&amp;");
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\'", "&apos;");
			str = str.replaceAll("\"", "&quot;");
//		  	str = str.replaceAll("%"  , "&#37;");
//		  	str = str.replaceAll(" "  , "&#10;");
//		  	str = str.replaceAll("\r"  , "&#10;");
//		  	str = str.replaceAll("\n"  , "&#10;");
//		  	str = str.replaceAll("\\("  , "&#40;");
//		  	str = str.replaceAll("\\)"  , "&#41;");
//		  	str = str.replaceAll("#"  , "&#35;");
			returnValue = str;

		}
		return returnValue;
	}

	public static boolean adminYn(String str) {
		return Pattern.matches("^[가-힣]*$", str);
	}

	public static String getClientIp(HttpServletRequest request) {
		String clientIp = StringUtils.nvl(request.getHeader("X-Forwarded-For"), "");
		if (clientIp == null || clientIp.length() == 0) {
			clientIp = StringUtils.nvl(request.getHeader("WL-Proxy-Client-IP"), "");
		}
		if (clientIp == null || clientIp.length() == 0) {
			clientIp = StringUtils.nvl(request.getHeader("Proxy-Client-IP"), "");
		}
		if (clientIp == null || clientIp.length() == 0) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp.trim();
	}

	public static String commaStr(Long num) {
		String commaNum = NumberFormat.getInstance(Locale.US).format(num);
		return commaNum;
	}

	public static String commaStr(int num) {
		String commaNum = NumberFormat.getInstance(Locale.US).format(num);
		return commaNum;
	}

	public static String nullTrim(String str) {
		if (str == null) {
			str = "";
		} else {
			str = str.trim();
		}
		return str;
	}

	public static boolean contains(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	



	private static final String FOLDER_SEPARATOR =  System.getProperty("file.separator");
	private static final char EXTENSION_SEPARATOR = '.';

	//---------------------------------------------------------------------
	// General convenience methods for working with Strings
	//---------------------------------------------------------------------

	/**
	 * 문자열의 길이가 > 0 인지 체크합니다.
	 *
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str 체크할 문자열
	 * @return
	 *      true : 널이아니고 길이가 > 0 이면
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}//:

	/**
	 * 문자열이 널이거나 길이가 0 이면 문자열이 빈것으로 봅니다.
	 * @param str  점검할 문자열
	 * @return
	 * 		true : 비었으면 ,
	 *      false: 비어있지 않으면
	 */
	public static boolean hasNotLength(String str) {
		return !hasLength(str);
	}//:



	/**
	 * 문자열이 텍스트를 가지고 있는지 체크합니다. 화이트스페이스는 문자로 간주
	 * 하지 않습니다.
	 * <p><pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str 체크할 문자열
	 * @return true: 널이아니고 길이가 > 0 이면
	 */
	public static boolean hasText(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}//:



	/**
	 * 문자열이 텍스트를 가지고 있지 않은지를 체크합니다. 화이트 스페이스는
	 * 문자로 간주하지 않습니다.
	 * @param str 체크할 문자열
	 * @return
	 * 		true: 문자열이 비었으면,  false: 문자열이 비어있지 않으면
	 */
	public static boolean hasNotText(String str) {
		return !hasText(str);
	}//:



	/**
	 * whitespace를 제거합니다.
	 *
	 * @param str 점검할 문자열
	 * @return
	 * 		변환된 문자열
	 *
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * 앞부분의 Whitespace를 제거합니다.
	 *
	 * @param str 점검할 문자열
	 * @return 트림된 문자열
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * 뒷부분의 whitespace를 제거합니다.
	 *
	 * @param str 체크할 문자열
	 * @return 트림된 문자열
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * 문자열 전체의 화이트스페이스를 제거합니다.
	 *
	 * @param str 체크할 문자열
	 * @return 트림된 문자열
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			}
			else {
				index++;
			}
		}
		return buf.toString();
	}


	/**
	 * 대소문자 구분하지 않고 문자열의 시작부분이 일치하는지 체크합니다.
	 * @param str 체크할 문자열
	 * @param prefix 검색할 문자열
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}


	/**
	 * 문자열의 끝이 주어진 문자열로 끝나는지 체크합니다.  대소문자 구분없습니다.
	 * @param str 체크할 문자열
	 * @param suffix 찾을 문자열
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * 주어진 문자열이 문자열에서 몇번 나오는지 셉니다.
	 * @param str 원본문자열. Return 0 if this is null.
	 * @param sub 찾을 문자열. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}//:




	/**
	 * 문자열에서 검색된 문자열을 제거합니다.
	 * @param pattern 삭제할 문자열
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}//:


	/**
	 * 문자열에서 chartsToDelete에 들어있는 문자들을 모두 제거합니다.
	 * @param charsToDelete 찾을 문자 셋트
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (inString == null || charsToDelete == null) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}


	//---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	//---------------------------------------------------------------------

	/**
	 * 문자열을 싱글쿼트(')로 감쌉니다.
	 * @param str 원본문자열
	 * @return 감싸진 문자열
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}//:



	/**
	 * 객체를 싱글쿼트로 감싼 문자열로 변환합니다. 오브젝트가 String 이어야
	 * 합니다.
	 * @param obj 원본객체(문자열이어야 작동함)
	 * @return  변환된 객체,문자열이 아니면 원본객체
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}//:

	/**
	 * 문자열의 첫글자를 대문자로 변환합니다.
	 * @param str 원본문자열
	 * @return 변환된 문자열
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}//:

	/**
	 * 문자열의 첫글자를 소문자로 변환합니다.
	 * @param str 원본문자열
	 * @return 변환된 문자열
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}//:

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}//:




	/**
	 * 전체 패스에서 파일명을 가져온다.
	 * e.g. "mypath/myfile.txt" -> "myfile.txt".
	 * @param path 파일경로
	 * @return 추출된 파일명
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}

		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if(separatorIndex == -1){
		    separatorIndex = path.lastIndexOf("\\");
		}

		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}//:

	/**
	 * 파일명에서 확장자를 분리합니다.
	 * e.g. "mypath/myfile.txt" -> "txt".
	 * @param path 파일패스
	 * @return 확장자
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * 파일명에서 확장자를 제거합니다.
	 * e.g. "mypath/myfile.txt" -> "mypath/myfile".
	 * @param path 파일경로
	 * @return 제거된 파일
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}


	//---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	//---------------------------------------------------------------------




	/**
	 * 켤렉션을 문자배열로 변환합니다. null이면 null을 반환합니다.
	 * @param collection 원본
	 * @return 문자배열
	 */
	public static String[] toStringArray(Collection collection) {
		if (collection == null) {
			return null;
		}
		return (String[]) collection.toArray(new String[collection.size()]);
	}//:


	/**
	 * 구분자로 한번만 문자열을 분리합니다.
	 * @param toSplit 원본문자열
	 * @param delimiter 구분자
	 * @return 두개의 요소를 가진 문자배열
	 */
	public static String[] splitFirst(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] {beforeDelimiter, afterDelimiter};
	}//:







	public static  String[] split(String input, String delimeter) {
		Pattern p = Pattern.compile(delimeter);
		return p.split(input);
	}//:


	public static String[] split2(String s, String s1) {
		StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
		int i = stringtokenizer.countTokens();
		String as[] = new String[i];
		for (int j = 0; j < i; j++) {
			as[j] = stringtokenizer.nextToken();
			System.out.println(j + " : " + as[j]);
		}

		return as;
	}


	/**
	 * 배열을 Vector로 만든다.
	 *
	 * @param array
	 *            원본 배열
	 * @return 배열과 같은 내용을 가지는 Vector
	 */
	public static Vector toVector(Object[] array) {
		if (array == null) {
			return null;
		}

		Vector vec = new Vector(array.length);

		for (int i = 0; i < array.length; i++) {
			vec.add(i, array[i]);
		}
		return vec;
	}



	/**
	 * 문자열의 Enemration을 소팅된 배열로 반환한다.
	 *
	 * @param source
	 *            원본 열거형
	 * @return 열거형의 값을 가진 배열
	 */
	public static String[] sortStringArray(Enumeration source) {
		Vector buf = new Vector();
		while (source.hasMoreElements()) {
			buf.add(source.nextElement());
		}
		String[] buf2 = new String[buf.size()];

		for (int i = 0; i < buf.size(); i++) {

			Object obj = buf.get(i);
			if (obj instanceof String) {
				buf2[i] = (String) obj;
			} else {
				throw new IllegalArgumentException("Not String Array");
			}
		}
		java.util.Arrays.sort(buf2);
		return buf2;
	}

	/**
	 * 문자열이 입력한 길이보다 남는 공백에 좌측정렬후 원하는 문자를 삽입힌다.
	 *
	 * <pre>
	 * ex) String source = "ABC"
	 *     String result = TextUtil.insertLeftChar(source, 5, '*');
	 *     result는 "ABC**"을 가지게 된다.
	 * </pre>
	 *
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @param ch
	 *            공백에 삽입할 원하는 문자
	 * @return 결과 문자열
	 */
	public static String insertLeftChar(String source, int length, char ch) {

		StringBuffer temp = new StringBuffer(length);

		if (source.length() <= length) {

			for (int i = 0; i < (length - source.length()); i++) {
				temp.append(ch);
			}
			temp.append(source);
		}
		return temp.toString();
	}

	/**
	 * 문자열이 null이라면 ""로 바꾼다. 아니라면 그대로 리턴...
	 *
	 * @param str
	 *            변환할 문자열
	 * @return 변경된 문자열
	 */
	public static String nullToEmpty(String str) {
		return str == null ? "" : str;
	}


	/**
	 * 주어진 String 매개변수가 null이거나 널 스트링이면("") chStr을 반환하고<br>
	 * 그렇지 않으면 str을 반환한다.
	 *
	 * @param str
	 *            검사할 문자열
	 * @param chStr
	 *            null이거나 널 스트링일경우 반환할 문자열
	 * @return 변환된 문자열
	 */
	public static String toNull(String str, String chStr) {
		if (str == null || str.equals(""))
			return chStr;
		else
			return str;

	}


	/**
	 * Oracle의 NVL과 같은 기능
	 *
	 * <pre>
	 *  ex) StringUtil.nvl("", "AA");
	 *      결과 : AA
	 * </pre>
	 *
	 * @param val
	 *            원래값
	 * @param defval
	 *            null 혹은 "" 일 경우 변경할값
	 * @return 결과 문자열
	 */
	public static String nvl(String val, String defval) {
		return ((val != null && !val.equals("")) ? val : defval);
	}//:



    /**
     * 스트링의 코드형식을 "8859_1"에서 "KSC5601"로 변경
     *
     * @param str      원본문자열
     * @return
     * 		변환된 문자열
     */
    static public String toKsc5601(String str) {
        try {
            if (str == null || str.equals("")) return str;
            return new String(str.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//:



    /**
     * KSC5601을 ISO_8859_1로 변환
     * @param str 원본문자열
     * @return
     * 		변환된 문자열
     */
    static public String toLatin(String str) {
        try {
            if (str == null || str.equals("")) return str;
            return new String(str.getBytes("KSC5601"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//:





    /**
     * XML문서에서 한글을 사용하기 위해서는 "&#x + 16진수 코드값"형태로
     * 만들어야 합니다. 주어진 문자열을 XML에서 사용할 수 있는 형태로 변환합니다.
     *
     *
     * @param str 변환될 문자열
     * @return  변환된 문자열
     */
    public static String convertXmlEntity(String str) {

    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i < str.length(); i++ ) {
    		   char ch = str.charAt(i);
    		   int  code = (int)ch ;
    		   sb.append("&#x" + Integer.toHexString(code).toUpperCase() + ";" );
    	}//for

    	return sb.toString();
    }//:




    /**
     * null이면 ""을 되돌리고 아니면 원래값을 되돌림
     * 2006. 3. 3 오후 2:29:27
     *
     * @param str
     * @return
     */
    public static String nvls(String str)
    {

        if( str == null) return "";
        else return  str;

    }//:








    /**
     * oracle의 NVL과 같은 기능입니다.
     * @param val
     * @return
     */
    public static String toNull(String val) {

        if(val == null || val.equals("")) return null;
        else return val;

    }//:

    /**
     * Oracle의 DECODE와 같은 기능을 합니다.
     *
     *
     * @author 김상현
     * @date 2006.02.17
     * @param expr1
     * @param decodeString
     * @return
     */
    public static String decode(String expr1, String decodeString)
    {

        decodeString  = decodeString.trim();
        String[] strs = toArray(decodeString, ",");
        return decode(expr1, strs);

    }//:





    /**
     * 실제로 디코드를 수행 합니다.
     * decode(String, String)에서 사용하는 내부 메소드 입니다.
     * @author 김상현
     * @date 2006.02.17
     * @param expr1 디코드할 원본 문자열
     * @param exprs 디코드 기준 배열
     * @return
     */
    private static String decode(String expr1, String[] exprs) {
		int i = 0;
		boolean hasElseValue = false;
		boolean isMatch = false;
		String rv = "";
		String expr = nvls(expr1);
		String ts = "";

		// 모두 비교하고 없으면 마지막 값이 리턴값
		hasElseValue = (exprs.length % 2) == 1 ? true : false;

		for (i = 0; i < exprs.length; i++) {
			ts = exprs[i];

			if (i == 0) {
				if (expr.equals(exprs[i]))
					isMatch = true;
				continue;
			}

			if ((i % 2) == 1) { // 되돌릴값
				if (isMatch) {
					rv = exprs[i];
					break;
				}
			} else { // 비교값
				if (expr.equals(exprs[i]))
					isMatch = true;

			}
		}// for

		if (!isMatch && hasElseValue)
			rv = exprs[exprs.length - 1];

		return rv;
	}// :


    /**
	 * 문자열을 분리하여 배열로 만듭니다.
	 *
	 * @param tokenstr
	 *            원본문자열
	 * @param delemeter
	 *            구분자
	 * @return 문자의 배열
	 */
	public static String[] toArray(String tokenstr, String delemeter) {

		if (tokenstr == null)
			return new String[0]; // null일경우 오류가 발생해서 추가
		if (tokenstr.equals(""))
			return new String[0];

		StringTokenizer tok = new StringTokenizer(tokenstr, delemeter);
		String[] arr;

		List data = new java.util.ArrayList();
		while (tok.hasMoreTokens()) {
			data.add(tok.nextToken());
		}
		arr = null;
		if (data.size() > 0) {
			arr = new String[data.size()];
		}
		for (int i = 0; i < data.size(); i++) {
			String str = (String) data.get(i);
			arr[i] = str.trim();

		}// for
		return arr;
	}// :


	/**
	 * 문자열의끝에서 주어진 길이만큼 분리합니다.
	 * @param str  원본 문자열
	 * @param length 길이
	 * @return
	 * 		잘려진 문자열
	 */
    public static String right(String str, int length)
    {
        return ( str.length()  >=  length)?    str.substring(str.length() - length): str;

    }//:





    /**
     * 문자열의 중간을 가져옵니다. 인덱스는 1 부터 시작합니다.
     * @param str  원본 문자열
     * @param start 가져올 시작 위치
     * @param length 길이
     * @return
     * 		잘려진 문자열
     */
    public static String mid(String str, int start, int length)
    {


        //0123456789
        //20050618
        if(str == null)      return "";
        if(str.equals(""))   return "";
        int str_len = str.length();
        if(start < 0 ||   start > (str_len -1))  return str;
        int left = str_len - start;
        if(left > length)
        {
            //
            return str.substring(start,  start + length);
        }
        else
        {
            return str.substring(start);
        }

    }//:






    /**
     * 문자열의 시작부분을 잘라냅니다.
     * @param str 원본문자열
     * @param length 길이
     * @return
     * 		잘려진 문자열
     */
    public static String left(String str, int length)
    {
        return  (str.length() >= length? str.substring(0, length): str);
    }//:



    /**
     * 문자의 길이를 구합니다. 한글은 2를 되돌립니다.
     * @param c 문자
     * @return
     * 		문자의 길이
     */
	public static int getLength(char c) {
		byte[] b = String.valueOf(c).getBytes();
		return b.length;
	}//:


	/**
	 * 문자열의 길이를 구합니다. 한글은  크기가 2로 계산됩니다.
	 *
	 * @param str 문자열
	 * @return
	 * 		문자열의 길이
	 */
	public static int getLength(String str) {
		byte[] b = str.getBytes();
		return b.length;
	}//:

	 /**
     * 오라클의 RPAD와 같은 기능입니다. 설명이 필요없겠죠?
     * @param src      원본문자열
     * @param length   총 문자열 길이
     * @param pad      패딩할 문자
     * @return
     * @date 2009. 5. 4.
     */
     public static String lpad(String src, int length, char pad) {
		StringBuffer sb = new StringBuffer();
		if (src == null)  {
			for(int i=0; i < length;i++) {
				sb.append(pad);
			}
			return sb.toString();
		}
		int srcLength = getLength(src);
		if(srcLength > length) {
			int len = 0;
			for(int i=0; i < length; i++) {
				len  += getLength(src.charAt(i));
				if(len <= length) {
					sb.append(src.charAt(i));
				}
			}
			return lpad(sb.toString(), length, pad);
		}else if(srcLength == length) {
			return src;
		}else {
			int len = 0;
			for(int i=srcLength; i < length; i++) {
				sb.append(pad);
			}
			return sb.toString() + src;
		}
	}// :


     /**
      * 오라클의 RPAD와 같은 기능입니다. 설명이 필요없겠죠?
      * @param src      원본문자열
      * @param length   총 문자열 길이
      * @param pad      패딩할 문자
      * @return
      * @date 2009. 5. 4.
      */
     public static String rpad(String src, int length, char pad) {
		StringBuffer sb = new StringBuffer();
		if (src == null)  {
			for(int i=0; i < length;i++) {
				sb.append(pad);
			}
			return sb.toString();
		}
		int srcLength = getLength(src);
		if(srcLength > length) {
			int len = 0;
			for(int i=0; i < length; i++) {
				len  += getLength(src.charAt(i));
				if(len <= length) {
					sb.append(src.charAt(i));
				}
			}
			return rpad(sb.toString(), length, pad);
		}else if(srcLength == length) {
			return src;
		}else {
			int len = 0;
			for(int i=srcLength; i < length; i++) {
				sb.append(pad);
			}
			return   src + sb.toString();
		}
	}// :




	// 원래초성
	// 0    1   2    3    4    5    6   7    8    9    10   11   12   13   14   15   16  17   18
	// ㄱ  ㄲ   ㄴ   ㄷ   ㄸ   ㄹ   ㅁ  ㅂ   ㅃ   ㅅ   ㅆ   ㅇ   ㅈ   ㅉ   ㅊ   ㅋ   ㅌ  ㅍ   ㅎ
	static String[] mChosung = new String[]
	{
		"ㄱ", "ㄱ", "ㄴ", "ㄷ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅂ" , "ㅅ", "ㅅ", "ㅇ"
		,"ㅈ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
	};




	/**
	 * 주어진 문자열의 초성을 구한다.
	 * @param str 원본문자열
	 * @return
	 * 		초성문자열
	 */
	public static String getChosung(String str)
	{

		String strChosung = "";

		if(str.length() == 0 ) {
			//throw new RuntimeException("빈문자열입니다.");
			return "";
		}

		int x = (int)str.charAt(0);
		if(x < 0xAC00)
			// 기타등록을 위해서 Exception대신 'ETC' 를 리턴한다.
			return "ETC";
			//throw new RuntimeException("완전한 한글 문자열이 아닙니다.");


		int subcode  =  (int)str.charAt(0);
		int chosung  =  (subcode  - 0xAC00)/ (21 * 28);

		//System.Console.WriteLine("chosung index : " + chosung);

		try{
			strChosung = mChosung[chosung];
		}catch(ArrayIndexOutOfBoundsException e){
			return "ETC";
		}

		return strChosung;


	}//:


    /**
     * 인코딩별 한글깨짐 여부를 확인하기 위해 사용.
     * "euc-kr", "ksc5601", "iso-8859-1", "8859_1", "ascii", "utf-8"
     * @param src
     * @throws UnsupportedEncodingException
     */
	public static void printEncodingStyle(String src) throws UnsupportedEncodingException{

        String charset[] = {"euc-kr", "ksc5601", "iso-8859-1", "8859_1", "ascii", "utf-8"};

        for(int i=0; i<charset.length ; i++){
                for(int j=0 ; j<charset.length ; j++){
                        if(i==j){
                                continue;
                        }else{
                        	System.out.println(charset[i]+" : "+charset[j]+" :"+new String(src.getBytes(charset[i]),charset[j]));
                        }
                }
        }                        //outer for
	}//:

	/**
     * <p>자릿수 마추기 (앞자리 빈공백은 '0'으로 채움)
     * @param  charNo : 다음 코드의 숫자 자리수, suffix : 다음 코드의 숫자 부분
     * @return  String
     */
    public static String getThreeChar(int charNo, int suffix) {

        String result = "";
        String strSuffix = String.valueOf(suffix);
        StringBuffer sb = new StringBuffer();

        if (strSuffix.length() == charNo) {
            result = strSuffix ;
        }
        else {

            /* 마추고자하는 자릿수에 맞게 '0'을 삽입 */
            for (int i = 1; i <= charNo - strSuffix.length(); i++) {
                sb.append("0");
            }

            sb.append(strSuffix);
            result = sb.toString();
        }

        return result ;
    }


    /**
     * 문자열을 자리수 만큼 채우고 빈자리는 왼쪽에 공백문자열을 추가
     * @param str 문자열
     * @param length 채울 길이
     * @return
     *      포맷된 문자열
     */
    public static String format(String str, int length) {
        return StringUtils.lpad(str, length, ' ');
    }//:

	/**
	 * Null 값을 체크하여 Null 혹은 ""이면 "0" String을 아니면 값을 trim하여 Return 한다.<BR>
	 * ex) Space2Zero("1212") => "1212"<BR>
	 *     Space2Zero(null) => "0"<BR>
	 *     Space2Zero("") => "0"<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static String space2Zero(Object val) {
		if(NullCheckS(val).equals(""))
			return "0";
		return val.toString().trim();
	}

	/**
	 * @param val String
	 * @return String
	 */
	public static String emptyToSpace(Object val) {
		if(NullCheckS(val).equals(""))
			return " ";
		return val.toString().trim();
	}
	/**
	 * Null 값을 체크하여 Null이면 "" String을 아니면 값을 trim하여 Return 한다.<BR>
	 * ex) NullCheckS("1212") => "1212"<BR>
	 *     NullCheckS(null) => ""<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static String NullCheckS(String val) {
		if(isEmpty(val))
			return "";
		return val.trim();
	}
	/**
	 * Null 값을 체크하여 Null이면 "" String을 아니면 값을 trim하여 Return 한다.<BR>
	 * ex) NullCheckS("1212") => "1212"<BR>
	 *     NullCheckS(null) => ""<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static String NullCheckS(Object val) {
		if(isEmpty(val))
			return "";
		return val.toString().trim();
	}
	/**
	 * Null 값을 체크하여 Null이면 default String을 아니면 값을 trim하여 Return 한다.<BR>
	 * ex) NullCheckS("1212", "default") => "1212"<BR>
	 *     NullCheckS(null, "default") => "defalut"<BR>
	 *
	 * @param str String
	 * @param pDefault String
	 * @return int
	 */
	public static String NullCheckS(String val, String pDefault) {
		if (val == null)
			return pDefault;
		return val.trim();
	}

	/**
	 * Null 값을 체크하여 Null이면 0을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212") => 1212<BR>
	 *     NullCheck(null) => 0<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static int NullCheck(String val) {
		if (isEmpty(val))
			return 0;
		return Integer.parseInt(val);
	}

	/**
	 * Null 값을 체크하여 Null이면 0을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212") => 1212<BR>
	 *     NullCheck(null) => 0<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static int NullCheck(Object val) {
		if(isEmpty(val))
			return 0;
		return Integer.parseInt(val.toString());
	}

	/**
	 * Null 값을 체크하여 Null이면 0을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212") => 1212<BR>
	 *     NullCheck(null) => 0<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static double NullCheckD(String val) {
		if(isEmpty(val))
			return 0;
		return Double.parseDouble(val);
	}
	/**
	 * Null 값을 체크하여 Null이면 0을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212") => 1212<BR>
	 *     NullCheck(null) => 0<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static double NullCheckD(Object val) {
		if(isEmpty(val))
			return 0;
		return Double.parseDouble(val.toString());
	}
	/**
	 * Null 값을 체크하여 Null이면 0을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212") => 1212<BR>
	 *     NullCheck(null) => 0<BR>
	 *
	 * @param val String
	 * @return String
	 */
	public static long NullCheckL(Object val) {
		if(isEmpty(val))
			return 0;
		return Long.parseLong(val.toString());
	}
	/**
	 * Null 값을 체크하여 Null이면 default int을 아니면 값을 parsing하여 Return 한다.<BR>
	 * ex) NullCheck("1212", 100) => 1212<BR>
	 *     NullCheck(null, 100) => 100<BR>
	 *
	 * @param str String
	 * @param pDefault String
	 * @return int
	 */
	public static int NullCheck(String val, int pDefault) {
		if(isEmpty(val))
			return pDefault;
		return Integer.parseInt(val);
	}

	/**
	 * 문자열에 '*'로 구분되어있는 경우 '*' 연산을 한다. <BR>
	 * ex) getLimitSize("1024*1024") => 1048576<BR>
	 *
	 * @param string String
	 * @return int
	 */
	public static long getLimitSize(String string) {
		List<String> list = getToken(string, "*");
		long size = 1;
		for(int m=0; m < list.size(); m++) {
			size *= stoi(list.get(m));
		}
		return size;
	}

	/**
	 * 파일의 사이즈를 Bytes, KB, MB 단위로 변환한다.<BR>
	 * ex) getFileSize(1)			=> 1 Bytes<BR>
	 *     getFileSize(1024)		=> 1.0 KB<BR>
	 *     getFileSize(1048576)		=> 1.0 MB<BR>
	 *     getFileSize(1073741824)	=> 1.0 GB<BR>
	 *
	 * @param filesize long
	 * @return String
	 */
	public static String getFileSize(long filesize) {
		DecimalFormat df = new DecimalFormat(".##");
		String fSize="";
		if ((filesize >= 1024) && (filesize < 1024 * 1024)) {
			fSize = df.format((float)filesize/1024).toString() + " KB" ;
		} else if ((filesize >= 1024 * 1024) && (filesize < 1024 * 1024 * 1024)) {
			fSize = df.format((float)filesize/(1024*1024)).toString() + " MB" ;
		} else if ( filesize >= 1024 * 1024 * 1024 ) {
			fSize = df.format((float)filesize/(1024*1024*1024)).toString() + " GB" ;
		} else {
			fSize = Long.toString(filesize) + " Bytes" ;
		}
		return fSize;
	}

	/**
	 * String을 int값으로 변환한다.
	 * @param str String
	 * @return int
	 */
	public static int stoi(String str) {
		if (NullCheckS(str).equals("")) {
			return 0;
		}
		return (Integer.valueOf(str).intValue());
	}

	/**
	 * int를 String값으로 변환한다.
	 * @param i int
	 * @return String
	 */
	public static String itos(int i) {
		return (new Integer(i).toString());
	}

	/**
	 * String을 float값으로 변환한다.
	 * @param str String
	 * @return float
	 */
	public static float stof(String str) {
		if (NullCheckS(str).equals("")) {
			return 0;
		}
		return (Float.valueOf(str).floatValue());
	}

	/**
	 * float를 String값으로 변환한다.
	 * @param f float
	 * @return String
	 */
	public static String ftos(float f) {
		return (new Float(f).toString());
	}

	/**
	 * String을 long값으로 변환한다.
	 * @param str String
	 * @return long
	 */
	public static long stol(String str) {
		if (NullCheckS(str).equals("")) {
			return 0L;
		}
		return (Long.valueOf(str).longValue());
	}

	/**
	 * long를 String값으로 변환한다.
	 * @param i long
	 * @return String
	 */
	public static String ltos(long i) {
		return (new Long(i).toString());
	}




	/**
	 * 한글(KSC5601)을 유니코드로 변환한다.
	 * @param kscStr String
	 * @return String
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String Ksc2Uni(String kscStr)
	throws java.io.UnsupportedEncodingException {
		String str = NullCheckS(kscStr);
		if (str.equals("")) {
			return "";
		} else {
			return new String(str.getBytes("KSC5601"), "8859_1");
		}
	}

	/**
	 * 유니코드를 한글(KSC5601)로 변환한다.
	 * @param UnicodeStr String
	 * @return	String
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String Uni2Ksc(String UnicodeStr)
	throws UnsupportedEncodingException {
		String str = NullCheckS(UnicodeStr);
		if (str.equals("")) {
			return "";
		} else {
			return new String(str.getBytes("8859_1"), "KSC5601");
		}
	}


	/**
	 * Null 값을 체크하여 Null이면 default String을 아니면 값을 trim하여 Return 한다.<BR>
	 * ex) getURLDecoder("1212", "UTF-8") => "1212"<BR>
	 *
	 * @param str String
	 * @param pDefault String
	 * @return int
	 */
	public static String getURLDecoder(String val, String unicode)
	throws UnsupportedEncodingException {
			String str = "";
			if (val != null && !val.equals("")) {
				str = URLDecoder.decode(val, unicode) ;
			}
			return str.trim();

	}


	/**
	 * 입력한 스트링을 반복한다.<BR>
	 * ex) repeatString("ABC", 3) => "ABCABCABC"
	 *
	 * @param string String
	 * @param repeatCnt int
	 * @return String
	 */
	public static String repeatString(String string, int repeatCnt) {
		String s = "";
		for(int i=0;i<repeatCnt;i++){
			s = s + string;
		}
		return s;
	}




	/**
	 * 가져온 문자열에서 delim로 선택된 내용을 삭제하여 전달한다.<BR>
	 * ex) delString("Kim's house", 's') => "Kims' houe"
	 *
	 * @param str String
	 * @param delim	String
	 * @return String
	 */
	public static String delString(String str, char delim) {
		// 전처리
		str = NullCheckS(str);
		if (str.equals(""))
			return "";
		//
		StringBuffer tempStr = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).indexOf(delim) == -1) {
				tempStr.append(str.substring(i, i + 1));
			}
		}
		return tempStr.toString();
	}

	/**
	 * 가져온 문자열에서 인용부호(')를 삭제하여 전달한다.<BR>
	 * ex) delApos("Kim's house") => "Kims house"
	 *
	 * @param str String
	 * @return String
	 */
	public static String delApos(String str) {
		return delString(str, '\'');
	}

	/**
	 * 가져온 문자열에서 delim으로 선택된 내용을 두개로 만들어 전달한다.<BR>
	 * ex) appendString("Hello Kim!", '!') => "Hello Kim!!"
	 *
	 * @param str
	 * @param delim
	 * @return
	 */
	public static String appendString(String str, char delim) {
		// 전처리
		str = NullCheckS(str);
		if (str.equals(""))
			return "";
		//
		int i = 0;
		StringBuffer tempStr = null;
		while ((i = str.indexOf(delim, i)) != -1) {
			tempStr = new StringBuffer();
			tempStr.append(str.substring(0, i) + delim + str.substring(i));
			str = tempStr.toString();
			i += 2;
		}
		return str;
	}

	/**
	 * 가져온 문자열에서 "'"를  두개로 만들어 전달한다.<BR>
	 * 특히 텍스트 입력을 받아 DB에 값으로 사용하는 부분에 반드시 사용한다.(조회조건 및 데이터 값)<BR>
	 * 단, PreparedStatement나 QueryRunner를 사용할때는 필요없다.<BR>
	 *
	 * ex) appendApos("Kim's House") => "Kims''s House"
	 *
	 * @param  str		String
	 * @param  delim	String
	 * @return 	String
	 */
	public static String appendApos(String str) {
		return appendString(str,'\'');
	}

	/**
	 * 문자열을 Numeric형으로 바꿀때 "."와 0~9 외의 다른 문자가 있는 경우 제거시킨후 반환한다.<BR>
	 * ex) toNumericType("+12,200,000.1212") => "12200000.1212"
	 *
	 * @param value String
	 * @return String
	 */
	public static String toNumericType(String value) {
		String result = null;
		if(value != null && !value.trim().equals("")) {
			try {
				char[] c = new char[value.length()];
				value.getChars(0,value.length(), c, 0);
				StringBuffer sb = new StringBuffer();
				for(int i = 0 ; i < c.length ; i++) {
					if( (int)c[i] == 46 || ( (int)c[i] > 47 && (int)c[i] < 58 ) )
						sb.append(c[i]);
				}
				result = sb.toString();
			}catch(Exception e){
				result = "0";
			}
		} else {
			result = "0";
		}
		return result;
	}

	/**
	 * 정수 문자열에 ','(comma)를 pos의 배수의 위치에 넣어 문자열로 만든다.<BR>
	 * 돈같은거 표현할때 좋다.<BR>
	 * ex) getComma("1200000",3) => "1,200,000"
	 *
	 * @param org String
	 * @param pos int
	 * @return String
	 */
	public static String getComma(String org, int pos) {
		String result = "";
		String front = "";
		String rear = "";

		int len = 0;
		int rearPos = 0;

		try {
			boolean flag = false;
			org = org.trim();

			if(org.charAt(0) == '-') {
				flag = true;
				org = org.substring(1);
			}

			if(org.indexOf(".") != -1) {
				front = org.substring(0,org.indexOf("."));
				rear = org.substring(org.indexOf("."));
			} else {
				front = org;
			}

			front = toNumericType(front);

			len = front.length();
			for(int i=len-1; i >=0;i--) {
				rearPos++;
				result = front.charAt(i) + result;
				if( (rearPos % pos) == 0 && i !=0) result = "," + result;
			}

			result += rear;

			if(flag)
				result = '-' + result;
		} catch(Exception e) {
			result = org;
		}

		return result;
	}

	/**
	 * 일반 문자열을 HTML문자열로 변환한다.<BR>
	 * 소스코드같은 것을 HTML에서 보여줄때 유용하게 사용.<BR>
	 * ex) htmlEscape("<html>") => &lt;html&gt;
	 * @param str String
	 * @return String
	 */
	public static String htmlEscape(String str) {
		//	전처리
		str = NullCheckS(str);
		if (str == null || str.length() <= 0) {
			return "";
		}
		int len = str.length();
		StringBuffer sb=new StringBuffer(len);
		for( int i = 0; i < len; i++ ) {
			char c = str.charAt(i);
			if ( c == '&' ) {
				sb.append("&amp;");
			} else if (c == '"') {
				sb.append("&quot;");
			} else if ( c == '<' ) {
				sb.append("&lt;");
			} else if ( c == '>' ) {
				sb.append("&gt;");
			} else if ( c == '\'' ) {
				sb.append("&#39;");
			} else if ( c == '\n' ) {
				sb.append("<BR>");
			} else if ( c == ' ' ) {
				sb.append("&nbsp;");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 입력 자료에 패턴(%s)에 자료를 순서대로 넣는다.
	 *
	 * @param pattern String
	 * @param arguments String[]
	 * @return String
	 */
	public static String sprintf(String pattern, String[] arguments) {
		StringBuffer buf = new StringBuffer();
		int iArgIndex = 0;  // 추가하는 문자열
		int iFront = 0;  // 검색시작위치
		int k = -1; // 검색위치
		while(( k = pattern.indexOf( "%s", iFront)) >= 0) { // $s가 존재하면
			buf.append(pattern.substring(iFront, k)).append(NullCheckS(arguments[iArgIndex++]));
			iFront = k + 2;
		}
		if ( iFront < pattern.length() ) { //카피안된 데이타가 남아 있으면
			buf.append(pattern.substring(iFront));
		}
		return buf.toString();
	}

	/**
	 * 입력 자료에 패턴(%s)에 자료를 넣는다.
	 *
	 * @param pattern String
	 * @param arguments String
	 * @return String
	 */
	public static String sprintf(String pattern, String arguments) {
		StringBuffer buf = new StringBuffer();
		int iFront = 0; // 검색시작위치
		int k = -1; // 검색위치
		while(( k = pattern.indexOf( "%s", iFront)) >= 0) { // $s가 존재하면
			buf.append(pattern.substring(iFront, k)).append(NullCheckS(arguments));
			iFront = k + 2;
		}
		if ( iFront < pattern.length() ) { //카피안된 데이타가 남아 있으면
			buf.append(pattern.substring(iFront));
		}
		return buf.toString();
	}

	/**
	 * 문자열을 분리 하여 토큰으로 나누어 원하는 위치의 값을 가져온다.<BR>
	 * ex) getToken("AA,BB,CC" , ",", 2) => "BB"
	 *
	 * @param pValue String
	 * @param pGubun String
	 * @param pos int
	 * @return String
	 */
	public static String getToken(String pValue , String pGubun, int pos) {
		// 전처리
		pValue = NullCheckS(pValue);
		if (pValue.equals(""))
			return "";
		//
		int i = 0;
		int j = 0;
		int count = 0;
		String string = "";
		while ((i = pValue.indexOf(pGubun, j)) != -1) {
			string = pValue.substring(j, i);
			count ++;
			j = i+1;
			// Loop 종료
			if (pos==count) {
				break;
			} else {
				string = pValue.substring(j, pValue.length());
			}
		}
		return string;
	}

	/**
	 * 스트링 배열을 토큰으로 구분하여 하나의 문자열로 반환한다.<BR>
	 * ex) toTokenFromString({"AA","BB","CC"} , ",") => "AA,BB,CC"
	 *
	 * @param pValue String
	 * @param pGubun String
	 * @param pos int
	 * @return String
	 */
	public static String toTokenFromString(String[] pValue , String pGubun) {
		// 전처리
		if (pValue == null || pValue.length == 0) {
			return "";
		}
		if (pGubun == null || pGubun.trim().equals("")) {
			return null;
		}
		//
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < pValue.length; i++) {
			string.append(pValue[i] + pGubun);
		}
		return string.substring(0, string.length()-pGubun.length());
	}

	/**
	 * List를 토큰으로 구분하여 하나의 문자열로 반환한다.<BR>
	 * ex) toTokenFromList(list, ",") => "AA,BB,CC"<BR>
	 * getToken()과 반대
	 *
	 * @param pValue String
	 * @param pGubun String
	 * @param pos int
	 * @return String
	 */
	public static String toTokenFromList(List<Object> pValue , String pGubun) {
		// 전처리
		if (pValue == null || pValue.size() == 0) {
			return "";
		}
		if (pGubun == null || pGubun.trim().equals("")) {
			return null;
		}
		//
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < pValue.size(); i++) {
			string.append(pValue.get(i) + pGubun);
		}
		return string.substring(0, string.length()-pGubun.length());
	}

	/**
	 * Map이 들어있는 List를 토큰으로 구분하여 하나의 문자열로 반환한다.<BR>
	 * ex) toTokenFromMapList(list, "CODE", ",") => "AA,BB,CC"
	 *
	 * @param pValue String
	 * @param pGubun String
	 * @param pos int
	 * @return String
	 */
	public static String toTokenFromMapList(List<Map<String,Object>> pValue, String mapKey, String pGubun) {
		// 전처리
		if (pValue == null || pValue.size() == 0) {
			return "";
		}
		if (mapKey == null || mapKey.trim().equals("")) {
			return "";
		}
		if (pGubun == null || pGubun.trim().equals("")) {
			return null;
		}
		//
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < pValue.size(); i++) {

			string.append(pValue.get(i).get(mapKey) + pGubun);
		}
		return string.substring(0, string.length()-pGubun.length());
	}



	/**
	 * 문자열을 분리 하여 토큰으로 나누어 벡터로 리턴을 한다.<BR>
	 * ex) getToken("AA,BB,CC" , ",") => AA BB CC를 List에 담아서 반환
	 *
	 * @param pValue String
	 * @param pGubun String
	 * @return List
	 */
	public static List<String> getToken(String pValue , String pGubun) {
		List<String> list = new ArrayList<String>();
		// 전처리
		pValue = NullCheckS(pValue);
		if (pValue.equals(""))
			return list;
		//
		int i = 0;
		int j = 0;
		String string = "";
		while ((i = pValue.indexOf(pGubun, j)) != -1) {
			//
			string = pValue.substring(j, i);
			list.add(string);
			//
			j = i+1;
		}
		// 마지막에 추가입력
		string = pValue.substring(j, pValue.length());
		list.add(string);
		return list;
	}

	/**
	 * 입력변수에 대하여 체우는 작업을 한다.<BR>
	 * ex) getFillString("12","0000") => "0012"
	 *
	 * @param fillString String
	 * @param filler String
	 * @return String
	 */
	public static String getFillString(String fillString, String filler) {
		// 마지막에 제거할 스트링 수
		int cutLength = fillString.length();
		int fillLength = filler.length();
		// 체우는 변수의 길이가 입력변수의 길이 보다 짧을 경우
		// 체우는 작업을 하지않고 입력변수를 돌려준다.
		if (cutLength <= fillLength) {
			fillString = filler + fillString;
			fillString = fillString.substring(cutLength, fillString.length());
		}
		return fillString;
	}



	/**
	 * 문자열의 원하는 특정문자열 혹은 문자를 특정문자열 혹은 문자로 대치한다.<BR>
	 * ( JDK 1.4 부터는 지원 )
	 * @param org 바꾸고자 하는 문자열
	 * @param srch 찾을 문자열
	 * @param replace 대치할 문자열
	 * @return 대치된 문자열을 반환한다.
	 */
	public static String replace(String string, String oldString, String newString) {
		if (string == null) {
			return "";
		}
		if (oldString == null || oldString.length() <= 0) {
			return string;
		}
		if (newString == null) {
			newString = "";
		}
		// 변경할 내용이 없으면 return
		if(string == null || oldString == null)
			return "";
		//
		String result = "";
		int i = 0;
		// Loop 변환
		do {
			i = string.indexOf(oldString);
			if(i != -1) {
				result += string.substring(0, i);
				result += newString;
				string = string.substring(i + oldString.length());
			} else {
				result += string;
				break;
			}
		} while(i != -1);
		// return
		return result;
	}

	/**
	 * 쿼리에 들어가는 ?를 변수명으로 바꾼다.
	 * @param preparedSQL
	 * @param params
	 * @return
	 */
	public static String getSQLLog(String preparedSQL, Object[] params) {
		StringBuilder sql = new StringBuilder(preparedSQL);
		for (int i = 0; i < params.length; i++) {
			int idx = sql.indexOf("?");
			if (idx >= 0) {
				if (params[i] == null) {
					sql.replace(idx, idx + 1, "NULL");
				} else if (params[i] instanceof String) {
					sql.replace(idx, idx + 1, "'" + params[i] + "'");
				} else {
					sql.replace(idx, idx + 1, params[i].toString());
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 지정된 사이즈만큼 문자열을 자른후 "..."을 뒤에 붙여 리턴한다.<BR>
	 * 주로 출력물이나 게시판에서 너무 긴 문자열을 자를때 사용한다.<BR>
	 * ex) stringToPeriod("Hello HiHi oh yea~", 10) => "Hello HiHi..."
	 *
	 * @param  currStr      String (변환할 문자열)
	 * @return String
	 * @auth
	 */
	public static String stringToPeriod(String currStr, int size){
		if (currStr==null) return "";
		if (size == 0) return "";

		currStr = currStr.trim();

		if (currStr.length() <= size)
			return currStr;
		else
			return currStr.substring(0, size) + "...";
	}

	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
	public static boolean isEmpty(Object str){
		return str == null || str.toString().trim().length() == 0;
	}
	public static String trim(String str){
		if(str == null) return "";
		else return str.trim();
	}
	public static String makeString(Object obj) {
		if(obj == null) return "";
		return obj.toString();
	}

	public static String strpad(char fch, int fillnum, String str) {  // strpad(문자, 전체길이, 포함할 문자열)
		String rtnstr = "";
		// '0'인 경우 우측에 문자를 채우고,
		str = (str == null) ? "" : str;										    // 이외의 경우에는 좌측에 문자를 채워서
		rtnstr = str;															                // 특정길이의 문자열을 만든다.

		if(str.getBytes().length > fillnum) rtnstr = subString(str, 0, fillnum);

		try {

			//fillnum이 문자열 길이보다 클 경우에는 인자로 준 str에 인자로 준 0혹은 ' '을 더한다
			while (str.getBytes().length < fillnum) {
				rtnstr = (fch != ' ') ? fch + rtnstr : rtnstr + fch;
				fillnum--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtnstr;
	}

	/*
	 * 전문 을 바이트 길이만큼 잘러서 return
	 */
	public static String subString(String str, int startIndex, int length)	{
		byte[] b1 = null;
		byte[] b2 = null;

		try {
			if (str == null) return "";
			b1 = str.getBytes();
			b2 = new byte[length];
			if (length > (b1.length - startIndex)) {
				length = b1.length - startIndex;
			}
			System.arraycopy(b1, startIndex, b2, 0, length);
		}catch (Exception e){
			e.printStackTrace();
		}
		return new String(b2);
	}

	/**
	 * "abc,ccc"의 문자열을 'abc', 'ccc' 로 변환하여 where의 In 에 사용한다.
	 */
	public static String makeInStatement(String data, String spliter)	{

		if(data == null) return "";

		String levels[] =data.split(spliter);

		String str = "";

		for (int i = 0; i < levels.length; i++) {
			str += "'"+ levels[i] + "',";
		}

		if(!str.equals("")) str = str.substring(0,str.length()-1);

		return str;
	}

	public static String maskIpAddress(String ipAddress){

		String spliter = ".";
		String ipArr[] = ipAddress.split("["+spliter+"]");
		if(ipArr.length != 4) return ipAddress;

		ipArr[2] = "XXX";
		String returnStr = "";
		for (int i = 0; i < ipArr.length; i++) {
			returnStr += ipArr[i] + spliter;
		}

		return returnStr.substring(0,returnStr.length()-1);
	}

	public static Double NaNtoZero(Object obj) {
		return Double.isNaN(NullCheckD(obj))||Double.isInfinite(NullCheckD(obj)) ? 0 : NullCheckD(obj);
	}

	public static String propertyStringConverter(String str) throws Exception{
		return new String(str.getBytes("8859_1"),"KSC5601");
	}

	public static String paramMapToStringWithDelimeter(Map<String, Object> map, String delimeter) {
		Set<String> set = map.keySet();
		String params = delimeter;
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			params += key+"="+map.get(key)+delimeter;
		}

		return params.substring(0, params.length()-delimeter.length());
	}

	public static double calculateStrings(boolean isAdd, String...strings) {

		double result = 0;
		for (int i = 0; i < strings.length; i++) {
			if(isAdd) {
				result += NullCheck(strings[i]);
			}else {
				if(i == 0) result = NullCheck(strings[i]);
				else result -= NullCheck(strings[i]);
			}
		}

		return result;
	}

	public static double calculateDouble(boolean isAdd, Object...objs) {

		double result = 0;
		for (int i = 0; i < objs.length; i++) {
			if(isAdd) {
				result += NullCheckD(objs[i]);
			}else {
				if(i == 0) result = NullCheckD(objs[i]);
				else result -= NullCheckD(objs[i]);
			}
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(maskIpAddress("192.168.10.52"));
	}


}///@.@
