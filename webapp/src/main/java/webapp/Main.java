package webapp;

import static spark.Spark.*;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        get("/hello", (req, res) -> 
        "<!DOCTYPE html>"+
        "<html>"+
        "<head>"+
        	"<title>Knowledge base</title>"+
        	"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>"+
        "</head>"+
        "<body>"+
        	"<nav class='navbar navbar-default'>"+
          "<div class='container-fluid'>"+
            "<div class='navbar-header'>"+
              "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1' aria-expanded='false'>"+
                "<span class='sr-only'>Toggle navigation</span>"+
                "<span class='icon-bar'></span>"+
                "<span class='icon-bar'></span>"+
                "<span class='icon-bar'></span>"+
              "</button>"+
              "<a class='navbar-brand' href='index.html'>Knowledge base</a>"+
            "</div>"+
            "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"+
              "<ul class='nav navbar-nav'>"+
               "<li class='active'><a href='#'>Search <span class='sr-onl'>(current)</span></a></li>"+
                "<li><a href='taggning.html'>Texttagging</a></li>"+ 
              "</ul>"+
              "<ul class='nav navbar-nav navbar-right'>"+
                "<li><a href=''#'>Settings</a></li>"+
              "</ul>"+
            "</div>"+
          "</div>"+
        	"</nav>"+
        	"<div class='container'>"+
        		"<form action='hello' class='form-horizontal' method='POST'>"+
        	  	 "<div class='form-group form-group-lg'>"+
        	    	"<div class='col-sm-10'>"+
        	      	 "<input class='form-control' type='text' id='search' name='search' placeholder='Enter search term'>"+
        	        "</div>"+
        	     "</div>"+
        	    "</form>"+
        	    "<div class='text-center'>"+
        	  		"<button type='button' class='btn btn-primary btn-lg'>Search</button>"+
        	  	"</div>"+
          	"</div>"+
        "</body>"+
        "</html>"
        
        );
        post("/hello", (request, response) -> {
            String search = "";
            Carrier carrier = null;
            try {
            	
                search = request.queryParams("search");
               
                Querying q = new Querying();
        		String[] context = {""};
        		carrier = q.querying(search, context,"Person");
        		
            } catch (Exception e) {}
            return carrier;
        });
    }
}