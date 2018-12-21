# ProgressButton
Show button with progress bar
  
## Setup
[![](https://jitpack.io/v/Droidhelios/ProgressButton.svg)](https://jitpack.io/#Droidhelios/ProgressButton)

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Add this to your module build.gradle

```gradle
   dependencies {
        implementation 'com.github.Droidhelios:ProgressButton:1.0' 
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
                  .setBackground(R.drawable.bg_button_disable)
                  .setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          executeTask();
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
