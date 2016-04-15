# OnTop Notification API
## Intro
The idea behind OnTop has to been to make it easy for others like myself who have developed a number of apps to stay on top whats happening with or within their apps. I'm sure there are other services that enable this but I wanted very a simple app/api combo that could be easily added with one line of code without bulky SDKs to send you push notifications to yourself for anything you can think of. Right now I'm using it to get notified of exceptions, user actions, and app usage. But you can use it for whatever else on whichever platform or app type. Also **the api and app are completely free to use!**

The way it works is you need to download the [OnTop Notifications App][playstorelink] on Play Store and add your app. You'll receive an app ID and SECRET KEY to use with the api.

The API has only one end point to send push notifications to yourself
```
GET http://ontop.tech/api/push
```
I also plan to provide class files for Android, PHP, and other languages soon to make things easier. If anyone whats to convert `OnTop.java` to any other language, just send it to me and I'll add it in.

## Api docs
### GET / POST Methods
Here's the full info on the GET endpoint. Remember that there's a 1000 character limit on GET calls. Aside from `id` and `key` params everything else is optional
```
GET http://ontop.tech/api/push?
    id          <app id>        required
    key         <app secret>    required
    message     <Any text>      Message that will primarily be shown on the notification (250 char max)
    category    <category tag>  Add a category to represent the entities/categories. i.e. "user", "song"
    action      <action tag>    Add a action tag to represent the action performed. i.e. "like", "add", "share"
    view        <View tag>      Add a view tag to represent the view/page that the event has occurred. i.e. "login", "add item"
    noti_vibrate<1 or 0>        Whether or not to vibrate on notification received for this notification (Default 1)
    noti_sound  <1 or 0>        Whether or not to play sound on notification received for this notification (Default 0)
    custom      <jSon string>   Encoded JSON String of any vars that you'd like to send with event (i.e. {"user_id":342})
```
In case your call is longer than 1000 characters or you prefer using POST, you can also send to the same end point but add `is_post=1`
```
POST    http://ontop.tech/api/push?is_post=1
        ... all the same var names for POST params
```

### Java (Android)
To make things easier I've added a class called `OnTop.java` for use in Android applictions that should make things easier Feel free to edit it as you need.
Just one dependency, you need [LoopJ](http://loopj.com/android-async-http/)'s Asynchronous Http client for Android or you can modify `OnTop.send()` to use your own method.
Add the line below to your app.gradle
```
compile 'com.loopj.android:android-async-http:1.4.9'
```
The class is easy enough to use. You can also read the docs for each method in the `OnTop.java` file to get more details.
```java
OnTop ontop = new OnTop(YOUR_APP_ID, YOUR_APP_SECRET);
ontop.setMessage("This is a test for appID: " + YOUR_APP_ID);
ontop.send();

//or just
new OnTop(YOUR_APP_ID, YOUR_APP_SECRET).setMessage("This is a test for appID: " + YOUR_APP_ID).send();
```
You can also set any of the following options to help you manage your events
##### Setting Category
```Java
/**
 * Add a category to represent the entities/categories. (i.e. "user", "song")
 * Can contain only alphabet, underscore, and numbers with max 64 characters.
 */
ontop.setCategory(String);
```
##### Setting action
```Java
/**
 * Add a action tag to represent the action performed. (i.e. "like", "add", "share")
 * Can contain only alphabet, underscore, and numbers with max 64 characters.
 */
ontop.setAction(String);
```
##### Setting View tag
```Java
/**
 * Add a view tag to represent the view/page that the event has occurred. (i.e. "login", "new_item")
 * Can contain only alphabet, underscore, and numbers with max 64 characters.
 */
ontop.setView(String);
```
##### Setting Custom fields
Just keep in mind that if you plan on using long strings or lots of data for custom, to use either use `send()` method or if you're doing it manually, to use the POST end method.
```Java
/**
 * Add a custom field to hold any meta data for future reference.
 * KEY      should be a string such as "user_id", "user_name", etc
 * VALUE    can be of type String, int, boolean, long, or float
 */
ontop.setCustom(KEY, VALUE);
```
##### Other Adjustments
```Java
/** If set to false the notification you receive wont vibrate your phone (it's true by default) */
ontop.setNotificationSound(boolean);
/** If set to true the notification you receive will beep your phone (its falseby default cause it gets annoying!) */
ontop.setNotificationVibrate(boolean);
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
**poya@gizmolabs.ca**


[playstorelink](https://play.google.com/store/apps/details?id=com.poya.ontop)
