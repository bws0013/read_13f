## read_13f

This a java program I built to retrieve info from companies related to their holdings as listed on their [13f filings](https://www.investopedia.com/terms/f/form-13f.asp). These are essentially the long positions of holdings of investment companies. The filings are reported to the sec within around 45 days of the end of the quarter. So the filings of an investment firm for the 4th quarter of 2017 will be reported around February 15th 2018.

##### Reminder: This program is meant for learning purposes, I do not and cannot recommend using it to make investment decisions.

Also keep in mind you will probably need at least java 7 to run this program.

If you do not care about the inner workings of the program you can clone/download the repo and follow the instructions in the folder called Just_Do_It_Already. Everything after this sentence is just me rambling about the program and its development and has nothing to do with running it.

That said, I built a program to test the hypothesis that the combined portfolios of several different profitable firms could be used to outperform the index. This is a remake of that program that, this is much easier to use. That I am releasing anything should tell you whether or not my tests yielded success in proving my hypothesis.


#### Simplified definitions for the sake of explanation
- 13f - A form submitted to the sec which details certain holdings of investment companies over a certain size
- cik - Central index key, a unique identifier for each firm who submits 13f and other filings to the sec
- conformation period - End date of reporting period of filing
- cusip - A unique identifier for a security


#### Questions you may have

What does the program do?
- The program retrieves and processes 13f filings by scraping them from the sec website.

How do I run it?
- See the top where I direct you to the Just_Do_It_Already directory.

What is the legality of scraping this data?
- According to the sec website, specifically
https://www.sec.gov/edgar/searchedgar/accessing-edgar-data.htm
(under Fair Access) with reference to this page:
https://www.sec.gov/privacy.htm#security
we should be good to use this data so long as we attempt to minimize their server load and
do not send more than 10 requests per second. The maximum number of requests one could send with
my program is 2 (and likely it actually only sends 1), so this program should be allowed. If I receive any indication that my program should not be used I will let everyone know and private the project.
- Thanks reddit user mapleloafs for asking about this!

Why is such a program necessary and why is this program not shorter?
- I enjoy coding and investing, this combines those 2 hobbies. It is long because there are several different formats these filings can appear in. I originally built a program to parse each type separately but it was too cumbersome and will never see the light of day.

Why does it take so long to run?
- Short answer, the program has a built in delay to prevent the SEC from feeling that I am abusing their services.
- Long answer, the program runs serially (ie not in parallel) so each filing takes a little while to download and then there is the built in delay. I believe a half second is a reasonable amount of time, if you think it should be higher/lower feel free to justify yourself in an issue. All in all it takes around 1 second per filing to get for me. Given that these filings are released on a quarterly basis it seems odd why someone would need it to be significantly faster than it is.

What can you use this for?
- I use it to compare different portfolios of companies. You can use it for something similar if you would like?

What is the difference between a 13F-HR and a 13F-HR/A? Which does this program use?
- When companies submit their filings they may have more info that they want to add later. This new information is made into what is called a 13F-HR/A.
- My program only uses 13F-HR as I was only running it after the initial release of 13F filings every quarter, so if a company submitted amended ones later I had already made my investment decisions.
- I am in the process of making a way to incorporate the amended filings as at the moment they are ignored.

What is the difference between a cusip and an excel_cusip?
- Short answer, I enjoy uniformity
- Long answer, when you open a csv in excel it makes assumptions about your data. It will treat cusips as numbers, which is fine as the starting 0s do not really matter, but I have other programs that use this data and part of how I test valid cusips involves needing the whole number.

Will this program get me to the moon?
- I have found that certain combinations of companies generally perform better than others. That said most of my money is in index funds and the results I found on here might have something to do with that.

Your maven is messed up...
- Not a question, but a fact. Im going to be honest, I am not good with maven so if you see any corrections that could/should be made let me know and I will see about correcting it.

#### Contribution

I guess if you would like to contribute you can just fork the repo and make a pull request. I generally only use github for personal projects and bitbucket for work so I am not as familiar with the interface on here.

#### Development related stuff

If you have any questions/comments/concerns feel free to create a github issue.

If you find a format that my program is unable to process please provide the link to the filing on the sec website and I will get right on adding it.

I have added some documentation if you want to see what the code is actually doing at the code level.

*****

- Things I am doing to improve the code
  - Basic documentation of the code (done as of 11/13/17)
  - Generate csv file output (done as of 11/13/17)
  - Menu options to do everything from terminal (done as of around 11/20/17)
  - Create the db programmatically (done as of around 11/20/17)
  - Better documentation of process (done as of 11/21/17)
  - Remove unnecessary stuff (always in progress)
  - Webpage abstraction of program (maybe when I learn to web)
  - Web Scraper to retrieve the selected 13f filing for a particular cik (done as of around 11/20/17)
  - Release to the internet (done as of 11/21/17)
