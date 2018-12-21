# ProgressButton
Show button with progress bar
  
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
[![](https://jitpack.io/v/org.bitbucket.droidhelios/progressbuttonx.svg)](https://jitpack.io/#org.bitbucket.droidhelios/progressbuttonx)
```gradle
dependencies {
    implementation 'org.bitbucket.droidhelios:ProgressButtonX:x.y'
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
