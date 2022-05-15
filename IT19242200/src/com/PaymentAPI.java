package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Servlet implementation class ItemsAPI
 */
@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  Payment itemObj = new Payment();
  
    public PaymentAPI() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = itemObj.insertPay(
				
				request.getParameter("bill_ID"),
				request.getParameter("card_Holder"),
				request.getParameter("card_type"),
				request.getParameter("card_No"),
				request.getParameter("cvv"),
				request.getParameter("amount"));
				response.getWriter().write(output);
	}

	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			
			String queryString = scanner.hasNext() ?
			scanner.useDelimiter("\\A").next() : "";
			
			scanner.close();
			
			String[] params = queryString.split("&");
			
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		}
		catch (Exception e)
		{
		}
			return map;
		}
	
	
	//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount, paymet
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount
		String output = itemObj.updatePay(
				paras.get("payID").toString(),
				paras.get("bill_ID").toString(),
				paras.get("card_Holder").toString(),
				paras.get("card_type").toString(),
				paras.get("card_No").toString(),
				paras.get("cvv").toString(),
				paras.get("amount").toString());

		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		
		String output = itemObj.deletePay(paras.get("payID").toString());
		
		response.getWriter().write(output);
	}

}

