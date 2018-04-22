package jsoup.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//WEPAPP007
class itemList
{
  public int salesInfoCnt;
  public String salesInfo;
  public itemList(int salesInfoCnt, String salesInfo){
	  this.salesInfoCnt=salesInfoCnt;
	  this.salesInfo=salesInfo;
  }
}

public class searchCrawling {

	
	public static void main(String arg[ ] ) throws IOException
	{
	
		//Declaring Variable For Crawling
		
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> price = new ArrayList<String>();
		ArrayList<String> salesCnt = new ArrayList<String>();

		ArrayList<itemList> alItemList = new ArrayList();

		//Get Data from Cupang E-Commerce
		Document crwCupang = Jsoup.connect("http://www.coupang.com/np/search?q=gtx+1080&listSize=36&brand=&filterType=&isPriceRange=false&priceRange=&minPrice=&maxPrice=&page=1&channel=user&filterKey=118080&filter=&rating=0&sorter=saleCountDesc").get();
		Elements cupang = crwCupang.select("[class=search-product-wrap]");
		for(Element e: cupang){
		  title.add("Cupang: " +e.select("dd>div[class=name]").text().toString());
		  price.add(e.select("dd>div>div>div[class=price]>em").text().toString());
		  salesCnt.add("0");
		}


		//Get Data from TicketMonster E-Commerce
		Document crwTmon = Jsoup.connect("http://search.ticketmonster.co.kr/search?keyword=gtx1080&thr=ma").get();
		Elements tmon= crwTmon.select("li[class=deal_item]> a");
		for(Element e: tmon){
		  title.add("Tmon: "+e.select("div>div>strong[class=deal_item_title]").text().toString());
		  price.add(e.select("div>div>span[class=deal_item_price").text().toString());
		  String purCheck=e.select("div>div>span[class=deal_item_purchase]>em").text().toString();
		  if (purCheck.trim().equals(""))
		  {
		    salesCnt.add("0");
		  }
		  else{
		    salesCnt.add(purCheck);
		  }
		}

		//Get Data from WeMap 
		Document crwWeMake = Jsoup.connect("http://www.wemakeprice.com/search?search_cate=top&search_keyword=gtx+1080").get();			
		Elements weMake= crwWeMake.select("li[item_id]>span >a ");
		for(Element e: weMake){

		  title.add("WeMake: "+e.select("span>strong").text().toString());
		  price.add(e.select("span>span>span>span[class=sale]").text().toString());
		  String purCheck=e.select("span>span>strong").text().toString();
		  
		  if (purCheck.trim().equals(""))
		  {
		    salesCnt.add("0");
		  }
		  else{
		    salesCnt.add(purCheck);
		  }
		}
		//combine price and graphic card title
		for(int i =0; i<title.size();i++)
		{
			  int salesNum=Integer.parseInt(salesCnt.get(i));
			  String itemInfo=title.get(i).toString() +" || "+price.get(i).toString();
			   alItemList.add(new itemList(salesNum,itemInfo));
		}
		//Sorting Data collections
		   Collections.sort(alItemList,new Comparator<itemList>()
		   {
			   public int compare(itemList i1, itemList i2)
			   {
				   return Integer.valueOf(i2.salesInfoCnt).compareTo(i1.salesInfoCnt);
			   }
		   });
		  //Pring the Data Collections. 
		  for(int i =0; i<alItemList.size();i++)
		  {
			  System.out.println(alItemList.get(i).salesInfo);
			 // System.out.println(alItemList.get(i).salesInfoCnt);
		  }

			
	}




}
