

## Project Info: 

* Extracts informations from web pages about premier league matches.
* The web page used in this project is NOT a pattern and have some mistakes, so is just an 
  example of how to implement an scala project and how to make an intensive use of jsoup libraries.
* Main program: **com.ldg.sport.stats.Main**
* Core of parser: **com.ldg.sport.stats.ParserHTML**

#### Test Info:

There are some recomendations for testing cases in:

**ParserHTMLTest**


How to run our project:

*cd proyect_root_directory*

*sbt* 

inside sbt console: 

*> compile* 

*> run [export-filename]*

And wait for: 
[success] XXXXXX indicating that everything was fine, if something was WRONG a Runtime exception will appear 
in this console.

Notes: 
export-filename: csv file name to export in proyect_root_directory. If you ignore file name,a default value 
is assigned: csvFile 

Thanks a lot to:

https://jsoup.org/


http://mojitoverde.blogspot.com
