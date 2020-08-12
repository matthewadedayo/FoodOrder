package com.Food.FoodOrder.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.validator.routines.EmailValidator;


import com.google.common.hash.Hashing;
import com.google.gson.Gson;
//import com.business.FoodOrder.dto.LoginResponse;
import com.Food.FoodOrder.dto.ServerResponse;
//import com.Food.FoodOrder.dto.SignInDto;

public class Utility {
	
	
    
    public static int generateActivationCode(){
		Random random = new Random();
		int numb = random.nextInt(900000);
		return numb;
	}
	 public static boolean isNull(Object object) {
	        return (object == null);
	    }

	    public static ArrayList<Object> validateFields(Object[] fields, Object[] names) {
	        ArrayList<Object> errors = new ArrayList<Object>();
	        int i = 0;
	        for (Object field : fields) {
	            if (field == null || field.toString()
	                .isEmpty()
	                || field.toString()
	                    .equalsIgnoreCase(" ")) {
	                errors.add(names[i]);
	            }
	            i++;
	        }
	        return errors;
	    }
	    
	    public static boolean isValidEmail(String email){
	        return EmailValidator.getInstance(true).isValid(email);
	    }
	    
	    public static boolean isValidPhone(String phone){
	        return phone.startsWith("+234") && phone.length() > 13 || phone.startsWith("070") && phone.length() > 9
	        		|| phone.startsWith("080") && phone.length() > 9 || phone.startsWith("090") && phone.length() > 9
	        		|| phone.startsWith("0") && phone.length() > 9;
	    }

	    @SuppressWarnings("unchecked")
	    public static <T> T[] arrayMerge(T[] array1, T[] array2, T[] array3) {
	        ArrayList<T> list = new ArrayList<T>();
	        list.addAll(Arrays.asList(array1));
	        list.addAll(Arrays.asList(array2));
	        list.addAll(Arrays.asList(array3));
	        T[] result = (T[]) Array.newInstance(array1.getClass()
	            .getComponentType(), list.size());
	        result = (T[]) list.toArray(result);
	        return result;
	    }

	    public static String arrayToString(ArrayList<Object> list) {
	        return list.toString()
	            .substring(1, list.toString()
	                .length() - 1);
	    }

	    public static Object[] objectArrayFromArrayList(ArrayList<Object> list) {
	        Object[] array = new Object[list.size()];
	        return list.toArray(array);
	    }

	    public static String capitalizeFirstLetter(String text) {
	        return text.substring(0, 1)
	            .toUpperCase()
	            .concat(text.substring(1, text.length()));
	    }

	    public static String today() {
	        return Calendar.getInstance()
	            .get(Calendar.YEAR) + "-"
	            + Calendar.getInstance()
	                .get(Calendar.MONTH)
	            + "-" + Calendar.getInstance()
	                .get(Calendar.DAY_OF_MONTH);
	    }

	    public static String hashPassword(Object password) {
	        return Hashing.sha512()
	            .hashString(password.toString(), Charset.defaultCharset())
	            .toString();
	    }

	    public static String toBase64(Object password) {
	        try {
	            return "Basic " + new String(Base64.getEncoder()
	                .encode(password.toString()
	                    .getBytes("utf-8")));
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static boolean comparePasswords(String newPassword, String oldHash) {
	        return hashPassword(newPassword).equals(oldHash);
	    }

	    public static Date toDate(String dateString) {
	        Date date = new Date();
	        try {
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
	            date = format.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return date;
	    }

	    public static String toDate() {
	        String date = null;
	        try {
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	            date = format.format(new Date());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return date;
	    }
	    public static String toDateAndTime() {
	        String date = null;
	        try {
	        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
	            date = format.format(new Date());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return date;
	    }

	    public int getYearFromDate(String date) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy");
	        @SuppressWarnings("deprecation")
	        Date instantDate = new Date(date);
	        return Integer.parseInt(format.format(instantDate));
	    }

	    public static Date getDateFromLocalDate(LocalDate localDate) {
	        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
	            .toInstant());
	        return date;
	    }

	    public static Date toShortDate(String dateString) {
	        Date date = new Date();
	        try {
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	            date = format.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return date;
	    }

	    public Date removeTime(Date date) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime();
	    }
	    
	    
	    /**
	     * Login request method, this method login as oAuth login token requires basic authorization set 
	     * @param urlPath
	     * @param request
	     * @param authorization
	     * @return ServerResponse 
	     */
	   /* public static ServerResponse loginHttpRequest(String urlPath, SignInDto request, String authorization) {
			try {
			
			ServerResponse response = new ServerResponse();
			
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection)
			
			url.openConnection();
	         
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", authorization);
			
			Gson gson = new Gson();
			String input  = "grant_type=" + URLEncoder.encode(request.getGrant_type()) + 
					"&username=" + URLEncoder.encode(request.getUsername()) +
					"&password=" + URLEncoder.encode(request.getPassword()) ;
			System.out.println("input: " + input);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader br = new BufferedReader(new
				
				
				InputStreamReader((conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
				
					conn.disconnect();
					
			//		System.out.println("Request data: " + output);
					LoginResponse login = gson.fromJson(output, LoginResponse.class);
					response.setData(login);
					response.setMessage("Login Successful");
			        response.setSuccess(true);
					response.setStatus(ServerResponseType.OK);
					return response;
			
				}
			
			}else if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
				
				       BufferedReader br = new BufferedReader(new
						
						
						InputStreamReader((conn.getErrorStream())));
						String output;
						while ((output = br.readLine()) != null) {
							
							System.out.println(output);
							conn.disconnect();
							response.setData("Failed to sign in");
							response.setMessage("Check your credentials and try again or contact support");
					        response.setSuccess(false);
							response.setStatus(ServerResponseType.FAILED);
							return response;
					 
						}
			}else if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				
			       BufferedReader br = new BufferedReader(new
					
					
					InputStreamReader((conn.getErrorStream())));
					String output;
					while ((output = br.readLine()) != null) {
						
						System.out.println(output);
						conn.disconnect();
						response.setData("You are not authorize ");
						response.setStatus(ServerResponseType.UNAUTHORIZED);
						return response;
				 
					}
			}
				
			} catch (MalformedURLException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			
			}
				return null;
			
		}
		
		
	    /**
	     * Convert credential to basic auth Base64 String format
	     * @param username
	     * @param password
	     * @return String
	     */
		
            /*public static String getCredentials(String username, String password) {
	        String plainClientCredentials = username + ":" + password;
	        String base64ClientCredentials = new String(org.apache.commons.codec.binary.Base64.encodeBase64(plainClientCredentials.getBytes()));

	        String authorization = "Basic " + base64ClientCredentials;
	        return authorization;
	    } */

}
