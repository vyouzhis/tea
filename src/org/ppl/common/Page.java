package org.ppl.common;

public class Page {

	public String s_page(String url, Integer total, Integer page, Integer limit, String para) {
		if(para.length()>0) para = '&'+para;
		Integer ceil = (int)Math.ceil(total/limit);
		Integer st=0;
		Integer ceil_limit=0;
		String spage, npage, ppage;
		
		if(ceil > 1)  ceil_limit=5;
	   
	    if(ceil >1 && page>=5){
	        ceil_limit=10;
	    }
	    if(page>=8){
	        st = (ceil-page) >10? (page-5):(ceil-10);
	        ceil_limit += st-1;
	        if(ceil - page <8){
	            st = page-2;
	            ceil_limit = ceil;
	        }
	        if(ceil - page ==10){
	            st = page-2;
	            ceil_limit =st+9;
	        }
	    }else {
	        st=1;
	    }
	    if(ceil < ceil_limit) ceil_limit=ceil;
	    spage = "";
	    for (;st<=ceil_limit;st++){
	        if (page == st || (page==0 && page==st-1)) {
	            if(page==0) page=1;
	            spage +="<b class='here_num' style='margin:5px;padding:0px;font-size:16px;'>[$page]</b>";
	        }else
	            
	             spage += "<a href='"+url+"?pag="+st+para+"' style='margin:5px;padding:0px;font-size:16px;'>[$st]</a>";
	            
	    }

	    if(page>=ceil){
	        npage = "[已到尾页]";
	    }else {
	    	
         npage = "<a href='"+url+"?pag="+(page+1)+para+"' style='margin:5px;padding:0px;font-size:16px;'>[下一页]</a>";
         npage += "<a href='"+url+"?pag="+ceil+para+"' style='margin:5px;padding:0px;font-size:16px;'>[到尾页]</a>";
	        
	    }
	   
	      ppage = (page>7) ? "<a href='"+url+"?"+para+"&pag=1&tol="+total+"' style='margin:5px;padding:0px;font-size:16px;'>[回首页]</a>" : "";
	   
	    return ceil>1?ppage+spage+npage:spage;
		
	}
}
