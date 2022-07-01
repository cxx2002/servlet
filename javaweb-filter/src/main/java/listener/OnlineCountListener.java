package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//统计网站在线人数：统计Session
public class OnlineCountListener implements HttpSessionListener {
    @Override
    //创建Session监听
    //一旦创建Session就会触发一次这个事件
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //因为Session的作用域在浏览器，所以我们要获取更高的作用域来统计在线人数
        ServletContext ctx = httpSessionEvent.getSession().getServletContext();
        Integer onlineCount = (Integer) ctx.getAttribute("OnlineCount");

        System.out.println(httpSessionEvent.getSession().getId());
        if(onlineCount==null)
            onlineCount=new Integer(1);
        else {
            int count=onlineCount.intValue();
            onlineCount= new Integer(count+1);
        }
        ctx.setAttribute("OnlineCount",onlineCount);
    }

    @Override
    //销毁Session监听     一旦销毁Session就会触发一次这个事件
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext ctx = httpSessionEvent.getSession().getServletContext();
        Integer onlineCount = (Integer) ctx.getAttribute("OnlineCount");

        if(onlineCount==null)
            onlineCount=new Integer(0);
        else {
            int count=onlineCount.intValue();
            onlineCount= new Integer(count-1);
        }
        ctx.setAttribute("OnlineCount",onlineCount);
    }
}
