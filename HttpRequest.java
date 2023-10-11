/**
 * HttpRequest - HTTP request container and parser
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpRequest {
    /** Help variables */
    final static String CRLF = "\r\n";
    final static int HTTP_PORT = 80;

    /** Store the request parameters */
    String method;
    String URI;
    String version;
    String headers = "";

    /** Server and port */ // IMPORTANT
    private String host;
    private int port;

    /** Create HttpRequest by reading it from the client socket */
    public HttpRequest(BufferedReader from) {
		String firstLine = "";
		try {
			firstLine = from.readLine();
		} catch (IOException e) {
			System.out.println("Error reading request line: " + e);
		}

		String[] tmp = firstLine.split(" ");

		if (tmp.length != 3) {
			System.out.println("Error: Invalid request line: " + firstLine);
			return;
		} else {
			method = tmp[0];
			URI = tmp[1];
			version = tmp[2];

		}
		/// Done

		// method = /* COMPLETE ME: extract the method from the first line */;
		// URI = /* COMPLETE ME: extract the URI from the first line */;
		// version = /* COMPLETE ME: extract the version of HTTP from the first line */;

		if (!method.equals("GET")) {
			System.out.println("Error: Method not GET");
		}
		try {
			String line = from.readLine();
			while (line.length() != 0) {
			headers += line + CRLF;

			/* We need to find host header to know which server to
			 * contact in case the request URI is not complete. */
			if(line.startsWith("Host:"))
			{
				tmp = line.split(" ");

				//Returns the value of the 

				if(tmp[1].indexOf(':')>0)
				{
					String[] tmp2 = tmp[1].split(":");
					host = tmp2[0];
					port = Integer.parseInt(tmp2[1]);
				}
				else
				{
					host = tmp[1];
					port = HTTP_PORT;
				}


			}
			/* COMPLETE ME: from the headers of the request, find the name of the server and its port number *
			

			* use Wireshark to capture an HTTP request message and inspect its headers.
			* You will see the header that contains the server name and port number should start with "Host:"
			* If the web server uses the default port number 80, that header contains only the server name in the format of "Host: servername\r\n", otherwise
			* it should contain both the server name and port number in the format of "Host: hostname:port\r\n"
			* 
			* Assign the server name to the host variable (line 22) and the port number to the port variable (line 23)
			*/

			// String statusLine = null;
			// String contentTypeLine = null;
			// String entityBody = null;
			// if (fileExists) {
			// 	statusLine = "HTTP/1.0 200 OK" + CRLF;
			// 	contentTypeLine = "Content-Type: " +
			// 			contentType(fileName) + CRLF;
			// } else {
			// 	statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			// 	contentTypeLine = "Content-Type: text/html" + CRLF;
			// 	entityBody = "<html><head><title>Not Found</title></head>"
			// 			+ "<body>404 Not Found</body></html>";
			// }

			// String server = "Host:";
			// String portNum = "Port:";
			// if (line.contains(server)){
			// 	host = line.substring(line.indexOf(server) + 5);
			// }

			// if (line.contains(portNum)){
			// 	port = Integer.parseInt(line.substring(line.indexOf(portNum) + 5));
			// }

			
			// line = from.readLine();

			//co pilot





			}
		} catch (IOException e) {
			System.out.println("Error reading from socket: " + e);
			return;
		}
    }

    /** Return host for which this request is intended */
    public String getHost() {
		return host;
    }

    /** Return port for server */
    public int getPort() {
		return port;
    }

    /**
     * Convert request into a string for easy re-sending.
     */
    public String toString() {
		String req = "";

		req = method + " " + URI + " " + version + CRLF;
		req += headers;
		/* This proxy does not support persistent connections */
		req += "Connection: close" + CRLF;
		req += CRLF;
		
		return req;
    }
}