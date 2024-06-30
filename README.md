# ProgressButton
Show button with progress bar

#### Library size is : 15Kb

### Screenshots
<p align="left">
    <img src="https://raw.githubusercontent.com/appsfeature/progress-button/master/screenshot/preview.gif" alt="Preview 1" width="250" />
</p>

## Setup

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
#### Dependency
[![](https://jitpack.io/v/appsfeature/progress-button.svg)](https://jitpack.io/#appsfeature/progress-button)
```gradle
dependencies {
    implementation 'com.github.appsfeature:progress-button:1.11'
}
```
 
### Usage
In your <b>activity_main.xml</b> class: 
```xml 
      <include layout="@layout/button_progress"/>
```

In your <b>activity</b> class:
#### Initialization method
```java 
    btnAction = ProgressButton.newInstance(this, getActivityRootView())
                  .setText("Send Request") 
                  .setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) { 
                      }
                  });
```

#### Update progress bar methods
```java
      //use this method to initiate progress bar while starting task in background
      btnAction.startProgress();

      // call this method when getting success response from background task
      btnAction.revertSuccessProgress(new ProgressButton.Listener() {
          @Override
          public void onAnimationCompleted() {
          }
      });
      // use this method when getting wrong response and revert the initial stage of button
      btnAction.revertProgress();
```
