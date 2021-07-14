# swvl_assessment
Swvl Assessment Test

#  A Simple Movies App Using Latest Android Development techniques
Movies Application in which user can able to see the movies along with their Title, Year and rating. 
Movies List is searchable and the search result will be categorized by **Year**
Each Search result category will hold at the most movies of this category by Year.


Libraries Used:

Libraries     | Purpose
------------- | -------------
Google Material Design | To make the UI Design
Android JetPack Navigation Component | I am following Single Activity Approach for that i have choose Navigation Component
Coroutines  | Asynchronous calls to database/network
KOIN DI | I have used KOIN as a Dependency Injection to ijnect ViewModel, Network and Repository
Coil | Image Loading Kotlin base library
accomponist-Glide | Loading images from network in compose
Databinding | Connecting data to xml and inflating layouts 
ViewBinding | Use third party library for view binding to access the xml component
retrofit | making network request/ logging network requests 
room | offline caching/ saving liked movies
Gson | parsing json
Timber | Timber library for logs the url and error.

<h1> About Application</h1>
<li> Create Room for storing the movies after reading the huge Json file and dump into local dp.</li>
<li> Reason to dump the json into db is because i dont need to read file again and again whenever i load application.</li>
<li> After dumping the date i am checking the Count Of rows to not save data in db again.</li>


