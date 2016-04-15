# Notification API
## Intro
The idea behind OnTop has to been to make it for others like myself who have developed a number of apps to stay on top whats happening with or within their software. I'm sure there are other services that enable this but I wanted a simple app/api combo that could be easily added with one line of code to send you push updates for anything you can think of like exceptions, user actions, app use or whatever else on whatever platform or app type.

The way it works is you download the app (OnTop Notifications) currently on Play Store and add and app. You'll receive an app ID nd SECRET to use with the api.

The API has only one end point to receive push notifications
```
GET http://ontop.tech/api/send
    id          app_id
    key         app_secret
    message     Any text
```
I also plan to provide class files for Android, PHP, and other languages soon to make things easier.

## Api docs
### Java (Android)

