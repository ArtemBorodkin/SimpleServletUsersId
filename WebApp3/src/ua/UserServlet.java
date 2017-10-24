package ua;

import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> users = new ConcurrentHashMap<>();
	private AtomicInteger counter;
	
   
	@Override
	public void init() throws ServletException {
		super.init();
		users.put(1, "Bogdan");
		users.put(2, "Ivan");
		users.put(3, "Sebastian");
		counter = new AtomicInteger(3);
	}
	
	private void printMap(PrintWriter out){
		for(Entry<Integer, String> entry : users.entrySet()){
			out.println("----------------------------------<br>");
			out.println("| "+entry.getKey()+ " "+ entry.getValue()+"<br>");	
		}
			out.println("----------------------------------<br>");
	}
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String testDigits = request.getParameter("id");
		boolean numbers = false;
		if(testDigits.matches("-?\\d+(\\.\\d+)?")){
			numbers = true;
		}
		System.out.println(numbers);
		if(numbers){
		Integer id = Integer.parseInt(request.getParameter("id"));
		String user = users.get(id);
		System.out.println(users.get(id));
		if(user==null){
			user="";}
			System.out.println(user);
			out.println("<h3>user: " + user + "</h3><br>");
			printMap(out);
			out.println("<a href='http://localhost:8080/WebApp3/user.html'>Back</a>");
			out.close();
		
		}else{
			out.println("<h3>Please enter only integer numbers</h3>");
			out.println("<h3>Users:</h3>");
			printMap(out);
			out.println("<a href='javascript:history.back();'>Back</a>");
			out.close();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		Integer id = null;
		if(!users.containsValue(name)){
			id = counter.incrementAndGet();
			users.put(id,name);
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(id==null){
			out.println("<h3>user " + name + "is already exists</h3>");
		} else{
			out.println("<h3>created user " + name + " with id=" +counter.get()+"</h3>");
		}
		out.print("<br>");
		out.println("<a href='http://localhost:8080/WebApp3/user.html'>Main page</a>");
		out.close();
	}

}
