# OnTop Notification API
## Intro
#### Summary
In summary, this is an api/app combo to send notifications to your phone using a GET or POST call. It's designed to keep you in the loop of whats happening while you're away from your computer.
Can be used for:
- Getting notified of new content/actions on your app that require review
- Reporting server crashes, bugs, or situations requiring immediate attention.
- Getting push from an internet connected device such as arduino or any IOT device (your 3d printer sending you a push that the print is done, etc.)
- Simple app analytics and engagement studies for new apps

#### Details
The idea behind OnTop has to been to make it easy for others like myself who have developed a number of apps to stay on top of what's happening with or within their apps. I'm sure there are other services that enable this but I wanted very a simple app/api combo that could be easily added with one line of code without bulky SDKs to send you push notifications to yourself. Right now I'm using it to get notified of exceptions, user actions, and app usage. But you can use it for whatever else on whichever platform or app type. 

<img src="https://ontop.tech/img/Screenshot4.jpg" width="200" /><img src="https://ontop.tech/img/Screenshot3.jpg" width="200" /><img src="https://ontop.tech/img/Screenshot1.jpg" width="200" /><img src="https://ontop.tech/img/Screenshot5.jpg" width="200" />


The way it works is you need to download the [OnTop Notifications App][playstorelink] on Play Store and add your app. You'll receive an app ID and SECRET KEY to use with the api.
The API has only one end point to send push notifications to yourself
```
GET https://ontop.tech/api/push
```
I also plan to provide class files for Android, PHP, and other languages soon to make the API easier to implement. If anyone wants to convert `OnTop.php` to any other language, just send it to me and I'll add it in.

## Api docs
### GET / POST Methods
Here's the full info on the GET endpoint. Aside from `id` and `key` params everything else is optional. **Also you can use the category/action combo to organize your notifications in groups** and manage their behaviours separately.
```
GET https://ontop.tech/api/push
    id          <app id>        required
    key         <app secret>    required
    message     <Any text>      Message that will primarily be shown on the notification (250 char max)
    category    <category tag>  Add a category to represent the entities/categories. i.e. "user", "song"
    action      <action tag>    Add a action tag to represent the action performed. i.e. "like", "add", "share"
    noti_action_url <url>       If you supply a url here, it'll show up as a clickable btn at the bottom of the notification
    custom      <jSon string>   Encoded JSON String of any vars that you'd like to send with event (i.e. {"user_id":342})
```
In case you prefer using POST, you can also send to the same end point but add `is_post=1` to the url.
```
POST    https://ontop.tech/api/push
            ... all the same parameters as GET
```

### Java / Android (OnTop.java)
To make things easier I've added a class called `OnTop.java` for use in Android applictions. Feel free to edit it as you need.
There's just one dependency tho, you need [LoopJ](http://loopj.com/android-async-http/)'s Asynchronous Http client for Android or you can modify `OnTop.send()` function to use your own method.
For the LoopJ plugin, add the line below to your app.gradle
```
compile 'com.loopj.android:android-async-http:1.4.9'
```
The class is easy enough to use. You can also read the docs for each method in the `OnTop.java` file to get more details.
```java
OnTop ontop = new OnTop(YOUR_APP_ID, YOUR_APP_SECRET);
ontop.setMessage("This is a test");
ontop.send();
//or just
new OnTop(YOUR_APP_ID, YOUR_APP_SECRET).setMessage("This is a test").send();
```
##### Options
You can also set any of the following options to help you manage your events
```Java
// Add a category to represent the entities/categories. (i.e. "user", "song")
// Can contain only alphabet, underscore, and numbers with max 64 characters.
ontop.setCategory(String);
// Add a action tag to represent the action performed. (i.e. "like", "add", "share")
// Can contain only alphabet, underscore, and numbers with max 64 characters.
ontop.setAction(String);
// Add a custom field to hold any meta data for future reference.
// KEY      should be a string such as "user_id", "user_name", etc
// VALUE    can be of type String, int, boolean, long, or float
ontop.setCustom(KEY, VALUE);
// Will add a clickable action button at the bottom of the notification  that'll take you to this url.
// URL      a string url (i.e. http://google.com)
ontop.setNotificationAction(URL);
```

### PHP API (OnTop.php)
The PHP functions are identical to the Java version so refer to Java docs for the descriptions. Except, the `send()` function uses a `file_get_contents` method so it doesn't need a secondary library.

Example:
```php
$ontop = new OnTop(YOU_APP_API, YOUR_APP_SECRET);
$ontop->setMessage("This is a test notification!");
$ontop->send();
```
Here's the sumary of availabe optional functions:
```php
$ontop->setMessage("Text");                         // set notification msg
$ontop->setCategory("user");                        // (optional) set event category
$ontop->setAction("follow");                        // (optional) set event action
$ontop->setCustom("user_id", 324);                  // (optional) set a custom key-value pair
$ontop->setNotificationAction("http://test.com");   // (optional) set a URL as an action button on the notification
$ontop->getCompiledUrl();                           // returns a compiled GET url that can be used in the browser
$ontop->send();                                     // makes the call using POST method
```

### JS Use Example using jQuery
```js
jQuery.ajax({
    type: "GET", 
    url: "https://ontop.tech/api/push?api_ver=1", 
    data: { 
        'id' : <app id>, 
        'key' : <secret key>, 
        'message' : <Your message> 
    }, 
    async: true, 
    cache: true, 
    success: function(response) { } 
});
```

### To Do
Here are some additions I'm thinking of, feel free to suggest more
* Multiple users per app (so a team can stay on top of whats happening)
* More comprehensive event search/filtering on app side
* Basic app stats
* A seperate messaging api to help connect your users to you so can give them live assistance.
* Live api tester and link generator on the app

## Contact
This is a fairly new API so if there's anything that you think would be cool to add or if there's any issues please definitely let me know!

**https://ontop.tech**

**poya@gizmolabs.ca** 

**[http://poya-r.com](http://poya-r.com)**


[playstorelink]: https://play.google.com/store/apps/details?id=com.poya.ontop
