package PensionGoods;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class pensionList {

	    public static void main(String[] args) throws IOException, ParseException {
	    	//http://finlife.fss.or.kr/finlifeapi/annuitySavingProductsSearch.json?auth={발급받은 인증키}&topFinGrpNo=060000&pageNo=1
	    	String apiUrl = "http://finlife.fss.or.kr/finlifeapi/annuitySavingProductsSearch.json"; //apiUrl
	        String auth = "e61e1249ab4274d879a749aa49708547"; 
	      
	        String topFinGrpNo = "060000";//권역코드
	        
	        int[]pageNumbers = new int[61];
	        for(int i = 0; i < pageNumbers.length; i++){ 
	            //배열의 인덱스는 0부터 시작하므로, 0부터 배열의 길이보다 하나 작을때까지 반복하면 배열의 크기만큼 반복할 수 있다.
	        	//1부터 61까지의 숫자 넣
	        	pageNumbers[i] = i + 1;  
	        }
	        //page로 나타내려면 문자열화 해야 하므로toString으로 변환 
	        String pageN = Arrays.toString(pageNumbers);
	        System.out.println(pageN);
	        String[] ary = pageN.split(", ");
	        System.out.println(ary);

	        for (int i=1 ; i<(ary.length-1) ; i++) {
	        //페이지번호(문자열 배열에 들어가 있는 '1'~'61'까지)
	        	
	    
//	    	String baseDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //조회하고싶은 날짜(서버날짜로 받아오기)
//	    	String baseTime =  new SimpleDateFormat("HHmm").format(Calendar.getInstance().getTime());//조회하고싶은 날짜(서버시간으로 받아오기)

	    		    	
	        StringBuilder urlBuilder = new StringBuilder("http://finlife.fss.or.kr/finlifeapi/annuitySavingProductsSearch.json"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("auth","UTF-8") + "=" + auth); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("topFinGrpNo","UTF-8") + "=" + URLEncoder.encode(topFinGrpNo, "UTF-8")); /*권역코*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(ary[i], "UTF-8")); /*페이지번호*/
	     
	        URL url = new URL(urlBuilder.toString());
	        System.out.println(url); //url 출력
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //HttpUrlConnection 클래스 생성
	        conn.setRequestMethod("GET");//요청방식 설정 (GET)
	        conn.setRequestProperty("Content-type", "application/json");//헤더의 메소드 정의

	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	      //프로토콜 반환 코드가 200이상 300이하인 경우 스트림으로 반환 결과값 받기
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        }
	      //그 이외인 경우 에러 발생
	        else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder(); //문자열을 담기 위한 객체
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();//접속해제

	        
	        String stringJson = sb.toString(); //stringbuilder-> string으로 변환하기
	        System.out.println(stringJson);

	        // string -> jsonobject 로
	        JSONParser jsonParser = new JSONParser();// json 객체 만들기, parser통해 파싱하기
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(stringJson);// parser로 문자열 데이터를 json 데이터로 변환


	        JSONObject parse_response = (JSONObject) jsonObj.get("result"); // response key값에 맞는 Value인 JSON객체를 가져오기
	        JSONArray parse_itemlist = (JSONArray) parse_response.get("baseList");  // items로 부터 itemlist 를 받아오기  itemlist : 뒤에 [ 로 시작하므로 jsonarray
	           
	        
//	        try {
//	        	 
//	    		FileWriter file = new FileWriter("c:\\test.json");
//	    		file.write(parse_itemlist.toJSONString());
//	    		file.flush();
//	    		file.close();
//	     
//	    	} catch (IOException e) {
//	    		e.printStackTrace();
//	    	}
	        
	        //consol창에서 출력 
	        System.out.println(parse_itemlist);
	        System.out.println("* 연금상품 *");

	        for (int j = 0; j < parse_itemlist.size(); j++) {

	           System.out.println("연금상품" + j + " ===========================================");

	           // 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
	           JSONObject pensionObject = (JSONObject) parse_itemlist.get(j);
	          
	           String saleCom  = String.valueOf(pensionObject.get("sale_co"));
	           //판매사에 'NH 투자증권 있을경우 아래 해당하는 값들 모두 추출 
	           if(saleCom.contains("NH투자증권")) {
	        	   
	           // JSON name으로 추출
	           System.out.println("금융회사==>" + pensionObject.get("kor_co_nm"));
	           System.out.println("금융상품명==>" + pensionObject.get("fin_prdt_nm"));
	           System.out.println("전년도 수익률==>" + pensionObject.get("btrm_prft_rate_1"));
	           System.out.println("공시이율==>" + pensionObject.get("dcls_rate"));

	           }
	        }
	    }
	}
}


