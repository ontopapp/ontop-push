# Notification API
## Intro
The idea behind OnTop has to been to make it for others like myself who have developed a number of apps to stay on top whats happening with or within their software. I'm sure there are other services that enable this but I wanted a simple app/api combo that could be easily added with one line of code to send you push updates for anything you can think of like exceptions, user actions, app use or whatever else on whatever platform or app type. Also the api and app are completely free to use!

The way it works is you download the app (OnTop Notifications) currently on Play Store and add and app. You'll receive an app ID nd SECRET to use with the api.

The API has only one end point to receive push notifications
```
GET http://ontop.tech/api/push
```
I also plan to provide class files for Android, PHP, and other languages soon to make things easier.

## Api docs
### GET / POST
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
Just one dependency, you need LoopJ's Asynchronous Http client for Android or you can modify `OnTop#send()` to use your own system.
Add the line below to your app.gradle
```
compile 'com.loopj.android:android-async-http:1.4.9'
```
